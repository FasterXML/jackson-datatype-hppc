package com.fasterxml.jackson.datatype.hppc.deser;

import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.*;

import com.fasterxml.jackson.databind.*;

import com.carrotsearch.hppc.*;

public class HppcContainerDeserializers
{
    /**
     * We can either register abstract type defaults via ObjectMapper, or
     * just do it here. For now let's just do it locally; this will allow
     * override of definitions by app code (by using ObjectMapper resolution)
     */
    protected final static HashMap<Class<?>, Class<?>> _concreteMapping =
        new HashMap<Class<?>, Class<?>>();
    static {
        // int:
        _concreteMapping.put(IntContainer.class, IntArrayList.class);
        _concreteMapping.put(IntIndexedContainer.class, IntArrayList.class);
        /* 07-May-2015, tatu: 0.6 -> 0.7 changed names, to drop "Open" part.
         *   Alas, this means that our module can't support pre-0.7 any more
         *   starting with 2.6.
         */
//        _concreteMapping.put(IntSet.class, IntOpenHashSet.class); // for HPPC-0.6
        _concreteMapping.put(IntSet.class, IntHashSet.class);
        _concreteMapping.put(IntDeque.class, IntArrayDeque.class);
    }
    
    /**
     * Method called to see if this serializer (or a serializer this serializer
     * knows) should be used for given type; if not, null is returned.
     */
    public static JsonDeserializer<?> findDeserializer(DeserializationConfig config,
            final JavaType origType)
        throws JsonMappingException
    {
        JavaType type = origType;
        Class<?> raw = type.getRawClass();
        
        if (IntContainer.class.isAssignableFrom(raw)) {
            // maybe we have mapping from abstract to concrete type?
            if (type.isAbstract()) {
                Class<?> concrete = _concreteMapping.get(raw);
                if (concrete != null) {
                    type = type.forcedNarrowBy(concrete);
                    raw = concrete;
                }
            }
            
            if (IntIndexedContainer.class.isAssignableFrom(raw)) {
                return new IntIndexedContainerDeserializer(type, config);
            }
            if (IntSet.class.isAssignableFrom(raw)) {
                return new IntSetDeserializer(type, config);
            }
            if (IntDeque.class.isAssignableFrom(raw)) {
                return new IntDequeDeserializer(type, config);
            }
            // how about this? should we signal an error?
            throw new JsonMappingException("Unrecognized HPPC IntContainer type: "+origType);
        } else if (LongContainer.class.isAssignableFrom(raw)) {
            // !!! TBI
        } else if (FloatContainer.class.isAssignableFrom(raw)) {
            // !!! TBI
        } else if (DoubleContainer.class.isAssignableFrom(raw)) {
            // !!! TBI
        } else if (ByteContainer.class.isAssignableFrom(raw)) {
            // !!! TBI
        } else if (ShortContainer.class.isAssignableFrom(raw)) {
            // !!! TBI
        } else if (CharContainer.class.isAssignableFrom(raw)) {
            // !!! TBI
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
    @SuppressWarnings("serial")
    static abstract class IntContainerDeserializerBase<T>
        extends ContainerDeserializerBase<T>
    {
        public IntContainerDeserializerBase(JavaType type, DeserializationConfig config)
        {
            super(type, config);
        }

        @Override
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
        private static final long serialVersionUID = 1L;

        public IntSetDeserializer(JavaType type, DeserializationConfig config)
        {
            super(type, config);
        }

        @Override
        protected void add(IntSet container, int value) {
            container.add(value);
        }
    }

    static class IntIndexedContainerDeserializer extends IntContainerDeserializerBase<IntIndexedContainer>
    {
        private static final long serialVersionUID = 1L;

        public IntIndexedContainerDeserializer(JavaType type, DeserializationConfig config)
        {
            super(type, config);
        }

        @Override
        protected void add(IntIndexedContainer container, int value) {
            container.add(value);
        }
    }
    
    static class IntDequeDeserializer extends IntContainerDeserializerBase<IntDeque>
    {
        private static final long serialVersionUID = 1L;

        public IntDequeDeserializer(JavaType type, DeserializationConfig config)
        {
            super(type, config);
        }

        @Override
        protected void add(IntDeque container, int value) {
            container.addLast(value);
        }
    }

}

