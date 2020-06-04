package fr.isedia.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class SimpleAsyncTask {


    public CompletableFuture<String> printAsync(String word, int sleepInMillis){

        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(()->{
           Thread.sleep(sleepInMillis);
           completableFuture.complete(word);
            return null;
        });

        return completableFuture;
    }

}
