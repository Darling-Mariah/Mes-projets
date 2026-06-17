package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe représentant un étudiant FISA
 */

public class FISA extends Etudiants {

	  private int rang;
	    private ArrayList<Dominantes> listeDominantes;

	    public FISA(String nom, String prenom, String filiere, int promo, String dateNaissance,
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

	    public ArrayList<Dominantes> getListeDominantes() {
	        return listeDominantes;
	    }

	    /**
	     * Ajoute une dominante si disponible et si < 5 choisies
	     */
	    public boolean ajouterDominante(Dominantes d) {
	        if (listeDominantes.size() >= 5) return false;
	        if (!d.aDesPlacesDisponibles()) return false;

	        listeDominantes.add(d);
	        d.addEtudiant(this); // Mettre à jour la dominante
	        return true;
	    }

	    /**
	     * Trie les dominantes par ordre alphabétique（可以替换为别的排序标准）
	     */
	    public ArrayList<Dominantes> classerLesDominantes() {
	        Collections.sort(listeDominantes, (d1, d2) -> d1.getNom().compareToIgnoreCase(d2.getNom()));
	        return listeDominantes;
	    }
}
