# API仕様書 (API_SPEC)

本ドキュメントは、ポートフォリオサイトに実装されている全エンドポイントの仕様をまとめたものです。
要件にあるJSON形式でレスポンスを返す「REST API」と、画面遷移やHTMLを返す「Webエンドポイント」に分けて記載しています。

---

## 1. REST API エンドポイント

JSON形式でデータをやり取りするAPIです。

### 1-1. コメント投稿API

- **URL**: `/api/comments`
- **HttpMethod**: `POST`
- **概要**: 新しいコメントを投稿します。

#### リクエストパラメータ (ボディ / JSON)

| 項目名  | 型     | 必須/任意 | 説明                                                                     |
| ------- | ------ | --------- | ------------------------------------------------------------------------ |
| name    | String | 任意      | 投稿者名。未入力または空白の場合はデフォルトで「名無しさん」となります。 |
| content | String | 必須      | コメント内容。空文字などはエラーとなります。                             |

**リクエスト例**:

```json
{
  "name": "Kate",
  "content": "素晴らしいポートフォリオですね！"
}
```

#### 成功時のレスポンス例 (200 OK)

登録されたコメントオブジェクトが返却されます。

```json
{
  "id": 1,
  "name": "Kate",
  "content": "素晴らしいポートフォリオですね！",
  "createdAt": "2026-02-27T16:18:20"
}
```

#### 発生しうるエラーコードとその理由

| HTTPステータスコード | エラー理由                                                                                      |
| -------------------- | ----------------------------------------------------------------------------------------------- |
| 400 Bad Request      | `content`（コメント内容）が `null` または空白のみの場合にバリデーションエラーとして発生します。 |

---

## 2. Webエンドポイント（HTML / フォーム送受信）

HTMLビューの返却や、フォーム送信時のリダイレクトを行うエンドポイントです。（JSON形式のレスポンスは持ちません）

### 2-1. ブログ関連

#### ブログ画面表示

- **URL**: `/blog`
- **HttpMethod**: `GET`
- **リクエストパラメータ (クエリ)**:
  - `showAll` (boolean, 任意) : デフォルトは `false`
- **レスポンス (200 OK)**: `blog.html` (HTMLビュー)

#### ブログ記事投稿処理

- **URL**: `/blog/post`
- **HttpMethod**: `POST`
- **リクエストパラメータ (フォームデータ `application/x-www-form-urlencoded`)**:
  - `title` (String, 必須)
  - `content` (String, 必須)
- **レスポンス (302 Found)**: `/blog` へのリダイレクト

### 2-2. お問い合わせ関連

#### お問い合わせ画面表示

- **URL**: `/contact`
- **HttpMethod**: `GET`
- **リクエストパラメータ**: なし
- **レスポンス (200 OK)**: `contact.html` (HTMLビュー)

#### お問い合わせ送信処理

- **URL**: `/contact`
- **HttpMethod**: `POST`
- **リクエストパラメータ (フォームデータ `application/x-www-form-urlencoded`)**:
  - `name` (String, 必須): 最大100文字
  - `email` (String, 必須): メールアドレス形式、最大100文字
  - `subject` (String, 任意): 最大255文字
  - `message` (String, 必須)
- **レスポンス**:
  - 成功時 (302 Found): `/contact/success` へのリダイレクト
  - 失敗時 (200 OK): バリデーションエラー時は再度 `contact.html` を返却

#### お問い合わせ完了画面表示

- **URL**: `/contact/success`
- **HttpMethod**: `GET`
- **リクエストパラメータ**: なし
- **レスポンス (200 OK)**: `contact_success.html` (HTMLビュー)

### 2-3. 各種ページ表示（固定ページ）

いずれもリクエストパラメータはなく、200 OK で対応するHTMLビューを返却します。

- **インデックス画面表示**
  - **URL**: `/`
  - **HttpMethod**: `GET`
  - **レスポンス**: `index.html` (HTMLビュー)
- **About画面表示**
  - **URL**: `/about`
  - **HttpMethod**: `GET`
  - **レスポンス**: `about.html` (HTMLビュー)
- **Skills画面表示**
  - **URL**: `/skills`
  - **HttpMethod**: `GET`
  - **レスポンス**: `skills.html` (HTMLビュー)
- **Works画面表示**
  - **URL**: `/works`
  - **HttpMethod**: `GET`
  - **レスポンス**: `works.html` (HTMLビュー)
