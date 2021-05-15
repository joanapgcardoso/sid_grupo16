package sid_grupo16;


import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DealWithCollection extends Thread{

	private MongoCollection<Document> collection;
	private String collectionName;
	private MongoDatabase dbLocal;
	private ObjectId id;
	private int identificador;



	public DealWithCollection(MongoCollection<Document> collection, String collectionName, MongoDatabase dbLocal, int identificador) {
		this.collection=collection;	
		this.collectionName= collectionName;
		this.identificador=identificador;
		this.dbLocal = dbLocal;
	}

	public synchronized void test(Document document, MongoCollection localCollection) {
		localCollection.insertOne(document);
		dbLocal.notifyAll();
	}


	public void run() {
		try {
			MongoCollection<Document> localCollection = dbLocal.getCollection(collectionName);
			//Inicializar base dados local
			//Criar uma condição para garantir que não insere duplicado 
			FindIterable<Document> doc = collection.find().sort(new Document("_id", -1)).limit(1);
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

}
