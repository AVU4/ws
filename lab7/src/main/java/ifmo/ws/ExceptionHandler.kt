package ifmo.ws

import ifmo.ws.exceptions.ServiceException
import ifmo.ws.to.responses.ExceptionResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(ServiceException::class)
    fun handleServiceException(exception: ServiceException) : ResponseEntity<ExceptionResponse> {
        val body = ExceptionResponse(exception.message)
        return ResponseEntity<ExceptionResponse>(body, exception.status)
    }
}