package de.gmuth.overarch.domain

open class View(
    val type: Type,
    val id: Id,
    val title: String,
    val layout: String = "top-down",
    val rels: Collection<Rel>,
    val nodes: MutableCollection<Node> = rels.flatMap { it.nodes }.distinct().toMutableList(),
) {
    enum class Type {
        Container, Context;

        override fun toString() = name.lowercase()
        fun elementType() = "${name.lowercase()}-view"
    }

    init {
        println("* $id (${type.elementType()}) ${rels.size} rels")
        //rels.forEach { println("- ${it.id}") }
    }

}
