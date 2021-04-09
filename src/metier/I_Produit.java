package metier;

public interface I_Produit
{
    boolean ajouter(int qteAchetee);
    boolean enlever(int qteVendue);
    String getNom();
    int getQuantite();
    double getPrixUnitaireHT();
    double getPrixUnitaireTTC();
    double getPrixStockTTC();
    int getId();
    String toString();
}