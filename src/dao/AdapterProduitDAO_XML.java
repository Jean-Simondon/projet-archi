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

    public AdapterProduitDAO_XML(){
        this.produitDAO_xml = new ProduitDAO_XML();
    }

    @Override
    public int create(String nom, double prixUnitaireHT, int qte) throws QteInvalide, PrixInvalide {
            this.produitDAO_xml.creer(new Produit(nom,prixUnitaireHT,qte));
            I_Produit produit = this.produitDAO_xml.lire(nom);
            return produit.getId();
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
        return this.produitDAO_xml.lire(nom);
    }

    @Override
    public List<I_Produit> readAll(){

           return this.produitDAO_xml.lireTous();

    }

    @Override
    public boolean update(I_Produit p) {
        return this.produitDAO_xml.maj(p);
    }

    @Override
    public boolean delete(I_Produit p) {
        return this.produitDAO_xml.supprimer(p);
    }

    @Override
    public Produit hydrateProduit() { //Pas utilisé parce que pas besoin d'hydrater
        return null;
    }
}
