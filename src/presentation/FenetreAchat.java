package presentation;

import controller.produit.ControllerAchatVente;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FenetreAchat extends JFrame implements ActionListener
{

	private static final String TAG = "FenetreAchat";

	private JButton btAchat;
	private JTextField txtQuantite;
	private JComboBox<String> combo;

	public FenetreAchat(String[] lesProduits)
	{

		setTitle("Achat");
		setBounds(500, 500, 200, 125);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		btAchat = new JButton("Achat");
		txtQuantite = new JTextField(5);
		txtQuantite.setText("0");

		combo = new JComboBox<>(lesProduits);
		combo.setPreferredSize(new Dimension(100, 20));
		contentPane.add(new JLabel("Produit"));
		contentPane.add(combo);
		contentPane.add(new JLabel("Quantité achetée"));
		contentPane.add(txtQuantite);
		contentPane.add(btAchat);

		btAchat.addActionListener(this);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == btAchat){
			ControllerAchatVente.acheterProduit((String) combo.getSelectedItem(),Integer.parseInt(txtQuantite.getText()));
		}
	}

}
