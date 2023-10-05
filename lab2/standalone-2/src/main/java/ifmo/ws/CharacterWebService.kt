package ifmo.ws

import javax.jws.WebMethod
import javax.jws.WebService

@WebService(serviceName = "CharacterService")
open class CharacterWebService(private val characterDAO: CharacterDAO) {

    @WebMethod(operationName = "getCharacters")
    open fun getCharacters(args: Map<String, Any>) : List<Character> {
        return characterDAO.getCharacters(args)
    }

    @WebMethod(operationName = "createCharacter")
    open fun createCharacter(args: Map<String, Any>): Long {
        return characterDAO.createCharacter(args)
    }

    @WebMethod(operationName = "removeCharacter")
    open fun removeCharacter(id: Long): Boolean {
        return characterDAO.removeCharacter(id)
    }

    @WebMethod(operationName = "updateCharacter")
    open fun updateCharacter(id: Long, args: Map<String, Any>): Boolean {
        return characterDAO.updateCharacter(id, args)
    }
}