package sid_grupo16;

import org.bson.types.ObjectId;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainGrupo16sql {

	private static String topic;

	public MainGrupo16sql() throws InterruptedException {

	}

	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
	}


	public void messageArrived(String topic, MqttMessage message) throws Exception {

		System.out.println("Message received:"+ new String(message.getPayload()) );
		String medicaoString = message.toString();
		String[] tokens = medicaoString.split("\""); 
		String id = tokens[5];
		String zona = tokens[9];
		String sensor = tokens[13];
		String med = tokens[21];
		ObjectId objId = new ObjectId(id);
		long time = objId.getTime();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(time);		

	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub

	}
}
