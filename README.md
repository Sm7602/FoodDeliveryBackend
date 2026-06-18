# 🍔 FoodDeliveryBackend

A RESTful backend API for a food delivery platform built with **Spring Boot 4.1**, **Spring Data JPA**, **MySQL**, and **Lombok**. It supports full CRUD operations for Restaurants, Menu Items, Customers, Carts, Orders, Order Items, and Delivery Partners.

---

## 🗂️ Project Structure

```
src/main/java/com/fdb/api/
├── FoodDeliveryBackendApplication.java
├── controller/
│   ├── CartController.java
│   ├── CustomerController.java
│   ├── DeliveryPartnerController.java
│   ├── FoodOrderController.java
│   ├── MenuItemController.java
│   ├── OrderItemController.java
│   └── RestaurantController.java
├── dao/
│   ├── CartRepository.java
│   ├── CustomerRepository.java
│   ├── DeliveryPartnerRepository.java
│   ├── FoodOrderRepository.java
│   ├── MenuItemRepository.java
│   ├── OrderItemRepository.java
│   └── RestaurantRepository.java
├── entity/
│   ├── Cart.java
│   ├── Customer.java
│   ├── DeliveryPartner.java
│   ├── FoodOrder.java
│   ├── MenuItem.java
│   ├── OrderItem.java
│   └── Restaurant.java
└── service/
    ├── CartService.java
    ├── CustomerService.java
    ├── DeliveryPartnerService.java
    ├── FoodOrderService.java
    ├── MenuItemService.java
    ├── OrderItemService.java
    └── RestaurantService.java
```

---

## ⚙️ Tech Stack

| Technology         | Version  |
|--------------------|----------|
| Java               | 21       |
| Spring Boot        | 4.1.0    |
| Spring Data JPA    | (managed)|
| MySQL Connector    | (managed)|
| Lombok             | (managed)|
| Spring DevTools    | (managed)|
| Maven              | Wrapper  |

---

## 🚀 Getting Started

### Prerequisites

- Java 21+
- MySQL 8+
- Maven (or use the included `mvnw` wrapper)

### 1. Clone the repository

```bash
git clone https://github.com/your-username/FoodDeliveryBackend.git
cd FoodDeliveryBackend
```

### 2. Configure the database

Create a MySQL database:

```sql
CREATE DATABASE fooddeliverybackend;
```

Update `src/main/resources/application.properties` with your credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fooddeliverybackend
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### 3. Build and run

```bash
./mvnw spring-boot:run
```

The server will start on **http://localhost:8080**

---

## 📡 API Endpoints

### 🏪 Restaurants — `/api/restaurants`

| Method | Endpoint              | Description                  |
|--------|-----------------------|------------------------------|
| POST   | `/api/restaurants`    | Create a new restaurant      |
| GET    | `/api/restaurants`    | Get all restaurants          |
| GET    | `/api/restaurants/{id}` | Get restaurant by ID       |
| PUT    | `/api/restaurants/{id}` | Update restaurant          |
| GET    | `/api/restaurants/search?keyword=` | Search by name  |
| DELETE | `/api/restaurants/{id}` | Delete a restaurant        |
| DELETE | `/api/restaurants`    | Delete all restaurants       |

### 🍽️ Menu Items — `/api/menu-items`

| Method | Endpoint                               | Description                    |
|--------|----------------------------------------|--------------------------------|
| POST   | `/api/menu-items/{restaurantId}`       | Create menu item for restaurant|
| GET    | `/api/menu-items`                      | Get all menu items             |
| GET    | `/api/menu-items/{id}`                 | Get menu item by ID            |
| GET    | `/api/menu-items/restaurant/{restaurantId}` | Get items by restaurant  |
| PUT    | `/api/menu-items/{id}`                 | Update menu item               |
| DELETE | `/api/menu-items/{id}`                 | Delete menu item               |

### 👤 Customers — `/api/customers`

| Method | Endpoint               | Description              |
|--------|------------------------|--------------------------|
| POST   | `/api/customers`       | Create a customer        |
| GET    | `/api/customers`       | Get all active customers |
| GET    | `/api/customers/{id}`  | Get customer by ID       |
| PUT    | `/api/customers/{id}`  | Update customer          |
| DELETE | `/api/customers/{id}`  | Delete customer          |
| DELETE | `/api/customers`       | Delete all customers     |

### 🛒 Cart — `/api/carts`

| Method | Endpoint                            | Description                     |
|--------|-------------------------------------|---------------------------------|
| POST   | `/api/carts/customer/{customerId}`  | Create cart for customer        |
| GET    | `/api/carts/customer/{customerId}`  | Get cart by customer ID         |
| PUT    | `/api/carts?cartId=&menuItemId=`    | Add menu item to cart           |
| DELETE | `/api/carts/{cartId}`               | Delete cart                     |
| DELETE | `/api/carts/menu-items?cartId=&menuItemId=` | Remove item from cart   |

### 📦 Orders — `/api/orders`

| Method | Endpoint            | Description       |
|--------|---------------------|-------------------|
| POST   | `/api/orders`       | Create an order   |
| GET    | `/api/orders`       | Get all orders    |
| GET    | `/api/orders/{id}`  | Get order by ID   |
| PUT    | `/api/orders/{id}`  | Update order      |
| DELETE | `/api/orders/{id}`  | Delete order      |

### 🧾 Order Items — `/api/order-items`

| Method | Endpoint                                  | Description            |
|--------|-------------------------------------------|------------------------|
| POST   | `/api/order-items/{orderId}/{menuItemId}` | Create order item      |
| GET    | `/api/order-items`                        | Get all order items    |
| GET    | `/api/order-items/{id}`                   | Get order item by ID   |
| PUT    | `/api/order-items/{id}`                   | Update order item      |
| DELETE | `/api/order-items/{id}`                   | Delete order item      |

### 🛵 Delivery Partners — `/api/delivery-partners`

| Method | Endpoint                       | Description                  |
|--------|--------------------------------|------------------------------|
| POST   | `/api/delivery-partners`       | Create delivery partner      |
| GET    | `/api/delivery-partners`       | Get all delivery partners    |
| GET    | `/api/delivery-partners/{id}`  | Get delivery partner by ID   |
| PUT    | `/api/delivery-partners/{id}`  | Update delivery partner      |
| DELETE | `/api/delivery-partners/{id}`  | Delete delivery partner      |

---

## ⚠️ Known Issues & Improvements

The following issues were identified during code review. They are good targets for future contributions.

### 🔴 Critical

1. **Hardcoded database credentials in `application.properties`**
   The file contains a real password (`souvik@7602`). This is a **security risk** if pushed to a public repository. Use environment variables or a `.env` file instead, and add `application.properties` to `.gitignore`.
   ```properties
   # Recommended
   spring.datasource.password=${DB_PASSWORD}
   ```

2. **`@EnableJpaRepositories` misplaced on `RestaurantRepository`**
   This annotation belongs on the main application class or a `@Configuration` class, not on a repository interface. It should be removed from `RestaurantRepository.java`.

3. **Infinite recursion / `StackOverflowError` risk on all JSON responses**
   Bidirectional JPA relationships (e.g., `Customer ↔ Cart`, `Restaurant ↔ MenuItem`, `FoodOrder ↔ OrderItem`) will cause Jackson to infinitely serialize both sides. Fix with `@JsonManagedReference` / `@JsonBackReference` or `@JsonIgnore` on the back-reference side.

4. **`Cart` entity uses `@OneToMany` to `MenuItem` without a join table or cascade config**
   The `List<MenuItem> menuItems` in `Cart` has no `@JoinTable` annotation. JPA will attempt to generate an ambiguous join table, which may fail or behave unexpectedly. A proper `@JoinTable` with `name`, `joinColumns`, and `inverseJoinColumns` is required.

### 🟠 Moderate

5. **No HTTP response status codes — all endpoints return `200 OK`**
   Controllers return raw entity objects and plain `String` messages. Use `ResponseEntity<>` to return proper HTTP status codes (`201 Created`, `204 No Content`, `404 Not Found`, etc.).

6. **No global exception handler**
   `RuntimeException` is thrown directly from services but never caught. This causes a `500 Internal Server Error` with a full stack trace to the client. Add a `@RestControllerAdvice` class to return clean error responses.

7. **`deleteMenuItemfromCart` returns void but controller ignores it**
   `CartController.deleteMenuItemfromCart()` calls the service but discards the returned updated `Cart`. It only returns a hardcoded string. The updated cart should be returned for consistency.

8. **`FoodOrder.orderStatus` is a raw `String`**
   Order status should be an `enum` (e.g., `PENDING`, `CONFIRMED`, `PREPARING`, `OUT_FOR_DELIVERY`, `DELIVERED`, `CANCELLED`) to prevent invalid values from being stored.

9. **`Restaurant.rating` is a `Double` with no validation**
   Rating should be validated to stay within a range (e.g., 0.0–5.0) using `@Min`/`@Max` or a custom validator.

10. **No input validation on any entity**
    Fields like `email`, `phoneNumber`, `price` have no `@NotNull`, `@Email`, `@Positive`, or `@Size` constraints. Add `spring-boot-starter-validation` and annotate entity/DTO fields.

### 🟡 Minor / Code Quality

11. **Typos in method and variable names**
    - `getMenuItemByresturentrant()` — should be `getMenuItemsByRestaurant()`
    - `deleteAllCustomer()` — should be `deleteAllCustomers()`
    - `deleteAllRestaurant()` / `deleteALLRestaurant()` — inconsistent naming

12. **`System.out.println()` used for logging throughout**
    Replace all `System.out.println()` calls with a proper logger (e.g., SLF4J via Lombok's `@Slf4j`):
    ```java
    @Slf4j
    public class CartService {
        // use log.info("CartService.createCart()");
    }
    ```

13. **No DTOs — entities are used directly as request/response bodies**
    Exposing JPA entities directly over the API leaks internal structure and causes the JSON recursion issues above. Introduce DTO classes and a mapping layer (e.g., MapStruct or manual mapping).

14. **`@Autowired` field injection used everywhere**
    Constructor injection is preferred for testability and to make dependencies explicit. Lombok's `@RequiredArgsConstructor` with `private final` fields is the recommended approach.

15. **No unit or integration tests**
    The test file `FoodDeliveryBackendApplicationTests.java` is empty (only the context-load test). Add service-layer unit tests and controller integration tests.

---

## 🔧 Suggested `application.properties` (safe version)

```properties
spring.application.name=FoodDeliveryBackend
server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/fooddeliverybackend
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit your changes: `git commit -m 'Add some feature'`
4. Push to the branch: `git push origin feature/your-feature`
5. Open a Pull Request

---

## 📄 License

This project is open source. Add a license file if you intend to share it publicly.

