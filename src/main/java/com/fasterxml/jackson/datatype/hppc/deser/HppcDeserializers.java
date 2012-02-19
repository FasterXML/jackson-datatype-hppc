package com.fasterxml.jackson.datatype.hppc.deser;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.Deserializers;

public class HppcDeserializers extends Deserializers.Base
    {
        @Override
        public JsonDeserializer<?> findBeanDeserializer(JavaType type,
                DeserializationConfig config, BeanDescription beanDesc)
            throws JsonMappingException
        {
            return HppcContainerDeserializers.findDeserializer(config, type);
        }

        /*
        @Override
        public JsonDeserializer<?> findCollectionLikeDeserializer(
                CollectionLikeType type, DeserializationConfig config,
                 BeanDescription arg3,
                BeanProperty arg4, TypeDeserializer arg5,
                JsonDeserializer<?> arg6) throws JsonMappingException {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public JsonDeserializer<?> findMapLikeDeserializer(MapLikeType type,
                DeserializationConfig config,
                BeanDescription arg3, BeanProperty arg4, KeyDeserializer arg5,
                TypeDeserializer arg6, JsonDeserializer<?> arg7)
                throws JsonMappingException {
            // TODO Auto-generated method stub
            return null;
        }
*/        
        
    }