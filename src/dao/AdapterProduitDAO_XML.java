package dao;

import exception.product.PrixInvalide;
import exception.product.QteInvalide;
import metier.I_Produit;
import metier.Produit;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdapterProduitDAO_XML implements I_ProduitDAO {

    private static final String TAG = "AdapterProduitDAO_XML";

    private final ProduitDAO_XML produitDAO_xml;

    private int counter;

    public AdapterProduitDAO_XML(){
        this.produitDAO_xml = new ProduitDAO_XML();
        this.counter = 1;
    }

    @Override
    public int create(String nom, double prixUnitaireHT, int qte){
            this.produitDAO_xml.creer(new Produit(counter,nom,prixUnitaireHT,qte));
            counter++;
            return counter;
    }

    /**
     * @param p le produit
     * @return l'ID du produit, donné par la BDD
     */
    @Override
    public int create(I_Produit p) throws PrixInvalide, QteInvalide {
        Logger.getLogger(TAG).log(Level.INFO," Creation Produit avec instance");
        return create(p.getNom(), p.getPrixUnitaireHT(), p.getQuantite());
    }

    @Override
    public I_Produit readById(int id) {
        return null; //On utilise pas cette methode vu qu'on a pas d'id en XML
    }

    public I_Produit readByName(String nom){
        Logger.getLogger(TAG).log(Level.INFO,"Read by name");
        return this.produitDAO_xml.lire(nom);
    }

    @Override
    public List<I_Produit> readAll(){
        Logger.getLogger(TAG).log(Level.INFO,"readAll");
        return this.produitDAO_xml.lireTous();
    }

    @Override
    public boolean update(I_Produit p) {
        Logger.getLogger(TAG).log(Level.INFO,"Update");
        return this.produitDAO_xml.maj(p);
    }

    @Override
    public boolean delete(I_Produit p) {
        Logger.getLogger(TAG).log(Level.INFO,"Delete");
        return this.produitDAO_xml.supprimer(p);
    }

    @Override
    public Produit hydrateProduit() { //Pas utilisé parce que pas besoin d'hydrater
        Logger.getLogger(TAG).log(Level.INFO,"Hydrate (should not be used)");
        return null;
    }

    public void disconnect(){
    }
}
