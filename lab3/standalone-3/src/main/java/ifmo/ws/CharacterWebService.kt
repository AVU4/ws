package ifmo.ws

import java.util.concurrent.Semaphore
import javax.jws.WebMethod
import javax.jws.WebService

@WebService(serviceName = "CharacterService")
open class CharacterWebService(private val characterDAO: CharacterDAO) {

    private val semaphore = Semaphore(3)

    @Throws(ServiceException::class)
    @WebMethod(operationName = "getCharacters")
    open fun getCharacters(args: Map<String, Any>) : List<Character> {
        if (semaphore.tryAcquire()) {
            try {
                if (args.isEmpty() || CharacterValidator.checkTheArgsToNPE(args)) {
                    val result = characterDAO.getCharacters(args)
                    semaphore.release()
                    return result
                }
                throw ServiceException(MessageConstants.NULL_ARGUMENT_EXCEPTION, CharacterServiceFault(MessageConstants.NULL_ARGUMENT_EXCEPTION))
            } catch (e: Exception) {
                semaphore.release()
                throw e
            }
        } else {
            throw ServiceException(MessageConstants.THROTTLING_EXCEPTION, CharacterServiceFault(MessageConstants.THROTTLING_EXCEPTION))
        }
    }

    @Throws(ServiceException::class)
    @WebMethod(operationName = "createCharacter")
    open fun createCharacter(args: Map<String, Any>): Long {
        if (semaphore.tryAcquire()) {
            try {
                if (!CharacterValidator.checkTheArgsToNPE(args)) {
                    throw ServiceException(MessageConstants.NULL_ARGUMENT_EXCEPTION, CharacterServiceFault(MessageConstants.NULL_ARGUMENT_EXCEPTION))
                }
                if (!CharacterValidator.isFullArgs(args)) {
                    throw ServiceException(MessageConstants.NOT_FULL_PARAMETERS_TO_INSERT, CharacterServiceFault(MessageConstants.NOT_FULL_PARAMETERS_TO_INSERT))
                }
                val result = characterDAO.createCharacter(args)
                semaphore.release()
                return result
            } catch (e: Exception) {
                semaphore.release()
                throw e
            }
        } else {
            throw ServiceException(MessageConstants.THROTTLING_EXCEPTION, CharacterServiceFault(MessageConstants.THROTTLING_EXCEPTION))
        }

    }

    @Throws(ServiceException::class)
    @WebMethod(operationName = "removeCharacter")
    open fun removeCharacter(id: Long?): Boolean {
        if (semaphore.tryAcquire()) {
            try {
                if (id == null) {
                    throw ServiceException(MessageConstants.INCORRECT_ID, CharacterServiceFault(MessageConstants.INCORRECT_ID))
                }
                val result = characterDAO.removeCharacter(id)
                semaphore.release()
                return result
            } catch (e : Exception) {
                semaphore.release()
                throw e
            }
        } else {
            throw ServiceException(MessageConstants.THROTTLING_EXCEPTION, CharacterServiceFault(MessageConstants.THROTTLING_EXCEPTION))
        }
    }

    @Throws(ServiceException::class)
    @WebMethod(operationName = "updateCharacter")
    open fun updateCharacter(id: Long?, args: Map<String, Any>): Boolean {
        if (semaphore.tryAcquire()) {
            try {
                if (id == null) {
                    throw ServiceException(MessageConstants.INCORRECT_ID, CharacterServiceFault(MessageConstants.INCORRECT_ID))
                }
                if (!CharacterValidator.checkTheArgsToNPE(args)) {
                    throw ServiceException(MessageConstants.NULL_ARGUMENT_EXCEPTION, CharacterServiceFault(MessageConstants.NULL_ARGUMENT_EXCEPTION))
                }

                val isNotEmptyArgs = args.isNotEmpty()
                val isCanModified = CharacterValidator.isModifiedArgs(args)

                if (isNotEmptyArgs && isCanModified) {
                    val result = characterDAO.updateCharacter(id, args)
                    semaphore.release()
                    return result
                } else if (isNotEmptyArgs) {
                    throw ServiceException(MessageConstants.NOT_INCORRECT_FIELD_TO_UPDATE, CharacterServiceFault(MessageConstants.NOT_INCORRECT_FIELD_TO_UPDATE))
                }
                throw ServiceException(MessageConstants.MISSED_FIELDS_TO_UPDATE, CharacterServiceFault(MessageConstants.MISSED_FIELDS_TO_UPDATE))
            } catch (e: Exception) {
                semaphore.release()
                throw e
            }
        } else {
            throw ServiceException(MessageConstants.THROTTLING_EXCEPTION, CharacterServiceFault(MessageConstants.THROTTLING_EXCEPTION))
        }

    }
}