package ifmo.ws

import ifmo.ws.generated.CharacterService
import ifmo.ws.generated.CreateCharacter
import ifmo.ws.generated.GetCharacters
import ifmo.ws.generated.UpdateCharacter
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

    var nameCriteria = GetCharacters.Arg0.Entry()
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


    val scorpion = CreateCharacter.Arg0()

    val name = CreateCharacter.Arg0.Entry()
    name.key = "name"
    name.value = "Scorpion"

    val race = CreateCharacter.Arg0.Entry()
    race.key = "race"
    race.value = "Human"

    val rank = CreateCharacter.Arg0.Entry()
    rank.key = "rank"
    rank.value = 12

    val homeWorld = CreateCharacter.Arg0.Entry()
    homeWorld.key = "home_world"
    homeWorld.value = "Earthrealm"

    scorpion.entry.add(name)
    scorpion.entry.add(race)
    scorpion.entry.add(rank)
    scorpion.entry.add(homeWorld)

    val scrorpionId = characterWebService.characterWebServicePort.createCharacter(scorpion)

    println("The id of created entity -- $scrorpionId")

    nameCriteria.value = "Scorpion"

    val scorpionUpdateFirst = UpdateCharacter.Arg1()

    var raceUpdate = UpdateCharacter.Arg1.Entry()
    raceUpdate.key = "race"
    raceUpdate.value = "Cyborg"

    scorpionUpdateFirst.entry.add(raceUpdate)
    characterWebService.characterWebServicePort.updateCharacter(scrorpionId, scorpionUpdateFirst)

    println("After first request to update")
    characters = characterWebService.characterWebServicePort.getCharacters(selectByName)
    characters.forEach { character ->  println("Got character - ${character.name} ${character.race} ${character.homeWorld} ${character.rank}") }

    val scorpionUpdateSecond = UpdateCharacter.Arg1()

    raceUpdate = UpdateCharacter.Arg1.Entry()
    raceUpdate.key = "race"
    raceUpdate.value = "Human"

    val rankUpdate = UpdateCharacter.Arg1.Entry()
    rankUpdate.key = "rank"
    rankUpdate.value = 15

    scorpionUpdateSecond.entry.add(raceUpdate)
    scorpionUpdateSecond.entry.add(rankUpdate)

    characterWebService.characterWebServicePort.updateCharacter(scrorpionId, scorpionUpdateSecond)

    println("After second request to update")
    characters = characterWebService.characterWebServicePort.getCharacters(selectByName)
    characters.forEach { character ->  println("Got character - ${character.name} ${character.race} ${character.homeWorld} ${character.rank}") }

    val isRemoved = characterWebService.characterWebServicePort.removeCharacter(scrorpionId)

    println("The entity ${if (isRemoved) "was" else "wasn't"} removed")

}