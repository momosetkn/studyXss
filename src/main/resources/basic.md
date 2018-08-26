# Webセキュリティ３回目「表示処理に伴う問題」基本編

とは？
- XSS
- エラーメッセージからの漏洩

***

# XSSされると？
- Cookie値を盗まれて、なりすまされる
- Webアプリの機能を悪用される(勝手に投稿されたり)
- 偽の入力フォームを表示されて、フィッシング

# XSSの注意点

対策が必要な箇所が多いが、軽視されがち（と書いてあった）。

使ってるテンプレートエンジンにもよるのかも？機械的にチェックできそう。

***

# XSS脆弱性の悪用例１

Cookieを罠サイトへ送信してしまう

# XSS脆弱性の悪用例２

Cookie以外にも悪用方法がある

Twitterでの一例
[JS/Twettir](https://www.mcafee.com/japan/security/virT.asp?v=JS/Twettir)

[Twitterの“XSS騒動”はどのように広まったか \- ITmedia NEWS](http://www.itmedia.co.jp/news/articles/1009/24/news023.html)

JavaScriptを実行させて、WebAPIを叩いてイタズラされる

#  XSS脆弱性の悪用例３

これはログインせずとも可能な攻撃

サンプルコードでは入力画面と編集画面を兼ねており、入力値の初期値が設定できる画面を紹介されている。

# XSS基本編の実演

# 1. Cookie盗む攻撃

攻撃対象：Cookie使ってて入力値そのまま出してるページ

XSSでCookie値の盗み出してみます

事前準備 => [クッキー書き込み](./writeCookie)

1. 入力パラメータをそのまま画面に表示するアプリです
- 正常系: [keyword=hello!](./keyword?keyword=hello!)
- XSSさせてみる: [keyword=&lt;script&gt;alert(document.cookie)&lt;/script&gt;](./keyword?keyword=<script>alert(document.cookie)</script>)
※Chromeだとガードされるよ

2. 実際に悪用してみる

- [罠サイト（受動的攻撃）](http://trap:8080/trap/keywordRanding)※Chromeだとガードされるよ

# 2. ログイン機能無いWebアプリへの攻撃

攻撃対象：Cookie使ってないけど、登録があるアプリで入力値そのまま出してるページ

罠サイト経由で正規サイトにXSSして、罠サイトへPostさせる

[正常系](./nandemoya)

[罠サイト](http://trap:8080/trap/nandemoyaRanding)※Chromeだとガードされるよ

# 3. エスケープしてるけど属性値を囲ってないXSS脆弱性

[正常系1](./attr?text=text)

[正常系2](./attr?text=<">')

[XSS](./attr?text=text+onmouseover%3Dalert(document.cookie))

***
