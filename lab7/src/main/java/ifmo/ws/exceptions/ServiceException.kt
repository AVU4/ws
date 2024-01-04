package ifmo.ws.exceptions

import org.springframework.http.HttpStatus

data class ServiceException (override val message: String, val status: HttpStatus) : Exception() {
}