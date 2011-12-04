package com.codelette.playground.future;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * A consumer which uses the most basic Future functionality to display the results from the producer.
 * @author Einar Jónsson
 */
public class FutureConsumer {

    public void consume(int numberOfConsumerItems) {

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfConsumerItems);

        List<Future<String>> results = new LinkedList<Future<String>>();
        for(int i = 0; i < numberOfConsumerItems; i++) {
            Callable<String> worker = new Producer();
            Future<String> result = executorService.submit(worker);
            results.add(result);
        }


        for( Future<String> result : results) {
            try {
                System.out.println(result.get());
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (ExecutionException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        executorService.shutdown(); //Otherwise the process will not terminate normally
    }

    public static void main(String[] args) {
        FutureConsumer futureConsumer = new FutureConsumer();
        futureConsumer.consume(5);

        System.out.println("The end");
    }
}
