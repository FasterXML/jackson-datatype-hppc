package com.fasterxml.jackson.datatype.hppc.ser;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.type.CollectionLikeType;

import com.carrotsearch.hppc.ObjectContainer;
import com.carrotsearch.hppc.ObjectIndexedContainer;
import com.carrotsearch.hppc.predicates.ObjectPredicate;

public class ObjectContainerSerializer
    extends ContainerSerializerBase<ObjectContainer<?>>
{
    public ObjectContainerSerializer(CollectionLikeType containerType) {
        // not sure if we can claim it is "object"... could be String, wrapper types etc:
        super(containerType, "any");
    }

    // !!! TODO: implement properly...
    
    @Override
    protected void serializeContents(final ObjectContainer<?> value, final JsonGenerator jgen, SerializerProvider provider)
           throws IOException, JsonGenerationException
    {
        /*
        if (value instanceof ObjectIndexedContainer<?>) {
            ObjectIndexedContainer<?> list = (ObjectIndexedContainer<?>) value;
            for (int i = 0, len = list.size(); i < len; ++i) {
                jgen.writeNumber(list.get(i));
            }
            return;
        }
        // doh. Can't throw checked exceptions through; hence need convoluted handling...
        final ExceptionHolder holder = new ExceptionHolder();
        value.forEach(new ObjectPredicate<?>() {
            @Override
            public boolean apply(Object value) {
                try {
                    jgen.writeNumber(value);
                } catch (IOException e) {
                    holder.assignException(e);
                    return false;
                }
                return true;
            }
        });
        holder.throwHeld();
        */
    }
}
