package stsa.kotlin_htmx.external.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class AgentsResponse(
    val id: String,
    val name: String,
    val description: String,
    val team: InTeam,
    val image: String
)