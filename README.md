# Notes API Full-Stack Project

This project contains a scalable REST API backend built with Java (Spring Boot) and a supportive frontend built with React (Vite).

## Tech Stack
- **Backend:** Java 17, Spring Boot 3.5, Spring Security, JWT, MySQL, Swagger/OpenAPI.
- **Frontend:** React, Vite, TypeScript, TailwindCSS v4, React Router Dom, Axios.

## Prerequisites
- Java 17+
- Node.js 18+
- MySQL Server (running locally with `notesdb` database created)

## Backend Setup (Spring Boot)
1. Open the `notes-api` folder.
2. Verify that your MySQL server is running and credentials match `src/main/resources/application.properties` (username: `root`, password: `Praveen@2005`).
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
4. The API will be available at `http://localhost:8080/api/v1`.
5. Swagger API Documentation is available at `http://localhost:8080/swagger-ui.html`.

## Frontend Setup (React)
1. Open the `notes-frontend` folder.
2. Install dependencies:
   ```bash
   npm install
   ```
3. Run the development server:
   ```bash
   npm run dev
   ```

## Features
- **Authentication:** JWT-based user registration and login.
- **Role-Based Access Control (RBAC):** Users can manage their own notes. Admins can view/manage all notes across the platform.
- **CRUD Operations:** Create, Read, Update, and Delete notes.
