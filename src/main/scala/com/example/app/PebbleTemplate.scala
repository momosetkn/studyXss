package com.example.app

import org.unbescape.html.HtmlEscape
import collection.JavaConverters._
import com.mitchellbosecke.pebble.PebbleEngine
import java.io.StringWriter
import java.io.Writer

object PebbleTemplate {

  def getEngine: PebbleEngine = {
    new PebbleEngine.Builder()
      .addEscapingStrategy("html5", HtmlEscape.escapeHtml5)
      .cacheActive(false)
      .build
  }

  def evaluate(templateName: String, context: Map[String, AnyRef]): String = {
    val compiledTemplate = getEngine.getTemplate(templateName)
    val writer: Writer = new StringWriter
    compiledTemplate.evaluate(writer, context.asJava)
    writer.toString
  }

}
