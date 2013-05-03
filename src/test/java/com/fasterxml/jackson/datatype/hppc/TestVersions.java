package com.fasterxml.jackson.datatype.hppc;

import java.io.*;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;

public class TestVersions extends HppcTestBase
{
    public void testMapperVersions() throws IOException
    {
        HppcModule module = new HppcModule();
        assertEquals(PackageVersion.VERSION, module.version());
    }
}

