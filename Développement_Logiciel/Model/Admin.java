package model;

/**
 * Classe représentant un administrateurauthor 
 * @author groupe7_4
 * @version 1.0
 */

public class Admin {

	    private String username;
	    private String password;

	    /**
	     * Constructeur
	     * @param username
	     * @param password
	     */
	    public Admin(String username, String password) {
	        this.username = username;
	        this.password = password;
	    }

	    // Getters
	    public String getUsername() {
	        return username;
	    }

	    public String getPassword() {
	        return password;
	    }

	    // Setters
	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    // Exemple de méthode pour vérifier un mot de passe (à améliorer avec hash plus tard)
	    public boolean verifierConnexion(String inputUsername, String inputPassword) {
	        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
	    }

	    // TODO: ajouter d'autres méthodes comme : validerEtudiant(), changerEtatSysteme() etc.
	}


