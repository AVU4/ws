package ifmo.ws

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.util.logging.Level
import java.util.logging.Logger

class CharacterDAO(private val databaseConnection: DatabaseConnection) {

    private val SELECT_SQL_QUERY = "SELECT * FROM Character"
    private val INSERT_SQL_QUERY = "INSERT INTO Character (name, race, rank, home_world) values (?, ?, ?, ?);"
    private val REMOVE_SQL_QUERY = "DELETE FROM Character WHERE id=?;"
    private val UPDATE_SQL_QUERY = "UPDATE Character"

    fun getCharacters(args: Map<String, Any?>): List<Character> {
        val characters = ArrayList<Character>()
        if (args.isEmpty()) {
            executeSelectSQLQuery(characters)
        } else {
            executeSelectSQLQueryWithArgs(characters, args)
        }
        return characters
    }

    fun createCharacter(args: Map<String, Any?>): Long {
        return executeInsertSQLQuery(args)
    }

    fun removeCharacter(id: Long): Boolean {
        return executeRemoveSQLQuery(id)
    }

    fun updateCharacter(id: Long, args: Map<String, Any?>): Boolean {
        return executeUpdateSQLQuery(id, args)
    }

    private fun executeUpdateSQLQuery(id: Long, args: Map<String, Any?>): Boolean {
        val sqlBuilder = StringBuilder(UPDATE_SQL_QUERY)
        sqlBuilder.append(" SET")
        var i = 1
        for (key in args.keys) {
            if (i != args.keys.size) {
                sqlBuilder.append(" ${key}=?,")
            } else {
                sqlBuilder.append(" ${key}=?")
            }
            i ++
        }
        sqlBuilder.append(" WHERE id=${id};")

        var rowsUpdated: Int? = null
        try {
            val connection = databaseConnection.getConnection()
            val statement = connection?.prepareStatement(sqlBuilder.toString())
            i = 1
            for (key in args.keys) {
                statement?.setObject(i, args[key])
                i++
            }
            rowsUpdated = statement?.executeUpdate()
        } catch (e: SQLException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, null, e)
        }
        rowsUpdated = rowsUpdated ?: 0
        if (rowsUpdated == 0) {
            throw ServiceException(MessageConstants.TRY_TO_UPDATE_NOT_EXISTED_ENTITY, CharacterServiceFault(MessageConstants.TRY_TO_UPDATE_NOT_EXISTED_ENTITY))
        }
        return true
    }

    private fun executeRemoveSQLQuery(id: Long): Boolean {
        var rowsDeleted: Int? = null
        try {
            val connection = databaseConnection.getConnection()
            val statement = connection?.prepareStatement(REMOVE_SQL_QUERY)
            statement?.setObject(1, id)
            rowsDeleted = statement?.executeUpdate()
        } catch (e: SQLException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, null, e)
            throw ServiceException(MessageConstants.SQL_EXCEPTION, CharacterServiceFault(MessageConstants.SQL_EXCEPTION))
        }
        rowsDeleted = rowsDeleted ?: 0
        if (rowsDeleted == 0) {
            throw ServiceException(MessageConstants.TRY_TO_REMOVE_NOT_EXISTED_ENTITY, CharacterServiceFault(MessageConstants.TRY_TO_REMOVE_NOT_EXISTED_ENTITY))
        }
        return true
    }

    private fun executeInsertSQLQuery(args: Map<String, Any?>): Long {
        var statement: PreparedStatement? = null
        try {
            val connection = databaseConnection.getConnection()
            statement = connection?.prepareStatement(INSERT_SQL_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)
            statement?.setObject(1, args["name"])
            statement?.setObject(2, args["race"])
            statement?.setObject(3, args["rank"])
            statement?.setObject(4, args["home_world"])
            statement?.executeUpdate()
        } catch (e: SQLException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, null, e)
            throw ServiceException(MessageConstants.SQL_EXCEPTION, CharacterServiceFault(MessageConstants.SQL_EXCEPTION))
        }

        val resultSet = statement?.generatedKeys
        return if (resultSet != null && resultSet.next()) {
            resultSet.getLong(1)
        } else {
            -1
        }
    }

    private fun executeSelectSQLQueryWithArgs(characters: ArrayList<Character>, args: Map<String, Any?>) {
        val sqlBuilder = StringBuilder(SELECT_SQL_QUERY)
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
            val connection = databaseConnection.getConnection()
            val statement = connection?.prepareStatement(sqlBuilder.toString())
            i = 1
            for (key in args.keys) {
                statement?.setObject(i, args[key])
                i++
            }
            val resultSet = statement?.executeQuery()
            handleResultSet(characters, resultSet)
        } catch (e: SQLException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, null, e)
            throw ServiceException(MessageConstants.SQL_EXCEPTION, CharacterServiceFault(MessageConstants.SQL_EXCEPTION))
        }
    }

    private fun executeSelectSQLQuery(characters: ArrayList<Character>) {
        try {
            val connection = databaseConnection.getConnection()
            val statement = connection?.createStatement()
            val resultSet = statement?.executeQuery(SELECT_SQL_QUERY)
            handleResultSet(characters, resultSet)
        } catch (e: SQLException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, null, e)
            throw ServiceException(MessageConstants.SQL_EXCEPTION, CharacterServiceFault(MessageConstants.SQL_EXCEPTION))
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