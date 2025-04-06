package stsa.kotlin_htmx.models.agent

class FakeAgentRepository : AgentRepository {
    private val agents = mutableListOf<Agent>()

    override suspend fun allAgents(): List<Agent> = agents

    override suspend fun addAgent(agent: Agent) {
        agents.add(agent)
    }

    override suspend fun findBy(
        id: String?, name: String?, description: String?, team: String?, image: String?
    ): List<Agent> {
        return agents.filter {
            (id == null || it.id == id) &&
            (name == null || it.name.contains(name, ignoreCase = true)) &&
            (description == null || it.description.contains(description, ignoreCase = true)) &&
            (team == null || it.team == team) && (image == null || it.image == image)
        }
    }
}
