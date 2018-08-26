# Webセキュリティ３回目「表示処理に伴う問題」エスケープの実装編


# HTMLエスケープ

- 要素内容: `<`,`&`を文字参照に
- 属性値: ダブルクォートで囲んで`<`,`&`,`"`
※シングルクォートで囲む場合は`'`もエスケープすること

※属性値をダブルクォートで囲むのはスペースを入れられたらガードできなくなるため

ダブルクォートで囲んでいないテンプレート
```
<a href={{ link }}>link</a>
```

そうすると？
```
link = "https://google.com onclick=alert();"
<a href=https://google.com onclick=alert();>link</a>
```

スペースを入れて属性値を指定されてしまうので、ダブルクォートで囲みましょう
```
<a href="https://google.com onclick=alert();">link</a>
```

※`&`もエスケープするのは文字参照のガードのため

参考情報

[【PHP関連】「&」（アンパサンド）をエスケープしなければならない実例: WEBプログラミング NOW\!](http://shimax.cocolog-nifty.com/search/2007/12/php_f864.html)

>「氏ね」をNGワードにしたいのに、「&rlo;ね氏」と入力されてしまうとチェックできまないことになります。


## hrefやsrc属性のXSS

`^https?://.*$`かどうかをチェックする

javascript:を使えないようにするため

リンク先のドメインチェック

### 未対策

<a href="./linkUnsafe?url=http://localhost:8080/">http://localhost:8080/</a><br>
<a href="./linkUnsafe?url=https://google.co.jp/">https://google.co.jp/</a><br>
<a href="./linkUnsafe?url=https://google.co.jp/ onclick=alert(document.cookie);">https://google.co.jp/ onclick=alert(document.cookie);</a><br>
<a href="./linkUnsafe?url=javascript:alert(document.cookie);">javascript:alert();</a><br>

### 対策済

<a href="./link?url=http://localhost:8080/">http://localhost:8080/</a><br>
<a href="./link?url=https://google.co.jp/">https://google.co.jp/</a><br>
<a href="./link?url=https://google.co.jp/ onclick=alert(document.cookie);">https://google.co.jp/ onclick=alert();</a><br>
<a href="./link?url=javascript:alert();">javascript:alert(document.cookie);</a><br>

# JavaScriptのエスケープ

JavaScriptの文字列リテラルとしてエスケープすべき文字

- \ => \\
- ' => \'
- " => \"
- 改行 => \n

JavaScriptにおいては文字参照も解釈してしまうので、`'`=>`&#39;`へのエスケープではだめ。

※TODO バッククォートはどうなんだろう？

# イベントハンドラのXSS

## 未対策

<a href="./jsEventUnsafe?message=hello">message=hello</a><br>
<a href="./jsEventUnsafe?message=');alert(document.cookie)//">message=');alert(document.cookie)//</a>

## 対策済

<a href="./jsEvent?message=hello">message=hello</a><br>
<a href="./jsEvent?message=');alert(document.cookie)//">message=');alert(document.cookie)//</a>

## おまけ

JavaScriptのテンプレートリテラル

<a href="./jsEvent2?message=「${personalData}」">message=${personalData}</a><br>
<a href="./jsEvent2?message=`);alert(document.cookie)//">message=`);alert(document.cookie)//</a><br>

# script要素内のXSS

script要素内はタグや文字参照を解釈しないので、HTMLエスケープは必要ない

JavaScriptとしてのエスケープをする。しかしそれだけでは不十分。

`</script>`という文字列が出現しないようにする。

※今回使ったテンプレートエンジン(Pebble)ではJavaScriptのエスケープ処理でうまいことやってくれてました。<br>
<のあとの/はエスケープする仕様らしい

[unbescape: powerful, fast and easy escape/unescape operations for Java](https://www.unbescape.org/)


## 未対策

<a href="./scriptTagUnsafe?logMsg=hello">hello</a><br>
<a href="./scriptTagUnsafe?logMsg=</script><script>alert(document.cookie)//">&lt;/script>&lt;script>alert(document.cookie)//</a>

## 対策済

<a href="./scriptTag?logMsg=hello">hello</a><br>
<a href="./scriptTag?logMsg=</script><script>alert(document.cookie)//">&lt;/script>&lt;script>alert(document.cookie)//</a>

script要素の外でパラメータを定義して、JavaScriptから参照するようにする方法もある。
```html
<div id="e1" data-foo="hello">
</div>
```

```js
var e1 = document.getElementById("e1");
console.log(e1.dataset.hello);
```

# インラインJSONP

```html
<script>
    display_length({"name": "徳丸"});
</script>
```

カスタムデータ属性とインラインJSONPをもちいる方法は安全性という観点では差が無いので、
使っているフレームワークなどで決めてください。

# HTMLやCSSを許す場合

SNSやブログなどは許したい場合ありますよね。

HTMLの構文を解析して、表示していい要素のみを抽出する方法があります。
そのためのライブラリもあります。

[HTML Purifier \- Filter your HTML the standards\-compliant way\!](http://htmlpurifier.org/)

# おまけ

CSSのexprssionsという機能でjavascriptを起動できてしまう（IEの拡張機能…、デフォルトではOFF）

[Masato Kinugawa Security Blog](https://masatokinugawa.l0.cm/)

