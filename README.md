<div align="center">

# 🍔 Food Delivery Backend

### A multi-role REST API for a food delivery marketplace, built with Spring Boot 3 and stateless JWT authentication

[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)](https://spring.io/projects/spring-security)
[![JWT](https://img.shields.io/badge/JJWT-0.13.0-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)](https://github.com/jwtk/jjwt)
[![MySQL](https://img.shields.io/badge/MySQL-8.x-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Hibernate](https://img.shields.io/badge/Hibernate-6-59666C?style=for-the-badge&logo=hibernate&logoColor=white)](https://hibernate.org/)
[![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](#-license)

**8 Entities** · **8 Controllers** · **38 Endpoints** · **24 DTOs** · **3 User Roles**

</div>

---

## 📖 Overview

**Food Delivery Backend** is a monolithic REST API that models the complete domain of an online food-ordering marketplace. It supports three distinct participant types — **Customers**, **Restaurants**, and **Delivery Partners** — each with a dedicated registration flow that provisions both a security principal and a role-specific domain profile.

The API covers the full ordering lifecycle: restaurants publish menus, customers build carts, orders are placed with line items, and delivery partners are assigned. Authentication is **stateless JWT** (HS256), passwords are **BCrypt**-hashed, and persistence runs on **MySQL** through Spring Data JPA.

> **📌 Project status — read before deploying**
> This is a **learning / portfolio project**, not a production service. Several
> cross-cutting concerns are deliberately listed as open work in the
> [Roadmap](#-roadmap): global exception handling, externalised secrets,
> transaction boundaries, and automated tests are **not yet implemented**.
> The [Known Limitations](#-known-limitations) section documents these
> honestly rather than hiding them. **Do not deploy this to a public
> environment as-is.**

---

## ✨ Features

| | Feature | Details |
|---|---|---|
| 🔐 | **Stateless JWT authentication** | HS256-signed tokens issued on both registration and login via JJWT 0.13, 24-hour expiry |
| 👥 | **Three user roles** | `CUSTOMER`, `DELIVERYPARTNER`, `RESTURANT` — each with its own registration endpoint and profile entity |
| 🔑 | **BCrypt password hashing** | Via `DaoAuthenticationProvider` and Spring Security's `PasswordEncoder` abstraction |
| 🧩 | **Split principal / profile model** | `User` (implements `UserDetails`) is linked 1:1 to a `Customer`, `Restaurant`, or `DeliveryPartner` profile |
| 🛡️ | **Custom JWT filter** | `OncePerRequestFilter` validating `Authorization: Bearer <token>`, positioned before `UsernamePasswordAuthenticationFilter` |
| 🗂️ | **Full CRUD across 8 resources** | Customers, Restaurants, Menu Items, Carts, Orders, Order Items, Delivery Partners |
| 🔗 | **Rich JPA data model** | `@OneToOne`, `@OneToMany`, and `@ManyToOne` associations across 8 entities |
| ✅ | **Bean Validation on domain DTOs** | `@NotBlank`, `@NotNull`, `@Positive`, `@Min`, `@DecimalMin`, `@Digits`, `@Pattern` with custom messages |
| 📦 | **Strict DTO boundaries** | Separate `Request` / `UpdateRequest` / `Response` types per resource — entities are never accepted as request bodies |
| 🔍 | **Restaurant search** | Case-insensitive partial-name matching via a derived query |
| 💰 | **`BigDecimal` money handling** | All prices, subtotals, and totals — never floating point |
| 🏗️ | **Clean layered architecture** | Controller → Service → Repository, consistently enforced |

---

## 🛠️ Tech Stack

| Layer | Technology | Version |
|-------|-----------|---------|
| **Language** | Java | 21 |
| **Framework** | Spring Boot | 3.5.3 |
| **Web** | Spring Web MVC | Boot-managed |
| **Security** | Spring Security | 6.x |
| **Persistence** | Spring Data JPA + Hibernate | 6.x |
| **Validation** | Jakarta Bean Validation | Boot-managed |
| **Tokens** | JJWT (`jjwt-api` / `-impl` / `-jackson`) | 0.13.0 |
| **Database** | MySQL | 8.x |
| **Boilerplate** | Project Lombok | Boot-managed |
| **Build** | Apache Maven (+ wrapper) | 3.9+ |

---

## 🏗️ Architecture

```
┌──────────────────────────────────────────────────────────────────────┐
│                             CLIENT                                   │
│                  (Postman · Web · Mobile App)                        │
└─────────────────────────────┬────────────────────────────────────────┘
                              │  HTTP + Authorization: Bearer <JWT>
                              ▼
┌──────────────────────────────────────────────────────────────────────┐
│                   SPRING SECURITY FILTER CHAIN                       │
│                                                                      │
│   ┌────────────────────────────┐      ┌──────────────────────────┐   │
│   │  JwtAuthenticationFilter   │─────▶│       JwtService         │   │
│   │  (OncePerRequestFilter)    │      │  extract · verify · exp  │   │
│   └─────────────┬──────────────┘      └──────────────────────────┘   │
│                 │                                                    │
│                 ▼                     ┌──────────────────────────┐   │
│      ┌──────────────────────┐         │   UserDetailsService     │   │
│      │  SecurityContext     │◀────────│   (loads User by email)  │   │
│      └──────────┬───────────┘         └──────────────────────────┘   │
│                 │                                                    │
│      ┌──────────▼───────────┐                                        │
│      │  AuthorizationFilter │  route rules per role                  │
│      └──────────┬───────────┘                                        │
└─────────────────┼────────────────────────────────────────────────────┘
                  ▼
┌──────────────────────────────────────────────────────────────────────┐
│  CONTROLLER LAYER          @RestController · @Valid · DTO in/out      │
│  Auth · Customer · Restaurant · MenuItem · Cart · Order · OrderItem   │
│  · DeliveryPartner                                                   │
└─────────────────┬────────────────────────────────────────────────────┘
                  ▼
┌──────────────────────────────────────────────────────────────────────┐
│  SERVICE LAYER             @Service · business rules · entity↔DTO     │
└─────────────────┬────────────────────────────────────────────────────┘
                  ▼
┌──────────────────────────────────────────────────────────────────────┐
│  REPOSITORY LAYER (dao)    JpaRepository · derived queries            │
└─────────────────┬────────────────────────────────────────────────────┘
                  ▼
┌──────────────────────────────────────────────────────────────────────┐
│  MySQL 8   ·   Hibernate ORM   ·   9 tables                           │
└──────────────────────────────────────────────────────────────────────┘
```

---

## 📁 Folder Structure

```
FoodDeliveryBackend/
├── src/
│   ├── main/
│   │   ├── java/com/fdb/api/
│   │   │   ├── FoodDeliveryBackendApplication.java   # Spring Boot entry point
│   │   │   │
│   │   │   ├── controller/                           # HTTP boundary — 8 classes
│   │   │   │   ├── AuthenticationController.java     #   /api/auth
│   │   │   │   ├── CustomerController.java           #   /api/customers
│   │   │   │   ├── RestaurantController.java         #   /api/restaurants
│   │   │   │   ├── MenuItemController.java           #   /api/menu-items
│   │   │   │   ├── CartController.java               #   /api/carts
│   │   │   │   ├── FoodOrderController.java          #   /api/orders
│   │   │   │   ├── OrderItemController.java          #   /api/order-items
│   │   │   │   └── DeliveryPartnerController.java    #   /api/delivery-partners
│   │   │   │
│   │   │   ├── service/                              # Business logic — 8 classes
│   │   │   │   ├── AuthenticationService.java        #   register × 3 + login
│   │   │   │   ├── CustomerService.java
│   │   │   │   ├── RestaurantService.java
│   │   │   │   ├── MenuItemService.java
│   │   │   │   ├── CartService.java
│   │   │   │   ├── FoodOrderService.java
│   │   │   │   ├── OrderItemService.java
│   │   │   │   └── DeliveryPartnerService.java
│   │   │   │
│   │   │   ├── dao/                                  # Spring Data JPA — 8 interfaces
│   │   │   │   ├── UserRepository.java               #   findByEmail
│   │   │   │   ├── CustomerRepository.java           #   findByActiveTrue
│   │   │   │   ├── RestaurantRepository.java         #   findByRestaurantNameContainingIgnoreCase
│   │   │   │   ├── MenuItemRepository.java           #   findByRestaurant
│   │   │   │   ├── CartRepository.java               #   findByCustomerId
│   │   │   │   ├── FoodOrderRepository.java
│   │   │   │   ├── OrderItemRepository.java
│   │   │   │   └── DeliveryPartnerRepository.java
│   │   │   │
│   │   │   ├── entity/                               # JPA domain model — 8 + 1 enum
│   │   │   │   ├── User.java                         #   implements UserDetails
│   │   │   │   ├── Role.java                         #   enum: CUSTOMER · DELIVERYPARTNER · RESTURANT
│   │   │   │   ├── Customer.java
│   │   │   │   ├── Restaurant.java
│   │   │   │   ├── DeliveryPartner.java
│   │   │   │   ├── MenuItem.java
│   │   │   │   ├── Cart.java
│   │   │   │   ├── FoodOrder.java
│   │   │   │   └── OrderItem.java
│   │   │   │
│   │   │   ├── dto/                                  # API contracts — 24 classes
│   │   │   │   ├── auth/                             #   4 × register/login DTOs
│   │   │   │   ├── customer/                         #   Request · UpdateRequest · Response
│   │   │   │   ├── restaurant/
│   │   │   │   ├── menuitem/
│   │   │   │   ├── cart/
│   │   │   │   ├── foodorder/
│   │   │   │   ├── orderitem/
│   │   │   │   └── deliverypartner/
│   │   │   │
│   │   │   └── security/                             # Auth infrastructure — 4 classes
│   │   │       ├── SecurityConfiguration.java        #   SecurityFilterChain, route rules
│   │   │       ├── ApplicationConfig.java            #   UserDetailsService, AuthProvider, BCrypt
│   │   │       ├── JwtService.java                   #   generate · parse · validate
│   │   │       └── JwtAuthenticationFilter.java      #   per-request bearer token filter
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/java/com/fdb/api/
│       └── FoodDeliveryBackendApplicationTests.java
│
├── .mvn/wrapper/
├── mvnw · mvnw.cmd
├── pom.xml
└── README.md
```

---

## 🗄️ Database Schema

Hibernate generates the schema from the entities on startup (`ddl-auto=update`). Nine tables:

| Table | Purpose | Key Columns |
|-------|---------|-------------|
| `users` | Security principal | `id`, `firstname`, `lastname`, `email`, `password` (BCrypt), `role` |
| `customer` | Customer profile | `id`, `first_name`, `last_name`, `phone_number`, `address`, `active`, `user_id` → `users` |
| `restaurant` | Restaurant profile | `id`, `restaurant_name`, `owner_name`, `address`, `phone_number`, `rating`, `active`, `user_id` → `users` |
| `delivery_partner` | Delivery partner profile | `id`, names, `phone_number`, `vehicle_number`, `vehicle_type`, `driving_license_number`, `available`, `user_id` → `users` |
| `menu_item` | Dishes offered | `id`, `item_name`, `description`, `category`, `price`, `available`, `restaurant_id` → `restaurant` |
| `cart` | Customer shopping cart | `id`, `total_amount`, `active`, `customer_id` → `customer` |
| `cart_menu_items` | Cart ↔ MenuItem join | `cart_id`, `menu_items_id` |
| `food_order` | Placed order | `id`, `order_number`, `total_amount`, `order_status`, `delivery_address`, `order_time`, `customer_id`, `restaurant_id`, `delivery_partner_id` |
| `order_item` | Order line item | `id`, `quantity`, `item_price`, `subtotal`, `active`, `food_order_id`, `menu_item_id` |

### Entity Relationship Diagram

```
                           ┌───────────────────┐
                           │       USER        │
                           │  implements       │
                           │  UserDetails      │
                           │───────────────────│
                           │ id (PK)           │
                           │ email             │
                           │ password (BCrypt) │
                           │ role (ENUM)       │
                           └─────────┬─────────┘
                                     │
                 1:1 ────────────────┼──────────────── 1:1
                  │            1:1   │                   │
        ┌─────────▼─────────┐  ┌─────▼──────────┐  ┌─────▼───────────────┐
        │     CUSTOMER      │  │   RESTAURANT   │  │  DELIVERY_PARTNER   │
        │───────────────────│  │────────────────│  │─────────────────────│
        │ id (PK)           │  │ id (PK)        │  │ id (PK)             │
        │ first/last_name   │  │ restaurant_name│  │ first/last_name     │
        │ phone · address   │  │ owner_name     │  │ vehicle_number      │
        │ user_id (FK)      │  │ rating         │  │ vehicle_type        │
        └──┬─────────────┬──┘  │ user_id (FK)   │  │ available           │
           │ 1:1         │     └──┬──────────┬──┘  │ user_id (FK)        │
           │             │        │ 1:N      │     └──────────┬──────────┘
     ┌─────▼─────┐       │  ┌─────▼────────┐ │                │ 1:N
     │   CART    │       │  │  MENU_ITEM   │ │                │
     │───────────│       │  │──────────────│ │                │
     │ id (PK)   │       │  │ id (PK)      │ │                │
     │ total_amt │◀─────▶│  │ item_name    │ │                │
     │ customer_ │  N:M  │  │ price        │ │                │
     │  id (FK)  │       │  │ category     │ │                │
     └───────────┘       │  │ restaurant_id│ │                │
       (via join         │  └──────┬───────┘ │                │
        table)           │         │         │                │
                    1:N  │    N:1  │    1:N  │                │
                    ┌────▼─────────┼─────────▼────────────────▼────┐
                    │              FOOD_ORDER                      │
                    │──────────────────────────────────────────────│
                    │ id (PK) · order_number · total_amount        │
                    │ order_status · delivery_address · order_time │
                    │ customer_id (FK) · restaurant_id (FK)        │
                    │ delivery_partner_id (FK)                     │
                    └──────────────────┬───────────────────────────┘
                                       │ 1:N
                          ┌────────────▼─────────────┐
                          │        ORDER_ITEM        │
                          │──────────────────────────│
                          │ id (PK) · quantity       │
                          │ item_price · subtotal    │
                          │ food_order_id (FK)       │
                          │ menu_item_id (FK) ───────┼──▶ MENU_ITEM
                          └──────────────────────────┘
```

### Relationship Reference

| Relationship | Type | Owning Side | Join Column |
|---|---|---|---|
| `Customer` → `User` | One-to-One | `Customer` | `customer.user_id` |
| `Restaurant` → `User` | One-to-One | `Restaurant` | `restaurant.user_id` |
| `DeliveryPartner` → `User` | One-to-One | `DeliveryPartner` | `delivery_partner.user_id` |
| `Customer` → `Cart` | One-to-One | `Cart` | `cart.customer_id` |
| `Customer` → `FoodOrder` | One-to-Many | `FoodOrder` | `food_order.customer_id` |
| `Restaurant` → `MenuItem` | One-to-Many | `MenuItem` | `menu_item.restaurant_id` |
| `Restaurant` → `FoodOrder` | One-to-Many | `FoodOrder` | `food_order.restaurant_id` |
| `DeliveryPartner` → `FoodOrder` | One-to-Many | `FoodOrder` | `food_order.delivery_partner_id` |
| `FoodOrder` → `OrderItem` | One-to-Many | `OrderItem` | `order_item.food_order_id` |
| `OrderItem` → `MenuItem` | Many-to-One | `OrderItem` | `order_item.menu_item_id` |
| `Cart` → `MenuItem` | Unidirectional collection | join table | `cart_menu_items` |

---

## 🚀 Getting Started

### Prerequisites

| Requirement | Minimum Version | Verify |
|-------------|-----------------|--------|
| **JDK** | 21 | `java -version` |
| **MySQL** | 8.0 | `mysql --version` |
| **Maven** | 3.9+ (or use `./mvnw`) | `mvn -v` |
| **Git** | any | `git --version` |

### 1️⃣ Clone the repository

```bash
git clone https://github.com/Sm7602/FoodDeliveryBackend-JWT-Authentication.git
cd FoodDeliveryBackend-JWT-Authentication
```

### 2️⃣ Create the database

```bash
mysql -u root -p -e "CREATE DATABASE fooddeliverybackend CHARACTER SET utf8mb4;"
```

Hibernate creates all nine tables automatically on first startup.

### 3️⃣ Configure the application

Edit `src/main/resources/application.properties`:

```properties
spring.application.name=FoodDeliveryBackend
server.port=8080

# ---- Datasource ----
spring.datasource.url=jdbc:mysql://localhost:3306/fooddeliverybackend
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD_HERE

# ---- JPA / Hibernate ----
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# ---- Logging ----
logging.level.org.springframework.security=DEBUG
```

> **⚠️ Never commit real credentials.**
> The current `application.properties` holds the datasource password as a
> literal. Before pushing, replace it with a placeholder and move the real
> value to an environment variable:
> `spring.datasource.password=${DB_PASSWORD}`.
> The same applies to the JWT signing key in `JwtService.java`, which is
> currently a hardcoded `String` constant — it should be
> `@Value("${jwt.secret}")`. Both are tracked in the [Roadmap](#-roadmap).

### 4️⃣ Run

```bash
./mvnw spring-boot:run          # macOS / Linux
mvnw.cmd spring-boot:run        # Windows
```

Or build and run the JAR:

```bash
./mvnw clean package -DskipTests
java -jar target/FoodDeliveryBackend-0.0.1-SNAPSHOT.jar
```

The API is available at **`http://localhost:8080`**.

### 5️⃣ API documentation

> **No Swagger / OpenAPI UI is configured yet.** Use the endpoint tables below
> with Postman or curl. Adding SpringDoc is the top item on the
> [Roadmap](#-roadmap) — once added, the UI will be served at
> `http://localhost:8080/swagger-ui.html`.

---

## 🔐 Authentication Flow

```
┌──────────┐                                               ┌──────────┐
│  CLIENT  │                                               │  SERVER  │
└────┬─────┘                                               └────┬─────┘
     │                                                          │
     │  ①  POST /api/auth/registerCustomer                      │
     │      { firstName, lastName, email, password, … }         │
     ├─────────────────────────────────────────────────────────▶│
     │                                                          │ ┌─────────────────────┐
     │                                                          ├─│ email already used? │
     │                                                          │ │ BCrypt(password)    │
     │                                                          │ │ save User           │
     │                                                          │ │ save Customer       │
     │                                                          │ │ sign JWT (HS256)    │
     │                                                          │ └─────────────────────┘
     │  ◀──── 200 { token, email, role }                        │
     │                                                          │
     │  ②  POST /api/auth/authenticate                          │
     │      { email, password }                                 │
     ├─────────────────────────────────────────────────────────▶│
     │                                                          │ ┌─────────────────────┐
     │                                                          ├─│ AuthenticationMgr   │
     │                                                          │ │  → DaoAuthProvider  │
     │                                                          │ │  → BCrypt.matches() │
     │                                                          │ │ sign JWT            │
     │                                                          │ └─────────────────────┘
     │  ◀──── 200 { token, email, role }                        │
     │                                                          │
     │  ③  GET /api/orders/1                                    │
     │      Authorization: Bearer <token>                       │
     ├─────────────────────────────────────────────────────────▶│
     │                                                          │ ┌─────────────────────┐
     │                                                          ├─│ JwtAuthFilter       │
     │                                                          │ │  extract subject    │
     │                                                          │ │  load UserDetails   │
     │                                                          │ │  verify sig + exp   │
     │                                                          │ │  set SecurityCtx    │
     │                                                          │ └─────────────────────┘
     │  ◀──── 200 { …order payload… }                           │
     │                                                          │
```

**Token properties**

| Property | Value |
|---|---|
| Algorithm | HS256 (HMAC-SHA256) |
| Subject (`sub`) | User's email address |
| Issued at (`iat`) | Time of issue |
| Expiry (`exp`) | Issue time + 24 hours |
| Custom claims | None currently |
| Session state | None — `SessionCreationPolicy.STATELESS` |

---

## 📡 API Documentation

### 🔓 Authentication — `/api/auth` *(public)*

| Method | Endpoint | Body |
|--------|----------|------|
| `POST` | `/api/auth/registerCustomer` | `firstName`, `lastName`, `email`, `password`, `phone`, `address` |
| `POST` | `/api/auth/registerRestaurant` | `restaurantName`, `ownerName`, `email`, `password`, `phone`, `address`, `licenseNumber` |
| `POST` | `/api/auth/registerDeliveryPartner` | `firstName`, `lastName`, `email`, `password`, `phone`, `vehicleNumber`, `vehicleType`, `drivingLicenseNumber` |
| `POST` | `/api/auth/authenticate` | `email`, `password` |

### 👤 Customers — `/api/customers`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/customers` | Create a customer profile |
| `GET` | `/api/customers/{id}` | Fetch by ID |
| `GET` | `/api/customers` | List all active customers |
| `PUT` | `/api/customers/{id}` | Update profile |
| `DELETE` | `/api/customers/{id}` | Delete by ID |
| `DELETE` | `/api/customers` | ⚠️ Delete **all** customers |

### 🏪 Restaurants — `/api/restaurants`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/restaurants` | Create a restaurant |
| `GET` | `/api/restaurants` | List all |
| `GET` | `/api/restaurants/{id}` | Fetch by ID |
| `GET` | `/api/restaurants/search?keyword=pizza` | Case-insensitive name search |
| `PUT` | `/api/restaurants/{id}` | Update |
| `DELETE` | `/api/restaurants/{id}` | Delete by ID |
| `DELETE` | `/api/restaurants` | ⚠️ Delete **all** restaurants |

### 🍕 Menu Items — `/api/menu-items`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/menu-items/{restaurantId}` | Create a menu item *(restaurant ID is read from the request body)* |
| `GET` | `/api/menu-items/{id}` | Fetch by ID |
| `GET` | `/api/menu-items` | List all |
| `GET` | `/api/menu-items/restaurant/{restaurantId}` | List a restaurant's menu |
| `PUT` | `/api/menu-items/{id}` | Update |
| `DELETE` | `/api/menu-items/{id}` | Delete |

### 🛒 Carts — `/api/carts`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/carts/createCart` | Create a cart for a customer |
| `GET` | `/api/carts/getCartByCustomerId` | Fetch a customer's cart *(takes a request body)* |
| `PUT` | `/api/carts/addMenuItemToCart` | Add an item, recalculating the total |
| `DELETE` | `/api/carts/menu-items` | Remove an item, recalculating the total |
| `DELETE` | `/api/carts/{cartId}` | Delete the cart |

### 📦 Orders — `/api/orders`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/orders` | Place an order *(auto-generates `ORD-<timestamp>`, status `Pending`)* |
| `GET` | `/api/orders/{id}` | Fetch by ID |
| `GET` | `/api/orders` | List all |
| `PUT` | `/api/orders/{id}` | Update status / address / total |
| `DELETE` | `/api/orders/{id}` | Delete |

### 🧾 Order Items — `/api/order-items`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/order-items` | Add a line item *(subtotal computed from the menu item's price × quantity)* |
| `GET` | `/api/order-items/{id}` | Fetch by ID |
| `GET` | `/api/order-items` | List all |
| `PUT` | `/api/order-items/{id}` | Update |
| `DELETE` | `/api/order-items/{id}` | Delete |

### 🛵 Delivery Partners — `/api/delivery-partners`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/delivery-partners` | Create a profile |
| `GET` | `/api/delivery-partners/{id}` | Fetch by ID |
| `GET` | `/api/delivery-partners` | List all |
| `PUT` | `/api/delivery-partners/{id}` | Update |
| `DELETE` | `/api/delivery-partners/{id}` | Delete |

---

## 📝 Sample API Requests & Responses

### Register a customer

```http
POST /api/auth/registerCustomer HTTP/1.1
Content-Type: application/json

{
  "firstName": "Arjun",
  "lastName":  "Sharma",
  "email":     "arjun.sharma@example.com",
  "password":  "SecurePass@123",
  "phone":     "9876543210",
  "address":   "42 Park Street, Kolkata 700016"
}
```

```json
HTTP/1.1 200 OK

{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcmp1bi5zaGFybWFAZXhhbXBsZS5jb20i...",
  "email": "arjun.sharma@example.com",
  "role":  "CUSTOMER"
}
```

### Log in

```http
POST /api/auth/authenticate HTTP/1.1
Content-Type: application/json

{
  "email":    "arjun.sharma@example.com",
  "password": "SecurePass@123"
}
```

```json
HTTP/1.1 200 OK

{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhcmp1bi5zaGFybWFAZXhhbXBsZS5jb20i...",
  "email": "arjun.sharma@example.com",
  "role":  "CUSTOMER"
}
```

### Create a menu item

```http
POST /api/menu-items/1 HTTP/1.1
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

{
  "itemName":     "Paneer Butter Masala",
  "description":  "Cottage cheese in a rich tomato and cashew gravy",
  "category":     "Main Course",
  "price":        320.00,
  "restaurantId": 1
}
```

```json
HTTP/1.1 200 OK

{
  "id": 7,
  "itemName": "Paneer Butter Masala",
  "description": "Cottage cheese in a rich tomato and cashew gravy",
  "category": "Main Course",
  "price": 320.00,
  "available": true,
  "createdAt": "2026-08-07T14:22:11.482",
  "updatedAt": "2026-08-07T14:22:11.482",
  "restaurant": { "id": 1, "restaurantName": "Spice Garden", "...": "..." }
}
```

### Place an order

```http
POST /api/orders HTTP/1.1
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...

{
  "deliveryAddress":  "42 Park Street, Kolkata 700016",
  "customerId":       1,
  "restaurantId":     1,
  "deliveryPartnerId": 3
}
```

```json
HTTP/1.1 200 OK

{
  "id": 12,
  "orderNumber": "ORD-1786213331482",
  "totalAmount": 0,
  "orderStatus": "Pending",
  "deliveryAddress": "42 Park Street, Kolkata 700016",
  "orderTime": "2026-08-07T14:25:31.482",
  "updatedAt": "2026-08-07T14:25:31.482",
  "customer": { "id": 1, "...": "..." },
  "restaurant": { "id": 1, "...": "..." },
  "deliveryPartner": { "id": 3, "...": "..." },
  "orderItems": null
}
```

### Validation failure

```http
POST /api/order-items HTTP/1.1
Content-Type: application/json

{ "quantity": 0, "itemPrice": 0.5, "foodOrderId": 12, "menuItemId": 7 }
```

Returns **`400 Bad Request`** with Spring Boot's default error body. The messages defined on `OrderItemRequest` — *"Quantity must be at least 1."* and *"Item Price must be greater than 0."* — are carried in that payload.

> A structured, consistent error envelope is on the [Roadmap](#-roadmap).

---

## 🧩 Key Modules

<details>
<summary><b>🔐 Security Module</b> — <code>com.fdb.api.security</code></summary>

| Class | Responsibility |
|---|---|
| `SecurityConfiguration` | Defines the `SecurityFilterChain`: CSRF disabled, `STATELESS` session policy, route authorization rules, and registration of the JWT filter before `UsernamePasswordAuthenticationFilter` |
| `ApplicationConfig` | Declares the `UserDetailsService` (a lambda over `UserRepository::findByEmail`), the `DaoAuthenticationProvider`, the `AuthenticationManager`, and the `BCryptPasswordEncoder` bean |
| `JwtService` | Signs tokens with HS256, extracts claims via a generic `Function<Claims,T>` resolver, and validates subject match plus expiry |
| `JwtAuthenticationFilter` | Extends `OncePerRequestFilter`; reads the `Authorization` header, resolves the user, validates the token, and populates the `SecurityContext` |

</details>

<details>
<summary><b>👥 Authentication Module</b> — <code>AuthenticationService</code></summary>

Handles all four auth flows. Each `register*` method:

1. Checks whether the email is already registered
2. Builds a `User` with a BCrypt-hashed password and the appropriate `Role`
3. Persists the `User`
4. Builds and persists the matching profile entity, linked to that `User`
5. Signs and returns a JWT alongside the email and role

`authenticate` delegates credential verification to the `AuthenticationManager`, then reloads the user and issues a fresh token.

</details>

<details>
<summary><b>🍽️ Domain Modules</b> — Customer · Restaurant · MenuItem · Cart · Order · OrderItem · DeliveryPartner</summary>

Each follows an identical shape:

- A **controller** exposing REST endpoints, applying `@Valid` to request bodies
- A **service** holding business logic and a private `convertToResponse(...)` mapper
- A **repository** extending `JpaRepository`, with derived queries where needed
- Three **DTOs** — `Request` (create), `UpdateRequest` (modify), `Response` (read)

Business logic worth noting:
- `CartService` recalculates `totalAmount` on every add/remove
- `OrderItemService` computes `subtotal` as `menuItem.price × quantity`, reading the price from the database rather than trusting the client
- `FoodOrderService` generates `ORD-<epochMillis>` order numbers and defaults new orders to `Pending`
- `CustomerService.getAllCustomers()` filters on `findByActiveTrue()`

</details>

---

## 🛡️ Security

| Control | Implementation |
|---|---|
| **Password storage** | BCrypt via `BCryptPasswordEncoder` (default strength 10, per-password salt) |
| **Token signing** | HS256 with a symmetric key, base64-decoded into a `SecretKey` |
| **Token validation** | Signature verification via `Jwts.parser().verifyWith(key)`, plus subject match and expiry check on every request |
| **Session policy** | `SessionCreationPolicy.STATELESS` — no `JSESSIONID`, no server-side session |
| **CSRF** | Disabled — correct for a stateless token-based API with no cookie auth |
| **Authority model** | `User.getAuthorities()` returns `ROLE_<enum name>` as a `SimpleGrantedAuthority` |
| **Route rules** | Declared in `SecurityConfiguration.securityFilterChain` |

> **⚠️ Security notice — must be fixed before any deployment**
>
> The route rules in `SecurityConfiguration` are declared in the wrong order.
> `.requestMatchers("/api/**").permitAll()` appears **first**, and Spring
> Security evaluates matchers top-down with first-match-wins. Because every
> controller is mapped under `/api`, that rule matches every request and the
> role-based rules below it are never reached — **the entire API is currently
> reachable without a token.**
>
> The fix is to reorder: `/api/auth/**` public, then each role rule, then
> `anyRequest().authenticated()` last. This is tracked as the top
> [Roadmap](#-roadmap) item.

---

## ✅ Validation

Bean Validation is applied to the domain create-request DTOs, with `@Valid` on the corresponding controller parameters.

```java
public class OrderItemRequest {

    @NotNull(message = "Quantity is required.")
    @Min(value = 1, message = "Quantity must be at least 1.")
    private Integer quantity;

    @NotNull(message = "Item Price is required.")
    @DecimalMin(value = "1.00", message = "Item Price must be greater than 0.")
    @Digits(integer = 8, fraction = 2,
            message = "Item Price can have up to 8 digits and 2 decimal places.")
    private BigDecimal itemPrice;

    @NotNull(message = "Food Order Id is required.")
    @Positive(message = "Food Order Id must be greater than 0.")
    private Long foodOrderId;
}
```

| DTO | Constraints applied |
|---|---|
| `CustomerRequest` | `@NotBlank` on names and address, `@Pattern` 10-digit phone, `@NotNull` user ID |
| `RestaurantRequest` | `@NotBlank` on name/owner/address, phone pattern |
| `MenuItemRequest` | `@NotBlank` name, price constraints, `@NotNull` restaurant ID |
| `FoodOrderRequest` | `@NotBlank` delivery address, `@NotNull` + `@Positive` on all three foreign keys |
| `OrderItemRequest` | `@Min` quantity, `@DecimalMin` + `@Digits` price, `@Positive` foreign keys |
| `CartRequest` | `@NotNull` on customer and menu item IDs |
| `DeliveryPartnerRequest` | `@NotBlank` on names, vehicle, and licence fields |

> **Gap:** the four DTOs in `dto/auth` carry **no validation annotations**, and
> `AuthenticationController` does not apply `@Valid`. Since these are the only
> endpoints reachable without a token, adding validation there is a priority
> [Roadmap](#-roadmap) item.

---

## 🚦 Exception Handling

> **Not yet implemented.** This is the largest known gap in the project and is
> documented here honestly rather than omitted.

Service methods currently signal failures by throwing `RuntimeException`:

```java
Customer customer = customerRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Customer not found with id : " + id));
```

With no `@RestControllerAdvice` to translate them, every one of these surfaces to the client as **HTTP 500**:

| Scenario | Correct status | Current status |
|---|---|---|
| Resource not found | `404` | `500` |
| Duplicate email on register | `409` | `500` |
| Wrong password | `401` | `500` |
| Expired or malformed token | `401` | `500` |
| Validation failure | `400` | `400` *(Spring default body)* |

**Planned design** — a `GlobalExceptionHandler` with custom exception types and a consistent envelope:

```json
{
  "timestamp": "2026-08-07T14:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Customer not found with id : 99",
  "path": "/api/customers/99",
  "fieldErrors": null
}
```

---

## ⚠️ Known Limitations

Documented deliberately — these are known, not undiscovered.

| Area | Limitation |
|---|---|
| 🔴 **Authorization** | Route-rule ordering bypasses all role enforcement (see [Security](#️-security)) |
| 🔴 **Secrets** | Datasource password and JWT signing key are hardcoded in source |
| 🔴 **Error handling** | No global handler — client errors return 500 |
| 🔴 **Transactions** | No `@Transactional`; a failed profile save can leave an orphaned `User` |
| 🟠 **Response payloads** | Response DTOs embed live JPA entities, causing N+1 queries and unbounded payloads |
| 🟠 **Data exposure** | `RestaurantResponse` includes the full `User`, which serialises the BCrypt password hash |
| 🟠 **Testing** | Only the generated `contextLoads()` test exists |
| 🟠 **Logging** | 88 `System.out.println` calls, including one that prints raw JWTs |
| 🟡 **Pagination** | List endpoints return every row |
| 🟡 **Schema** | `ddl-auto=update` with no migration tool; no unique index on `users.email` |
| 🟡 **Cart model** | No `CartItem` entity, so carts cannot hold per-item quantities |
| 🟡 **Order status** | Free-text `String` rather than a validated enum state machine |
| 🟡 **HTTP semantics** | Two cart endpoints use `GET`/`DELETE` with a request body |
| 🟡 **Naming** | `Role.RESTURANT` is misspelled and persisted as such |
| 🟢 **Docs** | No OpenAPI/Swagger UI |
| 🟢 **Ops** | No Docker, CI, Actuator, CORS config, or rate limiting |

---

## 🗺️ Roadmap

**Priority 1 — Correctness & Security**
- [ ] Reorder `SecurityConfiguration` route rules so authorization is actually enforced
- [ ] Move the datasource password and JWT secret to environment variables
- [ ] Add an `exception` package with `GlobalExceptionHandler` and custom exception types
- [ ] Add `@Transactional` to the registration flows
- [ ] Add validation to the auth DTOs and `@Valid` to `AuthenticationController`
- [ ] Add `@JsonIgnore` to `User.password` and remove `User` from `RestaurantResponse`
- [ ] Replace `System.out.println` with SLF4J logging

**Priority 2 — API Quality**
- [ ] SpringDoc OpenAPI with bearer-token security scheme
- [ ] `Pageable` on all list endpoints
- [ ] `ResponseEntity` with correct status codes (201, 204, 404, 409)
- [ ] Unit and integration tests (JUnit 5, MockMvc, Testcontainers)
- [ ] MapStruct to replace hand-written mappers
- [ ] Fix Lombok annotations on bidirectional entities
- [ ] Flyway migrations, `ddl-auto=validate`

**Priority 3 — Product Features**
- [ ] Refresh tokens, logout, and revocation
- [ ] Ownership-level authorization (prevent IDOR)
- [ ] `CartItem` entity with per-item quantities
- [ ] Order status enum with a validated state machine
- [ ] Server-side order total calculation
- [ ] Payment gateway integration
- [ ] Real-time order tracking (WebSocket)
- [ ] Redis caching, Docker Compose, GitHub Actions CI

---

## 📸 Screenshots

> *Placeholders — replace with real captures.*

| | |
|---|---|
| **Registration (Postman)** | `docs/screenshots/register.png` |
| **Login & JWT response** | `docs/screenshots/login.png` |
| **Authenticated request** | `docs/screenshots/authenticated-request.png` |
| **MySQL schema (Workbench)** | `docs/screenshots/schema.png` |
| **Entity relationship diagram** | `docs/screenshots/erd.png` |
| **Swagger UI** *(after Roadmap P2)* | `docs/screenshots/swagger.png` |

<div align="center">
  <img src="docs/screenshots/placeholder.png" alt="Screenshot placeholder" width="700"/>
</div>

---

## 👨‍💻 Author

<div align="center">

### **Souvik Maity**
*Java Backend Developer*

[![GitHub](https://img.shields.io/badge/GitHub-Sm7602-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Sm7602)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-souvik--maity-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/souvik-maity-2a6759333)

</div>

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome.

```bash
git checkout -b feature/your-feature
git commit -m "feat: add your feature"
git push origin feature/your-feature
```

Then open a pull request.

---

## 📄 License

Released under the **MIT License** — free to use, modify, and distribute for learning and commercial purposes.

---

<div align="center">

**⭐ If this project helped you learn Spring Boot and JWT, consider starring the repo.**

*Built with Spring Boot 3 · Java 21 · MySQL*

</div>
