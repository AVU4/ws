package ifmo.ws.services

import ifmo.ws.entities.Character
import ifmo.ws.repositories.CustomCharacterRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CharacterDAO {

    @Autowired
    private lateinit var characterRepository: CustomCharacterRepository

    fun getCharacters(args: Map<String, Any>) : List<Character> {
        return characterRepository.getCharacters(args)
    }
}