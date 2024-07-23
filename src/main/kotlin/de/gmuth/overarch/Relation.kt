package de.gmuth.overarch

import java.io.PrintWriter

open class Relation(
    id: Id,
    val from: Node? = null,
    val to: Node? = null,
    name: String? = null,
    desc: String? = null,
    tech: String? = null,
    type: Type = Type.REL
) : Element(id, type, name, desc, tech) {
    var direction: Direction? = null

    private constructor(
        from: Node,
        to: Node,
        name: String,
        desc: String? = null
    ) : this(from.buildRelationId(Id.formatForIdUsage(name), to), from, to, name, desc)

    val nodes: Collection<Node>
        get() = mutableListOf<Node>().apply {
            from?.let { add(it) }
            to?.let {add(it)}
        }

    val elements: Collection<Element>
        get() = listOf(from!!, to!!)

    fun up() = this.apply { direction = Direction.up }
    fun down() = this.apply { direction = Direction.down }
    fun left() = this.apply { direction = Direction.left }
    fun right() = this.apply { direction = Direction.right }

    override fun ednRef() = StringBuilder().run {
        append("{:ref :$id")
        direction?.let { append(" $it") }
        append("}")
    }.toString()

    override fun writeModelAttributes(printWriter: PrintWriter, prefix: String) = printWriter.run {
        super.writeModelAttributes(printWriter, prefix)
        println("$prefix :from :${from!!.id}")
        println("$prefix :to :${to!!.id}")
    }

}