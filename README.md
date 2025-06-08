# User-Management-Service
Repository containing code for a user management service application
# User Management Service

Very Early stages of development. 
This repository contains a personal project aimed at building a **configurable authentication and user management platform**.  Users can register their own applications and then manage the roles and users associated with those applications.  Each application can delegate login to this service which issues JSON Web Tokens (JWT) for authentication.

The goal of this project is to create a service that manages authentication/ roles for applications that have been registered.
The project is still in its early stages and many areas are under active development, but the core pieces are in place to demonstrate the overall design.

## Key Features

* **Spring Boot backend** – exposes REST endpoints for user creation, authentication and application registration.  Security is implemented using Spring Security with custom authentication and authorisation filters.
* **JWT based authentication** – on successful login the backend returns an access token (and refresh token cookie) which clients can use to authenticate subsequent requests.
* **Role based access control** – users are assigned one of three system roles (`ADMIN`, `USER_MANAGER` or `BASIC_USER`).  Only user managers can create applications.  The intention is that each registered application will also define its own set of roles.
* **React frontend** – a minimal Vite/React application provides a login page and admin area.  Material‑UI is used for styling and there is an optional dark mode toggle.
* **Docker development environment** – running `docker-compose up` starts a Postgres database, the Spring Boot backend and the React frontend for local testing.
* **Extensive tests** – unit tests cover model validation, service layer logic and controller endpoints.

## Repository Structure

```
backend/   – Spring Boot service
frontend/  – React single-page app
docker-compose.yml – container configuration for local development
```

### Backend

The backend application exposes routes under `/api` and `/auth`.  The important endpoints at this stage are:

* `POST /auth/login` – authenticate and receive an access token.
* `GET /auth/refreshToken` – obtain new tokens using the refresh cookie.
* `POST /api/v1/applications` – register a new application (requires a user with the `USER_MANAGER` role).
* `POST /user` – create a new user.

See the source under [`backend/src/main/java`](backend/src/main/java) for details.

### Frontend

The frontend (under [`frontend/src`](frontend/src)) is a small React application bootstrapped with Vite.  It currently offers a login form and some placeholder pages inside a Material‑UI shell component.  The UI consumes the backend APIs and stores the authentication state using a React context.

## Running Locally

Ensure Docker is installed and then start the stack:

```bash
docker-compose up --build
```

This launches the Postgres database, the backend API on port `8080` and the frontend on port `3000`.

## Future Work

The project is under development and the following areas are planned:

* Application specific roles and user assignments.
* Improved error handling and validation across the API.
* Additional front‑end pages for user and application management.

---

This repository demonstrates my approach to building a full stack application with Spring Boot and React.  Feedback is always welcome!
