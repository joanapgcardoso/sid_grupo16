package sid_grupo16;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class MainGrupo16mongo extends Thread{

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
		try {
			MongoCollection<Document> localCollection = dbLocal.getCollection("sensort1");
			//Inicializar base dados local
			//Criar uma condição para garantir que não insere duplicado 
			FindIterable<Document> doc = localCollection.find().sort(new Document("_id", -1)).limit(1);
			while(doc.cursor().hasNext()) {
				for(Document document : doc) {
					ObjectId id = document.getObjectId("_id");
					sleep(3000);
					System.out.println(document.toJson());
					localCollection.insertOne(document);
					//test(document, localCollection);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
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
