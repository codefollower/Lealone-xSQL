/*
 * Copyright Lealone Database Group.
 * Licensed under the Server Side Public License, v 1.
 * Initial Developer: zhh
 */
package org.lealone.xsql.postgresql.test.perf.async.vertx.lealone;

public class VertxLealoneUpdatePerfTest extends VertxLealonePerfTest {

    public static void main(String[] args) throws Throwable {
        String sql = "update test set f1=2 where name='abc1'";
        run("VertxLealoneUpdate", sql);
    }
}
