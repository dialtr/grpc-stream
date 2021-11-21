package org.example.server;

import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
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

    private static Logger logger = LogManager.getLogger(Service.class);

    // Make a random string containing 'charCount' uppercase letters.
    private static String makeRandomString(int charCount) {
        Random random = new Random();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < charCount; ++i) {
            sb.append(Character.toChars(65 + random.nextInt(26)));
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
    // the client without waiting. This causes data to buffer on the server
    // side and memory usage increases to unacceptable levels as a result.
    public void fetchNaive(Request request, StreamObserver<Response> rso) {
        logger.info("Start: Handling naive request");
        // Allocate a block of random data. We'll send it over and over again
        // for the purposes of this demo, as to avoid allocating lots of memory
        // and interfering with other observations we want to make concerning
        // heap memory usage.
        final String block = makeRandomString(BLOCK_SIZE);

        for (int i = 0; i < BLOCK_COUNT; ++i) {
            Response response = Response.newBuilder().setData(block).build();
            rso.onNext(response);
        }
        rso.onCompleted();
        logger.info("End: handling naive request");
    }

    // When in event driven mode, we need something to track the state of the
    // response. This becomes an argument to the "onReady" event handler.
    private static class ResponseContext {
        // Track number of blocks sent.
        private int blocksSent = 0;

        // Allocate a block of random data. We'll send it over and over again
        // for the purposes of this demo, as to avoid allocating lots of memory
        // and interfering with other observations we want to make concerning
        // heap memory usage.
        final private String block = makeRandomString(BLOCK_SIZE);

        // Helper that returns 'true' if we have more data to send. In this
        // simple example, we consider the call complete when we've sent
        // 'BLOCK_COUNT' blocks of data. In a real server, this might be
        // implemented in terms of checking a database connection, a file
        // for EOF, or perhaps a call to a downstream service.
        public boolean hasNext() {
            return (blocksSent < BLOCK_COUNT);
        }

        // Return the next block, updating a counter that tracks how many
        // we have sent.
        public String nextBlock() {
            // If we have no more data to send, return null.
            if (!hasNext()) {
                return null;
            }
            ++blocksSent;
            return block;
        }
    }

    // Event-driven version of fetch.
    // In this version, we set up an event handler so that when the framework
    // is ready for us to send data to the client, we send it. This minimizes
    // server-side buffering.
    public void fetchEventDriven(Request request, StreamObserver<Response> rso) {
        logger.info("Start: Handling event driven request");
        ServerCallStreamObserver<Response> responseStream =
                (ServerCallStreamObserver<Response>) rso;
        // Each request to the server results in a stream being sent back.
        // Since we plan to register an event handler to send data when
        // the client is ready, we need an object that we can pass as an
        // argument to the callback that remembers the state of where we
        // were in our response. In a real system, this might hold a reference
        // to a SQL DB connection, a file object, etc.
        ResponseContext context = new ResponseContext();

        // Finally, just arrange to handle the onReady event. This event
        // "fires" when the framework is ready to send data to a client.
        // Note that we use the lambda syntax for the event handler in
        // this example. We could have also delegated to a static function.
        responseStream.setOnReadyHandler(()-> {
            // First, we check to see if we have any data to send. We
            // do this because we have observed that the framework may
            // call the ready event handler even after the connection
            // has been completed.
            if (!context.hasNext()) {
                // Return immediately, as we must have already completed.
                return;
            }

            // Loop while we have data to send from the "context". If the
            // stream is not ready, just return. If it *is* ready, then we
            // prepare a response and send the next block. If we fall out
            // of the loop, it means we have no more data to send and can
            // then complete the stream connection.
            while (context.hasNext()) {
                if (!responseStream.isReady()) {
                    return;
                }
                Response response = Response.newBuilder()
                        .setData(context.nextBlock())
                        .build();
                responseStream.onNext(response);
            }
            logger.info("End: Handling event driven request");
            responseStream.onCompleted();
        });
    }
}
