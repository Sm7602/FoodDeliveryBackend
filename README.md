# 🍔 Food Delivery Backend — JWT Authentication

A production-style **REST API backend for a food delivery platform**, built with **Spring Boot 3.5**, **Spring Security 6**, and **JWT (JSON Web Token)** stateless authentication. It models the full delivery domain — customers, restaurants, menus, carts, orders, order items, and delivery partners — backed by **MySQL** via Spring Data JPA.

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen?logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6-green?logo=springsecurity)
![JWT](https://img.shields.io/badge/JJWT-0.13.0-blueviolet)
![MySQL](https://img.shields.io/badge/MySQL-8-blue?logo=mysql)
![Maven](https://img.shields.io/badge/Build-Maven-red?logo=apachemaven)

---

## ✨ Features

- **JWT-based stateless authentication** — register & login endpoints issue signed HS256 tokens (JJWT 0.13)
- **Role-based user model** — `CUSTOMER`, `DELIVERYPARTNER`, `RESTURANT` roles via Spring Security `GrantedAuthority`
- **BCrypt password hashing** with `DaoAuthenticationProvider`
- **Custom `OncePerRequestFilter`** that validates the `Authorization: Bearer <token>` header on every request
- **Full CRUD REST APIs** for the entire food-delivery domain (8 controllers)
- **Rich JPA data model** with `@OneToOne`, `@OneToMany`, `@ManyToOne` relationships
- **Lombok** for boilerplate-free entities, DTOs, and constructor injection
- **Auto schema generation** (`ddl-auto=update`) with formatted SQL logging

---

## 🏗️ Architecture

The project follows a classic **layered architecture**:

```
Client ──▶ JwtAuthenticationFilter ──▶ Controller ──▶ Service ──▶ Repository (DAO) ──▶ MySQL
                    │
                    ▼
               JwtService ◀──▶ UserDetailsService (SecurityContext)
```

```
src/main/java/com/fdb/api
├── FoodDeliveryBackendApplication.java   # Spring Boot entry point
├── controller/        # REST layer — 8 controllers (@RestController)
│   ├── AuthgenticationController.java    # /api/auth (register, authenticate)
│   ├── CustomerController.java           # /api/customers
│   ├── RestaurantController.java         # /api/restaurants
│   ├── MenuItemController.java           # /api/menu-items
│   ├── CartController.java               # /api/carts
│   ├── FoodOrderController.java          # /api/orders
│   ├── OrderItemController.java          # /api/order-items
│   └── DeliveryPartnerController.java    # /api/delivery-partners
├── service/           # Business logic (AuthenticationService + 7 domain services)
├── dao/               # Spring Data JPA repositories (8 interfaces)
├── entity/            # JPA entities: User, Customer, Restaurant, MenuItem,
│                      #   Cart, FoodOrder, OrderItem, DeliveryPartner, Role
├── dto/               # RegisterRequest, AuthenticationRequest, AuthenticationResponse
└── security/          # JWT + Spring Security configuration
    ├── SecurityConfiguration.java        # SecurityFilterChain (stateless, CSRF off)
    ├── ApplicationConfig.java            # UserDetailsService, AuthProvider, BCrypt
    ├── JwtService.java                   # Token generation / parsing / validation
    └── JwtAuthenticationFilter.java      # Once-per-request Bearer token filter
```

---

## 🔐 Authentication Flow

1. **Register** — `POST /api/auth/register` → user saved with BCrypt-hashed password → JWT returned
2. **Login** — `POST /api/auth/authenticate` → credentials verified by `AuthenticationManager` → JWT returned with email & role
3. **Authenticated requests** — client sends `Authorization: Bearer <token>`; `JwtAuthenticationFilter` extracts the email, loads the user, validates signature + expiry, and populates the `SecurityContext`
4. **Stateless sessions** — `SessionCreationPolicy.STATELESS`, no server-side session

### Auth API

| Method | Endpoint | Body | Response |
|--------|----------|------|----------|
| POST | `/api/auth/register` | `{ firstname, lastname, email, password, role }` | `{ token }` |
| POST | `/api/auth/authenticate` | `{ email, password }` | `{ token, email, role }` |

---

## 📡 Domain API Overview

| Resource | Base Path | Operations |
|----------|-----------|------------|
| Customers | `/api/customers` | Create, Get by ID, Get all, Update, Delete |
| Restaurants | `/api/restaurants` | CRUD + `GET /search` |
| Menu Items | `/api/menu-items` | CRUD + `GET /restaurant/{restaurantId}` |
| Carts | `/api/carts` | Create/Get per customer, Update, Delete, Remove items |
| Orders | `/api/orders` | Create, Get by ID, Get all, Update, Delete |
| Order Items | `/api/order-items` | Create (`/{orderId}/{menuItemId}`), CRUD |
| Delivery Partners | `/api/delivery-partners` | Create, Get by ID, Get all, Update, Delete |

---

## 🗄️ Data Model

- **Customer** 1—1 **Cart**, 1—N **FoodOrder**
- **Restaurant** 1—N **MenuItem**, 1—N **FoodOrder**
- **FoodOrder** N—1 Customer / Restaurant / DeliveryPartner, 1—N **OrderItem**
- **OrderItem** N—1 FoodOrder, N—1 MenuItem
- **Cart** 1—N MenuItem
- **User** implements `UserDetails` (email as username, enum `Role`)

---

## 🚀 Getting Started

### Prerequisites

- Java 21+
- MySQL 8.x
- Maven (or use the bundled `mvnw` wrapper)

### Setup

```bash
# 1. Clone
git clone https://github.com/<your-username>/FoodDeliveryBackend-JWT-Authentication.git
cd FoodDeliveryBackend-JWT-Authentication

# 2. Create the database
mysql -u root -p -e "CREATE DATABASE fooddeliverybackend;"

# 3. Configure src/main/resources/application.properties
#    spring.datasource.username / spring.datasource.password

# 4. Run
./mvnw spring-boot:run
```

The API starts at **http://localhost:8080**.

### Quick test

```bash
# Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"firstname":"John","lastname":"Doe","email":"john@test.com","password":"secret123","role":"CUSTOMER"}'

# Login
curl -X POST http://localhost:8080/api/auth/authenticate \
  -H "Content-Type: application/json" \
  -d '{"email":"john@test.com","password":"secret123"}'

# Use the token
curl http://localhost:8080/api/restaurants \
  -H "Authorization: Bearer <YOUR_TOKEN>"
```

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 3.5.3 (Web, Data JPA, Security, Validation) |
| Auth | JJWT 0.13.0 (HS256), BCrypt |
| Database | MySQL 8 (Hibernate, `ddl-auto=update`) |
| Boilerplate | Lombok |
| Build | Maven |

---

## ⚠️ Notes & Roadmap

- `/api/**` is currently `permitAll()` in `SecurityConfiguration` — tighten to `/api/auth/**` only and require authentication elsewhere, then add `@PreAuthorize` role checks per endpoint.
- Move the JWT secret and DB credentials out of `application.properties` into environment variables (`${JWT_SECRET}`); never commit secrets.
- Token expiry is currently ~24 seconds (`1000*60*24` ms) — likely intended as 24 hours (`1000*60*60*24`).
- Planned: refresh tokens, global exception handling (`@ControllerAdvice`), Swagger/OpenAPI docs, unit & integration tests.

---

## 📄 License

Open source — free to use for learning purposes.
