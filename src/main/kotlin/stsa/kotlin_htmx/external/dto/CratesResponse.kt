package stsa.kotlin_htmx.external.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class CratesResponse(
    val id: String,
    val name: String,
    val description: String?,
    val image: String
)

data class CratesIn(
    val id: String,
    val name: String,
    val image: String
)