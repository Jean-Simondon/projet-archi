package presentation;

import controller.ControllerEtatStock;
import dao.DAOManager;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FenetrePrincipale extends JFrame implements ActionListener,
		WindowListener {

	private static final String TAG = "FenetrePrincipale";

	private JButton btAfficher;
	private JButton btNouveauProduit;
	private JButton btSupprimerProduit;
//	private JButton btNouvelleCategorie;
//	private JButton btSupprimerCategorie;
	private JButton btAchat;
	private JButton btVente;
	private JButton btQuitter;

	
	public FenetrePrincipale() {
		System.out.println(TAG + " : FenetrePrincipale : Constructeur");
		
		setTitle("exercice Produits");
		setBounds(500, 500, 320, 250);
		JPanel panAffichage = new JPanel();
		JPanel panNouveauSupprimerProduit = new JPanel();
//		JPanel panNouveauSupprimerCategorie = new JPanel();
		JPanel panAchatVente = new JPanel();
		JPanel panQuitter = new JPanel();
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		btAfficher = new JButton("Quantités en stock");
		btNouveauProduit = new JButton("Nouveau Produit");
		btSupprimerProduit = new JButton("Supprimer Produit");
//		btNouvelleCategorie = new JButton("Nouvelle Categorie");
//		btSupprimerCategorie = new JButton("Supprimer Categorie");
		btAchat = new JButton("Achat Produits");
		btVente = new JButton("Vente Produits");
		btQuitter = new JButton("Quitter");
		panAffichage.add(btAfficher);
		panNouveauSupprimerProduit.add(btNouveauProduit); 
		panNouveauSupprimerProduit.add(btSupprimerProduit);
//		panNouveauSupprimerCategorie.add(btNouvelleCategorie); 
//		panNouveauSupprimerCategorie.add(btSupprimerCategorie);
		panAchatVente.add(btAchat); 
		panAchatVente.add(btVente);  
		panQuitter.add(btQuitter);

		contentPane.add(panAffichage);
//		contentPane.add(panNouveauSupprimerCategorie);
		contentPane.add(panNouveauSupprimerProduit);
		contentPane.add(panAchatVente);
		contentPane.add(panQuitter);

		btAfficher.addActionListener(this);
		btNouveauProduit.addActionListener(this);
		btSupprimerProduit.addActionListener(this);
//		btNouvelleCategorie.addActionListener(this);
//		btSupprimerCategorie.addActionListener(this);
		btAchat.addActionListener(this);
		btVente.addActionListener(this);
		btQuitter.addActionListener(this);

		DAOManager.ConnexionBD();
		
		addWindowListener(this);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println(TAG + " : actionPerformed");

/* tabProduits permet de tester le fonctionnement des fen�tres avec un tableau de noms de produits "en dur"
   Quand l'application fonctionnera, il faudra bien s�r r�cup�rer les noms des produits dans le Catalogue */
		String[] tabProduits = new String[] { "Mars", "Raider", "Twix", "Treets", "M&M's", "Smarties" };
/* M�me chose pour tabCategories (partie 4) */
//		String[] tabCategories = new String[] {"Bio", "Luxe" };
		
		if (e.getSource() == btAfficher){
			System.out.println(TAG + " : btAfficher");
			String affichage = ControllerEtatStock.getInformationsProduit();
			new FenetreAffichage(affichage);
		}
		if (e.getSource() == btNouveauProduit) {
			System.out.println(TAG + " : btNouveauProduit");
//			new FenetreNouveauProduit(tabCategories);
			new FenetreNouveauProduit();
		}
		if (e.getSource() == btSupprimerProduit) {
			System.out.println(TAG + " : btSupprimerProduit");
			new FenetreSuppressionProduit(tabProduits);
		}
//		if (e.getSource() == btNouvelleCategorie){
//			new FenetreNouvelleCategorie();}
//		if (e.getSource() == btSupprimerCategorie){
//			new FenetreSuppressionCategorie(tabCategories);}
		if (e.getSource() == btAchat) {
			System.out.println(TAG + " : btAchat");
			new FenetreAchat(tabProduits);
		}
		if (e.getSource() == btVente) {
			System.out.println(TAG + " : btVente");
			new FenetreVente(tabProduits);
		}
		if (e.getSource() == btQuitter){
			System.out.println(TAG + " : btQuitter");
			System.out.println(TAG + " : Au revoir");
			System.exit(0);
		}
	}

	public void windowClosing(WindowEvent arg0) {
		System.out.println(TAG + " : windowClosing");
		DAOManager.deconnexion();
		System.out.println("Au revoir");
		System.exit(0);
	}

	public void windowActivated(WindowEvent arg0) {
		System.out.println(TAG + " : windowActivated");
	}
	public void windowClosed(WindowEvent arg0) {
		System.out.println(TAG + " : windowClosed");
	}
	public void windowDeactivated(WindowEvent arg0) {
		System.out.println(TAG + " : windowDeactivated");
	}
	public void windowDeiconified(WindowEvent arg0) {
		System.out.println(TAG + " : windowDeiconified");
	}
	public void windowIconified(WindowEvent arg0) {
		System.out.println(TAG + " : windowIconified");
	}
	public void windowOpened(WindowEvent arg0) {
		System.out.println(TAG + " : windowOpened");
	}

	
	
	public static void main(String[] args) {
		System.out.println(TAG + " : main");
		new FenetrePrincipale();
	}

}
