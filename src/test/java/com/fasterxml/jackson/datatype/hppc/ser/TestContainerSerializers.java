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

    public void testShortSerializer() throws Exception
    {
        ObjectMapper mapper = mapperWithModule();

        ShortArrayList array = new ShortArrayList();
        array.add((short)-12, (short)0);
        assertEquals("[-12,0]", mapper.writeValueAsString(array));

        ShortOpenHashSet set = new ShortOpenHashSet();
        set.add((short)1, (short)2);
        String str = mapper.writeValueAsString(set);
        if (!"[1,2]".equals(str) && !"[2,1]".equals(str)) {
            fail("Incorrect serialization: "+str);
        }
    }

    public void testIntSerializer() throws Exception
    {
        ObjectMapper mapper = mapperWithModule();

        IntArrayList array = new IntArrayList();
        array.add(-12, 0);
        assertEquals("[-12,0]", mapper.writeValueAsString(array));

        IntOpenHashSet set = new IntOpenHashSet();
        set.add(1, 2);
        String str = mapper.writeValueAsString(set);
        if (!"[1,2]".equals(str) && !"[2,1]".equals(str)) {
            fail("Incorrect serialization: "+str);
        }
    }

    public void testLongSerializer() throws Exception
    {
        ObjectMapper mapper = mapperWithModule();

        LongArrayList array = new LongArrayList();
        array.add(-12L, 0L);
        assertEquals("[-12,0]", mapper.writeValueAsString(array));

        LongOpenHashSet set = new LongOpenHashSet();
        set.add(1L, 2L);
        String str = mapper.writeValueAsString(set);
        if (!"[1,2]".equals(str) && !"[2,1]".equals(str)) {
            fail("Incorrect serialization: "+str);
        }
    }

    public void testCharSerializer() throws Exception
    {
        ObjectMapper mapper = mapperWithModule();

        CharArrayList array = new CharArrayList();
        array.add('a', 'b');
        assertEquals("[\"a\",\"b\"]", mapper.writeValueAsString(array));

        CharOpenHashSet set = new CharOpenHashSet();
        set.add('d','e');
        String str = mapper.writeValueAsString(set);
        if (!"[\"d\",\"e\"]".equals(str) && !"[\"e\",\"d\"]".equals(str)) {
            fail("Incorrect serialization: "+str);
        }
    }

    public void testFloatSerializer() throws Exception
    {
        ObjectMapper mapper = mapperWithModule();
        FloatArrayList array = new FloatArrayList();
        array.add(-12.25f, 0.5f);
        assertEquals("[-12.25,0.5]", mapper.writeValueAsString(array));

        FloatOpenHashSet set = new FloatOpenHashSet();
        set.add(1.75f, 0.5f);
        String str = mapper.writeValueAsString(set);
        if (!"[1.75,0.5]".equals(str) && !"[0.5,1.75]".equals(str)) {
            fail("Incorrect serialization: "+str);
        }
    }

    public void testDoubleSerializer() throws Exception
    {
        ObjectMapper mapper = mapperWithModule();
        DoubleArrayList array = new DoubleArrayList();
        array.add(-12.25, 0.5);
        assertEquals("[-12.25,0.5]", mapper.writeValueAsString(array));

        DoubleOpenHashSet set = new DoubleOpenHashSet();
        set.add(1.75, 0.5);
        String str = mapper.writeValueAsString(set);
        if (!"[1.75,0.5]".equals(str) && !"[0.5,1.75]".equals(str)) {
            fail("Incorrect serialization: "+str);
        }
    }
    
}
