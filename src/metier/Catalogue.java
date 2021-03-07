package metier;

import dao.ProduitDAO;
import exception.database.MAJImpossible;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Catalogue implements I_Catalogue {

    private static final String TAG = "Catalogue";

    private ArrayList<I_Produit> lesProduits; // Agressive Loading, on récupère tous les produits dès l'instanciation du catalogue

    static private DecimalFormat df;

    public Catalogue() {
        lesProduits = ProduitDAO.readAll(); // Agressive loading

        if ( df == null ) {
            initialization();
        }
    }

    /**
     * Initialize le DecimalFormat de la classe Catalogue
     */
    private void initialization()
    {
        df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        df.setDecimalSeparatorAlwaysShown(true);
    }

    @Override
    public boolean addProduit(I_Produit produit) {
        if( isValidProduit(produit) ) {
            return lesProduits.add(produit);
        }
        return false;
    }

    @Override
    public boolean addProduit(String nom, double prix, int qte) {
        I_Produit produit = new Produit(nom,prix,qte);
        return addProduit(produit);
    }

    /**
     * Add a collection of products to the catalogue
     * @param l the collection
     * @return the number of items added
     */
    @Override
    public int addProduits(List<I_Produit> l) {
        if( l == null) {
            return 0;
        }
        int count = 0;
        for (I_Produit p : l) {
            if( addProduit(p) ) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean removeProduit(String nom) {
        int index = this.findIndexOfProduct(nom);
        if(index == -1 ){
            return false;
        }
        return this.lesProduits.remove(index) != null;
    }

    @Override
    public boolean acheterStock(String nomProduit, int qteAchetee) throws MAJImpossible {
        int index = this.findIndexOfProduct(nomProduit);
        if(index == -1 ){
            return false;
        }
        return this.lesProduits.get(index).ajouter(qteAchetee);
    }

    @Override
    public boolean vendreStock(String nomProduit, int qteVendue) throws MAJImpossible {
        int index = this.findIndexOfProduct(nomProduit);
        if(index == -1 ){
            return false;
        }
        return this.lesProduits.get(index).enlever(qteVendue);
    }

    @Override
    public String[] getNomProduits() {
        ArrayList<String> noms = new ArrayList<>();
        String[] arrayNom = new String[this.lesProduits.size()];

        for (I_Produit lesProduit : this.lesProduits) {
            noms.add(lesProduit.getNom());
        }
        noms.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        for (int i = 0 ; i < noms.size() ; i++){
            arrayNom[i] = noms.get(i);
        }

        return arrayNom;
    }

    @Override
    public double getMontantTotalTTC() {
        double montantTotal = 0;
        for(I_Produit produit : this.lesProduits){
            montantTotal += produit.getPrixStockTTC();
        }
        return Math.rint(montantTotal * 100) / 100;
    }

    public int findIndexOfProduct(String nomProduit){
        int index = -1;
        for(I_Produit produit : this.lesProduits){
            if(produit.getNom().equals(nomProduit)){
                index = this.lesProduits.indexOf(produit);
            }
        }
        return index;
    }

    //Todo  : je sais pas a quoi elle sert cette méthode..
    @Override
    public void clear() {

    }

    @Override
    public String toString() {
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
    public boolean isValidProduit(I_Produit produit) {
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

}

