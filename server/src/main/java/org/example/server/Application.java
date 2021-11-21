package org.example.server;

import java.util.concurrent.TimeUnit;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application {
    private static int SHUTDOWN_TIMEOUT_TIME_SECONDS = 30;

    private static final Logger logger = LogManager.getLogger(Application.class);

    @Parameter(names = {"--port", "-p"}, description = "Port number", required = true)
    public int port = 7171;

    private Server server;

    private Application() {
    }

    public static void main(String[] args) throws Exception {
        final Application application = new Application();
        JCommander.newBuilder()
                .addObject(application)
                .build()
                .parse(args);
        application.start();
    }

    void start() throws Exception {
        server = ServerBuilder.forPort(port)
                .addService(new Service())
                .build()
                .start();

        logger.info("Server started, listening on port " + port);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                Application.logger.info("Shutting down gRPC server since JVM is shutting down.");
                try {
                    Application.this.stop();
                } catch (InterruptedException e) {
                    Application.logger.info(e.getStackTrace().toString());
                }
                Application.logger.info("Server shutdown complete.");
            }
        });
        server.awaitTermination();
    }

    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(
                    SHUTDOWN_TIMEOUT_TIME_SECONDS,
                    TimeUnit.SECONDS
            );
        }
    }
}