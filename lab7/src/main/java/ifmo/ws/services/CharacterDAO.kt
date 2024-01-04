package ifmo.ws.services

import ifmo.ws.entities.Character
import ifmo.ws.repositories.CustomRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class CharacterDAO {

    @Autowired
    private lateinit var characterRepository: CustomRepository

    fun getCharacters(args: Map<String, Any>) : List<Character> {
        return characterRepository.getCharacters(args)
    }

    fun deleteCharacter(id: Long): Boolean {
        return characterRepository.deleteCharacter(id)
    }

    fun createCharacter(args: Map<String, Any>) : Long {
        return characterRepository.createCharacter(args)
    }

    fun updateCharacter(args: Map<String, Any>) : Boolean {
        return characterRepository.updateCharacter(args)
    }
}