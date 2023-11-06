package ifmo.ws.controllers

import ifmo.ws.entities.Character
import ifmo.ws.services.CharacterDAO
import ifmo.ws.to.GetCharactersRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CharacterController {

    @Autowired
    private lateinit var characterDAO: CharacterDAO

    @PostMapping("/characters")
    fun getCharacters(@RequestBody request: GetCharactersRequest) : List<Character> {
        val args = request.args
        return characterDAO.getCharacters(args)
    }
}