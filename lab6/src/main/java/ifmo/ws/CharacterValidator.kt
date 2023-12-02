package ifmo.ws

class CharacterValidator {
    companion object {
        fun checkTheArgsToNPE(args: Map<String, Any?>) : Boolean {
            return args.values.filterNotNull().size == args.size
        }

        fun isFullArgs(args: Map<String, Any?>): Boolean {
            return args.containsKey("name") && args.containsKey("race") && args.containsKey("rank") && args.containsKey("homeWorld")
        }

        fun isModifiedArgs(args: Map<String, Any?>): Boolean {
            return !args.containsKey("id") && !args.containsKey("name")
        }
    }
}