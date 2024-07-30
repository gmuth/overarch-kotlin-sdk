package de.gmuth.overarch.domain

class Queue(
    id: Id,
    name: String? = null,
    desc: String? = null,
    tech: String? = null,
    external: Boolean? = null
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
    }

    fun publisherIsAvailable() = getPublishers().isNotEmpty()

    fun getPublishers() = getIncomingRels(Type.PUBLISH)
        .filter { it.from != null }
        .map { it.from }

    fun getPublisher() = getPublishers()
        .apply { if (size > 1) println("WARNING: $size publishers found, use getPublishers() instead") }
        .single()

}