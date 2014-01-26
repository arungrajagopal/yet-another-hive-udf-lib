# Hive UDF Examples

As the name suggests, this is yet another Hive UDF library. Implementing algorithms that don't already exist in HiveSwarm, brickhouse etc.

## Compile

```
mvn compile
```

## Test

```
mvn test
```

## Build
```
mvn assembly:single
```

## Run

```
%> hive
hive> ADD JAR target/NAME_OF_ASSEMBLED.jar;
hive> create temporary function ldistance as 'com.infinitescaling.Levenshtein';
hive> select ldistance(full_name, first_name) from people limit 10;

```

## Credits
-> [This great article walks through creating java UDFs in Hive][blog-post].

[blog-post]:http://blog.matthewrathbone.com/2013/08/10/guide-to-writing-hive-udfs.html
