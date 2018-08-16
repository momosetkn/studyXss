# Webセキュリティ３回目「表示処理に伴う問題」実演

# 1. エラーを画面に出してしまってる

[エラー画面に出しちゃう](./xss1)

# 2. Cookie使ってて入力値そのまま出してる系

XSSでCookie値の盗み出してみます

事前準備 => [クッキー書き込み](./writeCookie)

1. 入力パラメータをそのまま画面に表示するアプリです
- 正常系: [keyword=hello!](./xss1?keyword=hello!)
- XSSさせてみる: [keyword=&lt;script&gt;alert(document.cookie)&lt;/script&gt;](./xss1?keyword=<script>alert(document.cookie)</script>)
※Chromeだとガードされるよ

2. 実際に悪用してみる

- [罠サイト（受動的攻撃）](http://trap:8080/trap/xss1Randing)※Chromeだとガードされるよ

# 3. Cookie使ってないけど、登録があるアプリで入力値そのまま出してる系

罠サイト経由で正規サイトにXSSして、罠サイトへPostさせる

[正常系](./xss2)

[罠サイト](http://trap:8080/trap/xss2Randing)※Chromeだとガードされるよ

# 4. Cookie使ってて入力値そのまま出してる系

エスケープしてるけど属性値を囲ってない

[正常系1](./xss3?text=text)

[正常系2](./xss3?text=<">')

[XSS](./xss3?text=text+onmouseover%3Dalert(document.cookie))

# 5. X-XSS-Protectionを試してみる

- [X-XSS-Protection: 0](./xss4_0?keyword=<script>alert(document.cookie)</script>)
- [X-XSS-Protection: 1](./xss4_1?keyword=<script>alert(document.cookie)</script>)
- [X-XSS-Protection: 1; mode=block](./xss4_1block?keyword=<script>alert(document.cookie)</script>)
- [X-XSS-Protection: 1; report=http://localhost:8080/police](./xss4_1report?keyword=<script>alert(document.cookie)</script>)

