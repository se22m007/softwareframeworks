# How to run
## Kafka
Use "docker compose up" in the kafka folder

## Webserver
Run the spring boot project in the backend folder

## UI
Run "npm i" inside the frontend folder (NodeJS required) and "npm start" afterwards. After launching the frontend, a button can be pressed to trigger the producer on the backend. The application connects to a websocket on the backend and shows data sent over it.