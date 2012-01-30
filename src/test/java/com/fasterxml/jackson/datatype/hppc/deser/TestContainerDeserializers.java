package com.fasterxml.jackson.datatype.hppc.deser;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Assert;

import com.carrotsearch.hppc.*;

import com.fasterxml.jackson.datatype.hppc.HppcTestBase;

public class TestContainerDeserializers extends HppcTestBase
{
    public void testIntDeserializers() throws Exception
    {
        ObjectMapper mapper = mapperWithModule();

        // first, direct
        IntArrayList array = mapper.readValue("[1,-3]", IntArrayList.class);
        Assert.assertArrayEquals(new int[] { 1, -3 }, array.toArray());
        IntOpenHashSet set = mapper.readValue("[-1234,0]", IntOpenHashSet.class);
        Assert.assertArrayEquals(new int[] { -1234, 0 }, set.toArray());
        IntArrayDeque dq = mapper.readValue("[0,13]", IntArrayDeque.class);
        Assert.assertArrayEquals(new int[] { -0, 13 }, dq.toArray());

        // then via abstract class defaulting
        IntIndexedContainer array2 = mapper.readValue("[1,-3]", IntIndexedContainer.class);
        Assert.assertArrayEquals(new int[] { 1, -3 }, array2.toArray());
        IntSet set2 = mapper.readValue("[-1234,0]", IntSet.class);
        Assert.assertArrayEquals(new int[] { -1234, 0 }, set2.toArray());
        IntDeque dq2 = mapper.readValue("[0,13]", IntDeque.class);
        Assert.assertArrayEquals(new int[] { -0, 13 }, dq2.toArray());
    }

}
