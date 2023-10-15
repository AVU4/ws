package ifmo.ws

import javax.xml.ws.WebFault

@WebFault(faultBean = "CharacterServiceFault")
class ServiceException (message: String, val fault: CharacterServiceFault, cause: Throwable) : Exception(message, cause) {
    constructor(message: String, fault: CharacterServiceFault): this(message, fault, Throwable())
}
