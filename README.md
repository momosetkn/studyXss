# try_scalatra #

## Build & Run ##

```sh
$ cd try_scalatra
$ sbt
> jetty:start
> browse
```

If `browse` doesn't launch your browser, manually open [http://localhost:8080/](http://localhost:8080/) in your browser.

# 表示処理に伴う問題

とは？
- XSS
- エラーメッセージからの漏洩

## XSSされると？
- Cookie値を盗まれて、なりすまされる
- Webアプリの機能を悪用される(CSRFされて殺人予告など)
- 偽の入力フォームを表示されて、フィッシング

## XSSの注意点

対策が必要な箇所が多いが、軽視されがち（と書いてあった）。使ってるテンプレートエンジンによるのかも？

