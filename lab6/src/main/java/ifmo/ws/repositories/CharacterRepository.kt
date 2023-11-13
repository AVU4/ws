package ifmo.ws.repositories

import ifmo.ws.entities.Character
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CharacterRepository: CrudRepository<Character, Long>, JpaSpecificationExecutor<Character> {
}