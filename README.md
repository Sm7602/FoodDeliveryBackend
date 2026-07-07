
# 🍔 Food Delivery Backend — JWT Authentication

A REST API backend for a food delivery platform built with **Spring Boot 3.5**, **Spring Security 6**, and stateless **JWT authentication**. It models the complete delivery domain — customers, restaurants, menus, carts, orders, order items, and delivery partners — with **role-based access control** and MySQL persistence via Spring Data JPA.

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen?logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6-green?logo=springsecurity)
![JWT](https://img.shields.io/badge/JJWT-0.13.0-blueviolet)
![MySQL](https://img.shields.io/badge/MySQL-8-blue?logo=mysql)
![Maven](https://img.shields.io/badge/Build-Maven-red?logo=apachemaven)

---

## ✨ Features

- **Stateless JWT authentication** — HS256-signed tokens issued on register/login (JJWT 0.13)
- **Three user roles** — `CUSTOMER`, `RESTAURANT`, `DELIVERYPARTNER`, each with its own registration endpoint and profile entity
- **Role-based URL authorization** — Spring Security route rules restrict each API area to its role
- **BCrypt password hashing** via `DaoAuthenticationProvider`
- **Custom `OncePerRequestFilter`** validating `Authorization: Bearer <token>` on every request
- **Full CRUD REST APIs** across 8 controllers for the entire delivery domain
- **Rich JPA data model** — `@OneToOne`, `@OneToMany`, `@ManyToOne` relationships
- **Duplicate-email guard** on all registration flows (returns `409 Conflict`)
- **Global exception handling** — `@RestControllerAdvice` with custom exceptions and structured JSON error responses (`400/401/404/409/500`)
- **Request validation** — `@Valid` with Bean Validation (`@NotBlank`, `@Email`, `@Size`) on all auth DTOs
- **Externalized configuration** — DB credentials, JWT secret & expiry via environment variables
- **Lombok** builders and constructor injection throughout

---

## 🏗️ Architecture

Classic layered architecture:

```
Client ──▶ JwtAuthenticationFilter ──▶ Controller ──▶ Service ──▶ Repository (DAO) ──▶ MySQL
                    │
                    ▼
               JwtService ◀──▶ UserDetailsService (SecurityContext)
```

```
src/main/java/com/fdb/api
├── FoodDeliveryBackendApplication.java   # Spring Boot entry point
├── controller/     # 8 REST controllers (auth + 7 domain resources)
├── service/        # AuthenticationService + 7 domain services
├── dao/            # 8 Spring Data JPA repositories
├── entity/         # User, Customer, Restaurant, MenuItem, Cart,
│                   # FoodOrder, OrderItem, DeliveryPartner, Role (enum)
├── dto/            # Register requests (per role), AuthenticationRequest/Response
├── exception/      # Custom exceptions + GlobalExceptionHandler (@RestControllerAdvice)
└── security/
    ├── SecurityConfiguration.java    # SecurityFilterChain — stateless, role rules, CSRF off
    ├── ApplicationConfig.java        # UserDetailsService, AuthProvider, BCrypt
    ├── JwtService.java               # Token generation / parsing / validation
    └── JwtAuthenticationFilter.java  # Once-per-request Bearer token filter
```

---

## 🔐 Authentication & Authorization

1. **Register** (per role) → user saved with BCrypt-hashed password + role-specific profile created → JWT returned
2. **Login** → credentials verified by `AuthenticationManager` → JWT returned
3. **Authenticated requests** → filter extracts email from the token, loads the user, validates signature + expiry, populates the `SecurityContext`
4. **Stateless** — `SessionCreationPolicy.STATELESS`, no server-side sessions

### Auth endpoints (`/api/auth` — public)

| Method | Endpoint | Body highlights |
|--------|----------|-----------------|
| POST | `/api/auth/registerCustomer` | name, email, password, phone, address |
| POST | `/api/auth/registerRestaurant` | restaurantName, ownerName, email, password, phone, address |
| POST | `/api/auth/registerDeliveryPartner` | name, email, password, vehicle & license details |
| POST | `/api/auth/authenticate` | email, password |

### Route authorization

| Path | Access |
|------|--------|
| `/api/auth/**` | Public |
| `/api/customers/**` | `ROLE_CUSTOMER` |
| `/api/restaurants/**` | `ROLE_RESTAURANT` |
| `/api/delivery-partners/**` | `ROLE_DELIVERYPARTNER` |
| Everything else | Any authenticated user |

---

## 📡 Domain API Overview

| Resource | Base Path | Operations |
|----------|-----------|------------|
| Customers | `/api/customers` | Create, Get by ID, Get all, Update, Delete |
| Restaurants | `/api/restaurants` | CRUD + `GET /search` |
| Menu Items | `/api/menu-items` | Create per restaurant, CRUD, `GET /restaurant/{restaurantId}` |
| Carts | `/api/carts` | Create/Get per customer, Update, Delete, Remove items |
| Orders | `/api/orders` | Create, Get by ID, Get all, Update, Delete |
| Order Items | `/api/order-items` | Create (`/{orderId}/{menuItemId}`), CRUD |
| Delivery Partners | `/api/delivery-partners` | Create, Get by ID, Get all, Update, Delete |

---

## 🗄️ Data Model

- **User** implements `UserDetails` (email as username, enum `Role`) — linked 1—1 to a Customer / Restaurant / DeliveryPartner profile
- **Customer** 1—1 **Cart**, 1—N **FoodOrder**
- **Restaurant** 1—N **MenuItem**, 1—N **FoodOrder**
- **FoodOrder** N—1 Customer / Restaurant / DeliveryPartner, 1—N **OrderItem**
- **OrderItem** N—1 FoodOrder, N—1 MenuItem

---

## 🚀 Getting Started

### Prerequisites

- Java 21+
- MySQL 8.x
- Maven (or the bundled `mvnw` wrapper)

### Setup

```bash
# 1. Clone
git clone https://github.com/<your-username>/FoodDeliveryBackend-JWT-Authentication.git
cd FoodDeliveryBackend-JWT-Authentication

# 2. Create the database
mysql -u root -p -e "CREATE DATABASE fooddeliverybackend;"

# 3. Set environment variables (all have local-dev defaults)
export DB_URL="jdbc:mysql://localhost:3306/fooddeliverybackend"
export DB_USERNAME="root"
export DB_PASSWORD="your-password"
export JWT_SECRET="<base64-encoded-256-bit-key>"   # e.g. openssl rand -base64 32
export JWT_EXPIRATION=86400000                      # 24 hours in ms

# 4. Run
./mvnw spring-boot:run
```

The API starts at **http://localhost:8080**. Hibernate creates the schema automatically (`ddl-auto=update`).

### Quick test

```bash
# Register a customer
curl -X POST http://localhost:8080/api/auth/registerCustomer \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John","lastName":"Doe","email":"john@test.com","password":"secret123","phone":"9999999999","address":"Kolkata"}'

# Login
curl -X POST http://localhost:8080/api/auth/authenticate \
  -H "Content-Type: application/json" \
  -d '{"email":"john@test.com","password":"secret123"}'

# Call a protected endpoint with the token
curl http://localhost:8080/api/customers \
  -H "Authorization: Bearer <YOUR_TOKEN>"
```

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 3.5.3 (Web, Data JPA, Security, Validation) |
| Auth | JJWT 0.13.0 (HS256), BCrypt |
| Database | MySQL 8 + Hibernate (`ddl-auto=update`) |
| Boilerplate | Lombok |
| Build | Maven |

---

## 🚦 Error Handling

All errors return a consistent JSON structure:

```json
{
  "timestamp": "2026-07-07T12:00:00",
  "status": 409,
  "error": "Conflict",
  "message": "Email is already registered. Please login.",
  "fieldErrors": null
}
```

| Case | Status |
|------|--------|
| Validation failure (`@Valid`) | `400` + per-field errors |
| Wrong email/password | `401` |
| Resource not found | `404` |
| Email already registered | `409` |
| Unexpected error | `500` |

---

## 🗺️ Roadmap

- [x] Externalize JWT secret & DB credentials to environment variables
- [x] Configurable token expiry (24 h default)
- [x] Global exception handling with `@RestControllerAdvice` + custom exceptions
- [x] Request validation with `@Valid` on DTOs
- [ ] Refresh tokens
- [ ] Swagger / OpenAPI documentation
- [ ] Unit & integration tests (JUnit 5, MockMvc, Testcontainers)
- [ ] Pagination & sorting on list endpoints
- [ ] Docker Compose setup (app + MySQL)

---

## 📄 License

Open source — free to use for learning purposes.
