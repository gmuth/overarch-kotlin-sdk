package de.gmuth.overarch

open class Id(val name: String, val namespace: String) {

    override fun toString() = "$namespace/${formatForIdUsage(name)}"

    companion object {
        fun formatForIdUsage(string: String) = string.replace("\\s+".toRegex(), "-").lowercase()
    }

}