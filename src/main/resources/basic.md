# Webセキュリティ３回目「表示処理に伴う問題_基本編

とは？
- XSS
- エラーメッセージからの漏洩

***

# XSSされると？
- Cookie値を盗まれて、なりすまされる
- Webアプリの機能を悪用される(CSRFされて殺人予告など)
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

JavaScriptを実行させて、WebAPIを叩いてイタズラされる

#  XSS脆弱性の悪用例３

これはログインせずとも可能な攻撃

サンプルコードでは入力画面と編集画面を兼ねており、入力値の初期値が設定できる画面を紹介されている。

# XSS基本編の実演

# 1. Cookie使ってて入力値そのまま出してる系

XSSでCookie値の盗み出してみます

事前準備 => [クッキー書き込み](./writeCookie)

1. 入力パラメータをそのまま画面に表示するアプリです
- 正常系: [keyword=hello!](./keyword?keyword=hello!)
- XSSさせてみる: [keyword=&lt;script&gt;alert(document.cookie)&lt;/script&gt;](./keyword?keyword=<script>alert(document.cookie)</script>)
※Chromeだとガードされるよ

2. 実際に悪用してみる

- [罠サイト（受動的攻撃）](http://trap:8080/trap/keywordRanding)※Chromeだとガードされるよ

# 2. Cookie使ってないけど、登録があるアプリで入力値そのまま出してる系

罠サイト経由で正規サイトにXSSして、罠サイトへPostさせる

[正常系](./nandemoya)

[罠サイト](http://trap:8080/trap/nandemoyaRanding)※Chromeだとガードされるよ

# 3. エスケープしてるけど属性値を囲ってないXSS脆弱性

[正常系1](./attr?text=text)

[正常系2](./attr?text=<">')

[XSS](./attr?text=text+onmouseover%3Dalert(document.cookie))

***

# 攻撃用のJavaScriptがどこにあるか？で考えてみる

## 反射型XSS

スクリプトが罠サイトにある

## 持続型XSS

スクリプトが攻撃対象サイトにある

罠を踏ませる手間が不要（注意深い人でも被害に遭う）

ターゲット例）掲示板

なお、これらとは別に
DOM-based XSSといってサーバーを経由せずにJavaScriptのみで表示しているパラメータがある場合に発生する可能性がある。
詳しくは4.17「JavaScriptの問題」で解説（と書いてあった）。

***

# 文字コードはちゃんと指定しましょう。

XSSの原因になることもある。
文字コードによる脆弱性もある（後続の６章で説明される）。

```
Content-Type: text/html;charset=utf-8
```

# XSSの保険的対策

対策する箇所多い、漏れが発生するかも？
＝＞漏れがあっても大丈夫なようにする

イマドキのブラウザはXSSフィルタ搭載がされている、らしいですが、

FireFoxだけなぜかサポートされてないようです。

```
X-XSS-Protection
```
参考情報
[X\-XSS\-Protection \- HTTP \| MDN](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/X-XSS-Protection)

# 実演

- [X-XSS-Protection: 0](./xssProtection_0?keyword=<script>alert(document.cookie)</script>)
- [X-XSS-Protection: 1](./xssProtection_1?keyword=<script>alert(document.cookie)</script>)
- [X-XSS-Protection: 1; mode=block](./xssProtection_1block?keyword=<script>alert(document.cookie)</script>)
- [X-XSS-Protection: 1; report=http://localhost:8080/police](./xssProtection_1report?keyword=<script>alert(document.cookie)</script>)
- [X-XSS-Protection: 1; mode=block; report=http://localhost:8080/police](./xssProtection_1blockReport?keyword=<script>alert(document.cookie)</script>)


## 入力値検証も保険になる（文字数不足）
## CookieをHttpOnly属性にして、JavaScriptからの読み出しをガードする

## TRACEメソッド

[実はそんなに怖くないTRACEメソッド \| 徳丸浩の日記](https://blog.tokumaru.org/2013/01/TRACE-method-is-not-so-dangerous-in-fact.html)

さらっとしか説明がなかったです。
2006年頃にすべてのブラウザで対応済みらしい。
しかし、脆弱性診断ではサーバーサイドでtraceメソッドを許容していると脆弱性とみなされることもある。

