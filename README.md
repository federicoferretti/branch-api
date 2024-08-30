# Branch api

La aplicación se basa en el uso de los índices geoespaciales que provee Mongo. Se incluyó Redis para cachear respuestas y Jwt para autenticación.

Es necesario correr primero el archivo docker-compose.yml antes que la aplicación para configurar correctamente la base de datos. Los puertos a utilizar son 27018 y 6380.

```bash
  docker-compose up -d
```
## API Reference

### Notas de Seguridad:
- Todos los endpoints requieren autenticación, para generar su token y testear se creo un usuario de prueba.
- username: user
- password: test

#### User login

```http
  POST /auth/login
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` | `string` | **Required**. |
| `password` | `string` | **Required**. |


#### Save a Branch

```http
  POST /branch
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `address` | `string` | **Required**. |
| `longitude` | `double` | **Required**.|
| `latitude` | `double` | **Required**.|

#### Get branch by ID

```http
  GET /branch/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. |


#### Get nearest Branch

```http
  GET /nearest
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `longitude`      | `double` | **Required**. |
| `latitude`      | `double` | **Required**. |


### Descripción de los Endpoints:

- **Save a Branch**:
    - **Método:** `POST`
    - **Ruta:** `/branch`
    - **Cuerpo de la solicitud:** Debe contener la dirección, la longitud, y la latitud de la sucursal. Estos campos tienen restricciones de validación, como el rango permitido para las coordenadas.
    - **Respuesta:** Retorna un objeto `Branch` creado con un estado `201 Created`.

- **Get Branch by ID**:
    - **Método:** `GET`
    - **Ruta:** `/branch/{id}`
    - **Parámetros:** Se requiere el `id` de la sucursal que se desea obtener.
    - **Respuesta:** Retorna el objeto `Branch` correspondiente al ID proporcionado o `404 Not Found` si no existe.

- **Get Nearest Branch**:
    - **Método:** `GET`
    - **Ruta:** `/branch/nearest`
    - **Parámetros de consulta:** Se deben proporcionar las coordenadas de longitud y latitud para buscar la sucursal más cercana.
    - **Respuesta:** Retorna la sucursal más cercana en función de las coordenadas proporcionadas.
