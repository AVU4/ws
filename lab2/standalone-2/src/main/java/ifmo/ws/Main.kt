package ifmo.ws

import javax.xml.ws.Endpoint

fun main(args: Array<String>) {
    val characterDAO = CharacterDAO(DatabaseConnection())
    Endpoint.publish("http://localhost:8080/CharacterService", CharacterWebService(characterDAO))
}