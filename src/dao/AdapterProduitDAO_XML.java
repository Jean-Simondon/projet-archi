package dao;

import exception.database.DeleteException;
import exception.database.HydrateException;
import exception.database.ReadException;
import exception.database.UpdateException;
import exception.product.ProductException;
import metier.I_Produit;
import metier.Produit;

import java.util.ArrayList;
import java.util.List;

public class AdapterProduitDAO_XML implements I_ProduitDAO {

    private static final String TAG = "AdapterProduitDAO_XML";

    private final ProduitDAO_XML produitDAO_xml;

    public AdapterProduitDAO_XML(){
        this.produitDAO_xml = new ProduitDAO_XML();
    }

    @Override
    public int create(String nom, double prixUnitaireHT, int qte) throws ProductException {
        if(this.produitDAO_xml.creer(new Produit(nom,prixUnitaireHT,qte))){
            return -1; //todo Changer ça pour retourner un id plus adéquat parce que le XML dao renvoie un boolean
        }
        else{
            throw new ProductException("Création du produit XML impossible");
        }
    }

    /**
     * @param p le produit
     * @return l'ID du produit, donné par la BDD
     */
    @Override
    public int create(I_Produit p) throws ProductException {
        System.out.println(TAG + " : Create With Instance");
        return create(p.getNom(), p.getPrixUnitaireHT(), p.getQuantite());
    }

    @Override
    public I_Produit readById(int id) throws ReadException, HydrateException {
        return null; //On utilise pas cette methode vu qu'on a pas d'id en XML
    }

    public I_Produit readByName(String nom){
        return this.produitDAO_xml.lire(nom);
    }

    @Override
    public List<I_Produit> readAll() throws ReadException {
       try{
           return this.produitDAO_xml.lireTous();
       }catch (Exception e){
           throw new ReadException();
       }

    }

    @Override
    public boolean update(I_Produit p) throws UpdateException {
        if(!this.produitDAO_xml.maj(p)){
            throw new UpdateException();
        }
        return true;
    }

    @Override
    public boolean delete(I_Produit p) throws DeleteException {
        if(!this.produitDAO_xml.supprimer(p)) {
            throw new DeleteException();
        }
        return true;
    }

    @Override
    public Produit hydrateProduit() throws HydrateException { //Pas utilisé parce que pas besoin d'hydrater
        return null;
    }
}
