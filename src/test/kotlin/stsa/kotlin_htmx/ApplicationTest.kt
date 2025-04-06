package stsa.kotlin_htmx

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.testing.testApplication
import stsa.kotlin_htmx.models.agent.FakeAgentRepository
import stsa.kotlin_htmx.models.agent.PostgresAgentRepository
import stsa.kotlin_htmx.models.crate.FakeCrateRepository
import stsa.kotlin_htmx.models.crate.PostgresCrateRepository
import stsa.kotlin_htmx.models.key.FakeKeyRepository
import stsa.kotlin_htmx.models.key.PostgresKeyRepository
import stsa.kotlin_htmx.models.skin.FakeSkinRepository
import stsa.kotlin_htmx.models.skin.PostgresSkinRepository
import stsa.kotlin_htmx.plugins.configureBasicAuthentication
import stsa.kotlin_htmx.plugins.configureHTTP
import stsa.kotlin_htmx.plugins.configureMonitoring
import stsa.kotlin_htmx.plugins.configureRouting
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            val fakeCrateRepository = FakeCrateRepository()
            val fakeAgentRepository = FakeAgentRepository()
            val fakeSkinRepository = FakeSkinRepository()
            val fakeKeyRepository = FakeKeyRepository()

            configureBasicAuthentication()
            configureHTTP()
            configureMonitoring()
            configureRouting()
            install(Compression)

            configurePageRoutes(fakeCrateRepository, fakeAgentRepository, fakeSkinRepository, fakeKeyRepository)
        }
        client.get("/skins").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
        client.get("/agents").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
        client.get("/crates").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
        client.get("/keys").apply {
            assertEquals(HttpStatusCode.Unauthorized, status)
        }

        client.get("/keys"){
            headers.append(HttpHeaders.Authorization, "Basic YWRtaW46cGFzc3dvcmQ=")
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }
}
