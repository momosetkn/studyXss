package com.example.app

//import com.example.app.PebbleTemplate.evaluate
import org.scalatra._

class MyScalatraServlet extends ScalatraServlet with PebbleTemplate{
  get("/hello") {
    views.html.hello()
  }

  get("/xss") {
    views.html.xss("<script>alert('hello');</script>")
  }

  //作ったコンテンツ
  get("/writeCookie"){
    cookies += ("cookieKey", "cookieValue")
    <h1>Cookieに書き込みました</h1>
  }

  get("/xss1") {
    evaluate("xss1.html",
      "keyword" -> params("keyword"))
  }

  //trap
  get("/trap/randing"){
    evaluate("trap/randing.html")
  }

  get("/trap/getCookie"){
    evaluate("trap/getCookie.html", "sid" -> params("sid"))
  }

}
