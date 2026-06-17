package dao;

import model.Admin;
import java.sql.*;

/**
 * Classe permettant l'acces aux donnees de la table administrateur de la BDD
 * @author groupe7_4
 * @version 1.0
 */



public class AdministrateurDAO extends ConnectionDAO {
	
	/**
	 * Construteur 
	 */
	private Connection con;
	
    public AdministrateurDAO(Connection con) {
    	super();
    	this.con=con;
    	
    }

    /**
     * Vérifie si un administrateur avec ce login existe
     */
    public Admin getByCredentials(String username, String password) {
    	   
    	   Connection connection=null;
    	   PreparedStatement stmt = null;
    	   ResultSet rs = null;
    	   Admin returnValue = null;
    	   
        try {
            // Debug info
            System.out.println("Tentative de connexion admin : " + username + " / " + password);
            connection =  DriverManager.getConnection(URL, LOGIN, PASS);
            String sql = "SELECT * FROM ADMINISTRATEUR WHERE UPPER(USERNAME) = ? AND PASSWORD = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, username.toUpperCase()); // Ignorer la casse pour le nom d'utilisateur
            stmt.setString(2, password);
            //Execution de la requête
             rs = stmt.executeQuery();
            if (rs.next()) {
                // Login réussi
            	returnValue = new Admin(rs.getString("USERNAME"), rs.getString("PASSWORD"));
            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }finally {
        	//fermeture du ResultSet,  du PreparedStatement et de la Connexion
        	try {
        		if (rs != null) 
					rs.close();
        	}catch (Exception ignore) {}
        	try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception ignore) {
			}
        }

        return returnValue; // Aucun administrateur trouvé
    }

}
