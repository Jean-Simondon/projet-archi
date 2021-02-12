package metier;

import dao.ProduitDAO;
import exception.database.MAJImpossible;

import java.text.DecimalFormat;

public class Produit implements I_Produit {

    private final int id;

    private int quantiteStock;

    private String nom;

    private double prixUnitaireHT;

    private double tauxTVA;

    private DecimalFormat df; // utile à former les sorties de chifres en String à 2 décmal après la virgule

    private ProduitDAO produitDAO; // CRUD vers la BDD

    /**
     * Constructeur avec id, donc déjà présent en BDD
     * @param id
     * @param nom
     * @param prixUnitaireHT
     * @param qte
     */
    public Produit(int id, String nom , double prixUnitaireHT, int qte)
    {
        this.id = id;
        this.quantiteStock = qte;
        this.nom = nom.trim();
        this.prixUnitaireHT = prixUnitaireHT;
        this.tauxTVA = 0.2;

        initialization();
    }

    /**
     * Constructeur lorsque l'on ne fournit pas l'ID, donc que le produit n'existe pas dans la BDD, alors on le crée avant d'instancier l'objet
     * @param nom
     * @param prixUnitaireHT
     * @param qte
     */
    public Produit(String nom , double prixUnitaireHT, int qte)
    {
        this.id = ProduitDAO.create(nom, prixUnitaireHT, qte);
        this.quantiteStock = qte;
        this.nom = nom.trim();
        this.prixUnitaireHT = prixUnitaireHT;
        this.tauxTVA = 0.2;

        initialization();
    }

    private void initialization()
    {
        df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        df.setDecimalSeparatorAlwaysShown(true);
    }

    /**
     * Add the number of items to the actual product
     * @return true if it's ok, else false if qte is less than 1
     */
    @Override
    public boolean ajouter(int qteAchetee) throws MAJImpossible {
        if(qteAchetee > 0 ){
            this.quantiteStock += qteAchetee;
            this.save();
            return true;
        }
        return false;
    }

    /**
     * remove the number of items to the actual product
     * @return true if it's ok, else false if qte is less than 1 or if qte is higher than actual stock
     */
    @Override
    public boolean enlever(int qteVendue) throws MAJImpossible {
        if(qteVendue > 0 && qteVendue < getQuantite()){
            this.quantiteStock -= qteVendue;
            this.save();
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

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return getNom() + " - prix HT : " + df.format( getPrixUnitaireHT() ) + " € - prix TTC : " + df.format(getPrixUnitaireTTC()) + " € - quantité en stock : " + getQuantite() + "\n";
    }

    @Override
    public void save() throws MAJImpossible {
        if(!ProduitDAO.update(this)){
            throw new MAJImpossible();
        }
    }
}
