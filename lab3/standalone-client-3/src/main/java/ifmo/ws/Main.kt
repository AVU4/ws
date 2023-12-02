package ifmo.ws

import ifmo.ws.generated.*
import java.net.Authenticator
import java.net.PasswordAuthentication
import java.net.URL
import java.util.concurrent.Executors
import javax.xml.ws.BindingProvider

fun main(args: Array<String>) {
    val THREADS_COUNT = 4
    val executors = Executors.newFixedThreadPool(THREADS_COUNT)
    for (i in 0..THREADS_COUNT) {
        executors.execute() { -> job() }
    }
    executors.shutdown()
}

fun job() {

    val authenticator = Authenticator.setDefault(object : Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication("user", "password".toCharArray())
        }
    })

    val characterService = CharacterService(
            URL("http://localhost:8080/CharacterService?wsdl")
//        URL("http://localhost:8080/standalone-webapp/ws/service?wsdl") this to test webapp
    )

    val characterWebService = characterService.characterWebServicePort

    val bindingProvider = characterWebService as BindingProvider
    bindingProvider.requestContext[BindingProvider.USERNAME_PROPERTY] = "user"
    bindingProvider.requestContext[BindingProvider.PASSWORD_PROPERTY] = "password"

    // Select all characters
    println("Request characters")
    try {
        characterWebService.getCharacters(GetCharacters.Arg0())
                .forEach {character -> println(character.name) }
    } catch (e: ServiceException_Exception) {
        println("ERROR: " + e.message)
    }


    // Select Sub-Zero
    val selectByName = GetCharacters.Arg0()

    var nameCriteria = GetCharacters.Arg0.Entry()
    nameCriteria.key = "name"
    nameCriteria.value = "Sub-Zero"

    selectByName.entry.add(nameCriteria)

    var subZeroId: Long? = null

    println("Request characters by name")
    try {
        characterWebService.getCharacters(selectByName)
                .forEach { character ->
                    run {
                        subZeroId = character.id
                        println("Got character - ${character.name} ${character.race} ${character.homeWorld} ${character.rank}")
                    }
                }
    } catch (e: ServiceException_Exception) {
        println("ERROR: " + e.message)
    }



    // Select characters by race -- Human
    val selectByRace = GetCharacters.Arg0()

    val raceCriteria = GetCharacters.Arg0.Entry()
    raceCriteria.key = "race"
    raceCriteria.value = "Human"

    selectByRace.entry.add(raceCriteria)

    println("Request characters by race")



    // Select characters by race -- Human and home_world -- Outworld
    val selectByRaceAndHomeWorld = GetCharacters.Arg0()

    val homeWorldCriteria = GetCharacters.Arg0.Entry()
    homeWorldCriteria.key = "home_world"
    homeWorldCriteria.value = "Outworld"

    selectByRaceAndHomeWorld.entry.add(raceCriteria)
    selectByRaceAndHomeWorld.entry.add(homeWorldCriteria)

    println("Request characters by race and home world")
    try {
        characterWebService.getCharacters(selectByRaceAndHomeWorld)
                .forEach { character ->  println("Got character - ${character.name} ${character.race} ${character.homeWorld} ${character.rank}") }
    } catch (e: ServiceException_Exception) {
        println("ERROR: " + e.message)
    }

    // Create the character -- Scorpion
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

    var scorpionId: Long? = null
    try {
        scorpionId = characterWebService.createCharacter(scorpion)
        println("The id of created entity -- $scorpionId")
    } catch (e: ServiceException_Exception) {
        println("ERROR: " + e.message)
    }

    // Update the race of Scorpion
    nameCriteria.value = "Scorpion"

    val scorpionUpdateFirst = UpdateCharacter.Arg1()

    var raceUpdate = UpdateCharacter.Arg1.Entry()
    raceUpdate.key = "race"
    raceUpdate.value = "Cyborg"

    scorpionUpdateFirst.entry.add(raceUpdate)
    try {
        characterWebService.updateCharacter(scorpionId, scorpionUpdateFirst)
    } catch (e: ServiceException_Exception) {
        println("ERROR: " + e.message)
    }



    // Select Scorpion with new state
    println("After first request to update")
    try {
        characterWebService.getCharacters(selectByName)
                .forEach { character ->  println("Got character - ${character.name} ${character.race} ${character.homeWorld} ${character.rank}") }
    } catch (e: ServiceException_Exception) {
        println("ERROR: " + e.message)
    }

    // Update Scorpion rank and race
    val scorpionUpdateSecond = UpdateCharacter.Arg1()

    raceUpdate = UpdateCharacter.Arg1.Entry()
    raceUpdate.key = "race"
    raceUpdate.value = "Human"

    val rankUpdate = UpdateCharacter.Arg1.Entry()
    rankUpdate.key = "rank"
    rankUpdate.value = 15

    scorpionUpdateSecond.entry.add(raceUpdate)
    scorpionUpdateSecond.entry.add(rankUpdate)

    try {
        characterWebService.updateCharacter(scorpionId, scorpionUpdateSecond)
    } catch (e: ServiceException_Exception) {
        println("ERROR: " + e.message)
    }



    // Select Scorpion with new state
    println("After second request to update")
    try {
        characterWebService.getCharacters(selectByName)
                .forEach { character ->  println("Got character - ${character.name} ${character.race} ${character.homeWorld} ${character.rank}") }
    } catch (e: ServiceException_Exception) {
        println("ERROR: " + e.message)
    }

    // Remove new character
    try {
        val isRemoved = characterWebService.removeCharacter(scorpionId)
        println("The entity ${if (isRemoved) "was" else "wasn't"} removed")
    } catch (e: ServiceException_Exception) {
        println("ERROR: " + e.message)
    }


    // Try to update not existed entity
    val notKnownCharacterUpdate = UpdateCharacter.Arg1()

    val notKnownCharacterRankUpdate = UpdateCharacter.Arg1.Entry()
    notKnownCharacterRankUpdate.key = "rank"
    notKnownCharacterRankUpdate.value = 15

    notKnownCharacterUpdate.entry.add(notKnownCharacterRankUpdate)

    try {
        characterWebService.updateCharacter(123, notKnownCharacterUpdate)
    } catch (e: ServiceException_Exception) {
        println("ERROR: " + e.message)
    }

    // Try to change not modified field
    val subZeroUpdate = UpdateCharacter.Arg1()
    val idUpdate = UpdateCharacter.Arg1.Entry()
    idUpdate.key = "id"
    idUpdate.value = 123

    subZeroUpdate.entry.add(idUpdate)

    try {
        characterWebService.updateCharacter(subZeroId, subZeroUpdate)
    } catch (e: ServiceException_Exception) {
        println("ERROR: " + e.message)
    }

    // Try to execute select with Null values
    val nullArgsSelect = GetCharacters.Arg0()

    val nullHomeWorld = GetCharacters.Arg0.Entry()
    nullHomeWorld.key = "home_world"
    nullHomeWorld.value = null

    val nullRace = GetCharacters.Arg0.Entry()
    nullRace.key = "race"
    nullRace.value = null

    nullArgsSelect.entry.add(nullHomeWorld)
    nullArgsSelect.entry.add(nullRace)

    try {
        characterWebService.getCharacters(nullArgsSelect)
    } catch (e: ServiceException_Exception) {
        println("ERROR: " + e.message)
    }

    // Try to insert entity without all fields
    val notFullInsert = CreateCharacter.Arg0()

    val notFullInsertRace = CreateCharacter.Arg0.Entry()
    notFullInsertRace.key = "race"
    notFullInsertRace.value = "Human"

    notFullInsert.entry.add(notFullInsertRace)

    try {
        characterWebService.createCharacter(notFullInsert)
    } catch (e: ServiceException_Exception) {
        println("ERROR: " + e.message)
    }
}