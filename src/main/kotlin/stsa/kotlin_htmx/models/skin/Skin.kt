package stsa.kotlin_htmx.models.skin

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Skin(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
    val team: String,
    val crates: List<String>
)