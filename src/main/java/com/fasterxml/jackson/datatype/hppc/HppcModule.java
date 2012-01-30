package com.fasterxml.jackson.datatype.hppc;

import java.lang.reflect.Type;

import com.fasterxml.jackson.core.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer;
import com.fasterxml.jackson.databind.type.*;

import com.carrotsearch.hppc.ObjectContainer;
import com.fasterxml.jackson.datatype.hppc.deser.HppcContainerDeserializers;
import com.fasterxml.jackson.datatype.hppc.ser.*;

public class HppcModule extends SimpleModule
{
    public HppcModule()
    {
        super("HppcDatatypeModule", ModuleVersion.instance.version());
    }
    
    @Override
    public void setupModule(SetupContext context)
    {
        super.setupModule(context);
        // must add a "type modifier", to recognize HPPC collection/map types
        context.addTypeModifier(new HppcTypeModifier());
        context.addDeserializers(new HppcDeserializers());
        context.addSerializers(new HppcSerializers());
    }

    /*
    /**********************************************************************
    /* Helper classes
    /**********************************************************************
     */

    /**
     * Ww need to ensure that parameterized ("generic") containers are
     * recognized as Collection-/Map-like types, so that associated annotations
     * are processed, and key/value types passed as expected.
     */
    static class HppcTypeModifier extends TypeModifier
    {
        @Override
        public JavaType modifyType(JavaType type, Type jdkType, TypeBindings bindings,
                TypeFactory typeFactory)
        {
            Class<?> raw = type.getRawClass();
            if (ObjectContainer.class.isAssignableFrom(raw)) {
                JavaType[] params = typeFactory.findTypeParameters(type, ObjectContainer.class);
                return typeFactory.constructCollectionLikeType(raw, params[0]);
            }
            return type;
        }
    }
    
    static class HppcSerializers extends Serializers.Base
    {
        public HppcSerializers() { }
        
        /* We add definitions of some Object-valued collections as Collection-like,
         * so that we get proper handling of various collection-bound
         * annotations.
         */
        @Override
        public JsonSerializer<?> findCollectionLikeSerializer(
                SerializationConfig config, CollectionLikeType containerType,
                BeanDescription arg2, BeanProperty property, TypeSerializer elementTypeSerializer,
                JsonSerializer<Object> elementValueSerializer)
        {
            if (ObjectContainer.class.isAssignableFrom(containerType.getRawClass())) {
                // hmmh. not sure if we can find 'forceStaticTyping' anywhere...
                boolean staticTyping = config.isEnabled(MapperConfig.Feature.USE_STATIC_TYPING);
                ObjectArraySerializer ser = new ObjectArraySerializer(containerType.getContentType(),
                        staticTyping, elementTypeSerializer, property, elementValueSerializer);
                return new ObjectContainerSerializer(containerType, ser);
            }
            return null;
        }

        @Override
        public JsonSerializer<?> findMapLikeSerializer(
                SerializationConfig config, MapLikeType arg1,
                BeanDescription arg2, BeanProperty arg3,
                JsonSerializer<Object> arg4, TypeSerializer arg5,
                JsonSerializer<Object> arg6) {
            // TODO: handle XxxMap with Object keys and/or values
            return null;
        }

        /**
         * Anything that we don't explicitly mark as Map- or Collection-like
         * will end up here...
         */
        @Override
        public JsonSerializer<?> findSerializer(SerializationConfig config,
                JavaType type, BeanDescription beanDesc, BeanProperty property)
        {
            return HppcContainerSerializers.getMatchingSerializer(type);
        }
        
    }

    static class HppcDeserializers extends Deserializers.Base
    {
        @Override
        public JsonDeserializer<?> findBeanDeserializer(JavaType type,
                DeserializationConfig config,
                BeanDescription beanDesc, BeanProperty property)
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
    
}