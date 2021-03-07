package metier;

import dao.I_ProduitDAO;
import dao.DAOFactory;
import exception.database.MAJImpossible;
import exception.database.UpdateException;
import exception.product.ProductException;

import java.sql.SQLException;
import java.text.DecimalFormat;

public class Produit implements I_Produit {

    private static final String TAG = "Produit";

    private final int id; // identifiant donné par la base de données

    private String nom;

    private double prixUnitaireHT;

    private int quantiteStock;

    private double tauxTVA = 0.2;

    static private DecimalFormat df; // utile à former les sorties de chifres en String à 2 décmal après la virgule

    private static I_ProduitDAO produitDAO; // CRUD vers la BDD, persistance des données

    /**
     * Constructeur avec id, donc déjà présent en BDD
     * @param id l'id
     * @param nom le nom
     * @param prixUnitaireHT le prix HT
     * @param qte la quantité
     */
    public Produit(int id, String nom , double prixUnitaireHT, int qte) throws ProductException {
        if( id < 0 ) {
            this.id = DAOFactory.getInstance().create(nom, prixUnitaireHT, qte);
        } else {
            this.id = id;
        }
        this.nom = nom.trim();
        this.prixUnitaireHT = prixUnitaireHT;
        this.quantiteStock = qte;

        if ( df == null ) {
            initialization();
        }
        if( produitDAO == null ){
            produitDAO = DAOFactory.getInstance();
        }

    }

    /**
     * Constructeur lorsque l'on ne fournit pas l'ID, donc que le produit n'existe pas dans la BDD, alors on le crée avant d'instancier l'objet
     * @param nom le nom du produit
     * @param prixUnitaireHT le prix
     * @param qte la quantité
     */
    public Produit(String nom , double prixUnitaireHT, int qte) throws ProductException {
        this(-1, nom, prixUnitaireHT, qte);
    }

    /**
     * Initialize le DecimalFormat de la classe Produit
     */
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
//            this.save(); // A décommenter quand la BDD sera dispo
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
//            this.save(); // A décommenter quand la BDD sera dispo
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
    public void save() throws UpdateException, SQLException {
        produitDAO.update(this);

    }
}
