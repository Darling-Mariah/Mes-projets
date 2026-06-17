package GUI;

import dao.AdministrateurDAO;

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

public class loginGUI {
	
	 private JFrame frame;
	    private JTextField usernameField;
	    private JPasswordField passwordField;

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            loginGUI login = new loginGUI();
	            login.frame.setVisible(true);
	        });
	    }

	    public loginGUI() {
	        initialize();
	    }

	  
	    private void initialize() {
	        frame = new JFrame("Connexion");
	        frame.getContentPane().setBackground(new Color(128, 0, 128));
	        frame.getContentPane().setForeground(new Color(0, 0, 0));
	        frame.setBounds(100, 100, 450, 500);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.getContentPane().setLayout(null);

	        JLabel userLabel = new JLabel("Username");
	        userLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
	        userLabel.setBounds(50, 140, 120, 25);
	        frame.getContentPane().add(userLabel);
	        
	        
	        usernameField = new JTextField();
	        usernameField.setFont(new Font("Tahoma", Font.BOLD, 14));
	        usernameField.setBounds(50, 163, 342, 36);
	        frame.getContentPane().add(usernameField);

	        JLabel passwordLabel = new JLabel("Password");
	        passwordLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
	        passwordLabel.setBounds(50, 223, 120, 25);
	        frame.getContentPane().add(passwordLabel);

	        passwordField = new JPasswordField();
	        passwordField.setFont(new Font("Times New Roman", Font.BOLD, 14));
	        passwordField.setBounds(50, 245, 342, 36);
	        frame.getContentPane().add(passwordField);

	        JButton adminLoginButton = new JButton("Admin Login");
	        adminLoginButton.setBackground(new Color(192, 192, 192));
	        adminLoginButton.setFont(new Font("Times New Roman", Font.BOLD, 12));
	        adminLoginButton.setBounds(20, 372, 120, 30);
	        frame.getContentPane().add(adminLoginButton);

	        JButton etudiantLoginButton = new JButton("Etudiant Login");
	        etudiantLoginButton.setBackground(new Color(192, 192, 192));
	        etudiantLoginButton.setFont(new Font("Times New Roman", Font.BOLD, 12));
	        etudiantLoginButton.setBounds(286, 372, 140, 30);
	        frame.getContentPane().add(etudiantLoginButton);
	        
	        JLabel lblNewLabel = new JLabel("WELCOME");
	        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
	        lblNewLabel.setBounds(150, 56, 231, 46);
	        frame.getContentPane().add(lblNewLabel);

	        // 🔒 Bouton admin
	        adminLoginButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                String username = usernameField.getText();
	                String password = String.valueOf(passwordField.getPassword());

	                ConnectionDAO connectionDAO = new ConnectionDAO();
	                Connection conn = connectionDAO.getConnection();

	                if (conn == null) {
	                    JOptionPane.showMessageDialog(frame, "Erreur de connexion à la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }

	                AdministrateurDAO adminDao = new AdministrateurDAO(conn);
	                Admin admin = adminDao.getByCredentials(username, password);

	                if (admin != null) {
	                	JOptionPane.showMessageDialog(frame, "✅ Connexion admin réussie !\nBienvenue " + admin.getUsername());
	                    frame.dispose();
	                    // TODO: ouvrir l'interface AdminGUI
	                } else {
	                    JOptionPane.showMessageDialog(frame, "❌ Identifiants admin incorrects", "Erreur", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        });

	        //  Bouton étudiant
	        etudiantLoginButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                String username = usernameField.getText();
	                String password = String.valueOf(passwordField.getPassword());

	                ConnectionDAO connectionDAO = new ConnectionDAO();
	                Connection conn = connectionDAO.getConnection();

	                if (conn == null) {
	                    JOptionPane.showMessageDialog(frame, "Erreur de connexion à la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }

	                EtudiantDAO etuDao = new EtudiantDAO(conn);
	                Etudiants etu = etuDao.getByCredentials(username, password);

	                if (etu != null) {
	                	JOptionPane.showMessageDialog(frame,
	                		    " Connexion réussie !\nBienvenue " + etu.getPrenom() + " " + etu.getNom());
	                	EtudiantIHM etuIHM = new EtudiantIHM();
	                    frame.dispose();
	                    // TODO: ouvrir StudentGUI	                
	                    } 
	                    else {
	                    JOptionPane.showMessageDialog(frame, "❌ Identifiants étudiant incorrects", "Erreur", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        });
	    }
}
