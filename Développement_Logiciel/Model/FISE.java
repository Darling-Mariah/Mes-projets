package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe représentant un étudiant FISE
 */

public class FISE extends Etudiants {

	  private int rang;
	    private ArrayList<Dominantes> listeDominantes;

	    public FISE(String nom, String prenom, String filiere, int promo, String dateNaissance,
	                String username, String password, int rang) {
	        super(nom, prenom, filiere, promo, dateNaissance, username, password);
	        this.rang = rang;
	        this.listeDominantes = new ArrayList<>();
	    }

	    public int getRang() {
	        return rang;
	    }

	    public void setRang(int rang) {
	        this.rang = rang;
	    }

	    /**
	     * Retourne la liste des dominantes sélectionnées
	     */
	    public ArrayList<Dominantes> getListeDominantes() {
	        return listeDominantes;
	    }

	    /**
	     * Ajoute une dominante à la liste si elle est disponible et si l'étudiant n'a pas déjà 5 dominantes
	     */
	    public boolean ajouterDominante(Dominantes d) {
	        if (listeDominantes.size() >= 5) return false;
	        if (!d.aDesPlacesDisponibles()) return false;

	        listeDominantes.add(d);
	        d.addEtudiant(this); // Met à jour la dominante aussi
	        return true;
	    }

	    /**
	     * Trie les dominantes par nom（可以改成别的规则）
	     */
	    public ArrayList<Dominantes> classerLesDominantes() {
	        Collections.sort(listeDominantes, (d1, d2) -> d1.getNom().compareToIgnoreCase(d2.getNom()));
	        return listeDominantes;
	    }
}
