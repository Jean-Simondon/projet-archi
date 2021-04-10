package dao.catalogue;

import exception.database.DeleteException;
import exception.database.HydrateException;
import exception.database.ReadException;
import exception.database.UpdateException;
import exception.product.ProductException;
import metier.catalogue.I_Catalogue;

import java.util.List;

public class CatalogueDAO implements I_CatalogueDAO {


    @Override
    public int create(String nom) throws ProductException {
        return 0;
    }

    @Override
    public int create(I_Catalogue c) throws ProductException {
        return 0;
    }

    @Override
    public I_Catalogue readById(int id) throws ReadException, HydrateException {
        return null;
    }

    @Override
    public I_Catalogue readByName(String name) throws ReadException, HydrateException, ProductException {
        return null;
    }

    @Override
    public List<I_Catalogue> readAll() throws ReadException {
        return null;
    }

    @Override
    public boolean update(I_Catalogue c) throws UpdateException {
        return false;
    }

    @Override
    public boolean delete(I_Catalogue c) throws DeleteException {
        return false;
    }

    @Override
    public I_Catalogue hydrateCatalogue() throws HydrateException, ProductException {
        return null;
    }

    @Override
    public void disconnect() {

    }
}
