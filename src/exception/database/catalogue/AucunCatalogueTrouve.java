package exception.database.catalogue;

public class AucunCatalogueTrouve extends CatalogueException {
    public AucunCatalogueTrouve()
    {
        super("Le catalogue recherché n'existe pas ");
    }
}
