package pi_zero;

public class Clock 
{
	private long nanoStartTime = 0;
	private long msStartTime = 0;
	
	public Clock()
	{
		nanoStartTime = System.nanoTime();
		msStartTime = System.currentTimeMillis();
	}
	
	public long getElapsedNanoTime()
	{
		return (System.nanoTime() - nanoStartTime);
	}
	
	public long getElapsedMiliTime()
	{
		return (System.currentTimeMillis() - msStartTime);
	}
	
	public void restart()
	{
		nanoStartTime = System.nanoTime();
		msStartTime = System.currentTimeMillis();
	}
}
