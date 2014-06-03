Project to build Jackson (http://jackson.codehaus.org) module (jar) to support JSON serialization and deserialization of High-Performance Primitive Collections (see http://labs.carrotsearch.com/hppc.html) datatypes.

## Status

Currently (2.4.0) following things are supported:

* Serializing all 'XxxContainer' (IntContainer, IntSet, IntArrayList etc) types

and following are not yet supported:

* Deserialization
* Serialization of 'map' types (ie. 'XYAssociateContainer' implementations)

plan is to support full fidelity of Jackson annotation configurability; meaning that all generic types (ones with 'Object' in name, and with generic type parameter) could be supported; as well as use of included type information.

## Usage

### Maven dependency

To use module on Maven-based projects, use following dependency:

```xml
<dependency>
  <groupId>com.fasterxml.jackson</groupId>
  <artifactId>jackson-datatype-hppc</artifactId>
  <version>2.4.0</version>
</dependency>
```

(or whatever version is most up-to-date at the moment)

### Registering module

To use the the Module in Jackson, simply register it with the ObjectMapper instance:
Modules are registered through ObjectMapper, like so:

```java
ObjectMapper mapper = new ObjectMapper();
mapper.register(new HppcModule());
```

after which you can read JSON as HPCC types, as well as write HPPC types as JSON. It's really that simple; convenient and efficient.
