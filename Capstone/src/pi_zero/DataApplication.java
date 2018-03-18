package pi_zero;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class DataApplication 
{
	private TransferTimer timer;
	private final String FILENAME = "C:\\Users\\Sam\\Desktop\\capstone_workspace_package\\Capstone\\data\\Data.txt";
	private final int COLUMN_COUNT = 3;
	private final int ROW_COUNT = 5;
	
	private int[][] allData = new int[ROW_COUNT][COLUMN_COUNT];
	
	public DataApplication()
	{
		obtainTestData();
	}
	
	private void obtainTestData()
	{
		BufferedReader br = null;
		FileReader fr = null;
		
		try
		{
			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);
			
			String nextLine;
			
			for (int currentRow = 0; currentRow < ROW_COUNT; currentRow++)
			{
				if ((nextLine = br.readLine()) != null)
				{
					StringTokenizer tokenizer = new StringTokenizer(nextLine);
					
					for (int currentColumn = 0; currentColumn < COLUMN_COUNT; currentColumn++)
					{
						if (tokenizer.hasMoreTokens())
						{
							allData[currentRow][currentColumn] = Integer.parseInt(tokenizer.nextToken());
						}
					}
				}
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try
			{
				br.close();
				fr.close();
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		
		printData();
	}
	
	void printData()
	{
		for (int r = 0; r < ROW_COUNT; r++)
		{
			for (int c = 0; c < COLUMN_COUNT; c++)
			{
				System.out.print(allData[r][c] + "\t");
			}
			
			System.out.println();
		}
		
		timer = new TransferTimer(5);
		
		while (true)
		{
			
		}
	}
	
	public void start()
	{
		MemoryPersistence persistence = new MemoryPersistence();
		
		int qos = 2;
		
		String topic = "MQTT Examples";
		String content = "Message from MqttPublishSample";
		String broker = "tcp://iot.eclipse.org:1883";
		String clientId = "JavaSample";

		try 
        {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: "+content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            System.out.println("Message published");
            sampleClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        } 
        catch(MqttException me)
        {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
	}
}
