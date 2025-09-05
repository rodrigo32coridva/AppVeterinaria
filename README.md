# App Veterinaria

Aplicación Android para la gestión de mascotas, conectada a un backend en Node.js con base de datos MySQL. Permite buscar, actualizar y eliminar mascotas de manera sencilla desde un dispositivo móvil.

---

## Funcionalidades

1. **Buscar Mascota por ID**
   - Permite ingresar un ID de mascota y mostrar sus datos: Nombre, Tipo, Raza, Color, Peso y Género.

2. **Actualizar Mascota**
   - Se puede modificar el nombre, tipo y raza de la mascota.
   - Los demás campos obligatorios (Color, Peso, Género) se mantienen automáticamente para cumplir con los requerimientos del backend.

3. **Eliminar Mascota**
   - Permite eliminar una mascota existente con confirmación previa.

4. **Interfaz amigable**
   - Uso de diálogos de confirmación para actualizar o eliminar mascotas.
   - Manejo de errores de conexión o cuando no se encuentra la mascota.

---

## Tecnologías utilizadas

- **Android Studio**: Desarrollo de la aplicación móvil.
- **Java**: Lenguaje principal de la app.
- **Volley**: Librería para consumo de APIs REST.
- **Node.js + Express**: Backend para manejar las rutas `/mascotas`.
- **MySQL**: Base de datos para almacenar información de las mascotas.
- **Git & GitHub**: Control de versiones y hosting del repositorio.

---

## Estructura del proyecto

- `app/src/main/java/com/example/veterinaria/`
  - `MainActivity.java`: Pantalla principal.
  - `buscar.java`: Pantalla para buscar, actualizar y eliminar mascotas.
- `app/src/main/res/layout/`
  - `activity_main.xml`: Layout principal.
  - `activity_buscar.xml`: Layout para buscar/editar/eliminar mascotas.
- `server/` (si se incluye el backend)
  - `index.js`: Servidor Node.js con rutas de mascotas.
  - `db.js`: Conexión a MySQL.

---

## Requisitos para ejecutar la app

1. Tener Android Studio instalado.
2. Conectar un dispositivo o usar un emulador.
3. Backend corriendo en la red local (ejemplo: `http://192.168.1.45:3000/mascotas`).
4. La base de datos MySQL debe tener la tabla `Mascota` con los campos:
   - `id`, `nombre`, `tipo`, `raza`, `color`, `peso`, `genero`.

---

## Uso

1. Abrir la app.
2. Ingresar el ID de la mascota que se desea buscar y presionar **Buscar**.
3. Modificar los campos que se permiten actualizar (Nombre, Tipo, Raza) y presionar **Actualizar**.
4. Para eliminar la mascota, presionar **Eliminar** y confirmar.
5. Los cambios se reflejan directamente en la base de datos a través del backend.

---

## Autor

- **Rodrigo Coridva**
- [Repositorio GitHub](https://github.com/rodrigo32coridva/AppVeterinaria)

---

