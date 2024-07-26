package de.gmuth.overarch.domain

open class Node(
    id: Id,
    type: Type,
    name: String? = null,
    desc: String? = null,
    tech: String? = null, // not for Persons
    subtype: String? = null, // not for Persons
    val external: Boolean? = null
) : Element(
    id = id,
    type = type,
    name = name,
    desc = desc,
    tech = tech,
    subtype = subtype
) {

    fun getOwnedQueues() = Queue.getOwnedBy(this)

    fun getRelsTo(target: Node) = rels.filter { it.to == target }

    var publishDirection: Direction? = null
    var subscribeDirection: Direction? = null

    val rels: MutableSet<Rel> = mutableSetOf()

    private fun buildRelId(idAction: String, target: Node, includeTargetNameInId: Boolean = true) = Id(
        "${this.id.name}-${idAction.lowercase()}${if (includeTargetNameInId) "-${target.id.name}" else ""}",
        namespace = this.id.namespace
    )

    private fun rel(
        id: Id,
        target: Node? = null,
        name: String? = null,
        desc: String? = null,
        tech: String? = null,
        type: Type = Type.REL,
    ) = Rel(
        id = id,
        type = type,
        from = this,
        to = target,
        name = name,
        desc = desc,
        tech = tech
    ).also {
        rels.add(it)
        target?.rels?.add(it)
    }

    fun rel(
        idAction: String,
        name: String,
        desc: String? = null,
        target: Node,
        includeTargetNameInId: Boolean = true
    ) =
        rel(buildRelId(idAction, target, includeTargetNameInId), target, name, desc)

    fun calls(name: String = "calls", desc: String? = null, tech: String? = null, target: Node) =
        rel(buildRelId("calls", target), target, name, desc, tech)

    fun sends(name: String, desc: String? = null, target: Node) =
        rel(buildRelId("sends-$name-to", target), target, "sends $name", desc)

    fun gets(name: String, desc: String? = null, source: Node) =
        rel(buildRelId("gets-$name-from", source), source, "gets $name", desc)

    fun publish(queue: Queue, queueDirection: Direction? = publishDirection) =
        rel(buildRelId("publishes", queue), queue, "publishes", type = Type.PUBLISH)
            .apply { direction = queueDirection }

    fun subscribe(queue: Queue) =
        rel(buildRelId("subscribes", queue), queue, "subscribes", type = Type.SUBSCRIBE)
            .apply { direction = subscribeDirection }

    fun subscribe(queues: Collection<Queue>, direction: Direction? = null) = queues.run {
        if (isEmpty()) throw IllegalArgumentException("List of queues must not be empty.")
        map { subscribe(it).direction(direction) }
    }

    fun subscribe(vararg queue: Queue, direction: Direction? = null) =
        subscribe(queue.toList(), direction)

}
