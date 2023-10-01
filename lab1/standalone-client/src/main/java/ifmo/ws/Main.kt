package ifmo.ws

import ifmo.ws.generated.CharacterService
import ifmo.ws.generated.GetCharacters
import java.net.URL

fun main(args: Array<String>) {
    val characterWebService = CharacterService(
        URL("http://localhost:8080/CharacterService?wsdl")
//        URL("http://localhost:8080/standalone-webapp/ws/service?wsdl") this to test webapp
    )

    println("Request characters")
    characterWebService.characterWebServicePort.getCharacters(GetCharacters.Arg0())
        .forEach {character -> println(character.name) }


    val selectByName = GetCharacters.Arg0()

    val nameCriteria = GetCharacters.Arg0.Entry()
    nameCriteria.key = "name"
    nameCriteria.value = "Sub-Zero"

    selectByName.entry.add(nameCriteria)

    println("Request characters by name")
    var characters = characterWebService.characterWebServicePort.getCharacters(selectByName)
    characters.forEach { character ->  println("Got character - ${character.name} ${character.race} ${character.homeWorld} ${character.rank}") }


    val selectByRace = GetCharacters.Arg0()

    val raceCriteria = GetCharacters.Arg0.Entry()
    raceCriteria.key = "race"
    raceCriteria.value = "Human"

    selectByRace.entry.add(raceCriteria)

    println("Request characters by race")
    characters = characterWebService.characterWebServicePort.getCharacters(selectByRace)
    characters.forEach { character ->  println("Got character - ${character.name} ${character.race} ${character.homeWorld} ${character.rank}") }


    val selectByRaceAndHomeWorld = GetCharacters.Arg0()

    val homeWorldCriteria = GetCharacters.Arg0.Entry()
    homeWorldCriteria.key = "home_world"
    homeWorldCriteria.value = "Outworld"

    selectByRaceAndHomeWorld.entry.add(raceCriteria)
    selectByRaceAndHomeWorld.entry.add(homeWorldCriteria)

    println("Request characters by race and home world")
    characters = characterWebService.characterWebServicePort.getCharacters(selectByRaceAndHomeWorld)
    characters.forEach { character ->  println("Got character - ${character.name} ${character.race} ${character.homeWorld} ${character.rank}") }





}