package stsa.kotlin_htmx.models.agent

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable

object AgentTable: IdTable<String>("agents") {
    override val id = varchar("id", 100).entityId().uniqueIndex()
    val name = varchar("name", 100)
    val description = varchar("description", 1000)
    val team = varchar("team", 100)
    val image = varchar("image", 1000)
}

class AgentDAO(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, AgentDAO>(AgentTable)

    var name by AgentTable.name
    var description by AgentTable.description
    var team by AgentTable.team
    var image by AgentTable.image
}

fun daoToAgentModel(dao: AgentDAO) = Agent(
    dao.id.value,
    dao.name,
    dao.description,
    dao.team,
    dao.image
)