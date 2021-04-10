package dao.catalogue;

public class CatalogueDAOFactory {

    private static final String TAG = "CatalogueDAOFactory";

    private static I_CatalogueDAO dao;

    public static I_CatalogueDAO getInstance()
    {
        System.out.println(TAG +  " : getInstance");
        if( dao == null) {
            dao = new CatalogueDAO();
        }
        return dao;
    }
}
