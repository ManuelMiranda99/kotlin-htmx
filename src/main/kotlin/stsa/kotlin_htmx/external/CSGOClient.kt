package stsa.kotlin_htmx.external

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.util.network.*
import kotlinx.serialization.SerializationException
import stsa.kotlin_htmx.external.dto.AgentsResponse
import stsa.kotlin_htmx.external.dto.CratesResponse
import stsa.kotlin_htmx.external.dto.KeysResponse
import stsa.kotlin_htmx.external.dto.SkinsResponse
import stsa.kotlin_htmx.util.NetworkError
import stsa.kotlin_htmx.util.Result

class CSGOClient {
    private val baseUrl = "https://bymykel.github.io/CSGO-API/api/en"

    suspend fun getCrates(): Result<List<CratesResponse>, NetworkError> {
        val response = try {
            client.get(
                urlString = "$baseUrl/crates.json"
            )
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }

        if (response.status.value == 200) {
            val crates = response.body<List<CratesResponse>>()
            return Result.Success(crates)
        } else {
            return Result.Error(NetworkError.UNKNOWN)
        }
    }

    suspend fun getSkins(): Result<List<SkinsResponse>, NetworkError> {
        val response = try {
            client.get(
                urlString = "$baseUrl/skins.json"
            )
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }

        if (response.status.value == 200) {
            val skins = response.body<List<SkinsResponse>>()
            return Result.Success(skins)
        } else {
            return Result.Error(NetworkError.UNKNOWN)
        }
    }

    suspend fun getAgents(): Result<List<AgentsResponse>, NetworkError> {
        val response = try {
            client.get(
                urlString = "$baseUrl/agents.json"
            )
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }

        if (response.status.value == 200) {
            val agents = response.body<List<AgentsResponse>>()
            return Result.Success(agents)
        } else {
            return Result.Error(NetworkError.UNKNOWN)
        }
    }

    suspend fun getKeys(): Result<List<KeysResponse>, NetworkError> {
        val response = try {
            client.get(
                urlString = "$baseUrl/keys.json"
            )
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }

        if (response.status.value == 200) {
            val keys = response.body<List<KeysResponse>>()
            return Result.Success(keys)
        } else {
            return Result.Error(NetworkError.UNKNOWN)
        }
    }
}