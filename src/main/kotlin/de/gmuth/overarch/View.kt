package de.gmuth.overarch

import java.io.File
import java.io.PrintWriter

open class View(
    val id: Id,
    val title: String,
    val relations: Collection<Relation>,
    val nodes: MutableCollection<Node> = relations.flatMap { it.nodes }.distinct().toMutableList(),
) {
    fun writeEdn(file: File) {
        val printWriter = PrintWriter(file.outputStream(), true)
        println("generate file ${file.name}")
        printWriter.println("; file ${file.path}")
        writeEdn(printWriter)
    }

    fun writeEdn(printerWriter: PrintWriter) = printerWriter.run {
        //println("; generated at ${LocalDateTime.now()}")
        println("; DO NOT MODIFY generated content")
        println("#{")
        println("  {:el :container-view")
        println("   :id :$id")
        println("   :spec  {:layout :top-down :include :related :plantuml {:sprite-libs [:azure :devicons]}}")
        println("   :title \"$title\"")
        println("   :ct [")
        nodes.distinct().forEach { println("       $it") }
        relations.distinct().forEach { println("       $it") }
        println("   ]}")
        println("}")
    }

}