package dao.catalogue;

import exception.database.DeleteException;
import exception.database.HydrateException;
import exception.database.ReadException;
import exception.database.UpdateException;
import exception.database.catalogue.CatalogueException;
import exception.product.ProductException;
import metier.catalogue.I_Catalogue;

import javax.xml.catalog.CatalogException;
import java.util.List;

public interface I_CatalogueDAO {

    int create(String nom) throws Exception;

    int create(I_Catalogue c) throws Exception;

    I_Catalogue readById(int id) throws ReadException, HydrateException;

    I_Catalogue readByName(String name) throws ReadException, HydrateException, CatalogueException;

    List<I_Catalogue> readAll();

    boolean update(I_Catalogue c) throws UpdateException;

    boolean delete(I_Catalogue c) throws DeleteException;
    boolean delete(String name) throws DeleteException;

    I_Catalogue hydrateCatalogue() throws HydrateException, CatalogueException;

    void disconnect();

}
