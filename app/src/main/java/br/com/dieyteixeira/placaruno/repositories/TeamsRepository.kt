package br.com.dieyteixeira.placaruno.repositories

import br.com.dieyteixeira.placaruno.database.dao.TeamDao
import br.com.dieyteixeira.placaruno.database.entities.TeamEntity
import br.com.dieyteixeira.placaruno.models.Team
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class TeamsRepository(
    private val dao: TeamDao
) {

    val teams get() = dao.findAll()

    suspend fun save(team: Team) = withContext(IO) {
        dao.save(team.toTeamEntity())
    }

    suspend fun delete(idT: String) {
        dao.delete(
            TeamEntity(idT = idT, titleT = "")
        )
    }

    suspend fun deleteP(team: Team) = withContext(IO) {
        dao.delete(team.toTeamEntity())
    }

    fun findById(idT: String) = dao.findById(idT)

}

fun Team.toTeamEntity() = TeamEntity(
    idT = idT,
    titleT = titleT,
    isDoneT = isDoneT
)

fun TeamEntity.toTeam() = Team(
    idT = this.idT,
    titleT = this.titleT,
    isDoneT = this.isDoneT
)