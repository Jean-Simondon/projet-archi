package metier;

public class Produit implements I_Produit{

    private int quantiteStock;

    private String nom;

    private double prixUnitaireHT;

    private double tauxTVA;

    public Produit(String nom , double prixUnitaireHT, int qte){
        this.quantiteStock = qte;
        this.nom = nom.trim();
        this.prixUnitaireHT = prixUnitaireHT;
        this.tauxTVA = 0.2;
    }

    /**
     * Add the number of items to the actual product
     * @return true if it's ok, else false if qte is less than 1
     */
    @Override
    public boolean ajouter(int qteAchetee) {
        if(qteAchetee > 0 ){
            this.quantiteStock += qteAchetee;
            return true;
        }
        return false;
    }

    /**
     * remove the number of items to the actual product
     * @return true if it's ok, else false if qte is less than 1 or if qte is higher than actual stock
     */
    @Override
    public boolean enlever(int qteVendue) {
        if(qteVendue > 0 && qteVendue < getQuantite()){
            this.quantiteStock -= qteVendue;
            return true;
        }
        return false;
    }

    @Override
    public String getNom() {
        return this.nom.strip().replace("\u0009"," ");
    }

    @Override
    public int getQuantite() {
        return this.quantiteStock;
    }

    @Override
    public double getPrixUnitaireHT() {
        return this.prixUnitaireHT;
    }

    @Override
    public double getPrixUnitaireTTC() {
        return this.prixUnitaireHT + (this.prixUnitaireHT * this.tauxTVA);
    }

    @Override
    public double getPrixStockTTC() {
        double somme = 0;
        for(int i = 0 ; i < this.quantiteStock ; i++){
            somme += this.getPrixUnitaireTTC();
        }
        return somme;
    }

    @Override
    public String toString() {
        return getNom() + " prixHT:" + getPrixUnitaireHT() + "€ prixTTC:" + getPrixUnitaireTTC() + "€ quantité en stock:" + getQuantite();
    }
}
