package stsa.kotlin_htmx.models.agent

import org.jetbrains.exposed.sql.or
import stsa.kotlin_htmx.models.suspendTransaction

class PostgresAgentRepository: AgentRepository {
    override suspend fun allAgents(): List<Agent> = suspendTransaction { AgentDAO.all().map(::daoToAgentModel) }

    override suspend fun addAgent(agent: Agent): Unit = suspendTransaction {
        AgentDAO.new(agent.id) {
            name = agent.name
            description = agent.description
            team = agent.team
            image = agent.image
        }
    }

    override suspend fun findBy(
        id: String?,
        name: String?,
        description: String?,
        team: String?,
        image: String?
    ): List<Agent> = suspendTransaction {
        AgentDAO.find {
            (AgentTable.id like "%$id%") or
            (AgentTable.name like "%$name%") or
            (AgentTable.description like "%$description%") or
            (AgentTable.team like "%$team%") or
            (AgentTable.image like "%$image%")
        }.map(::daoToAgentModel)
    }
}