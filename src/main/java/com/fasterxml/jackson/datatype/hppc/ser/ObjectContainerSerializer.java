package com.fasterxml.jackson.datatype.hppc.ser;

import java.io.IOException;

import com.fasterxml.jackson.core.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer;
import com.fasterxml.jackson.databind.type.*;

import com.carrotsearch.hppc.*;

/**
 * Note: this implementation does not yet properly handle all
 * polymorphic cases
 */
public class ObjectContainerSerializer
    extends ContainerSerializerBase<ObjectContainer<?>>
    implements ContextualSerializer
{
    /**
     * We will basically just delegate serialization to the standard
     * Object[] serializer; as we can not sub-class it.
     */
    protected final ObjectArraySerializer _delegate;

    /*
    /**********************************************************************
    /* Life-cycle
    /**********************************************************************
     */
    
    public ObjectContainerSerializer(CollectionLikeType containerType,
            ObjectArraySerializer delegate)
    {
        // not sure if we can claim it is "object"... could be String, wrapper types etc:
        super(containerType, "any");
        _delegate = delegate;
    }

    protected ObjectContainerSerializer(ObjectContainerSerializer base,
            ObjectArraySerializer delegate)
    {
        super(base);
        _delegate = delegate;
    }
    
    protected JsonSerializer<?> withDelegate(ObjectArraySerializer newDelegate) {
        return (newDelegate == _delegate) ? this : new ObjectContainerSerializer(this, newDelegate);
    }

    /*
    /**********************************************************************
    /* Serialization
    /**********************************************************************
     */
    
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

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov,
            BeanProperty property) throws JsonMappingException {
        return withDelegate((ObjectArraySerializer) _delegate.createContextual(prov, property));
    }        
}
