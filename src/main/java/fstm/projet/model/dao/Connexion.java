package fstm.projet.model.dao;
import java.net.UnknownHostException;

import com.mongodb.*;


public class Connexion {

	public static DB getConnex() {
		DB baseDb=null;
		MongoClient client=new MongoClient("localhost",27017);

		baseDb=client.getDB("DBDiagnostic");

		return baseDb;
		
	}
}
