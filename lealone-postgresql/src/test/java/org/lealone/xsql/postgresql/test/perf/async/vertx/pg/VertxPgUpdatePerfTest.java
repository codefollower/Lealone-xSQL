/*
 * Copyright Lealone Database Group.
 * Licensed under the Server Side Public License, v 1.
 * Initial Developer: zhh
 */
package org.lealone.xsql.postgresql.test.perf.async.vertx.pg;

public class VertxPgUpdatePerfTest extends VertxPgPerfTest {

    // 跑不起来，vertx有bug
    public static void main(String[] args) throws Throwable {
        String sql = "update test set f1=2 where name='abc1'";
        run("VertxPgUpdate", sql);
    }
}
