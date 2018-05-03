package pi_zero;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.net.ssl.SSLSocketFactory;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class DataApplication 
{
	private Clock clock = null;
	private final String FILENAME = "./data/data.txt";
	private int sleepTime = 0;
	private final int COLUMN_COUNT = 1;
	private final int ROW_COUNT = 1;
	private String certificateSigningRequestPath = "./certs/client.pem";
	private String clientKeyFilePath = "./certs/client.key";
	private String clientCertificateFilePath = "./Kura/ca.crt";
	private String password = "password";
	
	private int[][] allData = new int[ROW_COUNT][COLUMN_COUNT];
	
	public DataApplication(int time)
	{
		clock = new Clock();
		sleepTime = time;
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
	}
	
	void start()
	{
		String topic = "MQTT Examples";
		String content = "Test Message";
		//String broker = "ssl://192.168.0.111:1883";
		//String broker = "tcp://192.168.0.107:1883";
		String broker = "ssl://192.168.0.107:8883";
		String clientId = "JavaSample";

		MemoryPersistence persistence = new MemoryPersistence();
		SSLSocketFactory sslFactory = null;
		
		try 
		{
			sslFactory = SSLFactory.getSocketFactory(certificateSigningRequestPath, clientCertificateFilePath, clientKeyFilePath, password);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.exit(0);
		}
		
		int qos = 2;

		clock.restart();
		
		
		for (int currentRow = 0; currentRow < ROW_COUNT; currentRow++) 
		{
			for (int currentCol = 0; currentCol < COLUMN_COUNT; currentCol++) 
			{
				content = allData[currentRow][currentCol] + "";
				
				try 
				{
					MqttClient client = new MqttClient(broker, clientId, persistence);
					MqttConnectOptions connectionOptions = new MqttConnectOptions();
					MqttMessage message = new MqttMessage(content.getBytes());

					connectionOptions.setCleanSession(true);
					connectionOptions.setUserName("username");
					connectionOptions.setPassword(password.toCharArray());
					connectionOptions.setConnectionTimeout(60);
					connectionOptions.setKeepAliveInterval(60);
					connectionOptions.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
					connectionOptions.setSocketFactory(sslFactory);

					System.out.println("Connecting to broker: " + broker);
					client.connect(connectionOptions);
					System.out.println("Connected");

					System.out.println("Publishing message: " + content);
					message.setQos(qos);
					client.publish(topic, message);
					System.out.println("Message published");

					if (currentRow == ROW_COUNT - 1 && currentCol == COLUMN_COUNT - 1)
					{
						client.disconnect();
						System.out.println("Disconnected");
						System.exit(0);
					}
					
					System.out.println("Nano:\t" + clock.getElapsedNanoTime());
					System.out.println("milli:\t" + clock.getElapsedMiliTime());
				} 
				catch (MqttException me) 
				{
					System.out.println("reason " + me.getReasonCode());
					System.out.println("msg " + me.getMessage());
					System.out.println("loc " + me.getLocalizedMessage());
					System.out.println("cause " + me.getCause());
					System.out.println("excep " + me);
					me.printStackTrace();
				}

				try
				{
					Thread.sleep(sleepTime * 1000);
				} 
				catch(InterruptedException ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}
}
