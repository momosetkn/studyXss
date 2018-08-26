# Webセキュリティ３回目「表示処理に伴う問題」その他

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

[あまり知られていない脆弱性：DOM Based XSSにご用心 : アークウェブのブログ](https://www.ark-web.jp/blog/archives/2007/02/dom_based_xss.html)

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

# おまけ

脆弱性発見のプロ「キヌガワ マサト」さんは日本人だった - ITmedia エンタープライズ http://www.itmedia.co.jp/enterprise/articles/1412/20/news003.html

>キヌガワさんによると、趣味は音楽鑑賞とクロスサイトスクリプティング（XSS）。

>キヌガワさんの成果が特に目立つのはGoogleだ。2010年の制度開始以降、報奨金の対象になったものだけで127件の脆弱性を同社に報告しており、報告件数は世界で2番目に多い。

>キヌガワさんがバグ探しに魅力を感じるのは、成果が評価されるというだけではなく、コードやWebブラウザの複雑な挙動の中から、まるで「宝物」を見つけ出したかのような喜びを感じられるからだという。

>コンピュータ系の専門学校に在籍していた2009年にふとしたきっかけでXSSに“目覚め”、脆弱性探しに興味を覚えたのだという。

[Masato Kinugawa Security Blog](https://masatokinugawa.l0.cm/)

[Masato Kinugawa\(@kinugawamasato\)さん \| Twitter](https://twitter.com/kinugawamasato)
