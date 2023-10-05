package ifmo.ws

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.logging.Level
import java.util.logging.Logger

class DatabaseConnection {

    val JDBC_URL = "jdbc:postgresql://localhost:5432/ws"
    val JDBC_USER = "student"
    val JDBC_PASSWORD = "ifmo"

    companion object {
        init {
            try {
                Class.forName("org.postgresql.Driver")
            } catch (e: ClassNotFoundException) {
                Logger.getAnonymousLogger().log(Level.SEVERE, null, e)
            }
        }
    }

    fun getConnection(): Connection? {
        var connection: Connection? = null
        try {
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)
        } catch (e: SQLException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, null, e)
        }
        return connection
    }
}