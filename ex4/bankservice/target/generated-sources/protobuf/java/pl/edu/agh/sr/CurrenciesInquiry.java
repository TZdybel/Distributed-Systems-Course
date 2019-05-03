// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: HelloService.proto

package pl.edu.agh.sr;

/**
 * Protobuf type {@code pl.edu.agh.sr.CurrenciesInquiry}
 */
public  final class CurrenciesInquiry extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:pl.edu.agh.sr.CurrenciesInquiry)
    CurrenciesInquiryOrBuilder {
  // Use CurrenciesInquiry.newBuilder() to construct.
  private CurrenciesInquiry(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private CurrenciesInquiry() {
    currencies_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private CurrenciesInquiry(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 8: {
            int rawValue = input.readEnum();
            if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
              currencies_ = new java.util.ArrayList<java.lang.Integer>();
              mutable_bitField0_ |= 0x00000001;
            }
            currencies_.add(rawValue);
            break;
          }
          case 10: {
            int length = input.readRawVarint32();
            int oldLimit = input.pushLimit(length);
            while(input.getBytesUntilLimit() > 0) {
              int rawValue = input.readEnum();
              if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
                currencies_ = new java.util.ArrayList<java.lang.Integer>();
                mutable_bitField0_ |= 0x00000001;
              }
              currencies_.add(rawValue);
            }
            input.popLimit(oldLimit);
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
        currencies_ = java.util.Collections.unmodifiableList(currencies_);
      }
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return pl.edu.agh.sr.HelloService.internal_static_pl_edu_agh_sr_CurrenciesInquiry_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return pl.edu.agh.sr.HelloService.internal_static_pl_edu_agh_sr_CurrenciesInquiry_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            pl.edu.agh.sr.CurrenciesInquiry.class, pl.edu.agh.sr.CurrenciesInquiry.Builder.class);
  }

  public static final int CURRENCIES_FIELD_NUMBER = 1;
  private java.util.List<java.lang.Integer> currencies_;
  private static final com.google.protobuf.Internal.ListAdapter.Converter<
      java.lang.Integer, pl.edu.agh.sr.Currency> currencies_converter_ =
          new com.google.protobuf.Internal.ListAdapter.Converter<
              java.lang.Integer, pl.edu.agh.sr.Currency>() {
            public pl.edu.agh.sr.Currency convert(java.lang.Integer from) {
              pl.edu.agh.sr.Currency result = pl.edu.agh.sr.Currency.valueOf(from);
              return result == null ? pl.edu.agh.sr.Currency.UNRECOGNIZED : result;
            }
          };
  /**
   * <code>repeated .pl.edu.agh.sr.Currency currencies = 1;</code>
   */
  public java.util.List<pl.edu.agh.sr.Currency> getCurrenciesList() {
    return new com.google.protobuf.Internal.ListAdapter<
        java.lang.Integer, pl.edu.agh.sr.Currency>(currencies_, currencies_converter_);
  }
  /**
   * <code>repeated .pl.edu.agh.sr.Currency currencies = 1;</code>
   */
  public int getCurrenciesCount() {
    return currencies_.size();
  }
  /**
   * <code>repeated .pl.edu.agh.sr.Currency currencies = 1;</code>
   */
  public pl.edu.agh.sr.Currency getCurrencies(int index) {
    return currencies_converter_.convert(currencies_.get(index));
  }
  /**
   * <code>repeated .pl.edu.agh.sr.Currency currencies = 1;</code>
   */
  public java.util.List<java.lang.Integer>
  getCurrenciesValueList() {
    return currencies_;
  }
  /**
   * <code>repeated .pl.edu.agh.sr.Currency currencies = 1;</code>
   */
  public int getCurrenciesValue(int index) {
    return currencies_.get(index);
  }
  private int currenciesMemoizedSerializedSize;

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    getSerializedSize();
    if (getCurrenciesList().size() > 0) {
      output.writeUInt32NoTag(10);
      output.writeUInt32NoTag(currenciesMemoizedSerializedSize);
    }
    for (int i = 0; i < currencies_.size(); i++) {
      output.writeEnumNoTag(currencies_.get(i));
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    {
      int dataSize = 0;
      for (int i = 0; i < currencies_.size(); i++) {
        dataSize += com.google.protobuf.CodedOutputStream
          .computeEnumSizeNoTag(currencies_.get(i));
      }
      size += dataSize;
      if (!getCurrenciesList().isEmpty()) {  size += 1;
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32SizeNoTag(dataSize);
      }currenciesMemoizedSerializedSize = dataSize;
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof pl.edu.agh.sr.CurrenciesInquiry)) {
      return super.equals(obj);
    }
    pl.edu.agh.sr.CurrenciesInquiry other = (pl.edu.agh.sr.CurrenciesInquiry) obj;

    boolean result = true;
    result = result && currencies_.equals(other.currencies_);
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (getCurrenciesCount() > 0) {
      hash = (37 * hash) + CURRENCIES_FIELD_NUMBER;
      hash = (53 * hash) + currencies_.hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static pl.edu.agh.sr.CurrenciesInquiry parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static pl.edu.agh.sr.CurrenciesInquiry parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static pl.edu.agh.sr.CurrenciesInquiry parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static pl.edu.agh.sr.CurrenciesInquiry parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static pl.edu.agh.sr.CurrenciesInquiry parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static pl.edu.agh.sr.CurrenciesInquiry parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static pl.edu.agh.sr.CurrenciesInquiry parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static pl.edu.agh.sr.CurrenciesInquiry parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static pl.edu.agh.sr.CurrenciesInquiry parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static pl.edu.agh.sr.CurrenciesInquiry parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static pl.edu.agh.sr.CurrenciesInquiry parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static pl.edu.agh.sr.CurrenciesInquiry parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(pl.edu.agh.sr.CurrenciesInquiry prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code pl.edu.agh.sr.CurrenciesInquiry}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:pl.edu.agh.sr.CurrenciesInquiry)
      pl.edu.agh.sr.CurrenciesInquiryOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return pl.edu.agh.sr.HelloService.internal_static_pl_edu_agh_sr_CurrenciesInquiry_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return pl.edu.agh.sr.HelloService.internal_static_pl_edu_agh_sr_CurrenciesInquiry_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              pl.edu.agh.sr.CurrenciesInquiry.class, pl.edu.agh.sr.CurrenciesInquiry.Builder.class);
    }

    // Construct using pl.edu.agh.sr.CurrenciesInquiry.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      currencies_ = java.util.Collections.emptyList();
      bitField0_ = (bitField0_ & ~0x00000001);
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return pl.edu.agh.sr.HelloService.internal_static_pl_edu_agh_sr_CurrenciesInquiry_descriptor;
    }

    public pl.edu.agh.sr.CurrenciesInquiry getDefaultInstanceForType() {
      return pl.edu.agh.sr.CurrenciesInquiry.getDefaultInstance();
    }

    public pl.edu.agh.sr.CurrenciesInquiry build() {
      pl.edu.agh.sr.CurrenciesInquiry result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public pl.edu.agh.sr.CurrenciesInquiry buildPartial() {
      pl.edu.agh.sr.CurrenciesInquiry result = new pl.edu.agh.sr.CurrenciesInquiry(this);
      int from_bitField0_ = bitField0_;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        currencies_ = java.util.Collections.unmodifiableList(currencies_);
        bitField0_ = (bitField0_ & ~0x00000001);
      }
      result.currencies_ = currencies_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof pl.edu.agh.sr.CurrenciesInquiry) {
        return mergeFrom((pl.edu.agh.sr.CurrenciesInquiry)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(pl.edu.agh.sr.CurrenciesInquiry other) {
      if (other == pl.edu.agh.sr.CurrenciesInquiry.getDefaultInstance()) return this;
      if (!other.currencies_.isEmpty()) {
        if (currencies_.isEmpty()) {
          currencies_ = other.currencies_;
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          ensureCurrenciesIsMutable();
          currencies_.addAll(other.currencies_);
        }
        onChanged();
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      pl.edu.agh.sr.CurrenciesInquiry parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (pl.edu.agh.sr.CurrenciesInquiry) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<java.lang.Integer> currencies_ =
      java.util.Collections.emptyList();
    private void ensureCurrenciesIsMutable() {
      if (!((bitField0_ & 0x00000001) == 0x00000001)) {
        currencies_ = new java.util.ArrayList<java.lang.Integer>(currencies_);
        bitField0_ |= 0x00000001;
      }
    }
    /**
     * <code>repeated .pl.edu.agh.sr.Currency currencies = 1;</code>
     */
    public java.util.List<pl.edu.agh.sr.Currency> getCurrenciesList() {
      return new com.google.protobuf.Internal.ListAdapter<
          java.lang.Integer, pl.edu.agh.sr.Currency>(currencies_, currencies_converter_);
    }
    /**
     * <code>repeated .pl.edu.agh.sr.Currency currencies = 1;</code>
     */
    public int getCurrenciesCount() {
      return currencies_.size();
    }
    /**
     * <code>repeated .pl.edu.agh.sr.Currency currencies = 1;</code>
     */
    public pl.edu.agh.sr.Currency getCurrencies(int index) {
      return currencies_converter_.convert(currencies_.get(index));
    }
    /**
     * <code>repeated .pl.edu.agh.sr.Currency currencies = 1;</code>
     */
    public Builder setCurrencies(
        int index, pl.edu.agh.sr.Currency value) {
      if (value == null) {
        throw new NullPointerException();
      }
      ensureCurrenciesIsMutable();
      currencies_.set(index, value.getNumber());
      onChanged();
      return this;
    }
    /**
     * <code>repeated .pl.edu.agh.sr.Currency currencies = 1;</code>
     */
    public Builder addCurrencies(pl.edu.agh.sr.Currency value) {
      if (value == null) {
        throw new NullPointerException();
      }
      ensureCurrenciesIsMutable();
      currencies_.add(value.getNumber());
      onChanged();
      return this;
    }
    /**
     * <code>repeated .pl.edu.agh.sr.Currency currencies = 1;</code>
     */
    public Builder addAllCurrencies(
        java.lang.Iterable<? extends pl.edu.agh.sr.Currency> values) {
      ensureCurrenciesIsMutable();
      for (pl.edu.agh.sr.Currency value : values) {
        currencies_.add(value.getNumber());
      }
      onChanged();
      return this;
    }
    /**
     * <code>repeated .pl.edu.agh.sr.Currency currencies = 1;</code>
     */
    public Builder clearCurrencies() {
      currencies_ = java.util.Collections.emptyList();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     * <code>repeated .pl.edu.agh.sr.Currency currencies = 1;</code>
     */
    public java.util.List<java.lang.Integer>
    getCurrenciesValueList() {
      return java.util.Collections.unmodifiableList(currencies_);
    }
    /**
     * <code>repeated .pl.edu.agh.sr.Currency currencies = 1;</code>
     */
    public int getCurrenciesValue(int index) {
      return currencies_.get(index);
    }
    /**
     * <code>repeated .pl.edu.agh.sr.Currency currencies = 1;</code>
     */
    public Builder setCurrenciesValue(
        int index, int value) {
      ensureCurrenciesIsMutable();
      currencies_.set(index, value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated .pl.edu.agh.sr.Currency currencies = 1;</code>
     */
    public Builder addCurrenciesValue(int value) {
      ensureCurrenciesIsMutable();
      currencies_.add(value);
      onChanged();
      return this;
    }
    /**
     * <code>repeated .pl.edu.agh.sr.Currency currencies = 1;</code>
     */
    public Builder addAllCurrenciesValue(
        java.lang.Iterable<java.lang.Integer> values) {
      ensureCurrenciesIsMutable();
      for (int value : values) {
        currencies_.add(value);
      }
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:pl.edu.agh.sr.CurrenciesInquiry)
  }

  // @@protoc_insertion_point(class_scope:pl.edu.agh.sr.CurrenciesInquiry)
  private static final pl.edu.agh.sr.CurrenciesInquiry DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new pl.edu.agh.sr.CurrenciesInquiry();
  }

  public static pl.edu.agh.sr.CurrenciesInquiry getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<CurrenciesInquiry>
      PARSER = new com.google.protobuf.AbstractParser<CurrenciesInquiry>() {
    public CurrenciesInquiry parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new CurrenciesInquiry(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<CurrenciesInquiry> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<CurrenciesInquiry> getParserForType() {
    return PARSER;
  }

  public pl.edu.agh.sr.CurrenciesInquiry getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
