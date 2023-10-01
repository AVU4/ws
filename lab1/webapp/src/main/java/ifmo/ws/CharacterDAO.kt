package ifmo.ws

import java.sql.ResultSet
import java.sql.SQLException
import java.util.logging.Level
import java.util.logging.Logger
import javax.naming.InitialContext
import javax.sql.DataSource

class CharacterDAO {

    companion object {
        private val initialContext = InitialContext()
        private val dataSource = initialContext.lookup("java:comp/env/jdbc/dataSource") as DataSource
    }

    private val SQL_QUERY = "SELECT * FROM Character"

    fun getCharacters(args: Map<String, Any>): List<Character> {
        val characters = ArrayList<Character>()
        if (args.isEmpty()) {
            executeEmptySQLQuery(characters)
        } else {
            executeSQLQueryWithArgs(characters, args)
        }
        return characters
    }

    private fun executeSQLQueryWithArgs(characters: ArrayList<Character>, args: Map<String, Any>) {
        val sqlBuilder = StringBuilder(SQL_QUERY)
        sqlBuilder.append(" WHERE")
        var i = 1
        for (key in args.keys) {
            if (i != args.keys.size) {
                sqlBuilder.append(" ${key}=? AND")
            } else {
                sqlBuilder.append(" ${key}=?;")
            }
            i ++
        }

        try {
            val connection = dataSource.connection
            val statement = connection?.prepareStatement(sqlBuilder.toString())
            var i = 1
            for (key in args.keys) {
                statement?.setObject(i, args[key])
                i ++
            }
            val resultSet = statement?.executeQuery()
            handleResultSet(characters, resultSet)
        } catch (e: SQLException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, null, e)
        }
    }

    private fun executeEmptySQLQuery(characters: ArrayList<Character>) {
        try {
            val connection = dataSource.connection
            val statement = connection?.createStatement()
            val resultSet = statement?.executeQuery(SQL_QUERY)
            handleResultSet(characters, resultSet)
        } catch (e: SQLException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, null, e)
        }
    }

    private fun handleResultSet(characters: ArrayList<Character>, resultSet: ResultSet?) {
        while (resultSet != null && resultSet.next()) {
            characters.add(convertResultSetToCharacter(resultSet))
        }
    }

    private fun convertResultSetToCharacter(resultSet: ResultSet): Character {
        val id = resultSet.getLong("id")
        val name = resultSet.getString("name")
        val race = resultSet.getString("race")
        val rank = resultSet.getInt("rank")
        val homeWorld = resultSet.getString("home_world")
        return Character(id, name, rank, race, homeWorld)
    }
}