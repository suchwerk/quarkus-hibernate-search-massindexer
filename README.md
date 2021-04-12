# Test Massindexer

## Start a local elasticsearch

```shell script
docker run -it --rm=true --name elasticsearch_quarkus_test -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.5.0
```

## Run the test

A postgresql container is created automatically.

Start the test:

```shell script
./mvnw test
```

## Result

First a bunch of  FruitEntities will be created.
Then the rest endpoint for reindexing it called.

```
.....
Caused by: java.sql.SQLException: Connection is closed
        at io.agroal.pool.wrapper.ConnectionWrapper.lambda$static$0(ConnectionWrapper.java:51)
        at com.sun.proxy.$Proxy92.prepareStatement(Unknown Source)
        at io.agroal.pool.wrapper.ConnectionWrapper.prepareStatement(ConnectionWrapper.java:616)
        at org.hibernate.engine.jdbc.internal.StatementPreparerImpl$5.doPrepare(StatementPreparerImpl.java:149)
        at org.hibernate.engine.jdbc.internal.StatementPreparerImpl$StatementPreparationTemplate.prepareStatement(StatementPreparerImpl.java:176)
        ... 27 more

2021-04-12 19:19:02,977 INFO  [org.hib.sea.map.orm.mas.imp.LoggingMassIndexingMonitor] (executor-thread-1) HSEARCH000028: Mass indexing complete. Indexed 8000 entities.
```

### In the index

Indexed in es: 1200 (thats the amount of the first batch process (multiplied by no of threads)

```
➜ [qhsm] git:(master) curl 'localhost:9200/_cat/indices?v'
health status index              uuid                   pri rep docs.count docs.deleted store.size pri.store.size
yellow open   fruitentity-000001 YCOM7soBQDeiRCEaoRUOSA   1   1       1200        11813        1mb            1mb
```
