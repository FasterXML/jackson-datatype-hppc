package com.fasterxml.jackson.datatype.hppc.deser;

import java.io.IOException;

import org.codehaus.jackson.*;
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.type.JavaType;

import com.carrotsearch.hppc.*;

public class ContainerDeserializers
{
    /**
     * Method called to see if this serializer (or a serializer this serializer
     * knows) should be used for given type; if not, null is returned.
     */
    public static JsonDeserializer<?> findDeserializer(DeserializationConfig config,
            JavaType type)
    {
        Class<?> raw = type.getRawClass();
        
        if (IntContainer.class.isAssignableFrom(raw)) {
            if (IntIndexedContainer.class.isAssignableFrom(raw)) {
                return new IntIndexedContainerDeserializer(type, config);
            }
            if (IntSet.class.isAssignableFrom(raw)) {
                return new IntSetDeserializer(type, config);
            }
            if (IntDeque.class.isAssignableFrom(raw)) {
                return new IntDequeDeserializer(type, config);
            }
        }
        return null;
    }        
    
    /*
    /**********************************************************************
    /* Intermediate base classes
    /**********************************************************************
     */

    /**
     * Intermediate base class used for various integral (as opposed to
     * floating point) value container types.
     */
    static abstract class IntContainerDeserializerBase<T>
        extends ContainerDeserializerBase<T>
    {
        public IntContainerDeserializerBase(JavaType type, DeserializationConfig config)
        {
            super(type, config);
        }
        
        public void deserializeContents(JsonParser jp, DeserializationContext ctxt,
                T container)
            throws IOException, JsonProcessingException
        {
            JsonToken t;
            while ((t = jp.nextToken()) != JsonToken.END_ARRAY) {
                // whether we should allow truncating conversions?
                int value;
                if (t == JsonToken.VALUE_NUMBER_INT || t == JsonToken.VALUE_NUMBER_FLOAT) {
                    // should we catch overflow exceptions?
                    value = jp.getIntValue();
                } else {
                    if (t != JsonToken.VALUE_NULL) {
                        throw ctxt.mappingException(_valueClass.getComponentType());
                    }
                    value = 0;
                }
                add(container, value);
            }
        }

        protected abstract void add(T container, int value);
    }

    /*
    /**********************************************************************
    /* Concrete container implementations; basic integral types
    /**********************************************************************
     */

    // // // int containers
    
    static class IntSetDeserializer extends IntContainerDeserializerBase<IntSet>
    {
        public IntSetDeserializer(JavaType type, DeserializationConfig config)
        {
            super(type, config);
        }

        protected void add(IntSet container, int value) {
            container.add(value);
        }
    }

    static class IntIndexedContainerDeserializer extends IntContainerDeserializerBase<IntIndexedContainer>
    {
        public IntIndexedContainerDeserializer(JavaType type, DeserializationConfig config)
        {
            super(type, config);
        }

        protected void add(IntIndexedContainer container, int value) {
            container.add(value);
        }
    }
    
    static class IntDequeDeserializer extends IntContainerDeserializerBase<IntDeque>
    {
        public IntDequeDeserializer(JavaType type, DeserializationConfig config)
        {
            super(type, config);
        }

        protected void add(IntDeque container, int value) {
            container.addLast(value);
        }
    }

}

