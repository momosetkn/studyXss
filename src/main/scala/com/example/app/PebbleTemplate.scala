package com.example.app

import org.unbescape.html.HtmlEscape

import collection.JavaConverters._
import com.mitchellbosecke.pebble.PebbleEngine
import java.io.StringWriter
import java.io.Writer

import javax.servlet.http.HttpServletResponse

import scala.collection.mutable

trait PebbleTemplate {
  def response: HttpServletResponse

  def getEngine: PebbleEngine = {
    new PebbleEngine.Builder()
      .addEscapingStrategy("html5", HtmlEscape.escapeHtml5)
      .cacheActive(false)
      .build
  }

  def evaluate(templateName: String, context: (String, AnyRef)*): String = {
    response.setContentType("Content-Type: text/html;charset=utf-8")
    val _context = new mutable.HashMap[String, AnyRef]
    for (elem <- context) {
      _context.put(elem._1, elem._2)
    }
    val compiledTemplate = getEngine.getTemplate(templateName)
    val writer: Writer = new StringWriter
    compiledTemplate.evaluate(writer, _context.asJava)
    writer.toString
  }

}