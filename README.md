# ️FitFlow - Enterprise Gym Management SaaS Platform

> A robust, scalable, and secure backend system built to manage multi-gym operations, membership subscriptions, financial transactions, and user health tracking.

##  Project Overview
This project is a centralized platform designed to bridge the gap between **Gym Owners**, **Trainers**, and **Members**. It solves the problem of manual gym management by automating membership tracking, access control, and financial reporting.

Built with **Java Spring Boot**, it adheres to strict **RESTful API** standards and implements **State-of-the-Art Security** using JWT and Role-Based Access Control (RBAC).

## Tech Stack
*   **Backend:** Java 21, Spring Boot 3
*   **Security:** Spring Security 6, JWT (JSON Web Tokens), BCrypt Encryption
*   **Database:** PostgreSQL / MySQL (JPA & Hibernate)
*   **Payments:** Razorpay Payment Gateway Integration
*   **Architecture:** MVC, N-Tier (Controller-Service-Repository)

##  Key Features

### 1. Advanced Security & Auth
*   **RBAC (Role-Based Access Control):** Distinct logic flows for `OWNER`, `TRAINER`, and `MEMBER`.
*   **JWT Authentication:** Stateless security filter chain protecting all API endpoints.
*   **Secure Registration:** Logic to prevent unauthorized privilege escalation (e.g., users cannot register as Trainers directly).

### 2. Gym Business Automation
*   **Multi-Gym Management:** Owners can create and manage multiple gym locations and facilities.
*   **Staff Management:** Owners can promote Members to Trainers, granting specific data access privileges.
*   **Subscription Logic:** Dynamic plan creation (Gold/Silver) with automated expiration tracking.

### 3. Financial Integration
*   **Razorpay Integration:** End-to-end payment flow (Order Creation -> Payment Verification -> Signature Validation).
*   **Transaction Logging:** Immutable record keeping of all financial transactions for audit purposes.

### 4. Health & Automation
*   **Smart Check-In:** Logic to validate active subscriptions upon QR code scan.
*   **Workout & Diet Logging:** Complex data relationships allowing users to track sets, reps, and macros.



##  Future Roadmap (AI & Automation)
*   **Python Microservice:** Currently developing a Python flask service to predict user churn based on attendance logs.
*   **AI Recommendations:** Implementing an ML model to suggest workout weights based on previous log history.

---
*Built by [Amit Yadav]*
