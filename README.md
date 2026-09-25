# Spring Boot Trivia Application

A full-stack trivia web application built with a Spring Boot backend. The frontend is composed of simple Javascript / HTML / CSS. 
The application fetches questions from the OpenTDB Database. The app transforms these questions server-side so that the correct answer is filtered out of the JSON. Correct answers are stored in memory using UUIDs.

## Prerequisites
Java 21 JDK installed 

---

## Running the application

1. Start the backend server:

```bash
./gradlew bootRun
```
2. Open in browser
Navigate to `http://localhost:8080`

--- 

## Running tests
All JUnit 5 and MockMvc tests can be run using the following command:
```bash
./gradlew test
```

---

## API Summary

* `GET /questions`
Accepts 'amount', 'category', 'difficulty', and 'type'. Returns questions with UUIDs and the answers in shuffled order.
* `POST /checkanswers`
Accepts a JSON array of question UUIDs. Returns a map of correct answers and their UUID.