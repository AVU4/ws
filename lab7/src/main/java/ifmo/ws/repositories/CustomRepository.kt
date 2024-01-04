package ifmo.ws.repositories

import ifmo.ws.entities.Character

interface CustomRepository {

    fun getCharacters(args: Map<String, Any>): List<Character>
    fun deleteCharacter(id: Long) : Boolean
    fun createCharacter(args: Map<String, Any>): Long
    fun updateCharacter(args: Map<String, Any>): Boolean
}