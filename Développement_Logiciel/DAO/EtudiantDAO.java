package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Etudiants;
public class EtudiantDAO {

	 private Connection connection;

	    public EtudiantDAO(Connection connection) {
	        this.connection = connection;
	    }

	    /**
	     * Vérifie les identifiants et retourne un objet Etudiant avec rang et idPh
	     */
	    public Etudiants getByCredentials(String username, String password) {
	        try {
	            System.out.println("Tentative de connexion étudiant : " + username + " / " + password);

	            String sql = "SELECT * FROM ETUDIANT WHERE UPPER(USERNAME) = ? AND PASSWORD = ?";
	            PreparedStatement stmt = connection.prepareStatement(sql);
	            stmt.setString(1, username.toUpperCase());
	            stmt.setString(2, password);

	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                String nom = rs.getString("NOM");
	                String prenom = rs.getString("PRENOM");
	                String filiere = rs.getString("FILIERE");
	                int promo = rs.getInt("PROMO");
	                String dateNaissance = rs.getString("DATENAISSANCE");
	                
	                return new Etudiants(nom, prenom, filiere, promo, dateNaissance, username, password);
	                
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return null; // Aucun étudiant trouvé
	    }
}
