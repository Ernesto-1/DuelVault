DuelVault - Android Technical Test
Hola buen dia ! DuelVault es una aplicación de Android construida con las últimas tecnologías de desarrollo (Kotlin 2.0 y Jetpack Compose). 
La aplicación permite gestionar y visualizar información de cartas de Yu-Gi-Oh! consumiendo la API de YGOPRODECK.

🏗️ Arquitectura
El proyecto sigue los principios de Clean Architecture y está organizado por Features (Módulos de funcionalidad) para mejorar la escalabilidad y el mantenimiento.
Cada módulo (home, onboarding) contiene sus propias capas de datos, dominio y presentación.
MVVM (Model-View-ViewModel): Implementado en cada feature para separar la lógica de negocio de la interfaz de usuario.
Patrones de UI en Compose: * State Hoisting: Para una gestión de estados predecible y centralizada.


🛠️ Librerías y Tecnologías
UI: Jetpack Compose (BOM 2026.02.00) con Material 3.
Inyección de Dependencias: Dagger Hilt (v2.51.1).
Networking: Retrofit + OkHttp para el consumo de APIs REST.
Persistencia Local: Room Database para almacenamiento offline y DataStore para preferencias de usuario.
Navegación: Compose Navigation con Type-Safety (Kotlinx Serialization).
Carga de Imágenes: Coil (soporta carga de GIFs).


🚀 Instrucciones para Correr el Proyecto
Requisitos previos
Android Studio: Ladybug (2024.2.1) o superior.
JDK: Versión 17 o superior.
Dispositivo/Emulador: Android 9.0 (API 28) o superior.
Configuración de Variables de Entorno
El proyecto requiere una URL base para la API. Por seguridad y buenas prácticas, esta se maneja a través del archivo local.properties.
Localiza el archivo local.properties en la raíz de tu proyecto.
Agrega la siguiente línea al final del archivo:

YGOPRODECK=https://db.ygoprodeck.com/api/v7/

Pasos para ejecución
Clonar el repositorio.
Sincronizar el proyecto con Gradle (Gradle Sync).
Clean & Build: Ejecutar Build > Clean Project seguido de Build > Rebuild Project.
Seleccionar Flavor: El proyecto cuenta con Flavors para dev y prod. Asegúrate de seleccionar el que necesites en la pestaña Build Variants.
Ejecutar: Presiona el botón Run en Android Studio.

🔐 Seguridad y Optimización
ProGuard/R8: El código está ofuscado y optimizado para las versiones de producción, protegiendo los modelos de datos y los servicios de red.
Flavors: Configuración separada para entornos de desarrollo y producción, permitiendo diferentes configuraciones de red y logs.



