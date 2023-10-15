package ifmo.ws

class MessageConstants {

    companion object {
        const val NULL_ARGUMENT_EXCEPTION = "The argument for sql query can't be null"
        const val NOT_FULL_PARAMETERS_TO_INSERT = "The request doesn't contain all necessary arguments to insert"
        const val MISSED_FIELDS_TO_UPDATE = "The request doesn't contain fields to update"
        const val NOT_INCORRECT_FIELD_TO_UPDATE = "The request try to update field that can't be modified"
        const val TRY_TO_UPDATE_NOT_EXISTED_ENTITY = "The request try to update not existed entity"
        const val TRY_TO_REMOVE_NOT_EXISTED_ENTITY = "The request try to remove not existed entity"
        const val INCORRECT_ID = "The id mustn't be null"
        const val SQL_EXCEPTION = "The exception is thrown when sql query is executing"
    }

}