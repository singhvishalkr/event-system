

# 📩 Event Notification System – Java Backend Assignment

A Java Spring Boot application that accepts and processes EMAIL, SMS, and PUSH notifications asynchronously using dedicated queues and threads, with callback notifications, simulated failures, graceful shutdown, and full Docker and test support.

---

## 📌 Features

✅ Accepts `EMAIL`, `SMS`, and `PUSH` events via REST API  
✅ Each event type has its own **FIFO queue** and **dedicated thread**  
✅ Events are processed **asynchronously** with fixed delay  
✅ **10% random failure simulation**  
✅ Sends **callback POST** to client with final status  
✅ Gracefully shuts down and cleans up all threads  
✅ Fully **Dockerized** (with `Dockerfile` and `docker-compose.yml`)  
✅ Includes **JUnit test coverage**

---

## 📦 Technologies Used

- Java 21
- Spring Boot 3.x
- Spring Web, Validation, Lombok
- JUnit 5, Mockito
- Docker & Docker Compose
- Maven

---

## 🚀 API Specification

### ➕ POST `/api/events`

#### 📥 Request Body
```json
{
  "eventType": "EMAIL",
  "callbackUrl": "http://localhost:8081/callback",
  "payload": {
    "recipient": "user@example.com",
    "message": "Welcome to our service!"
  }
}

📤 Response

{
  "eventId": "e123",
  "message": "Event accepted for processing."
}


⸻

⚙️ Event Rules

Type	Delay	Payload Fields
EMAIL	5 sec	recipient, message
SMS	3 sec	phoneNumber, message
PUSH	2 sec	deviceId, message

	•	FIFO ordering per queue
	•	Random 10% failure
	•	POST callback with result after processing

⸻

🔁 Callback Example

✅ On Success

{
  "eventId": "e123",
  "status": "COMPLETED",
  "eventType": "EMAIL",
  "processedAt": "2025-07-01T12:34:56Z"
}

❌ On Failure

{
  "eventId": "e123",
  "status": "FAILED",
  "eventType": "EMAIL",
  "errorMessage": "Simulated processing failure",
  "processedAt": "2025-07-01T12:34:56Z"
}


⸻

🧪 Test Coverage

✔ Valid Event Submission
✔ Invalid EventType or Missing Fields → HTTP 400
✔ Callback sent on success/failure
✔ Random failure simulation
✔ Graceful shutdown and thread cleanup

See test classes:
	•	EventServiceImplTest.java
	•	EventControllerTest.java
	•	CallbackClientTest.java

⸻

🐳 Dockerized Setup

📄 Dockerfile & Compose
	•	Exposes port 8080
	•	Start with:

mvn clean package
docker compose up --build

Access API at: http://localhost:8080/api/events

⸻

🔧 Postman Collection

Use the provided collection in:

postman/event-notification-system.postman_collection.json


⸻

🧹 Graceful Shutdown

Press Ctrl+C →
✅ Accepts no new events
✅ Waits for all queued events to finish
✅ Cleanly terminates all threads

⸻

📁 Project Structure

src/
├── controller/
├── dto/
├── model/
├── processor/
├── Implementation/
├── Interface/
├── client/
├── resources/
└── test/

