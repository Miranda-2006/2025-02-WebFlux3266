# Parcialdos - Gestión de Cuentas Bancarias

Este proyecto es una API REST en Spring Boot para la gestión de cuentas bancarias usando JPA y MySQL. A continuación encontrarás la documentación del Proyecto a crear, DTOs, endpoints, ejemplos JSON, scripts SQL con la siguiente estructura en la aplicación.

---

## Estructura principal del proyecto

- `src/main/java/com/parcial/dos/parcialdos/account`
  - `entity/Account.java` - Entidad JPA que representa la tabla `accounts`.
  - `dto/AccountRequestDTO.java` - DTO de entrada para crear/actualizar cuentas.
  - `dto/AccountResponseDTO.java` - DTO de salida con todos los campos.
  - `dto/AccountOwnerBalanceDTO.java` - DTO reducido con `dueno` y `balanceActual`.
  - `repository/AccountRepository.java` - Extiende `JpaRepository<Account, Long>` y expone `findByAccountNumber`.
  - `service/IAccountService.java` - Interfaz del servicio (contrato).
  - `service/AccountService.java` - Implementación del servicio anotada con `@Service`.
  - `controller/AccountController.java` - Controlador REST exponiendo los endpoints.

- `src/main/resources/schema.sql` - Script SQL que (re)crea la tabla `accounts`.
- `src/main/resources/data.sql` - Script SQL que inserta 5 cuentas iniciales.
- `src/main/resources/application.properties` - Configuración de la aplicación (datasource, ejecutar scripts, puerto).

---

## Entidad: Account

Campos principales (en la entidad, nombres en inglés):
- `id` (BIGINT, PK, auto-increment)
- `accountNumber` (VARCHAR, unique)
- `ownerName` (VARCHAR)
- `balance` (DECIMAL)
- `active` (BOOLEAN)

---

## DTOs (nombres expuestos en la API - español)

1. AccountRequestDTO (request para crear/actualizar)
```json
{
  "numeroCuenta": "ACC1001",
  "dueno": "Alice",
  "balanceActual": 1000.00
}
```

2. AccountResponseDTO (respuesta completa)
```json
{
  "id": 1,
  "numeroCuenta": "ACC1001",
  "dueno": "Alice",
  "balanceActual": 1000.00,
  "active": true
}
```

3. AccountOwnerBalanceDTO (respuesta reducida para búsquedas por número)
```json
{
  "dueno": "Alice",
  "balanceActual": 1000.00
}
```

---

## Endpoints

Base: `/api/accounts`

- POST `/api/accounts`
  - Crea una cuenta.
  - Request body: `AccountRequestDTO` (ver arriba).
  - Response: `AccountResponseDTO` (201 o 200 dependiendo de la configuración).

- GET `/api/accounts`
  - Obtiene todas las cuentas.
  - Response: `[AccountResponseDTO]`

- GET `/api/accounts/{id}`
  - Obtiene una cuenta por ID.
  - Response: `AccountResponseDTO` o 404.

- PUT `/api/accounts/{id}`
  - Actualiza únicamente el `balanceActual` de la cuenta.
  - Request body: `AccountRequestDTO` (solo se usa `balanceActual`).
  - Response: `String` con un mensaje indicando el balance anterior y el nuevo, por ejemplo:
    `"La cuenta ACC1001 fue actualizada: balanceAnterior=1000.00, balanceActual=999.99"`
  - Si la cuenta no existe devuelve mensaje "Cuenta no encontrada" (si prefieres 404 lo cambiamos).

- DELETE `/api/accounts/{id}`
  - Borra la cuenta.
  - Response: 204 No Content

- GET `/api/accounts/by-number/{numeroCuenta}`
  - Busca por `numeroCuenta` y devuelve solo `dueno` y `balanceActual` (DTO reducido).
  - Response: `AccountOwnerBalanceDTO` o 404.

---

## Scripts SQL

- `schema.sql` (se ejecuta en cada arranque cuando `spring.sql.init.mode=always`):
  - Contiene `DROP TABLE IF EXISTS accounts;` y `CREATE TABLE accounts (...)`.
  - Nota: Esto recrea la tabla en cada arranque y borra datos previos.

- `data.sql` (se ejecuta después de `schema.sql`):
  - Inserta 5 cuentas iniciales: `ACC1001` - `ACC1005`.

Si prefieres no recrear la tabla en cada inicio, pueden cambiar `schema.sql` para usar `CREATE TABLE IF NOT EXISTS`.

---

## Configuración y ejecución

Asegúrate de tener:
- Java 17
- MySQL en ejecución
- Base de datos creada: `bankdb`

`application.properties` por defecto está configurado como:
```
spring.datasource.url=jdbc:mysql://localhost:3306/bankdb?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=Pass_123 (Password que tengan en su MySQL)
spring.jpa.hibernate.ddl-auto=none
spring.sql.init.mode=always
spring.sql.init.schema-locations=classpath:schema.sql
spring.sql.init.data-locations=classpath:data.sql
server.port=8080
```

Comandos útiles (PowerShell):
```powershell
cd "c:\Users\Newmi\Documents\FUTCO\2025-02-DS-II\parcialdos"
.\gradlew.bat build --no-daemon
.\gradlew.bat bootRun
```

---

### Colección Postman

Se ha incluido una colección Postman con los endpoints básicos en `postman/Parcialdos.postman_collection.json`.
Para importarla en Postman:

1. Abre Postman -> Import -> File -> selecciona `postman/Parcialdos.postman_collection.json`.
2. La colección crea requests para: crear, listar, obtener por id, actualizar balance, eliminar y buscar por número.


---


