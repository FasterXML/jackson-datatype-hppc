package com.fasterxml.jackson.datatype.hppc;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.*;

public class HppcDatatypeModule extends Module // can't use just SimpleModule, due to generic types
{
    private final String NAME = "HppcDatatypeModule";
    
    // Should externalize this, but how? Pre-build filtering?
    private final static Version VERSION = new Version(0, 1, 0, null); // 0.1.0

    public HppcDatatypeModule()
    {
        
    }

    @Override public String getModuleName() { return NAME; }
    @Override public Version version() { return VERSION; }
    
    @Override
    public void setupModule(SetupContext context)
    {
	//        context.addDeserializers(new GuavaDeserializers());
    }

}