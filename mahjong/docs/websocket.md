
### WebSocket(chat)
http://localhost:8089/index.html
```mermaid
graph LR
subgraph index.html
  subgraph send message
    name
    message
    send
  end
  greetings
end
subgraph app.js
  sendMessage
  showGreeting
end
subgraph MessageController
  MessageMapping
  greeting
  SendTo
end

send-->sendMessage
sendMessage-->MessageMapping
MessageMapping-->greeting
greeting-->SendTo
SendTo-->showGreeting
showGreeting-->greetings
```