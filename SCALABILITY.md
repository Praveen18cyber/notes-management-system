# System Scalability Note

This document outlines how the Notes API backend can be scaled to handle increased traffic and larger user bases as the application grows.

## 1. Architectural Evolution (Microservices)
Currently, the application is built as a monolith using Spring Boot. While effective for initial development, separating concerns into Microservices will aid scaling:
- **Auth Service:** A dedicated service for handling JWT generation, user registration, and login.
- **Notes Service:** A service strictly for managing the CRUD operations of Notes.
This allows individual services to be scaled independently (e.g., if Notes read operations spike, only the Notes Service needs scaling).

## 2. Caching (Redis)
To reduce database load and improve response times, caching can be introduced:
- **Session/Token Blacklisting:** Redis can be used to store invalidated JWT tokens when a user logs out.
- **Data Caching:** Frequently accessed data, such as a user's list of notes, can be cached in Redis. Using Spring `@Cacheable`, we can avoid hitting MySQL for every `GET /notes` request.

## 3. Load Balancing & Deployment (Docker & Kubernetes)
- **Containerization:** The Spring Boot API and React frontend can be Dockerized. This ensures consistency across development, staging, and production environments.
- **Load Balancing:** Deploying multiple instances of the backend API behind a Load Balancer (like Nginx, AWS ALB, or Kubernetes Ingress) will distribute incoming traffic evenly, preventing any single instance from becoming a bottleneck.
- **Database Scaling:** MySQL can be scaled using Read Replicas (for handling heavy read operations) while keeping a primary instance for write operations.

## 4. Security Enhancements
- **Rate Limiting:** Implement rate limiting (e.g., using Redis and Spring Cloud Gateway) to protect against DDoS attacks and brute-force login attempts.
- **API Gateway:** An API Gateway can act as a single entry point, handling routing, rate limiting, and SSL termination.
