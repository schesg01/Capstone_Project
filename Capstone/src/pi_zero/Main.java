package pi_zero;

public class Main 
{
	public static void main(String[] args) 
	{
		int sleepTime = Integer.parseInt(args[0]);
		DataApplication application = new DataApplication(sleepTime);
		application.start();
	}
}
