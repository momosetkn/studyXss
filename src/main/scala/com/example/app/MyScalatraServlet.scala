package com.example.app

//import com.example.app.PebbleTemplate.evaluate
import java.nio.charset.StandardCharsets
import java.util.regex.{Matcher, Pattern}

import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils
import org.scalatra._
import org.slf4j.{Logger, LoggerFactory}

class MyScalatraServlet extends ScalatraServlet
  with PebbleSupport
  with MarkdownSupport {

  val logger = LoggerFactory.getLogger(this.getClass)

  get("/hello") {
    views.html.hello()
  }

  //markdown
  get("/"){
    markdown("index.md")
  }
  get("/*.md"){
    markdown(requestPath)
  }

  //基本的にセキュリティ低め設定
  before("*"){
    response.setHeader("X-XSS-Protection", "0")
  }

  //2
  get("/writeCookie"){
    cookies += ("cookieKey", "cookieValue")
    <h1>Cookieに書き込みました</h1>
  }

  get("/keyword") {
    views.html.keyword(request.getParameter("keyword"))
  }

  //3
  get("/nandemoya") {
    views.html.nandemoya("", "", "", "", "")
  }

  post("/nandemoya") {
    views.html.nandemoya(
      request.getParameter("bought"),
      request.getParameter("product"),
      request.getParameter("tel"),
      request.getParameter("addr"),
      request.getParameter("name")
    )
  }

  //4
  get("/attr") {
    views.html.attr(request.getParameter("text"))
  }

  //5
  get("/xssProtection_0") {
    response.setHeader("X-XSS-Protection", "0")
    views.html.keyword(request.getParameter("keyword"))
  }
  get("/xssProtection_1") {
    response.setHeader("X-XSS-Protection", "1")
    views.html.keyword(request.getParameter("keyword"))
  }
  get("/xssProtection_1block") {
    response.setHeader("X-XSS-Protection", "1; mode=block")
    views.html.keyword(request.getParameter("keyword"))
  }
  get("/xssProtection_1report") {
    response.setHeader("X-XSS-Protection", "1; report=http://localhost:8080/police")
    views.html.keyword(request.getParameter("keyword"))
  }
  get("/xssProtection_1blockReport") {
    response.setHeader("X-XSS-Protection", "1; mode=block; report=http://localhost:8080/police")
    views.html.keyword(request.getParameter("keyword"))
  }
  //police
  post("/police") {
    logger.warn("XSS攻撃されています! 報告内容: {}",IOUtils.toString(request.getInputStream, StandardCharsets.UTF_8))
  }

  //6
  get("/link") {
    val url = request.getParameter("url")
    val badUrl: java.lang.Boolean = StringUtils.isNotEmpty(url) && !caseInsentiveMatches("^https?://.*$", url)
    val ownUrl: java.lang.Boolean = StringUtils.isNotEmpty(url) && !caseInsentiveMatches("^https?://localhost:8080/.*$", url)

    html("link.html",
      "url" -> url,
      "badUrl" -> badUrl,
      "ownUrl" -> ownUrl
    )
  }

  get("/linkUnsafe") {
    val url = request.getParameter("url")
    val badUrl: java.lang.Boolean = StringUtils.isNotEmpty(url) && !caseInsentiveMatches("^https?://.*$", url)
    val ownUrl: java.lang.Boolean = StringUtils.isNotEmpty(url) && !caseInsentiveMatches("^https?://localhost:8080/.*$", url)

    html("linkUnsafe.html",
      "url" -> url,
      "badUrl" -> badUrl,
      "ownUrl" -> ownUrl
    )
  }
  get("/jsEvent") {
    val message = request.getParameter("message")

    html("jsEvent.html",
      "message" -> message)
  }
  get("/jsEventUnsafe") {
    val message = request.getParameter("message")

    html("jsEventUnsafe.html",
      "message" -> message)
  }
  get("/jsEvent2") {
    val message = request.getParameter("message")

    html("jsEvent2.html",
      "message" -> message)
  }

  get("/scriptTag") {
    val logMsg = request.getParameter("logMsg")

    html("scriptTag.html",
      "logMsg" -> logMsg
    )
  }
  get("/scriptTagUnsafe") {
    val logMsg = request.getParameter("logMsg")

    html("scriptTagUnsafe.html",
      "logMsg" -> logMsg
    )
  }

  get("/foreignLink"){
    val url = request.getParameter("url")
    val badUrl: java.lang.Boolean = StringUtils.isNotEmpty(url) && !caseInsentiveMatches("^https?://.*$", url)
    html("foreignLink.html",
      "url" -> url,
      "badUrl" -> badUrl)
  }

  /**
    * 大文字小文字無視するmatches
    * @param regex
    * @param s
    * @return
    */
  def caseInsentiveMatches(regex: String, s: String): Boolean = {
    val p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
    val m = p.matcher(s)
    return m.matches
  }


  //trap
  get("/trap/keywordRanding"){
    html("trap/keywordRanding.html")
  }

  get("/trap/keywordGetCookie"){
    html("trap/keywordGetCookie.html", "sid" -> params("sid"))
  }

  get("/trap/nandemoyaRanding"){
    html("trap/nandemoyaRanding.html")
  }

  post("/trap/nandemoyaGetCredit"){
    html("trap/nandemoyaGetCredit.html",
      "params" -> request.getParameterMap)
  }

}
