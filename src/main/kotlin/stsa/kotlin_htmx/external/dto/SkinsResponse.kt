package stsa.kotlin_htmx.external.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class SkinsResponse(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
    val crates: List<CratesIn>,
    val team: InTeam
)
