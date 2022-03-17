###

http://localhost:8088/v1/player/all
x-mahjong-user: eyJpc3MiOiJpc3MiLCAic3ViIjoic3ViIiwgInVzZXJuYW1lIjoidXNlcm5hbWUiLCAiYml6R3JvdXAiOiJiaXpHcm91cCIsICJjdXN0b21QYXJhbSI6ImN1c3RvbVBhcmFtIn0=

###

post http://localhost:8088/v1/player/new
x-mahjong-user: eyJpc3MiOiJpc3MiLCAic3ViIjoic3ViIiwgInVzZXJuYW1lIjoidXNlcm5hbWUiLCAiYml6R3JvdXAiOiJiaXpHcm91cCIsICJjdXN0b21QYXJhbSI6ImN1c3RvbVBhcmFtIn0=
request-id: test-from-vscode
content-type: application/json

{"nickname": "player5"}

###

POST http://localhost:8088/v1/player/new
x-mahjong-user:eyJpc3MiOiJpc3MiLCAic3ViIjoic3ViIiwgInVzZXJuYW1lIjoidXNlcm5hbWUiLCAiYml6R3JvdXAiOiJiaXpHcm91cCIsICJjdXN0b21QYXJhbSI6ImN1c3RvbVBhcmFtIn0= 
request-id:new
Content-Type:application/json

{"playerId":0, "nickname":"player7", "deletedFlg":false, "createdTimestamp":null, "createdUser":null, "updatedTimestamp":null, "updatedUser":null}

###

POST http://localhost:8088/v1/player/new
x-mahjong-user:eyJpc3MiOiJpc3MiLCAic3ViIjoic3ViIiwgInVzZXJuYW1lIjoidXNlcm5hbWUiLCAiYml6R3JvdXAiOiJiaXpHcm91cCIsICJjdXN0b21QYXJhbSI6ImN1c3RvbVBhcmFtIn0= 
request-id:new
Content-Type:application/json

{"nickname":"player7"}

