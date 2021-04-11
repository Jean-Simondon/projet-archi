package dao;

import java.sql.*;

public class DAOManagerBD {

    private static final String TAG = "DAOManager";

    protected static ResultSet rs;
    protected static Connection cn;
    protected static PreparedStatement pst;

    public static void ConnexionBD()
    {
        System.out.println(TAG + " : ConnexionBD");

        String url = "jdbc:oracle:thin:@162.38.222.149:1521:iut";
        String driver = "oracle.jdbc.driver.OracleDriver";
        String login = "simondonj";
        String mdp = "21092020";

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

