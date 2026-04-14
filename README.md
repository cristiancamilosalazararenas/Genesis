# Genesis API — Persona 3: Planes · Admin · Transactions

API REST construida con Spring Boot 3, Spring Security (JWT) y JPA/MySQL.

## Stack

- Java 17 + Spring Boot 3
- Spring Security + JWT
- Spring Data JPA + MySQL
- Lombok

## Base URL

http://localhost:8080/api

## Autenticación

Todos los endpoints protegidos requieren:
Authorization: Bearer <TOKEN>

## Módulos — Persona 3

| Capa | Clases |
|---|---|
| DTOs | PlanRequest/Response, SubscriptionResponse, TransactionResponse, Admin DTOs |
| Services | IPlanService, ITransactionService, IAdminService |
| Impl | PlanServiceImpl, TransactionServiceImpl, AdminServiceImpl |
| Controllers | PlanController, TransactionController, AdminController |

---

## Endpoints

### Planes — /plans

| Método | URL | Rol | Descripción |
|---|---|---|---|
| GET | /plans | USER/ADMIN | Lista todos los planes |
| GET | /plans/{id} | ADMIN | Obtiene plan por ID |
| POST | /plans | ADMIN | Crea plan nuevo |
| PUT | /plans/{id} | ADMIN | Actualiza plan (falla si tiene suscripciones activas) |
| DELETE | /plans/{id} | ADMIN | Elimina plan (falla si tiene suscripciones activas) |
| POST | /plans/{id}/subscribe | USER | Suscribe al usuario autenticado |

### Transacciones — /transactions

| Método | URL | Rol | Descripción |
|---|---|---|---|
| GET | /transactions | USER/ADMIN | Historial paginado del usuario autenticado |

### Admin — Usuarios

| Método | URL | Descripción |
|---|---|---|
| GET | /admin/users | Lista paginada de usuarios |
| PATCH | /admin/users/{id}/status | Activa o desactiva usuario |
| PATCH | /admin/users/{id}/tokens | Recarga tokens manualmente |

### Admin — Operaciones

| Método | URL | Descripción |
|---|---|---|
| PATCH | /admin/operations/{code}/status | Activa o desactiva operación |

### Admin — Tasa de cambio

| Método | URL | Descripción |
|---|---|---|
| GET | /admin/exchange-rate | Consulta tasa COP/USD actual |
| PUT | /admin/exchange-rate | Actualiza tasa COP/USD |

### Admin — Métricas

| Método | URL | Descripción |
|---|---|---|
| GET | /admin/metrics/tokens-per-day | Tokens consumidos por día (params: from, to) |
| GET | /admin/metrics/top-operations | Operaciones más ejecutadas |
| GET | /admin/metrics/top-users | Usuarios con mayor consumo |

---

## Reglas de negocio clave

- Un usuario solo puede tener una suscripción ACTIVE a la vez. Al suscribirse a un plan nuevo, la anterior queda INACTIVE.
- Los tokens del plan nuevo se **suman** al balance existente (no se reemplazan).
- No se puede editar ni eliminar un plan que tenga suscripciones activas → 409 Conflict.
- Los endpoints /admin/** requieren rol ADMIN, de lo contrario devuelven 403.

---

## Códigos de respuesta

| Código | Significado |
|---|---|
| 200 | OK |
| 201 | Creado |
| 204 | Eliminado correctamente |
| 400 | Datos inválidos o campo faltante |
| 401 | Token ausente o expirado |
| 403 | Sin permisos (se necesita ADMIN) |
| 404 | Recurso no encontrado |
| 409 | Conflicto de negocio (plan con suscripciones activas) |