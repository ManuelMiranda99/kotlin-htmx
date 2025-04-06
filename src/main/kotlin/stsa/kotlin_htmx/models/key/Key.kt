package stsa.kotlin_htmx.models.key

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Key(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
    val crates: List<String>
)