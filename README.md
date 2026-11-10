# Zel E‑Comm

A full-stack e-commerce marketplace with a React storefront and a Spring Boot API. Customers can browse products, manage a cart and delivery locations, place orders, and pay with Stripe. Sellers and admins have tools for managing products and orders.

## Features

- Product catalog with category and keyword filtering
- Customer registration, login, and account verification
- Cart and delivery location management
- Order placement and order history
- Stripe payments with webhook-based payment confirmation
- Seller product and order management
- Admin tools for products, categories, orders, sellers, users, and analytics

## Technology

**Frontend:** React, TypeScript, Vite, Redux Toolkit, React Router, Tailwind CSS, Stripe Elements

**Backend:** Java 25, Spring Boot 4, Spring Security, Spring Data JPA, PostgreSQL, Flyway, Stripe Java SDK

## Project structure

```text
backend/    Spring Boot API, database migrations, and tests
frontend/   React storefront and admin interface
```

## Requirements

- Java 25
- Node.js and npm
- PostgreSQL
- Stripe account and Stripe CLI for local webhook testing

## Local setup

### 1. Configure the backend

Create `backend/.env` with the values expected by the application:

```dotenv
DB_USERNAME=your_database_username
DB_PASSWORD=your_database_password
JWT_SECRET=your_jwt_secret
STRIPE_SECRET_KEY=your_stripe_secret_key
STRIPE_WEBHOOK_SECRET=your_stripe_webhook_secret
```

The application is configured to connect to PostgreSQL at `localhost:5432/ecomm`. Create that database before starting the backend, or adjust the datasource configuration for your environment.

Flyway applies the schema migrations on startup. Hibernate validates the resulting schema.

### 2. Start the backend

From the `backend` directory:

```bash
./mvnw spring-boot:run
```

The API uses `http://localhost:8080` by default. API documentation is provided by Springdoc; check the application's OpenAPI configuration for the local Swagger UI path.

### 3. Configure and start the frontend

The frontend reads these Vite variables from `frontend/.env`:

```dotenv
VITE_FRONT_END_URL=http://localhost:5173
VITE_BACK_END_URL=http://localhost:8080
VITE_STRIPE_PUBLISHABLE_KEY=your_stripe_publishable_key
```

Install dependencies and start Vite:

```bash
cd frontend
npm install
npm run dev
```

Open `http://localhost:5173`.

### 4. Test Stripe webhooks locally

The backend expects a signed Stripe webhook. Use the Stripe CLI to forward payment events to:

```text
http://localhost:8080/api/v1/payments/stripe/webhook
```

Set the webhook signing secret provided by the CLI as `STRIPE_WEBHOOK_SECRET`.

## Useful commands

From `frontend/`:

```bash
npm run build
npm run lint
```

From `backend/`:

```bash
./mvnw test
```

## Main API areas

The API is rooted at `/api/v1`. Routes cover authentication, products, categories, carts, locations, orders, payments, users, and admin analytics. See the OpenAPI documentation for the complete endpoint list.

## Configuration and security

- Keep backend secrets out of source control.
- The Stripe publishable key belongs in the frontend; Stripe secret and webhook keys belong only in the backend.
- Configure the frontend origin, database connection, image storage, and Stripe credentials for each environment before deployment.
- The backend uses JWT authentication and role-based access for customer, seller, and admin operations.

## Notes

Product images are served from the backend's configured image directory. The current application settings use local paths and localhost URLs, so production deployment will require environment-specific values and an appropriate persistent image-storage setup.
