package stsa.kotlin_htmx.models.crate

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlinx.serialization.Serializable
import stsa.kotlin_htmx.models.key.Key
import stsa.kotlin_htmx.models.skin.Skin

@JsonIgnoreProperties(ignoreUnknown = true)
@Serializable
data class Crate(
    val id: String,
    val name: String,
    val description: String?,
    val image: String,
    val keys: List<Key> = emptyList(),
    val skins: List<Skin> = emptyList()
)
