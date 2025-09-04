# Personal Online Store

## 📌 Description
A **Personal Online Store** application built with **Spring Boot** and **PostgreSQL**.  
The goal is to create a personal e-commerce platform to manage products, categories, orders, and users with role-based access control.

---

## ⚙️ Tech Stack
- **Backend**: Java 17, Spring Boot 3.5.5
- **Database**: PostgreSQL
- **Security**: Spring Security, JWT (JSON Web Token)
- **ORM**: JPA + Hibernate
- **Tools**: Maven, Lombok
- **Version Control**: GitHub

---

## 🗄️ Database Design
### Main Entities:
- **User** (id, name, email, password, role)
- **Category** (id, name)
- **Product** (id, name, description, price, stock, category)
- **Order** (id, user, totalAmount, status, orderDetails)
- **OrderDetail** (id, order, product, quantity, price)

Relationships:
- User **1..n** → Order
- Category **1..n** → Product
- Order **1..n** → OrderDetail
- Product **1..n** → OrderDetail

All IDs use **UUID**.

---

## 🔐 Security
- **JWT Authentication**: used for login and access validation.
- **Role-based Access**:
    - `ROLE_USER` → can browse products and create orders.
    - `ROLE_ADMIN` → can manage categories, products, and orders.
    - `PUBLIC` → can browse products/categories without authentication.

---

## 📦 DTO & Mapper
To avoid exposing entity objects directly via API, DTOs and Mappers are used:
- `RegisterRequest`, `LoginRequest`, `UserResponse`
- `CategoryRequest`, `CategoryResponse`
- `ProductRequest`, `ProductResponse`
- `OrderRequest`, `OrderResponse`
- `OrderDetailRequest`, `OrderDetailResponse`

Manual mappers are implemented for converting between Entity ↔ DTO.

---

## 🌐 Main Endpoints
### AuthController
- `POST /api/auth/register` → Register a new user
- `POST /api/auth/login` → Login & return JWT token

### PublicController
- `GET /api/public/products` → List all products
- `GET /api/public/categories` → List all categories

### UserController (ROLE_USER)
- `POST /api/user/orders` → Create a new order
- `GET /api/user/orders` → List user’s own orders

### AdminController (ROLE_ADMIN)
- `POST /api/admin/categories` → Add a new category
- `POST /api/admin/products` → Add a new product
- `GET /api/admin/orders` → List all orders

---

## 🧪 Testing (via Postman)
- ✅ User Registration
- ✅ User Login → obtain JWT token
- ✅ Access public endpoints without login
- ✅ Access user endpoints with valid token → success
- ✅ Access admin endpoints with admin token → success
- ✅ Access restricted endpoints without proper role → `403 Forbidden`
