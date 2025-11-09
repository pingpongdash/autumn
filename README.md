# Autumn Sample Project

This is a simplified sample project for learning **JWT-based authentication** with **Spring Boot** and **Spring Security**.
It demonstrates a multi-module setup with API and web view modules, and integrates OpenAPI (Swagger) for API documentation.

---

## 🔹 Project Modules

- `autumn-apis`
  Provides REST APIs including `/api/v1/login`. Handles user authentication and JWT token issuance.

- `autumn-auth-core`
  Contains JWT utilities and security filter configuration. Implements `JwtAuthenticationFilter` for token verification.

- `autumn-views`
  Provides Thymeleaf-based web views, including the login page and a protected dashboard page.

- `autumn-base`
  Aggregates other modules and serves as the main Spring Boot application runner.

---

## 🔹 Features

1. **JWT Authentication Flow**
   - Login with `loginId` and `password` via `/api/v1/login`.
   - Receive a JWT token and store it in `sessionStorage`.
   - Access protected pages (e.g., `/dashboard`) with `Authorization: Bearer <token>` header.

2. **URL-based Access Control**
   - Public endpoints: `/`, `/index`, `/css/**`, `/themes/**`, `/api/v1/login`
   - Authenticated endpoints: `/dashboard`
   - All other URLs are denied by default.

3. **Spring Security + Custom Filter**
   - Stateless authentication; no HTTP session is used.
   - `JwtAuthenticationFilter` intercepts requests and validates the token.
   - Authentication failures return HTTP 401 Unauthorized.

4. **OpenAPI / Swagger Integration**
   - API documentation and testing available at `/swagger-ui/index.html`.
   - Easy to test login API and see how JWT works with protected endpoints.

---

## 🔹 How to Run

1. Build and run the project:
```bash
mvn clean install
mvn spring-boot:run -pl autumn-base
````

2. Open Swagger UI:

```
http://localhost:8080/swagger-ui/index.html
```

3. Test login:

   * Send a POST request to `/api/v1/login` with `loginId` and `password`.
   * Copy the returned token.

4. Authorize in Swagger:

   * Click **Authorize** in Swagger UI.
   * Paste token as `Bearer <token>`.

5. Access protected endpoints with the token.

---

## 🔹 Notes for Learning

* This is a **simplified educational sample**:

  * Uses `document.write` for dashboard redirect (not recommended in production)
  * JWT is stored in `sessionStorage` (XSS risks if you are not careful)
  * Token expiration and refresh are not implemented

* The project demonstrates:

  * JWT authentication flow
  * Spring Security URL-based authorization
  * Multi-module project structure
  * OpenAPI / Swagger integration

* You can extend this project to:

  * Use a SPA frontend (React/Vue) with proper routing
  * Implement refresh tokens
  * Add CSRF/XSS protection for production

---

## 🔹 References

* [Spring Boot Security](https://spring.io/projects/spring-boot)
* [Springdoc OpenAPI](https://springdoc.org/)
* [JWT Introduction](https://jwt.io/introduction/)

