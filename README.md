# Jackson 3 Deserialization issues with Spring Boot

## Issue

To reproduce the issue, run the tests

```
./gradlew test
```

Jackson 3 using custom deserializer of a JSON with nested object will throw an exception.


```txt
com.fasterxml.jackson.databind.DatabindException: class com.deskoh.entities.SourceSystem cannot be cast to class com.deskoh.entities.Project (com.deskoh.entities.SourceSystem and com.deskoh.entities.Project are in unnamed module of loader 'app')
 at [Source: REDACTED (`StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION` disabled); byte offset: #119] (through reference chain: com.deskoh.entities.Project["assets"])
	at app//com.fasterxml.jackson.databind.DatabindException.wrapWithPath(DatabindException.java:111)
	at app//com.fasterxml.jackson.databind.deser.bean.BeanDeserializerBase.wrapAndThrow(BeanDeserializerBase.java:1936)
	at app//com.fasterxml.jackson.databind.deser.bean.BeanDeserializer.deserializeFromObject(BeanDeserializer.java:654)
	at app//com.fasterxml.jackson.databind.deser.bean.BeanDeserializer.deserialize(BeanDeserializer.java:200)
	at app//com.fasterxml.jackson.databind.deser.DeserializationContextExt.readRootValue(DeserializationContextExt.java:266)
	at app//com.fasterxml.jackson.databind.ObjectMapper._readMapAndClose(ObjectMapper.java:2639)
	at app//com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:1622)
	at app//com.deskoh.entities.ProjectTest.given_project_can_deserialize(ProjectTest.java:24)
Caused by: java.lang.ClassCastException: class com.deskoh.entities.SourceSystem cannot be cast to class com.deskoh.entities.Project (com.deskoh.entities.SourceSystem and com.deskoh.entities.Project are in unnamed module of loader 'app')
	at com.deskoh.entities.AssetDeserializer.deserialize(AssetDeserializer.java:19)
	at com.deskoh.entities.AssetDeserializer.deserialize(AssetDeserializer.java:13)
	at com.fasterxml.jackson.databind.deser.impl.MethodProperty.deserializeAndSet(MethodProperty.java:120)
	at com.fasterxml.jackson.databind.deser.bean.BeanDeserializer.deserializeFromObject(BeanDeserializer.java:652)
	... 5 more
```

## Cause

The exception is thrown during custom deserialization.

```java
// src\main\java\com\deskoh\entities\AssetDeserializer.java
// `p.currentValue()` will evaluate to `SourceSystem` instead of `Project`
Project project = (Project) p.currentValue();
```

However the deserialization will work and unit test pass if

1. `org.springframework.boot` plugin is removed, OR

    > Note: Spring Boot 3 or Spring Boot 4 will cause the same deserialization issue

    ```diff
    plugins {
        id 'java'
    -   id 'org.springframework.boot' version '4.0.5'
    }
    ```

1.  `AllArgsConstructor` or `SourceSystem` is removed, OR

    > Note: Similar behaviour with lombok `@AllArgsConstructor` annotation

    ```diff
    public class SourceSystem {
    -   public SourceSystem(String id, String name) {
    -       this.id = id;
    -       this.name = name;
    -   }
    }
    ```

1. Jackson 2 is used (see jackson2 branch)
