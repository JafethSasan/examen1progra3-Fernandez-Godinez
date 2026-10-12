
package cr.ac.una.app;

import cr.ac.una.config.ConexionBD;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class App {

    public static void main(String[] args) {
        int opcion;

        do {
            Consola.titulo("Examen Práctico Integrador - Programación III");

            System.out.println("1. Probar conexión con la base de datos");
            System.out.println("0. Salir");

            opcion = Consola.leerEntero("Opción: ");

            switch (opcion) {
                case 1 -> probarConexion();
                case 0 -> System.out.println("Fin del programa.");
                default -> System.out.println("Opción no válida.");
            }

            System.out.println();

        } while (opcion != 0);
    }

    private static void probarConexion() {
        System.out.println("Probando conexión con la base de datos...");

        try (Connection con = ConexionBD.obtenerConexion()) {

            if (con == null || con.isClosed()) {
                System.out.println(
                    "No fue posible establecer la conexión."
                );
                return;
            }

            DatabaseMetaData meta = con.getMetaData();

            System.out.println("Conexión exitosa.");
            System.out.println(
                "Motor: " + meta.getDatabaseProductName()
            );
            System.out.println(
                "Versión: " + meta.getDatabaseProductVersion()
            );
            System.out.println(
                "Base de datos: " + con.getCatalog()
            );

        } catch (SQLException e) {
            System.out.println("No fue posible conectar con la base de datos.");
            System.out.println("Detalle: " + e.getMessage());
            System.out.println("Código SQL: " + e.getErrorCode());
            System.out.println("Estado SQL: " + e.getSQLState());
        } catch (RuntimeException e) {
            System.out.println(
                "Error en la configuración de la conexión: "
                + e.getMessage()
            );
        }
    }
}

