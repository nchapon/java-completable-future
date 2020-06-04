package fr.isedia.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CompletableFutureExamplesTest {


    @Test
    void supplyAsync() throws Exception{
    //   Simple Computation
        CompletableFuture<String> future = new CompletableFuture<>().supplyAsync(() -> "Hello");
        assertEquals("Hello", future.get());
    }

    @Test
    void processingResults() throws Exception{

        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future = completableFuture.thenApply(s -> s + " World!");

        assertEquals("Hello World!", future.get());
    }


    @Test
    void combiningFutures() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture =
                CompletableFuture.supplyAsync(() -> "Hello")
                        .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World!"));

        assertEquals("Hello World!", completableFuture.get());

    }


    @Test
    @DisplayName("Running multiple methods in parallel")
    void runningMultipleMethodsInParallel() throws ExecutionException, InterruptedException {

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Beautiful");
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "World");

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1, future2, future3);
        combinedFuture.get();

        assertAll("Futures",
                ()-> assertTrue(future1.isDone()),
                ()-> assertTrue(future2.isDone()),
                ()-> assertTrue(future3.isDone()));

        String combined = Stream.of(future1, future2, future3)
                .map(CompletableFuture::join)
                .collect(Collectors.joining(" "));

        assertEquals("Hello Beautiful World",combined);


    }
}
