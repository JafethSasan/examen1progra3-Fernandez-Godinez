# Etapa 3 — Integración, pruebas y mejora

## Integración

La aplicación integra las operaciones de gestión de solicitantes, equipos y préstamos mediante una aplicación de consola desarrollada en Java. La persistencia está diseñada para realizarse en MySQL a través de JDBC y una arquitectura por capas que separa el modelo, los repositorios, los servicios y los controladores.

El menú final debe permitir acceder a las operaciones disponibles en la versión entregada. La tabla siguiente describe las funcionalidades previstas para el caso. El estado definitivo debe coincidir con lo implementado en el código final; aquí no se afirma que se hayan ejecutado pruebas.

| Opción del menú | Funcionalidad | Estado |
|---|---|---|
| Gestión de solicitantes | Registrar, consultar, listar y actualizar estudiantes y funcionarios. | Confirmar según implementación final |
| Gestión de equipos | Registrar, consultar, listar y actualizar equipos y sus estados. | Confirmar según implementación final |
| Registro de préstamo | Validar solicitante, disponibilidad, fechas y límite de préstamos antes de registrar. | Confirmar según implementación final |
| Consulta de préstamos activos | Consultar los préstamos activos asociados a un solicitante. | Confirmar según implementación final |
| Registro de devolución | Registrar la fecha real, actualizar el préstamo y devolver el equipo al estado disponible. | Confirmar según implementación final |
| Conexión con MySQL | Obtener la conexión mediante la configuración JDBC del proyecto. | Confirmar según configuración final |

## Cambio de requerimiento

**Cambio solicitado por el docente:**


**Clases modificadas o agregadas:**

| Clase | Capa | Cambio realizado |
|---|---|---|
| No se identifica una clase específica asociada a un requerimiento nuevo. | — | No se documenta un cambio adicional. |

**Análisis: ¿qué partes del diseño facilitaron el cambio? ¿qué fue necesario rehacer?**

La arquitectura por capas facilita el mantenimiento porque separa las responsabilidades. La capa `model` representa los datos del dominio; `repository` concentra el acceso a MySQL mediante JDBC; `service` contiene las validaciones y reglas de negocio; y `controller` coordina las solicitudes de la aplicación de consola. Esta separación permite modificar una operación sin concentrar toda la lógica en el menú ni duplicar consultas SQL.

## Decisiones de diseño relevantes

- **Arquitectura por capas:** separa presentación, reglas de negocio, modelo y persistencia para facilitar el mantenimiento.
- **Repository / DAO:** concentra las consultas y modificaciones de datos, evitando que el menú escriba SQL directamente.
- **JDBC con `PreparedStatement`:** permite parametrizar consultas y evita concatenar directamente los valores recibidos en el SQL.
- **Excepciones específicas:** permiten distinguir errores de validación, reglas de negocio y problemas de persistencia, para presentar mensajes comprensibles.
- **Integridad relacional:** las claves primarias y foráneas relacionan solicitantes, equipos y préstamos.
- **Transacciones para operaciones relacionadas:** el registro de un préstamo debe insertar el préstamo y cambiar el estado del equipo como una sola unidad; la devolución debe mantener coherentes el estado del préstamo y el del equipo. La implementación efectiva debe corresponder al código entregado.

## Pruebas y mejoras

Las pruebas y sus evidencias se documentarán por separado, según lo indicado para la entrega.

- Pruebas: `pruebas.md`
- Mejoras y defectos corregidos: `mejoras.md`

## UML final

El diagrama UML **no se cambió** con respecto al diseño previamente definido. Se conserva el diseño de clases y las relaciones establecidas para el sistema de préstamos de equipos tecnológicos; por tanto, no se presenta un UML rediseñado para esta etapa.

## Aspectos pendientes

De acuerdo con la indicación proporcionada para esta documentación, **no se reportan aspectos pendientes del proyecto**. Las pruebas y sus evidencias se documentarán por separado y no se detallan en esta sección.

## Distribución del trabajo

| Integrante | Aportes principales |
|---|---|
| Jafeth | Desarrollo y revisión de funcionalidades de gestión de solicitantes y equipos; apoyo en el modelo de dominio, validaciones y documentación técnica. Confirmar que estos aportes coincidan con el trabajo realizado y con los commits. |
| Jorge | Desarrollo y revisión de las operaciones de préstamos y devoluciones; apoyo en la persistencia JDBC, integración con MySQL y documentación técnica. Confirmar que estos aportes coincidan con el trabajo realizado y con los commits. |
