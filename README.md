Project to build Jackson (http://jackson.codehaus.org) module (jar) to support JSON serialization and deserialization of High-Performance Primitive Collections (see http://labs.carrotsearch.com/hppc.html) datatypes.

# Usage

Modules are registered through ObjectMapper, like so:

    ObjectMapper mapper = new ObjectMapper();
    mapper.register(new HppcModule());

after which you can read JSON as HPCC types, as well as write HPPC types as JSON. It's really that simple; convenient and efficient.
