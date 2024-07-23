package de.gmuth.overarch

import java.io.File
import java.io.OutputStream
import java.io.PrintWriter

open class Element(
    val id: Id,
    val type: Type,
    val name: String? = null,
    val desc: String? = null,
    val tech: String? = null,
    val subtype: String? = null,
) {
    enum class Type {
        PERSON, COMPONENT, CONTAINER, SYSTEM, REL, PUBLISH, SUBSCRIBE;

        override fun toString() = name.lowercase() // match overarch types
    }

    open fun ednRef() =  "{:ref :$id}"

    override fun toString() = ednRef()

    fun writeModel(outputStream: OutputStream = java.lang.System.out) =
        writeModel(PrintWriter(outputStream, true))

    fun writeModel(printWriter: PrintWriter, prefix: String = "  ") = printWriter.run {
        println("$prefix{:el :$type")
        println("$prefix :id :$id")
        writeModelAttributes(printWriter, prefix)
        println("$prefix}")
    }

    open fun writeModelAttributes(printWriter: PrintWriter, prefix: String) = printWriter.run {
        subtype?.let { println("$prefix :subtype :$it") }
        name?.let { println("$prefix :name \"$it\"") }
        desc?.let { println("$prefix :desc \"$it\"") }
        tech?.let { println("$prefix :tech \"$it\"") }
    }

    init {
        elementMap[id] = this
        println("$type[$id] ${name ?: ""}")
    }

    companion object {
        init {
            println("overarch-kotlin-sdk version 01")
        }
        private val elementMap: MutableMap<Id, Element> = mutableMapOf()
        fun get(id: Id) = elementMap.get(id)
        val allElements: Collection<Element>
            get() = elementMap.values

        fun writeModelFilteredByNamespace(file: File, namespaceStartsWith: String, loadElements: Collection<Element>) =
            // known issue: elements MUST be loaded/instantiated before
            writeModel(file, allElements.filter { it.id.namespace.startsWith(namespaceStartsWith) })

        fun writeModel(file: File, elements: Collection<Element>) {
            val printWriter = PrintWriter(file.outputStream(), true)
            println("generate file ${file.absolutePath}")
            printWriter.println("; file ${file.unixPath()}")
            writeModel(printWriter, elements)
        }

        fun writeModel(printWriter: PrintWriter, elements: Collection<Element>) = printWriter.run {
            //println("; generated at ${LocalDateTime.now()}")
            println("; DO NOT MODIFY generated content")
            println("#{")
            println("; nodes")
            elements
                .filter { it is Node }
                .distinct()
                .forEach { it.writeModel(this) }
            println("; relations")
            elements
                .filter { it is Relation }
                .distinct()
                .forEach { it.writeModel(this) }
            println("}")
        }
    }
}