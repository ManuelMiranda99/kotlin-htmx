package stsa.kotlin_htmx.models.crate

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlinx.serialization.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
@Serializable
data class Crate(
    val id: String,
    val name: String,
    val description: String?,
    val image: String,
    val keys: List<String> = emptyList(),
    val skins: List<String> = emptyList()
)
