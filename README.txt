===========================================
     GESTOR DE GASTOS PERSONALES - JAVA
===========================================

Versión: 1.0

Esta aplicación fue desarrollada en Java para organizar tus gastos personales mensuales, separando:

   ✴️  Gastos Únicos  
   🛍️  Compras Recurrentes  
   🧾  Pagos Fijos

-------------------------------------------
FUNCIONALIDADES PRINCIPALES
-------------------------------------------

✔ Registrar sueldo mensual  
✔ Añadir gastos con nombre, cantidad, precio y tipo  
✔ Generar lista de compras copiable (para WhatsApp, por ejemplo)  
✔ Guarda datos automáticamente en un archivo `.dat`  
✔ Detecta cuando empieza un nuevo mes y conserva solo los gastos recurrentes  
✔ Guarda la fecha de creación del archivo de datos

-------------------------------------------
¿CÓMO USAR LA APLICACIÓN?
-------------------------------------------

1. Ingresa tu sueldo mensual y presiona "Guardar".

2. Añade gastos rellenando:  
   ▪ Nombre del gasto  
   ▪ Cantidad  
   ▪ Precio por unidad  
   ▪ Tipo de gasto:  
      - Único (solo se registra una vez)  
      - Compra mensual (va a la lista de compras)  
      - Pago fijo (no aparece en la lista)

3. Presiona "Añadir Gasto".

4. Puedes eliminar cualquier gasto desde la lista principal.

5. En la sección derecha puedes:  
   ▪ Generar tu lista de compras (para copiar y pegar)  
   ▪ Limpiar la lista

Todos los datos se guardan automáticamente y se mantienen al cerrar y volver a abrir la app.

-------------------------------------------
¿CÓMO DESCARGAR, COMPILAR Y EJECUTAR EL PROYECTO?
-------------------------------------------

💻 Requisitos:
- Java JDK 8 o superior
- Visual Studio Code con las extensiones de Java (Extension Pack for Java)
- Git instalado

📥 Pasos para clonar, compilar y ejecutar el proyecto:

1. Abre Visual Studio Code.

2. Abre una terminal (`Terminal > New Terminal`).

3. Clona el repositorio desde GitHub:

   git clone https://github.com/Zanaborio21/GestorGastos.git

4. Entra a la carpeta del proyecto:

   cd GestorGastosJava

5. Compila los archivos Java:

   javac *.java

   Esto generará los archivos `.class` necesarios para poder ejecutar el programa.

6. Ejecuta la aplicación:

   java ControlGastosApp

✅ ¡Y lesto! Se abrirá la interfaz gráfica del programa.  
📂 El archivo `gastos.dat` se generará automáticamente en la misma carpeta para guardar tus datos.

-------------------------------------------
ARCHIVOS DEL PROYECTO
-------------------------------------------

📁 ControlGastosApp.java  
   ▸ Lógica de la aplicación y la GUI

📁 EstadoApp.java  
   ▸ Contiene el estado del programa: sueldo, gastos, mes, fecha de creación

📁 Gasto.java  
   ▸ Representa un gasto individual con cantidad, monto, tipo

-------------------------------------------
MEJORAS FUTURAS (TO-DO)
-------------------------------------------

🌑 Modo oscuro  
📤 Exportación a Excel  
✏️ Edición de gastos  
📊 Gráficos y estadísticas  

-------------------------------------------
NOTAS DEL BOSS
-------------------------------------------

Esta app se actualiza cuando me da la gana 😎  
La versión 2.0... algún día caerá, si hay motivación y café ☕.
