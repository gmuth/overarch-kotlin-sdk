package de.gmuth.overarch.domain

import de.gmuth.overarch.export.unixPath
import java.io.File
import java.io.PrintWriter

open class View(
    val id: Id,
    val title: String,
    val rels: Collection<Rel>,
    val nodes: MutableCollection<Node> = rels.flatMap { it.nodes }.distinct().toMutableList(),
) {
    init {
        println("View[$id] $title: ${rels.size} rels")
        //rels.forEach { println("- ${it.id}") }
    }

    fun writeEdn(file: File) {
        val printWriter = PrintWriter(file.outputStream(), true)
        println("generate file ${file.name}")
        printWriter.println("; file ${file.unixPath()}")
        writeEdn(printWriter)
    }

    private fun Element.ednRef() = "{:ref :$id}"

    private fun Rel.ednRef() = StringBuilder().run {
        append("{:ref :$id")
        direction?.let { append(" ${it.toEdn()}") }
        append("}")
    }.toString()

    private fun Direction.toEdn() = ":direction :${name.lowercase()}"

    fun writeEdn(printerWriter: PrintWriter) = printerWriter.run {
        //println("; generated at ${LocalDateTime.now()}")
        println("; DO NOT MODIFY generated content")
        println("#{")
        println("  {:el :container-view")
        println("   :id :$id")
        println("   :spec  {:layout :top-down :include :related :plantuml {:sprite-libs [:azure :devicons]}}")
        println("   :title \"$title\"")
        println("   :ct [")
        nodes.distinct().sortedBy { it.id }.forEach { println("       ${it.ednRef()}") }
        rels.distinct().sortedBy { it.id }.forEach { println("       ${it.ednRef()}") }
        println("   ]}")
        println("}")
    }

}