package stsa.kotlin_htmx.models.crate

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Crate(
    val id: String,
    val name: String,
    val description: String?,
    val image: String,
    val keys: List<String> = emptyList(),
    val skins: List<String> = emptyList()
)
