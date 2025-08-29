# 🐾 Veterinaria App

Aplicación móvil para **gestionar mascotas** en una veterinaria, desarrollada en **Android Studio (Java)**, que consume un **backend en Node.js + Express** con base de datos **MySQL**.

**Funcionalidades principales:**
- Registrar nuevas mascotas.
- Listar todas las mascotas registradas.
- Validaciones: campos obligatorios, tipo de mascota permitido, evita duplicados.

---

## 📂 Estructura del proyecto
```
Veterinaria/
├─ android-app/ # Código fuente de la app Android
│ ├─ app/src/main/java/com/example/veterinaria/
│ │ ├─ MainActivity.java
│ │ ├─ registro.java
│ │ ├─ lista.java
│ │ └─ ...
│ └─ res/ # Layouts, imágenes y recursos
├─ backend/ # Servidor Node.js
│ ├─ db.js # Configuración de la base de datos
│ ├─ index.js # Servidor Express y endpoints
│ └─ .env # Variables de entorno
└─ README.md
```


---

## ⚙️ Tecnologías utilizadas

- **Android Studio** (Java, Volley)
- **Node.js + Express** (API REST)
- **MySQL** (Base de datos)
- **Git** (Control de versiones)

---

## 🚀 Instalación y ejecución

### Backend (Node.js + MySQL)


---

## ⚙️ Tecnologías utilizadas

- **Android Studio** (Java, Volley)
- **Node.js + Express** (API REST)
- **MySQL** (Base de datos)
- **Git** (Control de versiones)

---

## 🚀 Instalación y ejecución

### Backend (Node.js + MySQL)

1. Clonar el repositorio:

```bash
git clone https://github.com/tuusuario/veterinaria.git
cd veterinaria/backend
```

Instalar dependencias:
```

bash
npm install
```
Configurar la base de datos en .env:
```
env
DB_HOST=localhost
DB_USER=root
DB_PASSWORD=tu_password
DB_NAME=veterinaria
PORT=3000
```
Crear la tabla Mascota en MySQL:
```
sql
Copiar código
CREATE TABLE Mascota (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50),
    tipo VARCHAR(20),
    raza VARCHAR(50),
    color VARCHAR(30),
    peso VARCHAR(10),
    genero VARCHAR(10)
);
```

Ejecutar el servidor:

```bash
node index.js
```
Servidor corriendo en http://192.168.X.X:3000 (reemplaza X.X por tu IP local).


## 📱 App Android

1. Abrir el proyecto en Android Studio.

2. La URL del backend ya está configurada en los archivos `registro.java` y `lista.java`:

```java
private final String URL = "http://192.168.X.X:3000/mascotas"; // Reemplaza X.X por tu IP local
```
3.Ejecutar la app en emulador o dispositivo físico conectado a la misma red que el backend.

📌 Validaciones y reglas de negocio

Todos los campos son obligatorios.

Solo se permiten mascotas de tipo Perro o Gato.

Evita registrar duplicados (misma mascota con el mismo nombre y raza).


✅ Esto hace que:

- El bloque de código se vea con formato resaltado (`java`).  
- Las listas tengan viñetas uniformes.  
- La sección de validaciones se vea clara y ordenada.  

