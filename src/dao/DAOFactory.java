package dao;

/**
 * renvoie une instance du DAO souhaité
 */
public class DAOFactory {

    private static final String TAG = "DAOFactory";

    private static I_ProduitDAO dao;

    public static I_ProduitDAO getInstance(){
        System.out.println(TAG +  " : getInstance");
        if( dao == null) {
            dao = new ProduitDAO();
        }
        return dao;
    }

}
