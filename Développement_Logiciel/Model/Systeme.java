package model;

public class Systeme {
	
	 private int id;
	    private String etape;

	    public Systeme(int id, String etape) {
	        this.id = id;
	        this.etape = etape;
	    }

	    public int getId() {
	        return id;
	    }

	    public String getEtape() {
	        return etape;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public void setEtape(String etape) {
	        this.etape = etape;
	    }

	    public boolean isOuvertPourFISA() {
	        return "Ouvert aux FISA".equalsIgnoreCase(etape);
	    }

	    public boolean isOuvertPourFISE() {
	        return "Ouvert aux FISE".equalsIgnoreCase(etape);
	    }

	    public boolean isValidationFISA() {
	        return "FISA Valider".equalsIgnoreCase(etape);
	    }

	    public boolean isValidationFISE() {
	        return "FISE Valider".equalsIgnoreCase(etape);
	    }

	    public boolean estArchive() {
	        return "Archiver".equalsIgnoreCase(etape);
	    }

}
