package com.fasterxml.jackson.datatype.hppc.ser;

import org.codehaus.jackson.map.ObjectMapper;

import com.carrotsearch.hppc.*;
import com.fasterxml.jackson.datatype.hppc.HppcTestBase;

public class TestContainerSerializers extends HppcTestBase
{
    public void testByteSerializer() throws Exception
    {
        ObjectMapper mapper = mapperWithModule();

        ByteArrayList array = new ByteArrayList();
        array.add((byte)-12, (byte)0);
        assertEquals("[-12,0]", mapper.writeValueAsString(array));

        ByteOpenHashSet set = new ByteOpenHashSet();
        set.add((byte)1, (byte)2);
        String str = mapper.writeValueAsString(set);
        if (!"[1,2]".equals(str) && !"[2,1]".equals(str)) {
            fail("Incorrect serialization: "+str);
        }
    }
}
