package dao;

public class DAOFactory {

    private static final String TAG = "DAOFactory";

    public static ProduitDAO_I getInstance(){
        return new ProduitDAO();
    }

}
