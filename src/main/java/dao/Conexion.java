package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Conexion {

    protected static Connection cnx = null;

    public static Connection conectar() throws Exception {

        try {

            String user = "sa2";

            String pwd = "73829730";

            String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

            String url = "jdbc:sqlserver://localhost:1433;databaseName=bdPizzaHut";

            Class.forName(driver).newInstance();

            cnx = DriverManager.getConnection(url, user, pwd);

        } catch (SQLException e) {

            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "Error en la Conexion{0}", e.getMessage());

        }

        return cnx;

    }

    public static void cerrarCnx() throws Exception {

        if (Conexion.cnx != null) {

            cnx.close();

        }

    }

    public static void main(String[] args) {

        Conexion cones = new Conexion();

        try {

            cones.conectar();

            if (cones.cnx == null) {

                LogManager lgmngr = LogManager.getLogManager();
                Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
                log.log(Level.INFO, "Apagado");

            } else {

                LogManager lgmngr = LogManager.getLogManager();
                Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
                log.log(Level.INFO, "Encendido");

            }

        } catch (SQLException e) {

            LogManager lgmngr = LogManager.getLogManager();
            Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);
            log.log(Level.INFO, "{0}", e.getMessage());

        } catch (Exception ex) {

            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

}
