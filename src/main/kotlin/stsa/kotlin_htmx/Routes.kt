package stsa.kotlin_htmx

import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.util.reflect.*
import kotlinx.html.*
import stsa.kotlin_htmx.pages.*
import stsa.kotlin_htmx.link.pages.LinkMainPage
import org.slf4j.LoggerFactory
import stsa.kotlin_htmx.external.CSGOClient
import stsa.kotlin_htmx.models.agent.Agent
import stsa.kotlin_htmx.models.agent.AgentRepository
import stsa.kotlin_htmx.models.crate.Crate
import stsa.kotlin_htmx.models.crate.CrateRepository
import stsa.kotlin_htmx.models.key.Key
import stsa.kotlin_htmx.models.key.KeyRepository
import stsa.kotlin_htmx.models.skin.Skin
import stsa.kotlin_htmx.models.skin.SkinRepository
import stsa.kotlin_htmx.util.Result

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
            post {
                when (val result = csgoClient.getCrates()) {
                    is Result.Error -> call.respond(mapOf("error" to result.error.name), typeInfo<Map<String, String>>())
                    is Result.Success -> {
                        result.data.forEach {crate ->
                            crateRepository.addCrate(crate)
                        }

                        call.respond(mapOf("data" to "success"), typeInfo<Map<String, String>>())
                    }
                }
            }

            get {
                val result = crateRepository.allCrates()
                call.respond(result, typeInfo<List<Crate>>())
            }
        }

        route("/skins") {
            post {
                when (val result = csgoClient.getSkins()) {
                    is Result.Error -> call.respond(mapOf("error" to result.error.name), typeInfo<Map<String, String>>())
                    is Result.Success -> {
                        result.data.forEach{skin ->
                            skinRepository.addSkin(Skin(
                                id =  skin.id,
                                name = skin.name,
                                description = skin.description,
                                team = skin.team.name,
                                image = skin.image,
                                crates = emptyList()
                            ))

                            skin.crates.forEach{crate ->
                                crateRepository.insertCrateSkinRelation(
                                    crateId = crate.id,
                                    skinId = skin.id
                                )
                            }
                        }

                        call.respond(mapOf("data" to "success"), typeInfo<Map<String, String>>())
                    }
                }
            }

            get {
                val result = skinRepository.allSkins()
                call.respond(result, typeInfo<List<Skin>>())
            }
        }

        route("/agents") {
            post {
                when (val result = csgoClient.getAgents()) {
                    is Result.Error -> call.respond(mapOf("error" to result.error.name), typeInfo<Map<String, String>>())
                    is Result.Success -> {
                        result.data.forEach{agent ->
                            agentRepository.addAgent(Agent(
                                id = agent.id,
                                name = agent.name,
                                description = agent.description,
                                team = agent.team.name,
                                image = agent.image
                            ))
                        }

                        call.respond(mapOf("data" to "success"), typeInfo<Map<String, String>>())
                    }
                }
            }

            get {
                val result = agentRepository.allAgents()
                call.respond(result, typeInfo<List<Agent>>())
            }
        }

        route("/keys") {
            post {
                when (val result = csgoClient.getKeys()) {
                    is Result.Error -> call.respond(mapOf("error" to result.error.name), typeInfo<Map<String, String>>())
                    is Result.Success -> {
                        result.data.forEach{key ->
                            keyRepository.addKey(Key(
                                id = key.id,
                                name = key.name,
                                description = key.description,
                                image = key.image,
                                crates = emptyList()
                            ))

                            key.crates.forEach{crate ->
                                crateRepository.insertCrateKeyRelation(
                                    crateId = crate.id,
                                    keyId = key.id
                                )
                            }
                        }

                        call.respond(mapOf("data" to "success"), typeInfo<Map<String, String>>())
                    }
                }
            }

            get {
                val result = keyRepository.allKeys()
                call.respond(result, typeInfo<List<Key>>())
            }
        }
    }
}
