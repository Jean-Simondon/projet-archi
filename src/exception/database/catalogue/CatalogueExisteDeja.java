package exception.database.catalogue;

public class CatalogueExisteDeja extends CatalogueException{
    public CatalogueExisteDeja()
    {
        super("Un catalogue existe déjà sous ce nom");
    }
}
