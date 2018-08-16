package com.example.app

//import com.example.app.PebbleTemplate.evaluate
import org.scalatra._
import org.slf4j.Logger

class MyScalatraServlet extends ScalatraServlet
  with PebbleSupport
  with MarkdownSupport {
  get("/hello") {
    views.html.hello()
  }

  get("/xss") {
    views.html.xss("<script>alert('hello');</script>")
  }

  //markdown
  get("/"){
    markdown("index.md")
  }
  get("/demo"){
    markdown("demo.md")
  }
  get("/description"){
    markdown("description.md")
  }


  //2
  get("/writeCookie"){
    cookies += ("cookieKey", "cookieValue")
    <h1>Cookieに書き込みました</h1>
  }

  get("/xss1") {
    html("xss1.html",
      "keyword" -> params("keyword"))
  }

  //3
  get("/xss2") {
    html("xss2.html")
  }

  post("/xss2") {
    html("xss2.html",
      "params" -> request.getParameterMap)
  }

  //4
  get("/xss3") {
    html("xss3.html",
      "text" -> params("text"))
  }

  //5
  get("/xss4_0") {
    response.setHeader("X-XSS-Protection", "0")
    html("xss1.html",
      "keyword" -> params("keyword"))
  }
  get("/xss4_1") {
    response.setHeader("X-XSS-Protection", "1")
    html("xss1.html",
      "keyword" -> params("keyword"))
  }
  get("/xss4_1block") {
    response.setHeader("X-XSS-Protection", "1; mode=block")
    html("xss1.html",
      "keyword" -> params("keyword"))
  }
  get("/xss4_1report") {
    response.setHeader("X-XSS-Protection", "1; report=http://localhost:8080/police")
    html("xss1.html",
      "keyword" -> params("keyword"))
  }

  //trap
  get("/trap/xss1Randing"){
    html("trap/xss1Randing.html")
  }

  get("/trap/xss1GetCookie"){
    html("trap/xss1GetCookie.html", "sid" -> params("sid"))
  }

  get("/trap/xss2Randing"){
    html("trap/xss2Randing.html")
  }

  post("/trap/xss2GetCredit"){
    html("trap/xss2GetCredit.html",
      "params" -> request.getParameterMap)
  }

}
