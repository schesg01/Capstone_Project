package pi_zero;

public class Main 
{
	/**
	 * @param args
	 * args[0] is the wait time between data transfers - value should be integer
	 * args[1] determines if new test data should be generated - value should be 1 for true, 0 for false.
	 */
	public static void main(String[] args) 
	{
		// By default, sleepTime is set to 3 seconds
		int sleepTime = 3; 
		
		if (args[0] != null)
		{
			sleepTime = Integer.parseInt(args[0]);
		}

		// By default, do not generate test data
		if (args[1] != null && Integer.parseInt(args[1]) == 1)
		{
			generateTestData();
		}
		
		DataApplication application = new DataApplication(sleepTime);
		application.start();
	}
	
	private static void generateTestData()
	{
		TestDataGenerator testDataGen = new TestDataGenerator();
		testDataGen.createData();
	}
}
