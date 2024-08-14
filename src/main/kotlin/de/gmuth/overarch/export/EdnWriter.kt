package de.gmuth.overarch.export

import de.gmuth.overarch.domain.*
import de.gmuth.overarch.domain.Element.Companion.allElements
import java.io.File
import java.io.OutputStream
import java.io.PrintWriter
import java.lang.System

open class EdnWriter(val printWriter: PrintWriter) {

    constructor(outputStream: OutputStream = System.out) : this(PrintWriter(outputStream, true))

    constructor(file: File) : this(file.outputStream()) {
        println(">>> generate file ${file.absolutePath}")
        printWriter.println("; file ${file.unixPath()}")
    }

    constructor(optionalPath: String? = null, filename: String = "model-gen.edn") :
            this(
                File(
                    "$outputDirectory${if (optionalPath == null) "" else "${File.separator}$optionalPath"}",
                    filename
                )
            )

    fun writeElementsFilteredByNamespace(namespaceStartsWith: String, loadElements: Collection<Element>) =
        // known issue: elements MUST be loaded/instantiated before
        writeElements(allElements.filter { it.id.namespace.startsWith(namespaceStartsWith) })

    fun writeElements(elements: Iterable<Element>, prefix: String = "  ") = printWriter.run {
        println("; DO NOT MODIFY generated content")
        println("#{")
        fun writeWithTitle(title: String, elements: Collection<Element>) = elements.run {
            if (isNotEmpty()) {
                println("$prefix; $title")
                forEach {
                    try {
                        writeElement(it, prefix)
                    } catch (throwable: Throwable) {
                        throw RuntimeException("Failed to write model for element ${it.id}", throwable)
                    }
                }
            }
        }
        elements.distinct().sortedBy { it.id }.run {
            writeWithTitle("nodes", filterIsInstance<Node>())
            writeWithTitle("rels", filterIsInstance<Rel>())
        }
        println("}")
    }

    fun writeModel(model: Model) = writeElements(model.elements)

    fun writeElement(element: Element, prefix: String = "  ") = element.run {

        requireNotNull(element.name) { "Elements without name can only be used for reference (kotlin or {:ref :<id>})." }

        // required attributes
        printWriter.println("$prefix{:el :$type")
        printWriter.println("$prefix :id :$id")

        // rel attributes
        if (this is Rel) {
            printWriter.println("$prefix :from :${from!!.id}")
            printWriter.println("$prefix :to :${to!!.id}")
        }

        // optional attributes
        subtype?.let { printWriter.println("$prefix :subtype :$it") }
        name?.let { printWriter.println("$prefix :name \"$it\"") }
        desc?.let { printWriter.println("$prefix :desc \"$it\"") }
        tech?.let { printWriter.println("$prefix :tech \"$it\"") }

        // node attribute
        if (this is Node) {
            external?.let { printWriter.println("$prefix :external $it") }
            sprite?.let { printWriter.println("$prefix :sprite \"$it\"") }
        }

        // tags
        if (tags.isNotEmpty()) {
            val tagsString = tags.joinToString(" ", "#{", "}") { "\"$it\"" }
            printWriter.println("$prefix :tags $tagsString")
        }

        printWriter.println("$prefix}")
    }

    // --- write Views ---

    fun writeViews(views: Collection<View>, prefix: String = "  ") = printWriter.run {
        println("; DO NOT MODIFY generated content")
        println("#{")
        views.iterator().run {
            while (hasNext()) {
                writeView(next())
                if (hasNext()) println("$prefix,")
            }
        }
        println("}")
    }

    fun writeView(view: View, prefix: String = "  ") = printWriter.run {
        println("$prefix{:el :${view.type.elementType()}")
        println("$prefix :id :${view.id}")
        // :include :related
        println("$prefix :spec  {:layout :top-down :plantuml {:sprite-libs [:azure :devicons]}}")
        println("$prefix :title \"${view.title}\"")
        println("$prefix :ct [")
        println("$prefix     ; nodes")
        view.nodes.distinct().sortedBy { it.id }.forEach { println("$prefix     ${it.ednRef()}") }
        println("$prefix     ; rels")
        view.rels.distinct().sortedBy { it.id }.forEach { println("$prefix     ${it.ednRef()}") }
        println("$prefix ]}")
    }

    private fun Element.ednRef() = "{:ref :$id}"

    private fun Rel.ednRef() = StringBuilder().run {
        append("{:ref :$id")
        direction?.let { append(" ${it.toEdn()}") }
        append("}")
    }.toString()

    private fun Direction.toEdn() = ":direction :${name.lowercase()}"

    // --- write edn refs only ---

    fun writeElementRefs(model: Model) = writeElementRefs(model.elements)

    fun writeElementRefs(elements: Iterable<Element>, prefix: String = "") = printWriter.run {
        for (element in elements) with(element) {
            println("$prefix{:ref :$id}")
        }
    }

    companion object {
        var outputDirectory: String = "models"

        fun writeModel(optionalPath: String? = null, model: Model, filename: String = "model-gen.edn") =
            EdnWriter(optionalPath, filename).writeModel(model)

        fun writeViews(optionalPath: String? = null, vararg views: View, filename: String = "views-gen.edn") =
            EdnWriter(optionalPath, filename).writeViews(views.toList())
    }
}