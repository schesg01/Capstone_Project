package pi_zero;

import java.util.Timer;
import java.util.TimerTask;

public class TransferTimer extends Timer
{
	private Timer timer;
	private int timerLengthInSeconds = 0;
	
	public TransferTimer(int timerLength)
	{
		timer = new Timer();
		timerLengthInSeconds = timerLength;
		timer.schedule(new TransferTask(), timerLengthInSeconds * 1000);
	}
	
	class TransferTask extends TimerTask
	{
		@Override
		public void run() 
		{
			System.out.println("Timer worked.");
			timer.schedule(new TransferTask(), timerLengthInSeconds * 1000);
		}
	}
}
