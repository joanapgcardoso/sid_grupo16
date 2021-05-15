package sid_grupo16;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class MainGrupo16mongo {

	// MainPrograma1 + DealWithCollection

	private MongoDatabase dbLocal;
	private MongoDatabase dbCloud;
	private MongoClient clientCloud;
	private MongoClient clientLocal;


	public void mongoConnectionCloud() {
		try {	
			clientCloud = new MongoClient(new MongoClientURI("mongodb://aluno:aluno@194.210.86.10:27017"));
			dbCloud =  (MongoDatabase) clientCloud.getDatabase("sid2021");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mongoConnectionLocal() {
		try {
			clientLocal = new MongoClient(new MongoClientURI("mongodb://localhost:27019,localhost:25019,localhost:23019/?replicaSet=replicademo"));
			clientLocal = new MongoClient("localhost",27017);
			dbLocal = (MongoDatabase) clientLocal.getDatabase("BaseDados");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public void getCollections() throws InterruptedException {
		int identificador =0;
		for ( String collectionName : ((MongoDatabase) dbCloud).listCollectionNames()) { 
			DealWithCollection dwc = new DealWithCollection((MongoCollection<Document>) dbCloud.getCollection(collectionName),collectionName,dbLocal,identificador);
			dwc.start();
			System.out.println("Coleção "+collectionName+" iniciada com sucesso!"+"+"+identificador);
			identificador++;	
		}
	}



	public static void main(String [] args) throws InterruptedException  {

		MainGrupo16mongo main = new MainGrupo16mongo();
		main.mongoConnectionLocal();
		main.mongoConnectionCloud();
		main.getCollections();
	}

	//MainPrograma3
}
