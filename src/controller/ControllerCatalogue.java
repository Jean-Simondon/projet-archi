package controller;

import dao.catalogue.I_CatalogueDAO;
import dao.catalogue.CatalogueDAOFactory;

public class ControllerCatalogue {

    /**
     * Retourne tous les catalogues
     */
    public void getCatalogues()
    {
        I_CatalogueDAO dao = CatalogueDAOFactory.getInstance();
        return dao.readAll();
    }



}
