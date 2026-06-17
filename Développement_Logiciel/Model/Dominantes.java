package model;
import java.util.ArrayList;

/**
 * Classe de la liste des dominantes
 * @author groupe7_4
 * @version 1.0
 */

public class Dominantes {

	private String nom;
    private int placesDispo;
    private int placesPrises;
    private ArrayList<Etudiants> listeEtudiants;

/**
 * Constructeur
 * @param nom
 * @param placesDispo
 * @param placesPrises
 */
    public Dominantes(String nom, int placesDispo, int placesPrises) {
        this.nom = nom;
        this.placesDispo = placesDispo;
        this.placesPrises = placesPrises;
        this.listeEtudiants = new ArrayList<>();
    }

//récupération et modifiication du nom de la dominante
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

//récupération et modifiication du nombre de places disponible dans la dominante 
    public int getPlacesDispo() {
        return placesDispo;
    }

    public void setPlacesDispo(int placesDispo) {
        this.placesDispo = placesDispo;
    }

//récupération et modifiication du nombre de places disponible dans la dominante 
    public int getPlacesPrises() {
        return placesPrises;
    }

    public void setPlacesPrises(int placesPrises) {
        this.placesPrises = placesPrises;
    }

//récupération de la liste des étudiants dans la dominante 
    public ArrayList<Etudiants> getListeEtudiants() {
        return listeEtudiants;
    }

    
// Vérifie s'il reste des places disponibles
    
    public boolean aDesPlacesDisponibles() {
        return placesPrises < placesDispo;
    }

    
// Ajoute un étudiant à cette dominante s'il reste de la place
    
    public boolean addEtudiant(Etudiants e) {
        if (!aDesPlacesDisponibles()) return false;

        listeEtudiants.add(e);
        placesPrises++;
        return true;
    }

    @Override
    public String toString() {
        return nom + " (" + placesPrises + "/" + placesDispo + ")";
    }
}
