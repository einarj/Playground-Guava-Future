package com.codelette.playground.future;

import com.google.common.util.concurrent.*;

import java.util.concurrent.*;

/**
 * A consumer which uses callbacks to display the results from the producer.
 * @author Einar Jónsson
 */
public class CallbackConsumer {

    public void consume(int numberOfConsumerItems) {

        // Futures.addCallback requires ListenableFuture
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(numberOfConsumerItems));

        for (int i = 0; i < numberOfConsumerItems; i++) {
            Callable<String> worker = new Producer();
            ListenableFuture<String> result = executorService.submit(worker);

            FutureCallbackImpl callback = new FutureCallbackImpl();
            Futures.addCallback(result, callback);
        }

        // ...and then we do nothing, let the callbacks take care of things

        executorService.shutdown(); //Otherwise the process will not terminate normally
    }

    public static void main(String[] args) {
        CallbackConsumer consumer = new CallbackConsumer();
        consumer.consume(5);
    }


    private class FutureCallbackImpl implements FutureCallback<String> {


        public void onFailure(Throwable throwable) {
            System.out.println("You sir, have failed miserably!");
            throwable.printStackTrace();
        }

        public void onSuccess(String time) {
            System.out.println("The time is " + time);
        }
    }
}
