package de.gmuth.overarch.domain

open class Person(
    id: Id
) : Node(
    id = id,
    type = Type.PERSON
)
