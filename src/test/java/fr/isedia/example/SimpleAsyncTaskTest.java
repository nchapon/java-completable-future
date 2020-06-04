package fr.isedia.example;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SimpleAsyncTaskTest {

    @Test
    void shouldPrintHello() throws ExecutionException, InterruptedException {
        Future<String> future = new SimpleAsyncTask().printAsync("Hello", 500);
        assertEquals("Hello",future.get());
    }


    @Test
    void runInParallel() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future1 = new SimpleAsyncTask().printAsync("Hello",1000);
        CompletableFuture<String> future2 = new SimpleAsyncTask().printAsync("Beautiful",1000);
        CompletableFuture<String> future3 = new SimpleAsyncTask().printAsync("World",1000);

        CompletableFuture<Void> future = CompletableFuture.allOf(future1, future2, future3);
        future.get();

        String combined = Stream.of(future1, future2, future3)
                .map(CompletableFuture::join)
                .collect(Collectors.joining(" "));

        assertEquals("Hello Beautiful World",combined);


    }
}