// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: example.proto

package org.example.proto;

public final class ExampleProtos {
  private ExampleProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_proto_Request_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_proto_Request_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_proto_Response_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_proto_Response_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rexample.proto\022\005proto\"\030\n\007Request\022\r\n\005que" +
      "ry\030\001 \001(\t\"\030\n\010Response\022\014\n\004data\030\001 \001(\t27\n\007Ex" +
      "ample\022,\n\005Fetch\022\016.proto.Request\032\017.proto.R" +
      "esponse\"\0000\001B$\n\021org.example.protoB\rExampl" +
      "eProtosP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_proto_Request_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_proto_Request_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_proto_Request_descriptor,
        new java.lang.String[] { "Query", });
    internal_static_proto_Response_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_proto_Response_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_proto_Response_descriptor,
        new java.lang.String[] { "Data", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
