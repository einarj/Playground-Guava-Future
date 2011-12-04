package com.codelette.playground.future;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;

/**
 * A callable that produces the current time formatted as HH:mm:ss.
 * @author Einar Jónsson
 */
public class Producer implements Callable<String> {

    // TODO: Add to github, but make sure to override the global username/email settings

    private SecureRandom randomGenerator = new SecureRandom();
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    
    public String getTime() {
        return timeFormat.format(new Date());
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    public String call() throws Exception {
        
        long sleepTime = Math.abs(randomGenerator.nextLong()) % 20000; // Max 20 seconds
        Thread.sleep(sleepTime);
        
        return getTime();
    }
}
