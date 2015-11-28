package com.fasterxml.jackson.datatype.hppc.ser;

import java.io.IOException;
import java.lang.reflect.Type;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.ContainerSerializer;

/**
 * Base class for various container (~= Collection) serializers.
 */
public abstract class ContainerSerializerBase<T>
    extends ContainerSerializer<T>
{
    private static final long serialVersionUID = 1L;

    protected final String _schemeElementType;

    protected ContainerSerializerBase(Class<T> type, String schemaElementType)
    {
        super(type);
        _schemeElementType = schemaElementType;
    }

    protected ContainerSerializerBase(JavaType type, String schemaElementType)
    {
        super(type);
        _schemeElementType = schemaElementType;
    }

    protected ContainerSerializerBase(ContainerSerializerBase<?> src) {
        super(src._handledType, true);
        _schemeElementType = src._schemeElementType;
    }

    /*
    /**********************************************************
    /* Simple accessor overrides, defaults
    /**********************************************************
     */
    
    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint)
    {
        ObjectNode o = createSchemaNode("array", true);
        o.set("items", createSchemaNode(_schemeElementType));
        return o;
    }
    
    @Override
    public abstract boolean isEmpty(SerializerProvider provider, T value);

    @Override
    public JsonSerializer<?> getContentSerializer() {
        // We are not delegating, for most part, so while not dynamic claim we don't have it
        return null;
    }

    @Override
    protected ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer vts) {
        // May or may not be supportable, but for now fail loudly, not quietly
        throw new UnsupportedOperationException();
    }

    @Override
    public JavaType getContentType() {
        // Not sure how to efficiently support this; could resolve types of course
        return null;
    }

    @Override
    public abstract void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint)
        throws JsonMappingException;

    /*
    /**********************************************************
    /* Serialization
    /**********************************************************
     */
    
    @Override
    public void serialize(T value, JsonGenerator jgen, SerializerProvider provider)
        throws IOException
    {
        jgen.writeStartArray();
        serializeContents(value, jgen, provider);
        jgen.writeEndArray();
    }

    protected abstract void serializeContents(T value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException;
    
    @Override
    public void serializeWithType(T value, JsonGenerator jgen, SerializerProvider provider,
            TypeSerializer typeSer)
        throws IOException
    {
        typeSer.writeTypePrefixForArray(value, jgen);
        serializeContents(value, jgen, provider);
        typeSer.writeTypeSuffixForArray(value, jgen);
    }

    /*
    /**********************************************************
    /* Helper methods for sub-classes
    /**********************************************************
     */
    
    protected JsonSerializer<?> getSerializer(JavaType type)
    {
        if (_handledType.isAssignableFrom(type.getRawClass())) {
            return this;
        }
        return null;
    }
}
