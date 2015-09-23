package org.drl.lutz.sosmehringplatzapp.main.utils;

import android.util.Log;

/*
 * Taken from http://stackoverflow.com/questions/4075180/application-idle-time and modified
 */
public class IdleTimer extends Thread {


    public interface IdleTimerEventListener {
        public void onIdleTooLong();
    }

    private IdleTimerEventListener listener = null;

    private static final String TAG= IdleTimer.class.getName();
    private long lastUsed;
    private long period;
    private boolean stop,paused;



    public IdleTimer(long period,IdleTimerEventListener listener)
    {
        this.period = period;
        this.listener = listener;
        this.paused = false;
        this.stop = false;
    }

    public void run()
    {
        long idle=0;

        this.reset();

        do {
            try {
                Thread.sleep(2000); //check every 2 seconds
            } catch (InterruptedException e) {
                stop = true;
            }

            idle=System.currentTimeMillis()-lastUsed;

            if(!paused && idle > period) {
                if (listener != null)
                    listener.onIdleTooLong();
                stop = true;
            }
        }
        while(!stop);
    }

    public synchronized void reset()
    {
        lastUsed=System.currentTimeMillis();
    }

    public synchronized void stopTimer()
    {
        this.interrupt();
    }

    public synchronized void pause()
    {
        this.paused = true;
    }

    public synchronized void unpause()
    {
        this.reset();
        this.paused = false;
    }

}