package com.example.app

import org.scalatra._

class MyScalatraServlet extends ScalatraServlet {

  get("/hello") {
    views.html.hello()
  }

  get("/xss") {
    views.html.xss("<script>alert('hello');</script>")
  }

  get("/template") {
    val context: Map[String, AnyRef] =  Map(
      "message" -> params("q")
    )
    response.setContentType("Content-Type: text/html;charset=utf-8")
    PebbleTemplate.evaluate("template.html", context)
  }


}
