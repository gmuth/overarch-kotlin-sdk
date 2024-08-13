package de.gmuth.overarch.domain

open class ContextView(id: Id, title: String, rels: Collection<Rel>) : View(Type.Context, id, title, rels) {
    constructor(id: Id, title: String, vararg rels: Rel) : this(id, title, rels.toList())
}
