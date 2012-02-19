package com.fasterxml.jackson.datatype.hppc.ser;

import java.io.IOException;
import java.lang.reflect.Type;

import com.fasterxml.jackson.core.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Base class for various container (~= Collection) serializers.
 */
public abstract class ContainerSerializerBase<T>
    extends StdSerializer<T>
{
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
    
    public JsonSerializer<?> getSerializer(JavaType type)
    {
        if (_handledType.isAssignableFrom(type.getRawClass())) {
            return this;
        }
        return null;
    }
    
    @Override
    public void serialize(T value, JsonGenerator jgen, SerializerProvider provider)
        throws IOException, JsonGenerationException
    {
        jgen.writeStartArray();
        serializeContents(value, jgen, provider);
        jgen.writeEndArray();
    }
    
    @Override
    public void serializeWithType(T value, JsonGenerator jgen, SerializerProvider provider,
            TypeSerializer typeSer)
        throws IOException, JsonGenerationException
    {
        typeSer.writeTypePrefixForArray(value, jgen);
        serializeContents(value, jgen, provider);
        typeSer.writeTypeSuffixForArray(value, jgen);
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint)
    {
        ObjectNode o = createSchemaNode("array", true);
        o.put("items", createSchemaNode(_schemeElementType));
        return o;
    }
    
    protected abstract void serializeContents(T value, JsonGenerator jgen, SerializerProvider provider)
        throws IOException, JsonGenerationException;
}
