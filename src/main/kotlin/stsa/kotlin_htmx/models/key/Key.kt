package stsa.kotlin_htmx.models.key

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlinx.serialization.Serializable
import stsa.kotlin_htmx.external.dto.CratesIn

@JsonIgnoreProperties(ignoreUnknown = true)
@Serializable
data class Key(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
    val crates: List<String>
)