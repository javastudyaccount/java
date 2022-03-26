### api
1. Page not found(404)
2. Message not readable(400)
3. Input validation error(400)
   1. BadRequestException
      1. BindingResult
4. Data integrity violation(500)
   1. DuplicateKeyException
5. Communication error(500)
   1. DB stoped(Failed to obtain JDBC Connection)
6. Security error(400)
   1. x-mahjong-userヘッダが認識できませんでした。
   
### web
1. Page not found(404)
1. Input validation error
2. API error
3. Communication error
4. Security error