package ifmo.ws.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {

    @GetMapping("/check")
    fun check() : ResponseEntity<Any> {
        return ResponseEntity.ok().build()
    }
}