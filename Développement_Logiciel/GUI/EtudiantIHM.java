package GUI;
import dao.ConnectionDAO;
import dao.EtudiantDAO;

import model.Etudiants;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import model.Admin;
import java.awt.Font;
import java.awt.Color;

public class EtudiantIHM {

	 private JFrame jframe;
	    
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            EtudiantIHM menu = new  EtudiantIHM();
	            menu.jframe.setVisible(true);
	        });
	    }   
	     public EtudiantIHM() {
		        initialize();
		    }

		  
		    private void initialize() {
		        jframe = new JFrame("Menu Etudiant");
		        jframe.getContentPane().setFont(new Font("Times New Roman", Font.BOLD, 14));
		        jframe.getContentPane().setBackground(new Color(128, 0, 128));
		        jframe.getContentPane().setForeground(new Color(0, 0, 0));
		        jframe.setBounds(100, 100, 600, 650);
		        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        jframe.getContentPane().setLayout(null);
		        
		        JButton btnNewButton = new JButton("FAIRE MES CHOIX");
		        btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
		        btnNewButton.setBounds(95, 211, 409, 75);
		        jframe.getContentPane().add(btnNewButton);
		        
		        JButton btnNewButton_1 = new JButton("VOIR MES INFOS");
		        btnNewButton_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		        btnNewButton_1.addActionListener(new ActionListener() {
		        	public void actionPerformed(ActionEvent e) {
		        	}
		        });
		        btnNewButton_1.setBounds(95, 413, 409, 75);
		        jframe.getContentPane().add(btnNewButton_1);
		        
		        JLabel lblNewLabel = new JLabel("MENU");
		        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 34));
		        lblNewLabel.setBounds(231, 81, 139, 51);
		        jframe.getContentPane().add(lblNewLabel);
                jframe.setVisible(true);
		        
		    }        
	    }

