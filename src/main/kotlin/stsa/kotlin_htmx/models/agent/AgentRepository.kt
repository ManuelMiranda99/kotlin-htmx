package stsa.kotlin_htmx.models.agent

interface AgentRepository {
    suspend fun allAgents(): List<Agent>
    suspend fun addAgent(agent: Agent)

    suspend fun findBy(
        id: String? = null,
        name: String? = null,
        description: String? = null,
        team: String? = null,
        image: String? = null,
    ) : List<Agent>
}