# My API – User Management (Jakarta EE + WildFly)

This project is a RESTful API for user management built with **Jakarta EE**, **JPA (Hibernate 6)**, and deployed on **WildFly**.
It demonstrates a clean layered architecture using DTOs, Bean Validation, exception mapping, and PostgreSQL integration.

---

## 🚀 Technologies Used

- Java 17+
- Jakarta EE 10
- JAX-RS (REST)
- JPA / Hibernate 6
- Bean Validation (Jakarta Validation)
- PostgreSQL
- WildFly 30
- Maven
- Postman (for testing)

---

## ⚙️ Requirements

- Java 17+
- Maven 3.8+
- WildFly 30+
- PostgreSQL
- Postman (optional, for testing)

---

## 🗄️ Database Configuration

Create a PostgreSQL database:

```sql
CREATE DATABASE myapi_db;
```

Configure a datasource in WildFly with the following JNDI name:

```
java:/jdbc/MyApiDS
```

The persistence unit is defined in:

```
src/main/resources/META-INF/persistence.xml
```

Hibernate schema management is configured as:

```xml
<property name="hibernate.hbm2ddl.auto" value="update"/>
```

---

## ▶️ Running the Application

### Build the project

```bash
mvn clean package
```

### Deploy to WildFly

```bash
cp target/my-api.war $WILDFLY_HOME/standalone/deployments/
```

### Start WildFly

```bash
$WILDFLY_HOME/bin/standalone.sh
```

The application will be available at:

```
http://localhost:8080/my-api
```

---

## 🔍 API Endpoints

| Method | Endpoint               | Description        |
|------|--------------------------|--------------------|
| GET  | /users                   | List users         |
| GET  | /users/{id}              | Get user by ID     |
| POST | /users                   | Create user        |
| PUT  | /users/{id}              | Update user        |
| PUT  | /users/{id}/password     | Update password    |
| DELETE | /users/{id}            | Delete user        |

---

## 🔐 Security Notes

- Passwords are hashed using BCrypt
- Password fields are never returned in API responses
- DTOs are used to strictly control input and output data

---

## 🧪 Testing

You can test the API using **Postman** or **curl**.

Example:

```bash
curl http://localhost:8080/my-api/users
```
