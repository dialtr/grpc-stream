package org.example.client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.example.proto.ExampleGrpc;
import org.example.proto.Request;
import org.example.proto.Response;

import java.util.Iterator;

public class Application {
    // The hostname of the server; defaults to localhost.
    @Parameter(names = {"--hostname", "-n"}, description = "Host name", required = false)
    public String host = "localhost";

    // The port of the server; defaults to 7171.
    @Parameter(names = {"--port", "-p"}, description = "Port number", required = true)
    public int port = 7171;

    // The query string to pass in the request; defaults to "naive".
    // Pass "eventdriven" to demonstrate the event driven behavior.
    @Parameter(names = {"--query", "-q"}, description = "Query string", required = false)
    public String queryString = "naive";

    // Number of requests to send (1 by default.)
    @Parameter(names = {"--count", "-c"}, description = "Request count", required = false)
    public int count = 1;

    // Verbose indicates whether we should print the response to the console.
    @Parameter(names = {"--verbose", "-v"}, description = "Verbose", required = false)
    public boolean verbose = false;

    @Parameter(names = {"--threads", "-t"}, description = "Client thread count", required = false)
    public int threads = 1;

    public static void main(String[] args) {
        final Application application = new Application();

        JCommander.newBuilder()
                .addObject(application)
                .build()
                .parse(args);

        application.start();
    }

    private void makeRequest(ExampleGrpc.ExampleBlockingStub stub) {
        // Construct request to the server. The queryString (set with the
        // command-line argument --query, -q, determines whether the server
        // should use event driven handling or the naive approach.
        final Request request = Request.newBuilder()
                .setQuery(queryString)
                .build();

        Iterator<Response> responseStream = stub.fetch(request);
        while (responseStream.hasNext()) {
            final Response block = responseStream.next();
            // Maybe print the data to the console; will slow down
            // network reads since we're using the blocking stop, which
            // will help demonstrate the problem, even when running on the
            // localhost.
            if (verbose) {
                System.out.println(block.getData());
            }
        }
    }

    public void start() {
        final String target = host + ":" + port;
        final ManagedChannel channel =
                ManagedChannelBuilder
                        .forTarget(target)
                        .usePlaintext()
                        .build();

        final ExampleGrpc.ExampleBlockingStub blockingStub =
                ExampleGrpc.newBlockingStub(channel);

        for (int i = 0; i < threads; ++i) {
            final int thread_id = i;
            new Thread(() -> {
                try {
                    for (int j = 0; j < count; ++j) {
                        System.out.println("Thread " + thread_id + ", Starting request " + j);
                        makeRequest(blockingStub);
                        System.out.println("Thread " + thread_id + ", Completed request " + j);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    System.out.println("Request failed!");
                }
            }).start();
        }
    }
}