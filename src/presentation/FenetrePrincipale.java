package presentation;

import controller.produit.ControllerAchatVente;
import controller.produit.ControllerCreationSuppression;
import controller.produit.ControllerEtatStock;
import controller.produit.ControllerManager;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class FenetrePrincipale extends JFrame implements ActionListener, WindowListener
{

	private static final String TAG = "FenetrePrincipale";

	private String nomCatalogue;

	private JButton btAfficher;
	private JButton btNouveauProduit;
	private JButton btSupprimerProduit;
//	private JButton btNouvelleCategorie;
//	private JButton btSupprimerCategorie;
	private JButton btAchat;
	private JButton btVente;
	private JButton btQuitter;


	public FenetrePrincipale(String nomCatalogue)
	{
		Logger.getLogger(TAG).log(Level.INFO,"constructeur");
		this.nomCatalogue = nomCatalogue;
		
		setTitle("Exercice");
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

		ControllerManager.aggresiveLoading(nomCatalogue);

		addWindowListener(this);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		Logger.getLogger(TAG).log(Level.INFO,"actionPerformed");

		String[] tabProduits = new String[] { "Mars", "Raider", "Twix", "Treets", "M&M's", "Smarties" };

		/* TABLEAU DE TEST POUR QUAND ON AURA LES CATEGORIE  */
		String[] tabCategories = new String[] {"Bio", "Luxe" };
		
		if ( e.getSource() == btAfficher ) {
			Logger.getLogger(TAG).log(Level.INFO,"btAfficher");
			String affichage = ControllerEtatStock.getInformationsProduit();
			new FenetreAffichage(nomCatalogue, affichage);
		}
		if ( e.getSource() == btNouveauProduit ) {
			Logger.getLogger(TAG).log(Level.INFO,"btNouveauProduit");
//			new FenetreNouveauProduit(tabCategories);
			new FenetreNouveauProduit();
		}
		if ( e.getSource() == btSupprimerProduit ) {
			Logger.getLogger(TAG).log(Level.INFO,"btSupprimerProduit");
			new FenetreSuppressionProduit(ControllerCreationSuppression.getNomsProduits());
		}
//		if (e.getSource() == btNouvelleCategorie){
//			new FenetreNouvelleCategorie();}
//		if (e.getSource() == btSupprimerCategorie){
//			new FenetreSuppressionCategorie(tabCategories);}
		if (e.getSource() == btAchat) {
			Logger.getLogger(TAG).log(Level.INFO,"btAchat");
			new FenetreAchat(ControllerAchatVente.getNomsProduits());
		}
		if (e.getSource() == btVente) {
			Logger.getLogger(TAG).log(Level.INFO,"btVente");
			new FenetreVente(ControllerAchatVente.getNomsProduits());
		}
		if (e.getSource() == btQuitter){
			Logger.getLogger(TAG).log(Level.INFO,"btQuitter");
			System.out.println(TAG + " : Au revoir");
			System.exit(0);
		}
	}

	public void windowClosing(WindowEvent arg0)
	{
		Logger.getLogger(TAG).log(Level.INFO,"windowClosing");
		ControllerManager.disconnect();
		System.out.println("Au revoir");
		System.exit(0);
	}

	public void windowActivated(WindowEvent arg0)
	{
//		Logger.getLogger(TAG).log(Level.INFO,"windowActivated");
	}
	public void windowClosed(WindowEvent arg0)
	{
//		Logger.getLogger(TAG).log(Level.INFO,"windowClosed");
	}
	public void windowDeactivated(WindowEvent arg0)
	{
//		Logger.getLogger(TAG).log(Level.INFO,"windowDeactivated");
	}
	public void windowDeiconified(WindowEvent arg0)
	{
//		Logger.getLogger(TAG).log(Level.INFO,"windowDeiconified");
	}
	public void windowIconified(WindowEvent arg0)
	{
//		Logger.getLogger(TAG).log(Level.INFO,"windowIconified");
	}
	public void windowOpened(WindowEvent arg0)
	{
//		Logger.getLogger(TAG).log(Level.INFO,"windowOpened");
	}


	/*
	public static void main(String[] args)
	{
		Logger.getLogger(TAG).log(Level.INFO,"main launch");
		new FenetrePrincipale("");
	}
	 */

}
