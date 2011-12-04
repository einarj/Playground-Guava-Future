package com.codelette.playground.future;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;


import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * A consumer which uses a listener to react when a worker completes.
 * @author Einar Jónsson
 */
public class ListenerConsumer {

    public void consume(int numberOfConsumerItems) {

        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(numberOfConsumerItems));

        List<Future<String>> results = new LinkedList<Future<String>>();
        for (int i = 0; i < numberOfConsumerItems; i++) {
            Callable<String> worker = new Producer();
            ListenableFuture<String> result = executorService.submit(worker);
            result.addListener(new Runnable() {

                public void run() {
                    System.out.println("Yay! We're done.");
                }
            }, executorService);

            results.add(result);
        }


        for (Future<String> result : results) {
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
        ListenerConsumer consumer = new ListenerConsumer();
        consumer.consume(5);

        System.out.println("The end");
    }

}
