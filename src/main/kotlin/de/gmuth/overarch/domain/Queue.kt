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
        val getAllQueues: Collection<Queue> = allElements.filterIsInstance<Queue>()
        fun findOwnedBy(node: Node) = getAllQueues.filter { it.owner == node }
    }
}