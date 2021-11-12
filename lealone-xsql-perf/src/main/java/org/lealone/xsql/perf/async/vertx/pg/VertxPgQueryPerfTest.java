/*
 * Copyright Lealone Database Group.
 * Licensed under the Server Side Public License, v 1.
 * Initial Developer: zhh
 */
package org.lealone.xsql.perf.async.vertx.pg;

import org.lealone.xsql.postgresql.test.perf.async.vertx.pg.VertxPgPerfTest;

public class VertxPgQueryPerfTest extends VertxPgPerfTest {

    public static void main(String[] args) throws Throwable {
        String sql = "select count(*) from test where f1+f2>1";
        run("VertxPgQuery", sql);
    }
}
