package de.gmuth.overarch.domain

import de.gmuth.overarch.domain.Direction.*

open class Rel(
    id: Id,
    type: Type = Type.REL,
    val from: Node? = null,
    val to: Node? = null,
    name: String? = null,
    desc: String? = null,
    tech: String? = null,
) : Element(
    id = id,
    type = type,
    name = name,
    desc = desc,
    tech = tech
) {
    var direction: Direction? = null

    val nodes: Collection<Node>
        get() = mutableListOf<Node>().apply {
            from?.let { add(it) }
            to?.let { add(it) }
        }

    fun direction(new: Direction?) = this.apply { direction = new }
    fun up() = direction(UP)
    fun down() = direction(DOWN)
    fun left() = direction(LEFT)
    fun right() = direction(RIGHT)
}
