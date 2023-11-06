package ifmo.ws.repositories

import ifmo.ws.entities.Character
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

@Service
class CustomCharacterRepository(@Autowired private val characterRepository: CharacterRepository) {

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
    fun getCharacters(args: Map<String, Any>): List<Character> {
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
}