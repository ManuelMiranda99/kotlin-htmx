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
import stsa.kotlin_htmx.external.dto.AgentsResponse
import stsa.kotlin_htmx.external.dto.CratesResponse
import stsa.kotlin_htmx.external.dto.KeysResponse
import stsa.kotlin_htmx.external.dto.SkinsResponse
import stsa.kotlin_htmx.util.Result

private val logger = LoggerFactory.getLogger("stsa.kotlin_htmx.Routes")

fun Application.configurePageRoutes() {
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

        get("/crates") {
            when (val result = csgoClient.getCrates()) {
                is Result.Success -> call.respond(mapOf("data" to result.data), typeInfo<Map<String, List<CratesResponse>>>())
                is Result.Error -> call.respond(mapOf("error" to result.error.name), typeInfo<Map<String, String>>())
            }
        }

        get("/skins") {
            when (val result = csgoClient.getSkins()) {
                is Result.Success -> call.respond(mapOf("data" to result.data), typeInfo<Map<String, List<SkinsResponse>>>())
                is Result.Error -> call.respond(mapOf("error" to result.error.name), typeInfo<Map<String, String>>())
            }
        }

        get("/agents") {
            when (val result = csgoClient.getAgents()) {
                is Result.Success -> call.respond(mapOf("data" to result.data), typeInfo<Map<String, List<AgentsResponse>>>())
                is Result.Error -> call.respond(mapOf("error" to result.error.name), typeInfo<Map<String, String>>())
            }
        }

        get("/keys") {
            when (val result = csgoClient.getKeys()) {
                is Result.Success -> call.respond(mapOf("data" to result.data), typeInfo<Map<String, List<KeysResponse>>>())
                is Result.Error -> call.respond(mapOf("error" to result.error.name), typeInfo<Map<String, String>>())
            }
        }
    }
}
