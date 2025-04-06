package stsa.kotlin_htmx

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.testing.*
import org.junit.Test
import org.xml.sax.InputSource
import stsa.kotlin_htmx.models.agent.FakeAgentRepository
import stsa.kotlin_htmx.models.crate.FakeCrateRepository
import stsa.kotlin_htmx.models.key.FakeKeyRepository
import stsa.kotlin_htmx.models.skin.FakeSkinRepository
import stsa.kotlin_htmx.plugins.configureBasicAuthentication
import stsa.kotlin_htmx.plugins.configureHTTP
import stsa.kotlin_htmx.plugins.configureMonitoring
import stsa.kotlin_htmx.plugins.configureRouting
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.test.assertEquals

class DataHandlingTest {
    @Test
    fun shouldGenerateXml() = testApplication {
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

        client.get("/xml").apply {
            val dbf = DocumentBuilderFactory.newInstance()
            val db = dbf.newDocumentBuilder()
            assertEquals(
                db.parse(InputSource(StringReader(this.body<String>()))).xmlEncoding, "UTF-8")
        }
    }
}