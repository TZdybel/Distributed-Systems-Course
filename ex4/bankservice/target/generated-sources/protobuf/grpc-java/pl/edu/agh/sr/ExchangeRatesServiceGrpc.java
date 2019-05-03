package pl.edu.agh.sr;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.4.0)",
    comments = "Source: HelloService.proto")
public final class ExchangeRatesServiceGrpc {

  private ExchangeRatesServiceGrpc() {}

  public static final String SERVICE_NAME = "pl.edu.agh.sr.ExchangeRatesServer";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<pl.edu.agh.sr.CurrenciesInquiry,
      pl.edu.agh.sr.CurrenciesResponse> METHOD_EXCHANGE_RATES =
      io.grpc.MethodDescriptor.<pl.edu.agh.sr.CurrenciesInquiry, pl.edu.agh.sr.CurrenciesResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "pl.edu.agh.sr.ExchangeRatesServer", "exchangeRates"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              pl.edu.agh.sr.CurrenciesInquiry.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              pl.edu.agh.sr.CurrenciesResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ExchangeRatesServiceStub newStub(io.grpc.Channel channel) {
    return new ExchangeRatesServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ExchangeRatesServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ExchangeRatesServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ExchangeRatesServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ExchangeRatesServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class ExchangeRatesServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void exchangeRates(pl.edu.agh.sr.CurrenciesInquiry request,
        io.grpc.stub.StreamObserver<pl.edu.agh.sr.CurrenciesResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_EXCHANGE_RATES, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_EXCHANGE_RATES,
            asyncServerStreamingCall(
              new MethodHandlers<
                pl.edu.agh.sr.CurrenciesInquiry,
                pl.edu.agh.sr.CurrenciesResponse>(
                  this, METHODID_EXCHANGE_RATES)))
          .build();
    }
  }

  /**
   */
  public static final class ExchangeRatesServiceStub extends io.grpc.stub.AbstractStub<ExchangeRatesServiceStub> {
    private ExchangeRatesServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ExchangeRatesServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExchangeRatesServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ExchangeRatesServiceStub(channel, callOptions);
    }

    /**
     */
    public void exchangeRates(pl.edu.agh.sr.CurrenciesInquiry request,
        io.grpc.stub.StreamObserver<pl.edu.agh.sr.CurrenciesResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_EXCHANGE_RATES, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ExchangeRatesServiceBlockingStub extends io.grpc.stub.AbstractStub<ExchangeRatesServiceBlockingStub> {
    private ExchangeRatesServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ExchangeRatesServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExchangeRatesServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ExchangeRatesServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<pl.edu.agh.sr.CurrenciesResponse> exchangeRates(
        pl.edu.agh.sr.CurrenciesInquiry request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_EXCHANGE_RATES, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ExchangeRatesServiceFutureStub extends io.grpc.stub.AbstractStub<ExchangeRatesServiceFutureStub> {
    private ExchangeRatesServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ExchangeRatesServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExchangeRatesServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ExchangeRatesServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_EXCHANGE_RATES = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ExchangeRatesServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ExchangeRatesServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_EXCHANGE_RATES:
          serviceImpl.exchangeRates((pl.edu.agh.sr.CurrenciesInquiry) request,
              (io.grpc.stub.StreamObserver<pl.edu.agh.sr.CurrenciesResponse>) responseObserver);
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

  private static final class ExchangeRatesServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return pl.edu.agh.sr.HelloService.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ExchangeRatesServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ExchangeRatesServiceDescriptorSupplier())
              .addMethod(METHOD_EXCHANGE_RATES)
              .build();
        }
      }
    }
    return result;
  }
}
