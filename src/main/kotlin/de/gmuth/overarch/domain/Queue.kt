package de.gmuth.overarch.domain

class Queue(
    id: Id,
    name: String? = null,
    desc: String? = null,
    tech: String? = null,
    external: Boolean? = null,
    val owner: Node? = null,
) : Container(
    id,
    name = name,
    desc = desc,
    subtype = "queue",
    tech = tech,
    external = external,
) {
    companion object {
        private val allQueues: Collection<Queue>
            get() = allElements.filterIsInstance<Queue>()

        fun getOwnedBy(node: Node) = allQueues.filter { it.owner == node }
    }

    override fun toString() = StringBuilder("Queue[$id]").run {
        tech?.let { append(" tech='$tech'") }
        owner?.let { append(" owner=${owner.id}") }
    }.toString()
}