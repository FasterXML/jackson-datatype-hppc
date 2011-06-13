package com.fasterxml.jackson.datatype.hppc.ser;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.map.TypeSerializer;
import org.codehaus.jackson.map.ser.impl.ObjectArraySerializer;
import org.codehaus.jackson.map.type.CollectionLikeType;

import com.carrotsearch.hppc.*;

/**
 * Note: this implementation does not yet properly handle all
 * polymorphic cases
 */
public class ObjectContainerSerializer
    extends ContainerSerializerBase<ObjectContainer<?>>
    implements ResolvableSerializer
{
    /**
     * We will basically just delegate serialization to the standard
     * Object[] serializer; as we can not sub-class it.
     */
    protected final ObjectArraySerializer _delegate;

    public ObjectContainerSerializer(CollectionLikeType containerType,
            ObjectArraySerializer delegate)
    {
        // not sure if we can claim it is "object"... could be String, wrapper types etc:
        super(containerType, "any");
        _delegate = delegate;
    }

    @Override
    public void serialize(ObjectContainer<?> value, JsonGenerator jgen, SerializerProvider provider)
        throws IOException, JsonGenerationException
    {
        _delegate.serialize(value.toArray(), jgen, provider);
    }
    
    @Override
    public void serializeWithType(ObjectContainer<?> value, JsonGenerator jgen, SerializerProvider provider,
            TypeSerializer typeSer)
        throws IOException, JsonGenerationException
    {
        _delegate.serializeWithType(value.toArray(), jgen, provider, typeSer);
    }

    protected void serializeContents(ObjectContainer<?> value, JsonGenerator jgen, SerializerProvider provider)
        throws IOException, JsonGenerationException {
        throw new IllegalStateException();
    }
    
    /**
     * Need to get callback to resolve value serializer, if static typing
     * is used (either being forced, or because value type is final)
     */
    public void resolve(SerializerProvider provider)
        throws JsonMappingException
    {
        _delegate.resolve(provider);
    }        

}
