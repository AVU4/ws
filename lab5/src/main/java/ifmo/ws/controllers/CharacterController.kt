package ifmo.ws.controllers

import ifmo.ws.entities.Character
import ifmo.ws.services.CharacterDAO
import ifmo.ws.to.requests.CreateCharacterRequest
import ifmo.ws.to.requests.DeleteCharacterRequest
import ifmo.ws.to.requests.GetCharactersRequest
import ifmo.ws.to.requests.UpdateCharacterRequest
import ifmo.ws.to.responses.OperationResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class CharacterController {

    @Autowired
    private lateinit var characterDAO: CharacterDAO

    @PostMapping("/characters")
    fun getCharacters(@RequestBody request: GetCharactersRequest) : List<Character> {
        val args = request.args
        return characterDAO.getCharacters(args)
    }

    @DeleteMapping("/character")
    fun deleteCharacter(@RequestBody request: DeleteCharacterRequest) : OperationResponse {
        val id = request.id
        val result = characterDAO.deleteCharacter(id)
        return OperationResponse(null, result)
    }

    @PostMapping("/character")
    fun createCharacter(@RequestBody request: CreateCharacterRequest) : OperationResponse {
        val args = request.args
        val result = characterDAO.createCharacter(args)
        return OperationResponse(result, true)
    }

    @PutMapping("/character")
    fun updateCharacter(@RequestBody request: UpdateCharacterRequest) : OperationResponse {
        val args = request.args
        val result = characterDAO.updateCharacter(args)
        return OperationResponse(null, result)
    }
}