# Webセキュリティ３回目「表示処理に伴う問題」実演

# 1. エラーを画面に出してしまってる

[エラー画面に出しちゃう](./keyword)

# 2. Cookie使ってて入力値そのまま出してる系

XSSでCookie値の盗み出してみます

事前準備 => [クッキー書き込み](./writeCookie)

1. 入力パラメータをそのまま画面に表示するアプリです
- 正常系: [keyword=hello!](./keyword?keyword=hello!)
- XSSさせてみる: [keyword=&lt;script&gt;alert(document.cookie)&lt;/script&gt;](./keyword?keyword=<script>alert(document.cookie)</script>)
※Chromeだとガードされるよ

2. 実際に悪用してみる

- [罠サイト（受動的攻撃）](http://trap:8080/trap/keywordRanding)※Chromeだとガードされるよ

# 3. Cookie使ってないけど、登録があるアプリで入力値そのまま出してる系

罠サイト経由で正規サイトにXSSして、罠サイトへPostさせる

[正常系](./nandemoya)

[罠サイト](http://trap:8080/trap/nandemoyaRanding)※Chromeだとガードされるよ

# 4. エスケープしてるけど属性値を囲ってないXSS脆弱性

[正常系1](./attr?text=text)

[正常系2](./attr?text=<">')

[XSS](./attr?text=text+onmouseover%3Dalert(document.cookie))

# 5. X-XSS-Protectionを試してみる

- [X-XSS-Protection: 0](./xssProtection_0?keyword=<script>alert(document.cookie)</script>)
- [X-XSS-Protection: 1](./xssProtection_1?keyword=<script>alert(document.cookie)</script>)
- [X-XSS-Protection: 1; mode=block](./xssProtection_1block?keyword=<script>alert(document.cookie)</script>)
- [X-XSS-Protection: 1; report=http://localhost:8080/police](./xssProtection_1report?keyword=<script>alert(document.cookie)</script>)
- [X-XSS-Protection: 1; mode=block; report=http://localhost:8080/police](./xssProtection_1blockReport?keyword=<script>alert(document.cookie)</script>)

# 6. 

<a href="./other?initMsg=hello&url=https://google.co.jp">正常系2</a>
<a href="./other?initMsg=');alert(document.cookie)//&url=">正常系2</a>