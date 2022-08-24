package org.example.server;

import java.util.concurrent.TimeUnit;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import io.grpc.netty.NettyServerBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application {
    private static int SHUTDOWN_TIMEOUT_TIME_SECONDS = 30;

    private static final Logger logger = LogManager.getLogger(Application.class);

    @Parameter(names = {"--port", "-p"}, description = "Port number", required = true)
    public int port = 7171;

    @Parameter(names = {"--use-custom-keepalive-settings"},
            description = "Use custom keepalive settings", required = false)
    public boolean useCustomKeepAliveSettings = false;

    @Parameter(names = {"--keepalive-time"},
            description = "Delay time for next keepalive", required = false)
    public int keepAliveTime = 30;

    @Parameter(names = {"--keepalive-timeout"},
            description = "Timeout for keepalive pings", required = false)
    public int keepAliveTimeOut = 5;

    @Parameter(names = {"--permit-keepalive-without-calls"},
    description = "Permit client keepalives without calls", required = false)
    public boolean permitKeepaliveWithoutCalls = false;

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

    void configureNetty(ServerBuilder serverBuilder) {
        if (!(serverBuilder instanceof  NettyServerBuilder)) {
            System.out.println("Error: ServerBuilder not a NettyServerBuilder." +
            "Can't configure custom settings.");
            return;
        }

        if (!useCustomKeepAliveSettings) {
            System.out.println("Using custom netty keepalive settings");
            return;
        } else {
            System.out.println("Using custom netty keepalive settings");
        }

        NettyServerBuilder nettyServerBuilder = (NettyServerBuilder) serverBuilder;
        nettyServerBuilder
                .keepAliveTime(keepAliveTime, TimeUnit.SECONDS)
                .keepAliveTimeout(keepAliveTimeOut, TimeUnit.SECONDS)
                .permitKeepAliveWithoutCalls(permitKeepaliveWithoutCalls);
    }

    void start() throws Exception {
        final ServerBuilder serverBuilder = ServerBuilder.forPort(port);

        configureNetty(serverBuilder);

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