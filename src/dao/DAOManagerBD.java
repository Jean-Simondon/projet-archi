package dao;

import java.sql.*;

public class DAOManagerBD {

    private static final String TAG = "DAOManager";

    private static String url = "jdbc:oracle:thin:@162.38.222.149:1521:iut";
    private static String driver = "oracle.jdbc.driver.OracleDriver";
    private static String login = "simondonj";
    private static String mdp = "21092020";

    protected static ResultSet rs;
    protected static Connection cn;
    protected static PreparedStatement pst;
    protected static Statement st;

    public static void ConnexionBD()
    {
        System.out.println(TAG + " : ConnexionBD");

        try {
            Class.forName(driver);
        } catch ( ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            if (cn == null) {
                System.out.println(TAG + " : Connexion en cours");
                cn = DriverManager.getConnection(url, login, mdp);
            } else {
                System.out.println(TAG + " : La connexion existait déjà");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void deconnexion()
    {
        System.out.println(TAG + " : deconnexionDB");

        try {
            rs.close();
            pst.close();
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

