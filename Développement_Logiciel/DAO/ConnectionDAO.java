package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Classe permettant l'acces a la base de donnees
 * 
 * @author groupe7_4
 * @version 1.0
 * */
public class ConnectionDAO {
	
	
		/**
		 * Parametres de connexion a la base de donnees oracle
		 * URL, LOGIN et PASS sont des constantes
		 */
		
	   // machine personelle donc....
		final static String URL   = "jdbc:oracle:thin:@oracle.esigelec.fr:1521:orcl";
		
		final static String LOGIN = "C##BDD7_4"; //nom de conexion
		final static String PASS  = "BDD74";  //mot de passe de conexion
		
		/**
		 * Constructor
		 * 
		 */
		
		public ConnectionDAO() {
			// chargement du pilote de bases de donnees
			try {
				Class.forName("oracle.jdbc.OracleDriver");
			} catch (ClassNotFoundException e) {
				System.err.println("Impossible de charger le pilote de BDD, ne pas oublier d'importer le fichier .jar dans le projet");
			}
		}
		 public Connection getConnection() {
		        try {
		            return DriverManager.getConnection(URL, LOGIN, PASS);
		        } catch (SQLException e) {
		            e.printStackTrace();
		            return null;
		        }
		    }
	}


