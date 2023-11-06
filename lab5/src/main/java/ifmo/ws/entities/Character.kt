package ifmo.ws.entities

import jakarta.persistence.*

@Entity
@Table(name = "Character")
class Character() {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    lateinit var name: String
    var rank: Int = 0
    lateinit var race: String
    lateinit var homeWorld: String
}