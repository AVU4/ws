package ifmo.ws.repositories

import ifmo.ws.entities.Character
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class CustomCharacterRepository() : CustomRepository {

    @Autowired
    private lateinit var characterRepository: CharacterRepository

    companion object {
        val VALID_KEYS = listOf("id", "name", "rank", "race", "homeWorld")
        val KEY_TO_SPEC: Map<String, (key: String, value: Any) -> Specification<Character>> = mapOf(
                Pair("id") { key, value -> idSpecification(key, value) },
                Pair("name") { key, value -> nameSpecification(key, value) },
                Pair("rank") { key, value -> rankSpecification(key, value) },
                Pair("race") { key, value -> raceSpecification(key, value) },
                Pair("homeWorld") { key, value -> homeWorldSpecification(key, value) }
        )


        private fun idSpecification(id: String, value: Any): Specification<Character> {
            return Specification<Character> { root, query, criteriaBuilder ->
                val valueDB = root.get<Any>(id)
                criteriaBuilder.equal(valueDB, value)
            }
        }

        private fun nameSpecification(name: String, value: Any): Specification<Character> {
            return Specification<Character> { root, query, criteriaBuilder ->
                val valueDB = root.get<Any>(name)
                criteriaBuilder.equal(valueDB, value)
            }
        }

        private fun rankSpecification(rank: String, value: Any): Specification<Character> {
            return Specification<Character> { root, query, criteriaBuilder ->
                val valueDB = root.get<Any>(rank)
                criteriaBuilder.equal(valueDB, value)
            }
        }

        private fun raceSpecification(race: String, value: Any): Specification<Character> {
            return Specification<Character> { root, query, criteriaBuilder ->
                val valueDB = root.get<Any>(race)
                criteriaBuilder.equal(valueDB, value)
            }
        }

        private fun homeWorldSpecification(homeWorld: String, value: Any): Specification<Character> {
            return Specification<Character> { root, query, criteriaBuilder ->
                val valueDB = root.get<Any>(homeWorld)
                criteriaBuilder.equal(valueDB, value)
            }
        }
    }

    @Transactional(readOnly = true)
    override fun getCharacters(args: Map<String, Any>): List<Character> {
        val specifications = ArrayList<Specification<Character>>()
        for (key in args.keys) {
            if (VALID_KEYS.contains(key)) {
                val function = KEY_TO_SPEC[key]
                val value = args[key]
                if (value != null) {
                    val specification = function!!.invoke(key, value)
                    specifications.add(specification)
                }
            }
        }
        return characterRepository.findAll(Specification.allOf(specifications))
    }

    @Transactional
    override fun deleteCharacter(id: Long) : Boolean {
        val function = KEY_TO_SPEC["id"]
        val specification = function!!.invoke("id", id)
        val deletedRows = characterRepository.delete(specification)
        return deletedRows > 0
    }

    @Transactional
    override fun createCharacter(args: Map<String, Any>): Long {
        val character = buildCharacter(args)
        val createdCharacter = characterRepository.save(character)
        return createdCharacter.id
    }

    @Transactional
    override fun updateCharacter(args: Map<String, Any>): Boolean {
        val id = (args["id"]!! as Number).toLong()
        val characterOptional = characterRepository.findById(id)

        if (characterOptional.isPresent) {
            val character = characterOptional.get()

            character.rank = args["rank"] as Int
            character.race = args["race"] as String
            character.name = args["name"] as String
            character.homeWorld = args["homeWorld"] as String

            characterRepository.save(character)
            return true
        }
        return false
    }

    private fun buildCharacter(args: Map<String, Any>) : Character {
        val character = Character()

        val name = args["name"]!! as String
        val rank = args["rank"]!! as Int
        val race = args["race"]!! as String
        val homeWorld = args["homeWorld"]!! as String

        character.name = name
        character.race = race
        character.rank = rank
        character.homeWorld = homeWorld

        return character
    }
}