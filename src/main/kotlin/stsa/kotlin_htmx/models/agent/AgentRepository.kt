package stsa.kotlin_htmx.models.agent

interface AgentRepository {
    suspend fun allAgents(): List<Agent>
    suspend fun addAgent(agent: Agent)
}