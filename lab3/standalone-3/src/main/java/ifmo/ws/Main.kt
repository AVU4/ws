package ifmo.ws

import com.sun.net.httpserver.BasicAuthenticator
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress
import javax.xml.ws.Endpoint

fun main(args: Array<String>) {
    val server = HttpServer.create()

    server.bind(InetSocketAddress("localhost", 8080), 0)

    val context = server.createContext("/CharacterService")
    context.setAuthenticator(object : BasicAuthenticator("realm") {
        override fun checkCredentials(p0: String?, p1: String?): Boolean {
            return "user" == p0 && "password" == p1
        }
    })

    System.setProperty("com.sun.xml.ws.fault.SOAPFaultBuilder.disableCaptureStackTrace", "false")
    val characterDAO = CharacterDAO(DatabaseConnection())
    val endpoint = Endpoint.create(CharacterWebService(characterDAO))

    endpoint.publish(context)

    server.start()
}