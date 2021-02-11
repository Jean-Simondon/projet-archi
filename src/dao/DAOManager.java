package dao;

import java.sql.*;

public class DAOManager {

    public static ResultSet rs;
    public static Connection cn;
    public static PreparedStatement pst;

    public static void ConnexionBD()
    {
        String url = "jdbc:oracle:thin:@162.38.222.149:1521:iut";
        String driver = "oracle.jdbc.driver.OracleDriver";
        String login = "simondonj";
        String mdp = "21092020";

        System.out.println("Connexion");

        try {
            Class.forName(driver);
        } catch ( ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            cn = DriverManager.getConnection(url, login, mdp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("fin connexion");

    }

    public static void deconnexion()
    {

        System.out.println("début déconnexion");

        try {
            rs.close();
            pst.close();
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }





}
