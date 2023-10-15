package ifmo.ws

import javax.xml.ws.Endpoint

fun main(args: Array<String>) {
    System.setProperty("com.sun.xml.ws.fault.SOAPFaultBuilder.disableCaptureStackTrace", "false")
    val characterDAO = CharacterDAO(DatabaseConnection())
    Endpoint.publish("http://localhost:8080/CharacterService", CharacterWebService(characterDAO))
}