package dao.catalogue;

import exception.database.DeleteException;
import exception.database.HydrateException;
import exception.database.ReadException;
import exception.database.UpdateException;
import exception.product.ProductException;
import metier.catalogue.I_Catalogue;

import java.util.List;

public interface I_CatalogueDAO {

    int create(String nom) throws ProductException;

    int create(I_Catalogue c) throws ProductException;

    I_Catalogue readById(int id) throws ReadException, HydrateException;

    I_Catalogue readByName(String name) throws ReadException, HydrateException, ProductException;

    List<I_Catalogue> readAll() throws ReadException;

    boolean update(I_Catalogue c) throws UpdateException;

    boolean delete(I_Catalogue c) throws DeleteException;

    I_Catalogue hydrateCatalogue() throws HydrateException, ProductException;

    void disconnect();

}
