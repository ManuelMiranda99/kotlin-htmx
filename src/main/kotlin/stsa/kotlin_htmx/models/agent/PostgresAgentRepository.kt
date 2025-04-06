package stsa.kotlin_htmx.models.agent

import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import stsa.kotlin_htmx.models.suspendTransaction

class PostgresAgentRepository: AgentRepository {
    init {
        transaction { SchemaUtils.create(AgentTable) }
    }

    override suspend fun allAgents(): List<Agent> = suspendTransaction { AgentDAO.all().map(::daoToAgentModel) }

    override suspend fun addAgent(agent: Agent): Unit = suspendTransaction {
        AgentDAO.new(agent.id) {
            name = agent.name
            description = agent.description
            team = agent.team
            image = agent.image
        }
    }
}