package de.gmuth.overarch.export

import de.gmuth.overarch.domain.Element
import de.gmuth.overarch.domain.Model
import de.gmuth.overarch.domain.Node
import de.gmuth.overarch.domain.Rel
import java.io.File
import java.io.OutputStream
import java.io.PrintWriter

open class EdnWriter(val printWriter: PrintWriter) {

    constructor(outputStream: OutputStream = System.out) : this(PrintWriter(outputStream, true))

    constructor(file: File) : this(file.outputStream()) {
        println("generate file ${file.absolutePath}")
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
        writeElements(Element
            .allElements
            .filter { it.id.namespace.startsWith(namespaceStartsWith) }
            .sortedBy { it.id }
        )

    fun writeElements(elements: Iterable<Element>) = printWriter.run {
        println("; DO NOT MODIFY generated content")
        println("#{")
        fun writeWithTitle(title: String, elements: Collection<Element>) = elements.run {
            if (isNotEmpty()) {
                println("; $title");
                forEach { writeElement(it) }
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

        // required attributes
        printWriter.println("$prefix{:el :$type")
        printWriter.println("$prefix :id :$id")

        // rel attributes
        if(this is Rel) {
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
        }

        printWriter.println("$prefix}")
    }

    companion object {
        var outputDirectory: String = "models"
        fun writeModel(model: Model, optionalPath: String? = null, filename: String = "model-gen.edn") =
            EdnWriter(optionalPath, filename).writeModel(model)
    }
}