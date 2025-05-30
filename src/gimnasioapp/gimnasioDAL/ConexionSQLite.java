package gimnasioapp.gimnasioDAL;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSQLite {

    // Ruta a la base de datos SQLite
    private static final String URL = "jdbc:sqlite:" + new File("src/resources/db/GimnasioApp.s3db").getAbsolutePath();

    public static Connection conectar() {
        Connection conn = null;
        try {
            // Cargar el driver JDBC para SQLite (normalmente opcional en versiones modernas)
            Class.forName("org.sqlite.JDBC");

            // Establecer la conexión
            conn = DriverManager.getConnection(URL);
            System.out.println("✅ Conexión establecida con SQLite.");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver JDBC no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar a la base de datos: " + e.getMessage());
        }
        return conn;
    }

   
}
    