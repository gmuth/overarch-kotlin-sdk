package de.gmuth.overarch.domain

open class ContextView(id: Id, title: String, layout: String = "top-down", rels: Collection<Rel>) :
    View(Type.Context, id, title, layout, rels) {
    constructor(id: Id, title: String, layout: String = "top-down", vararg rels: Rel) :
            this(id, title, layout, rels.toList())
}
