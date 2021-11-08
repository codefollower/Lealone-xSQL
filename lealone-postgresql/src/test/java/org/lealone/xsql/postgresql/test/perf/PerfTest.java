/*
 * Copyright Lealone Database Group.
 * Licensed under the Server Side Public License, v 1.
 * Initial Developer: zhh
 */
package org.lealone.xsql.postgresql.test.perf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

//-XX:+UnlockExperimentalVMOptions -XX:+UseZGC -Xmx800M
public abstract class PerfTest {

    public static Connection getConnection(int port, String user, String password) throws Exception {
        String url = "jdbc:postgresql://localhost:" + port + "/test";
        Properties info = new Properties();
        info.put("user", user);
        info.put("password", password);
        return DriverManager.getConnection(url, info);
    }

    public static void run(String name, Statement statement) throws Throwable {
        String sql = "select count(*) from test where f1+f2>1";
        int count = 1000;
        for (int i = 0; i < count * 5; i++)
            statement.executeQuery(sql);

        int loop = 20;
        for (int j = 0; j < loop; j++) {
            long t1 = System.nanoTime();
            for (int i = 0; i < count; i++)
                statement.executeQuery(sql);
            long t2 = System.nanoTime();
            System.out.println(name + ": " + TimeUnit.NANOSECONDS.toMicros(t2 - t1) / count);
        }
        System.out.println();
        System.out.println("time: 微秒");
        System.out.println("loop: " + loop + " * " + count);
        System.out.println("sql : " + sql);
    }
}
