package stsa.kotlin_htmx.models.agent

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlinx.serialization.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
@Serializable
data class Agent(
    val id: String,
    val name: String,
    val description: String,
    val team: String,
    val image: String
)