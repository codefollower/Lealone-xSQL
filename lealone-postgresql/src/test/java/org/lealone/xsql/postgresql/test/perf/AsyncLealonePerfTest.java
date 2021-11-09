/*
 * Copyright Lealone Database Group.
 * Licensed under the Server Side Public License, v 1.
 * Initial Developer: zhh
 */
package org.lealone.xsql.postgresql.test.perf;

import java.sql.Connection;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.lealone.client.jdbc.JdbcStatement;
import org.lealone.db.Constants;

public class AsyncLealonePerfTest extends PerfTest {

    public static void main(String[] args) throws Throwable {
        String url = "jdbc:lealone:tcp://localhost:" + Constants.DEFAULT_TCP_PORT + "/lealone";
        Connection conn = getConnection(url, "root", "");
        Statement statement = conn.createStatement();
        statement.executeUpdate("set QUERY_CACHE_SIZE 0;");

        // PerfTest.run("AsyncLealone", statement);
        run("AsyncLealone", statement);
        statement.close();
        conn.close();
    }

    public static void run(String name, Statement statement) throws Throwable {
        String sql = "select count(*) from test where f1+f2>1";
        int count = 1000;
        for (int i = 0; i < count * 5; i++)
            statement.executeQuery(sql);

        JdbcStatement stmt = (JdbcStatement) statement;
        int loop = 20;
        for (int j = 0; j < loop; j++) {
            CountDownLatch latch = new CountDownLatch(count);
            long t1 = System.nanoTime();
            for (int i = 0; i < count; i++)
                stmt.executeQueryAsync(sql).onComplete(ar -> {
                    latch.countDown();
                });
            latch.await();
            long t2 = System.nanoTime();
            System.out.println(name + ": " + TimeUnit.NANOSECONDS.toMicros(t2 - t1) / count);
        }
        System.out.println();
        System.out.println("time: 微秒");
        System.out.println("loop: " + loop + " * " + count);
        System.out.println("sql : " + sql);
    }
}
