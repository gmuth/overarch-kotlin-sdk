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
        private val queueMap: MutableMap<Id, Queue> = mutableMapOf()
        fun findOwnedBy(node: Node) = queueMap.values.filter { it.owner == node }
    }

    init {
        queueMap[id] = this
    }
}