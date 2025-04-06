package stsa.kotlin_htmx.models.skin

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlinx.serialization.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
@Serializable
data class Skin(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
    val team: String,
    val crates: List<String>
)