package pi_zero;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class TestDataGenerator 
{
	private final String FILE_NAME = "./data/generated_data.txt";
	private final int ROW_COUNT = 10000;
	private final int COLUMN_COUNT = 3;
	private Random random;
	
	public TestDataGenerator()
	{
		random = new Random();
	}
	
	public void createData()
	{
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;

		try 
		{
			fileWriter = new FileWriter(FILE_NAME);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			System.out.println("Generating test data of " + COLUMN_COUNT * ROW_COUNT + " integers.");
			
			for (int row = 0; row < ROW_COUNT; row++)
			{
				for (int col = 0; col < COLUMN_COUNT; col++)
				{
					int nextValue = random.nextInt(100) + 1;
					// Write a new random integer between 1 and 100 to the test data file
					bufferedWriter.write(nextValue + "\t");
				}
				
				bufferedWriter.write("\n");
			}
			
			bufferedWriter.close();
			fileWriter.close();

			System.out.println("Data generated successfully.");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();

		}
	}
}
