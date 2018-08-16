package com.example.app

import java.io.InputStreamReader

import javax.servlet.http.HttpServletResponse

trait MarkdownSupport {
  def response: HttpServletResponse

  import com.vladsch.flexmark.html.HtmlRenderer
  import com.vladsch.flexmark.parser.Parser

  def markdown(fileName: String): String = {
    response.setContentType("text/html;charset=utf-8")
    val parser= Parser.builder().build
    val renderer = HtmlRenderer.builder().build

    // You can re-use parser and renderer instances
    val is = this.getClass.getClassLoader.getResourceAsStream(fileName)
    val document = parser.parseReader(new InputStreamReader(is))
    val content = renderer.render(document) // "<p>This is <em>Sparta</em></p>\n"
    s"""
      |<!DOCTYPE html>
      |<html lang="ja">
      |<head>
      |    <meta charset="UTF-8">
      |    <title>${fileName}</title>
      |</head>
      |<body>
      |${content}
      |</body>
      |</html>
    """.stripMargin.trim
  }
}
