package stsa.kotlin_htmx.plugins

import io.ktor.server.application.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import stsa.kotlin_htmx.external.CSGOClient
import stsa.kotlin_htmx.models.agent.Agent
import stsa.kotlin_htmx.models.agent.AgentRepository
import stsa.kotlin_htmx.models.crate.CrateRepository
import stsa.kotlin_htmx.models.key.Key
import stsa.kotlin_htmx.models.key.KeyRepository
import stsa.kotlin_htmx.models.skin.Skin
import stsa.kotlin_htmx.models.skin.SkinRepository
import stsa.kotlin_htmx.util.NetworkError
import stsa.kotlin_htmx.util.Result

inline fun <T> handleResult(result: Result<T, NetworkError>, onSuccess: (T) -> Unit) {
    when (result) {
        is Result.Error -> throw IllegalStateException("Something went wrong")
        is Result.Success -> onSuccess(result.data)
    }
}

private suspend fun loadCrates(csgoClient: CSGOClient, crateRepository: CrateRepository) {
    handleResult(csgoClient.getCrates()) {crates ->
        crates.map { crate ->
            crateRepository.addCrate(crate)
        }
    }
}

private suspend fun loadAgents(csgoClient: CSGOClient, agentRepository: AgentRepository) {
    handleResult(csgoClient.getAgents()) {agents ->
        agents.map { agent ->
            agentRepository.addAgent(Agent(
                id = agent.id,
                name = agent.name,
                description = agent.description,
                team = agent.team.name,
                image = agent.image
            ))
        }
    }
}

private suspend fun loadSkins(csgoClient: CSGOClient, skinRepository: SkinRepository, crateRepository: CrateRepository) {
    handleResult(csgoClient.getSkins()) {skins ->
        skins.map { skin ->
            skinRepository.addSkin(
                Skin(
                    id =  skin.id,
                    name = skin.name,
                    description = skin.description,
                    image = skin.image,
                    team = skin.team.name,
                    crates = emptyList()
                )
            )

            skin.crates.forEach{crate ->
                crateRepository.insertCrateSkinRelation(
                    crateId = crate.id,
                    skinId = skin.id
                )
            }
        }
    }
}

private suspend fun loadKeys(csgoClient: CSGOClient, keyRepository: KeyRepository, crateRepository: CrateRepository) {
    handleResult(csgoClient.getKeys()) {keys ->
        keys.map { key ->
            keyRepository.addKey(
                Key(
                    id = key.id,
                    name = key.name,
                    description = key.description,
                    image = key.image,
                    crates = emptyList()
                )
            )

            key.crates.forEach{crate ->
                crateRepository.insertCrateKeyRelation(
                    crateId = crate.id,
                    keyId = key.id
                )
            }
        }
    }
}


suspend fun Application.configureLoadData(
    crateRepository: CrateRepository,
    agentRepository: AgentRepository,
    skinRepository: SkinRepository,
    keyRepository: KeyRepository
) {
    val csgoClient = CSGOClient()

    loadCrates(csgoClient, crateRepository)

    coroutineScope {
        val agentsJob = async { loadAgents(csgoClient, agentRepository) }
        val skinsJob = async { loadSkins(csgoClient, skinRepository, crateRepository) }
        val keysJob = async { loadKeys(csgoClient, keyRepository, crateRepository) }

        awaitAll(agentsJob, skinsJob, keysJob)
    }
}