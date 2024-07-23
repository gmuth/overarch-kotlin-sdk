package de.gmuth.overarch.domain

data class Id(val name: String, val namespace: String) : Comparable<Id> {

    override fun compareTo(other: Id) = namespace.compareTo(other.namespace)
        .run { if (this == 0) name.compareTo(other.name) else this }

    override fun toString() = "$namespace/${formatForIdUsage(name)}"

    companion object {
        fun formatForIdUsage(string: String) = string.replace("\\s+".toRegex(), "-").lowercase()
    }

}