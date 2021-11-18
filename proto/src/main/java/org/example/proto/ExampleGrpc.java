package org.example.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.36.0)",
    comments = "Source: example.proto")
public final class ExampleGrpc {

  private ExampleGrpc() {}

  public static final String SERVICE_NAME = "proto.Example";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.example.proto.Request,
      org.example.proto.Response> getFetchMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Fetch",
      requestType = org.example.proto.Request.class,
      responseType = org.example.proto.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<org.example.proto.Request,
      org.example.proto.Response> getFetchMethod() {
    io.grpc.MethodDescriptor<org.example.proto.Request, org.example.proto.Response> getFetchMethod;
    if ((getFetchMethod = ExampleGrpc.getFetchMethod) == null) {
      synchronized (ExampleGrpc.class) {
        if ((getFetchMethod = ExampleGrpc.getFetchMethod) == null) {
          ExampleGrpc.getFetchMethod = getFetchMethod =
              io.grpc.MethodDescriptor.<org.example.proto.Request, org.example.proto.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Fetch"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.proto.Request.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.proto.Response.getDefaultInstance()))
              .setSchemaDescriptor(new ExampleMethodDescriptorSupplier("Fetch"))
              .build();
        }
      }
    }
    return getFetchMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ExampleStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ExampleStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ExampleStub>() {
        @java.lang.Override
        public ExampleStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ExampleStub(channel, callOptions);
        }
      };
    return ExampleStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ExampleBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ExampleBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ExampleBlockingStub>() {
        @java.lang.Override
        public ExampleBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ExampleBlockingStub(channel, callOptions);
        }
      };
    return ExampleBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ExampleFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ExampleFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ExampleFutureStub>() {
        @java.lang.Override
        public ExampleFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ExampleFutureStub(channel, callOptions);
        }
      };
    return ExampleFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ExampleImplBase implements io.grpc.BindableService {

    /**
     */
    public void fetch(org.example.proto.Request request,
        io.grpc.stub.StreamObserver<org.example.proto.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFetchMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getFetchMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                org.example.proto.Request,
                org.example.proto.Response>(
                  this, METHODID_FETCH)))
          .build();
    }
  }

  /**
   */
  public static final class ExampleStub extends io.grpc.stub.AbstractAsyncStub<ExampleStub> {
    private ExampleStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExampleStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ExampleStub(channel, callOptions);
    }

    /**
     */
    public void fetch(org.example.proto.Request request,
        io.grpc.stub.StreamObserver<org.example.proto.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getFetchMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ExampleBlockingStub extends io.grpc.stub.AbstractBlockingStub<ExampleBlockingStub> {
    private ExampleBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExampleBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ExampleBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<org.example.proto.Response> fetch(
        org.example.proto.Request request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getFetchMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ExampleFutureStub extends io.grpc.stub.AbstractFutureStub<ExampleFutureStub> {
    private ExampleFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExampleFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ExampleFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_FETCH = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ExampleImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ExampleImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_FETCH:
          serviceImpl.fetch((org.example.proto.Request) request,
              (io.grpc.stub.StreamObserver<org.example.proto.Response>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ExampleBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ExampleBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.example.proto.ExampleProtos.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Example");
    }
  }

  private static final class ExampleFileDescriptorSupplier
      extends ExampleBaseDescriptorSupplier {
    ExampleFileDescriptorSupplier() {}
  }

  private static final class ExampleMethodDescriptorSupplier
      extends ExampleBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ExampleMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ExampleGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ExampleFileDescriptorSupplier())
              .addMethod(getFetchMethod())
              .build();
        }
      }
    }
    return result;
  }
}
