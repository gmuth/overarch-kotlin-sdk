package de.gmuth.overarch.domain

open class ContainerView(id: Id, title: String, rels: Collection<Rel>) : View(Type.Container, id, title, rels) {
    constructor(id: Id, title: String, vararg rels: Rel) : this(id, title, rels.toList())
}
