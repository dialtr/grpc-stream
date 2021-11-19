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
    // The port of the server; defaults to 7171.
    @Parameter(names = {"--port", "-p"}, description  = "Port number", required = true)
    public int port = 7171;

    // The query string to pass in the request; defaults to "naive".
    // Pass "eventdriven" to demonstrate the event driven behavior.
    @Parameter(names = {"--query", "-q"}, description = "Query string", required = false)
    public String queryString = "naive";

    // Verbose indicates whether we should print the response to the console.
    @Parameter(names = {"--verbose", "-v"}, description = "Verbose", required = false)
    public boolean verbose = false;

    public static void main(String[] args) {
        final Application application = new Application();
        JCommander.newBuilder()
                .addObject(application)
                .build()
                .parse(args);
        application.start();
    }

    public void start() {
        String target = "localhost:" + port;
        ManagedChannel channel =
                ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        final ExampleGrpc.ExampleBlockingStub blockingStub =
                ExampleGrpc.newBlockingStub(channel);
        try {
            // Construct request to the server. The queryString (set with the
            // command-line argument --query, -q, determines whether the server
            // should use event driven handling or the naive approach.
            Request request = Request.newBuilder()
                    .setQuery(queryString)
                    .build();

            Iterator<Response> responseStream = blockingStub.fetch(request);
            while (responseStream.hasNext()) {
                final Response block  = responseStream.next();
                // Maybe print the data to the console; will slow down
                // network reads since we're using the blocking stop, which
                // will help demonstrate the problem, even when running on the
                // localhost.
                if (verbose) {
                    System.out.println(block.getData());
                }
            }
        } catch (StatusRuntimeException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}