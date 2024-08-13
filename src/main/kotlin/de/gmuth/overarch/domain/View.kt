package de.gmuth.overarch.domain

open class View(
    val type: Type = Type.Container,
    val id: Id,
    val title: String,
    val rels: Collection<Rel>,
    val nodes: MutableCollection<Node> = rels.flatMap { it.nodes }.distinct().toMutableList(),
) {
    enum class Type {
        Container, Context;

        override fun toString() = name.lowercase()
        fun elementType() = "${name.lowercase()}-view"
    }

    constructor(type: Type = Type.Container, id: Id, title: String, vararg rels: Rel) :
            this(type, id, title, rels.toList())

    init {
        println("* $id (${type.elementType()}) ${rels.size} rels")
        //rels.forEach { println("- ${it.id}") }
    }

}
