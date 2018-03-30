package pi_zero;

public class Main 
{
	public static void main(String[] args) 
	{
		if (Integer.parseInt(args[1]) == 1)
		{
			generateTestData();
		}

		int sleepTime = Integer.parseInt(args[0]);
		
		DataApplication application = new DataApplication(sleepTime);
		application.start();
	}
	
	private static void generateTestData()
	{
		TestDataGenerator testDataGen = new TestDataGenerator();
		testDataGen.createData();
	}
}
