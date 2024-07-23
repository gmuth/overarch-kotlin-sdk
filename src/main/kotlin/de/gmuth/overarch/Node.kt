package de.gmuth.overarch

import java.io.PrintWriter

open class Node(
    id: Id,
    type: Type,
    name: String? = null,
    desc: String? = null,
    tech: String? = null,
    subtype: String? = null,
    val external: Boolean? = null
) : Element(id, type, name, desc, tech, subtype) {

    val relations: MutableSet<Relation> = mutableSetOf()

    fun buildRelationId(action: String, target: Node? = null) = Id(
        "${this.id.name}-${action.lowercase()}${if (target == null) "" else "-${target.id.name}"}",
        namespace = this.id.namespace
    )

    fun relation(
        id: Id,
        target: Node? = null,
        name: String? = null,
        desc: String? = null,
        tech: String? = null,
        type: Type = Type.REL
    ) =
        Relation(id, this, target, name, desc, tech, type).also {
            relations.add(it)
            target?.relations?.add(it)
        }

    fun relation(action: String, name: String, desc: String? = null, target: Node, includeTargetNameInId: Boolean = true) =
        relation(buildRelationId(action, if(includeTargetNameInId) target else null), target, name, desc)

    fun calls(name: String, desc: String? = null, tech: String? = null, to: Node) =
        relation(buildRelationId("calls", to), to, name, desc, tech)

    fun sends(name: String, desc: String? = null, to: Node) =
        relation(buildRelationId("sends-$name-to", to), to, "sends $name", desc)

    fun gets(name: String, desc: String? = null, from: Node) =
        relation(buildRelationId("gets-$name-from", from), from, "gets $name", desc)

    fun publish(queue: Queue) =
        relation(buildRelationId("publishes", queue), queue, "publishes", type = Type.PUBLISH)

    fun subscribe(queue: Queue) =
        relation(buildRelationId("subscribes", queue), queue, "subscribes", type = Type.SUBSCRIBE)

    override fun writeModelAttributes(printWriter: PrintWriter, prefix: String) = printWriter.run {
        super.writeModelAttributes(printWriter, prefix)
        external?.let { println("$prefix :external $it") }
    }
}







