/*
 * Copyright Lealone Database Group.
 * Licensed under the Server Side Public License, v 1.
 * Initial Developer: zhh
 */
package org.lealone.xsql.postgresql.test.perf.async.vertx.lealone;

import org.lealone.xsql.postgresql.server.PgServer;
import org.lealone.xsql.postgresql.test.perf.async.vertx.VertxPerfTest;

import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlClient;

public abstract class VertxLealonePerfTest extends VertxPerfTest {

    public static void run(String name, String sql) throws Throwable {
        PgConnectOptions connectOptions = new PgConnectOptions();
        connectOptions.setPort(PgServer.DEFAULT_PORT).setHost("localhost");
        connectOptions.setDatabase("test").setUser("test").setPassword("test");

        PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
        SqlClient client = PgPool.client(connectOptions, poolOptions);
        client.query("set QUERY_CACHE_SIZE 0").execute(ar -> {
        });
        run(client, name, sql);
        client.close();
    }
}
