# Informe de mejoras
 
## Mejora de calidad implementada
 
| Campo | Detalle |
|---|---|
| **Tipo de mejora** | Separación de responsabilidades y reutilización de validaciones |
| **Clases involucradas** | `PrestamoService`, `Validador`, `App` |
| **Commit** | _Pendiente de registrar el commit real_ |
 
### Problema identificado
 
En el sistema de préstamo de equipos tecnológicos es necesario validar varias reglas de negocio: que el solicitante no supere el límite de préstamos activos, que el equipo esté disponible y que las fechas sean válidas. Si estas comprobaciones se realizan directamente en el menú o se repiten en diferentes métodos, el código se vuelve más difícil de mantener y aumenta la posibilidad de inconsistencias.
 
### Antes
 
> **Fragmento ilustrativo.** Debe sustituirse por el código anterior real, que se puede recuperar del historial de Git.
 
```java
if (equipo.getEstado() != EstadoEquipo.DISPONIBLE) {
    System.out.println("El equipo no está disponible.");
    return;
}
```
 
### Después
 
Las validaciones se centralizan en la capa de servicio, en `PrestamoService.registrar`, que aplica las reglas de negocio antes de registrar el préstamo. Las comprobaciones comunes (campos obligatorios y formato de correo) están en la clase `Validador`, que reutilizan los tres servicios.
 
Extracto real de `PrestamoService.registrar`:
 
```java
identificacion = Validador.requerido(identificacion, "identificación del solicitante");
codigoEquipo = Validador.requerido(codigoEquipo, "código del equipo");
if (fechaEsperada == null)
    throw new ValidacionException("Debe indicar la fecha de devolución esperada.");
 
LocalDate hoy = LocalDate.now();
if (!fechaEsperada.isAfter(hoy))
    throw new ValidacionException("La fecha esperada de devolución debe ser posterior a hoy (" + hoy + ").");
 
Solicitante s = solicitantes.buscarPorId(identificacion)
        .orElseThrow(() -> new ValidacionException("No existe el solicitante indicado."));
Equipo e = equipos.buscarPorCodigo(codigoEquipo)
        .orElseThrow(() -> new ValidacionException("No existe el equipo indicado."));
 
if (e.getEstado() != EstadoEquipo.DISPONIBLE)
    throw new EquipoNoDisponibleException("El equipo está en estado " + e.getEstado() + " y no se puede prestar.");
 
int limite = s.getTipo() == TipoSolicitante.ESTUDIANTE ? 2 : 4;
int activos = prestamos.contarActivosPorSolicitante(identificacion);
if (activos >= limite)
    throw new LimitePrestamosException("El solicitante ya alcanzó el límite de " + limite + " préstamos activos.");
```
 
### Beneficio obtenido
 
| Beneficio | Detalle |
|---|---|
| **Mantenimiento más simple** | Cada regla está escrita en un solo lugar. Si cambia, se modifica en un único método. |
| **Sin comprobaciones repetidas** | La interfaz de consola no repite reglas; `App` solo lee datos, llama al controlador y muestra el mensaje de la excepción. |
| **Reglas reutilizables** | `Validador` se usa desde `SolicitanteService`, `EquipoService` y `PrestamoService`. |
| **Separación de capas** | La lógica de negocio queda separada de la interfaz de consola, y se puede probar sin ella. |
| **Errores diferenciados** | Cada violación lanza una excepción propia (`ValidacionException`, `EquipoNoDisponibleException`, `LimitePrestamosException`), en lugar de un mensaje impreso. |
 
---
 
## Defectos encontrados y corregidos
 
| ID | Defecto | Cómo se detectó (prueba) | Corrección | Commit |
|:---:|---|---|---|---|
| **D-01** | Posibilidad de registrar un préstamo para un equipo que no está disponible. | Intentar prestar un equipo cuyo estado sea `PRESTADO` o `EN_REPARACION`. Verificar que se rechace la operación y que no se registre un préstamo nuevo. | Validar el estado del equipo en la capa de servicio antes de registrar el préstamo. | corregidos en el commit final |
| **D-02** | Posibilidad de superar el límite de préstamos activos permitido para un solicitante. | Intentar registrar un préstamo cuando un estudiante ya tiene dos préstamos activos o un funcionario ya tiene cuatro. | Comprobar el tipo de solicitante y la cantidad de préstamos activos antes de autorizar la operación. | corregidos en el commit final |
| **D-03** | Posibilidad de procesar una devolución más de una vez. | Intentar devolver un préstamo cuyo estado ya sea `DEVUELTO`. Verificar que la operación se rechace y que los datos no vuelvan a modificarse. | Verificar que el préstamo esté en estado `ACTIVO` antes de procesar la devolución. | corregidos en el commit final |
 
### Estado de verificación en el código actual
 
| ID | Corrección presente en el código | Prueba automatizada que lo verifica | Resultado |
|:---:|---|---|:---:|
| **D-01** | `PrestamoService.registrar` lanza `EquipoNoDisponibleException` si el estado no es `DISPONIBLE`. | `rechazaEquipoEnReparacion` | ✅ Pasa |
| **D-02** | `PrestamoService.registrar` compara los préstamos activos con el límite (2 estudiante, 4 funcionario) y lanza `LimitePrestamosException`. | `aplicaLimiteDeDosPrestamosAEstudiante` y `funcionarioPuedeTenerHastaCuatroPrestamos` | ✅ Pasan |
| **D-03** | `PrestamoService.devolver` lanza `ValidacionException` si el préstamo no está `ACTIVO`. El repositorio lo vuelve a comprobar dentro de la transacción. | `rechazaDevolverDosVeces` | ✅ Pasa |
 

 
