package metier.catalogue;

import dao.produit.ProduitDAOFactory;
import dao.produit.I_ProduitDAO;
import exception.database.DeleteException;
import exception.database.ReadException;
import exception.database.UpdateException;
import exception.product.PrixInvalide;
import exception.product.ProductException;
import exception.product.QteInvalide;
import metier.produit.I_Produit;
import metier.produit.Produit;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Catalogue implements I_Catalogue
{

    private int id;

    private int nbProduits;

    private static final String TAG = "[Catalogue]";

    private List<I_Produit> lesProduits; // Agressive Loading, on récupère tous les produits dès l'instanciation du catalogue

    private String nom;

    static private DecimalFormat df;

    /**
     * Data mapper : Les classes métiers tels que catalogue utilisent les DAO, et non la classe produit en elle même
     */
    private I_ProduitDAO produitDAO;

    public Catalogue(int id, String nom, int nbProduits)
    {
        this.id = id;
        this.nbProduits = nbProduits;
        this.nom = nom;
        this.lesProduits = new ArrayList<>();
        if ( df == null ) {
            initializeDF();
        }
    }

    public void load()
    {
        try {
            produitDAO = ProduitDAOFactory.getInstance();
            lesProduits = produitDAO.readByCatalogue(this.id); // Agressive loading
        } catch (ReadException e) {
            Logger.getLogger(Catalogue.TAG).log(Level.WARNING,"Erreur pendant l'initialisation du catalogue");
        }
    }

    /**
     * Initialize le DecimalFormat de la classe Catalogue
     */
    private void initializeDF()
    {
        df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        df.setDecimalSeparatorAlwaysShown(true);
    }

    @Override
    public boolean addProduit(I_Produit produit)
    {
        if( isValidProduit(produit) ) {
            try{
                this.produitDAO.create(produit);
                lesProduits.add(produit);
                return true;
            }catch (ProductException e ) {
                e.printStackTrace();
                return false;
            }
        }
        majNbProduit();
        return false;
    }

    @Override
    public boolean addProduit(String nom, double prix, int qte)
    {
        try{
            I_Produit produit = new Produit(nom,prix,qte);
            return addProduit(produit);
        }catch(QteInvalide | PrixInvalide e ) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Add a collection of products to the catalogue
     * @param l the collection
     * @return the number of items added
     */
    @Override
    public int addProduits(List<I_Produit> l)
    {
        if( l == null) {
            return 0;
        }
        int count = 0;
        for (I_Produit p : l) {
            if( addProduit(p) ) {
                count++;
            }
        }
        majNbProduit();
        return count;
    }

    @Override
    public boolean removeProduit(String nom)
    {
        int index = this.findIndexOfProduct(nom);
        if(index == -1 ){
            return false;
        }
        try {
            if(produitDAO.delete(this.lesProduits.remove(index))){
                majNbProduit();
                return true;
            }
            else{
                return false;
            }
        } catch (DeleteException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean acheterStock(String nomProduit, int qteAchetee)
    {
        int index = this.findIndexOfProduct(nomProduit);
        if(index == -1){
            return false;
        }
        I_Produit produit = this.lesProduits.get(index);
        try {
            produit.ajouter(qteAchetee);
            this.produitDAO.update(produit);
            return true;
        }catch (UpdateException e ){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean vendreStock(String nomProduit, int qteVendue)
    {
        int index = this.findIndexOfProduct(nomProduit);
        if(index == -1){
            return false;
        }
        I_Produit produit = this.lesProduits.get(index);
        try {
            produit.enlever(qteVendue);
            this.produitDAO.update(produit);
            return true;
        }catch (UpdateException e ){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String[] getNomProduits()
    {
        ArrayList<String> noms = new ArrayList<>();
        String[] arrayNom = new String[this.lesProduits.size()];

        for (I_Produit lesProduit : this.lesProduits) {
            noms.add(lesProduit.getNom());
        }
        noms.sort(String::compareTo);

        for (int i = 0 ; i < noms.size() ; i++){
            arrayNom[i] = noms.get(i);
        }

        return arrayNom;
    }

    @Override
    public double getMontantTotalTTC()
    {
        double montantTotal = 0;
        for(I_Produit produit : this.lesProduits){
            montantTotal += produit.getPrixStockTTC();
        }
        return Math.rint(montantTotal * 100) / 100;
    }

    @Override
    public String getName() {
        return this.nom;
    }

    @Override
    public String getNameAndNumber() {
        return this.nom + " : " + this.lesProduits.size() + " produits";
    }

    public int findIndexOfProduct(String nomProduit)
    {
        int index = -1;
        for(I_Produit produit : this.lesProduits){
            if(produit.getNom().equals(nomProduit)){
                index = this.lesProduits.indexOf(produit);
            }
        }
        return index;
    }

    /**
     * Je crois que cette méthode sert à "terminer" le programme du coup
     */
    @Override
    public void clear()
    {
        this.produitDAO.disconnect();
    }

    @Override
    public String toString()
    {
        String lastLine = "\n" + "Montant total TTC du stock : " + df.format( getMontantTotalTTC()  )+ " €";

        StringBuilder body = new StringBuilder();
        for (I_Produit produit : lesProduits) {
            body.append(produit.toString());
        }
        return body + lastLine;
    }


    /**
     * vérifie si un produit remplie les conditions pour être ajouté au catalogue
     * @param produit les produits
     * @return Bolean estValide
     */
    public boolean isValidProduit(I_Produit produit)
    {
        if (produit == null) {
            return false;
        } else if ( lesProduits.contains(produit) ) {
            return false;
        }
        if( produit.getQuantite() < 0) {
            return false;
        }
        if( produit.getPrixUnitaireHT() < 1) {
            return false;
        }
        for (I_Produit p : lesProduits) {
            if(p.getNom().equals(produit.getNom())){
                return false;
            }
        }
        return true;
    }

    /**
     * Mets à jour le nombre de nbProduit en fonction de la taille de lesProduits
     * Doit etre appelé à la fin de toutes les méthodes modifiant la liste lesProduits
     */
    public void majNbProduit(){
        this.nbProduits = this.lesProduits.size();
    }

    public int getNbProduits(){
        return this.nbProduits;
    }

    public int getId()
    {
        return id;
    }
}


