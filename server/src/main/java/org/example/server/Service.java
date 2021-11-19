package org.example.server;

import io.grpc.stub.StreamObserver;
import org.example.proto.ExampleGrpc;
import org.example.proto.Request;
import org.example.proto.Response;

import java.util.Random;

public class Service extends ExampleGrpc.ExampleImplBase {
    // The size of each block sent as we stream. It's large because we
    // want to demonstrate that sending data faster than the client is
    // able to consume it will cause data to buffer in the server and
    // in turn, cause memory problems.
    private static int BLOCK_SIZE = 1024 * 1024;

    // Number of blocks to send. This is also large on purpose so that
    // we can demonstrate how sending too much can cause buffering on
    // the server side.
    private static int BLOCK_COUNT = 8 * 1024;

    // Make a random string containing 'charCount' uppercase letters.
    private String makeRandomString(int charCount) {
        Random random = new Random();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < charCount; ++i) {
            sb.append(random.nextInt(26) + 'A');
        }
        return sb.toString();
    }

    // The implementation of 'fetch' delegates to one of two handlers so
    // that we can measure the behavior of each approach: a naive approach
    // that pushes large amounts of data to the caller in a loop, and an
    // event-driven implementation that uses the GRPC framework's
    // setOnReadyHandler function to configure a "ready event" handler.
    //
    // In this example, the request to the server contains a single string
    // named 'query'. If the query contains the string 'eventdriven', then
    // we delegate to the event-driven version of 'fetch'. If the query
    // string is empty or contains anything else, we delegate to the naive
    // version.
    @Override
    public void fetch(Request request, StreamObserver<Response> rso) {
        if (request.getQuery().compareToIgnoreCase("eventdriven") == 0) {
            fetchEventDriven(request, rso);
        } else {
            fetchNaive(request, rso);
        }
    }

    // The naive implementation just loops, sending N blocks of size K to
    // the client without waiting.
    public void fetchNaive(Request request, StreamObserver<Response> rso) {
        final String block = makeRandomString(BLOCK_SIZE);
        for (int i = 0; i < BLOCK_COUNT; ++i) {
            Response response = Response.newBuilder().setData(block).build();
            rso.onNext(response);
        }
        rso.onCompleted();
    }

    public void fetchEventDriven(Request request, StreamObserver<Response> rso) {

    }
}
