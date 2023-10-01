package ifmo.ws

import javax.jws.WebMethod
import javax.jws.WebService


@WebService(serviceName = "CharacterService")
open class CharacterWebService {

    private val characterDAO = CharacterDAO()

    @WebMethod(operationName = "getCharacters")
    open fun getCharacters(args: Map<String, Any>): List<Character> {
        return characterDAO.getCharacters(args)
    }

}