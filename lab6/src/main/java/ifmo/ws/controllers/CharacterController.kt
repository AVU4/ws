package ifmo.ws.controllers

import ifmo.ws.CharacterValidator
import ifmo.ws.entities.Character
import ifmo.ws.exceptions.MessageConstants
import ifmo.ws.exceptions.ServiceException
import ifmo.ws.services.CharacterDAO
import ifmo.ws.to.requests.CreateCharacterRequest
import ifmo.ws.to.requests.DeleteCharacterRequest
import ifmo.ws.to.requests.GetCharactersRequest
import ifmo.ws.to.requests.UpdateCharacterRequest
import ifmo.ws.to.responses.OperationResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class CharacterController {

    @Autowired
    private lateinit var characterDAO: CharacterDAO

    @PostMapping("/characters")
    fun getCharacters(@RequestBody request: GetCharactersRequest) : List<Character> {
        val args = request.args
        if (args.isEmpty() || CharacterValidator.checkTheArgsToNPE(args)) {
            return characterDAO.getCharacters(args)
        }
        throw ServiceException(MessageConstants.NULL_ARGUMENT_EXCEPTION, HttpStatus.BAD_REQUEST)
    }

    @DeleteMapping("/character")
    fun deleteCharacter(@RequestBody request: DeleteCharacterRequest) : OperationResponse {
        val id = request.id ?: throw ServiceException(MessageConstants.INCORRECT_ID, HttpStatus.NOT_FOUND)
        val result = characterDAO.deleteCharacter(id)
        return OperationResponse(null, result)
    }

    @PostMapping("/character")
    fun createCharacter(@RequestBody request: CreateCharacterRequest) : OperationResponse {
        val args = request.args
        if (!CharacterValidator.checkTheArgsToNPE(args)) {
            throw ServiceException(MessageConstants.NULL_ARGUMENT_EXCEPTION, HttpStatus.BAD_REQUEST)
        }
        if (!CharacterValidator.isFullArgs(args)) {
            throw ServiceException(MessageConstants.NOT_FULL_PARAMETERS_TO_INSERT, HttpStatus.BAD_REQUEST)
        }
        val result = characterDAO.createCharacter(args)
        return OperationResponse(result, true)
    }

    @PutMapping("/character")
    fun updateCharacter(@RequestBody request: UpdateCharacterRequest) : OperationResponse {
        val id = request.id ?: throw ServiceException(MessageConstants.INCORRECT_ID, HttpStatus.NOT_FOUND)
        val args = request.args
        if (!CharacterValidator.checkTheArgsToNPE(args)) {
            throw ServiceException(MessageConstants.NULL_ARGUMENT_EXCEPTION, HttpStatus.BAD_REQUEST)
        }

        val isNotEmptyArgs = args.isNotEmpty()
        val isCanModified = CharacterValidator.isModifiedArgs(args)

        if (isNotEmptyArgs && isCanModified) {
            val result = characterDAO.updateCharacter(args)
            return OperationResponse(null, result)
        } else if (isNotEmptyArgs) {
            throw ServiceException(MessageConstants.NOT_INCORRECT_FIELD_TO_UPDATE, HttpStatus.BAD_REQUEST)
        }
        throw ServiceException(MessageConstants.MISSED_FIELDS_TO_UPDATE, HttpStatus.BAD_REQUEST)
    }
}