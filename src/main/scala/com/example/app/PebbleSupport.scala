package com.example.app

import org.unbescape.html.HtmlEscape

import collection.JavaConverters._
import com.mitchellbosecke.pebble.PebbleEngine
import java.io.StringWriter
import java.io.Writer

import javax.servlet.http.HttpServletResponse

import scala.collection.mutable

trait PebbleSupport {
  def response: HttpServletResponse

  def html(templateName: String, context: (String, AnyRef)*): String = {
    response.setContentType("text/html;charset=utf-8")
    val _context = new mutable.HashMap[String, AnyRef]
    for (elem <- context) {
      _context.put(elem._1, elem._2)
    }
    val compiledTemplate = engine.getEngine.getTemplate(templateName)
    val writer: Writer = new StringWriter
    compiledTemplate.evaluate(writer, _context.asJava)
    writer.toString
  }
}

object engine{
  def getEngine: PebbleEngine = {
    new PebbleEngine.Builder()
      .addEscapingStrategy("html5", HtmlEscape.escapeHtml5)
      .cacheActive(false)
      .build
  }
}