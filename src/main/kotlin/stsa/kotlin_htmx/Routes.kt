package stsa.kotlin_htmx

import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.html.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*
import stsa.kotlin_htmx.pages.*
import stsa.kotlin_htmx.link.pages.LinkMainPage
import org.slf4j.LoggerFactory
import stsa.kotlin_htmx.external.CSGOClient
import stsa.kotlin_htmx.models.agent.AgentRepository
import stsa.kotlin_htmx.models.crate.CrateRepository
import stsa.kotlin_htmx.models.key.KeyRepository
import stsa.kotlin_htmx.models.skin.SkinRepository
import stsa.kotlin_htmx.util.cacheOrGet
import stsa.kotlin_htmx.util.generateCacheKey

private val logger = LoggerFactory.getLogger("stsa.kotlin_htmx.Routes")

fun Application.configurePageRoutes(crateRepository: CrateRepository, agentRepository: AgentRepository, skinRepository: SkinRepository, keyRepository: KeyRepository) {
    val csgoClient = CSGOClient()

    install(ContentNegotiation) {
        jackson()
    }

    routing {
        get {
            call.respondHtmlTemplate(MainTemplate(template = EmptyTemplate(), "Front page")) {
                mainSectionTemplate {
                    emptyContentWrapper {
                        section {
                            p {
                                +"Startrack Demo"
                            }
                        }
                    }
                }
            }
        }

        route("/link") {
            val linkMainPage = LinkMainPage()
            get {
                linkMainPage.renderMainPage(this)
            }
        }

        route("/crates") {
            get {
                val result = cacheOrGet("crates_all", "all_crates") {
                    crateRepository.allCrates()
                }

                call.respond(result)
            }

            get("/search") {
                val id = call.request.queryParameters["id"]
                val name = call.request.queryParameters["name"]
                val description = call.request.queryParameters["description"]
                val image = call.request.queryParameters["image"]
                val keys = call.request.queryParameters["keys"]
                val skins = call.request.queryParameters["skins"]

                val cacheKey = generateCacheKey(id, name, description, image, keys, skins)

                val searchResult = cacheOrGet("crates_search", cacheKey) {
                    crateRepository.findBy(id, name, description, image, keys, skins)
                }

                call.respond(searchResult)
            }
        }

        route("/skins") {
            get {
                val result = cacheOrGet("skins_all", "all_skins") {
                    skinRepository.allSkins()
                }
                call.respond(result)
            }

            get("/search") {
                val id = call.request.queryParameters["id"]
                val name = call.request.queryParameters["name"]
                val description = call.request.queryParameters["description"]
                val image = call.request.queryParameters["image"]
                val team = call.request.queryParameters["team"]
                val crates = call.request.queryParameters["crates"]

                val cacheKey = generateCacheKey(id, name, description, image, team, crates)

                val searchResult = cacheOrGet("skins_search", cacheKey) {
                    skinRepository.findBy(id, name, description, image, team, crates)
                }

                call.respond(searchResult)
            }
        }

        route("/agents") {
            get {
                val result = cacheOrGet("agents_all", "all_agents") {
                    agentRepository.allAgents()
                }
                call.respond(result)
            }

            get("/search") {
                val id = call.request.queryParameters["id"]
                val name = call.request.queryParameters["name"]
                val description = call.request.queryParameters["description"]
                val team = call.request.queryParameters["team"]
                val image = call.request.queryParameters["image"]

                val cacheKey = generateCacheKey(id, name, description, team, image)

                val searchResult = cacheOrGet("agents_search", cacheKey) {
                    agentRepository.findBy(id, name, description, team, image)
                }

                call.respond(searchResult)
            }
        }

        authenticate("auth-basic") {
            route("/keys") {
                get {
                    val result = cacheOrGet("keys_all", "all_keys") {
                        keyRepository.allKeys()
                    }
                    call.respond(result)
                }

                get("/search") {
                    val id = call.request.queryParameters["id"]
                    val name = call.request.queryParameters["name"]
                    val description = call.request.queryParameters["description"]
                    val image = call.request.queryParameters["image"]
                    val crates = call.request.queryParameters["crates"]

                    val cacheKey = generateCacheKey(id, name, description, image, crates)

                    val searchResult = cacheOrGet("keys_search", cacheKey) {
                        keyRepository.findBy(id, name, description, image, crates)
                    }

                    call.respond(searchResult)
                }
            }
        }

        route("/xml") {
            get {
                call.respondText("OK")
            }
        }
    }
}
