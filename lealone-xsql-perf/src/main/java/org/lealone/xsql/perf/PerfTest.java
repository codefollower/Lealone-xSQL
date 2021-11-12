/*
 * Copyright Lealone Database Group.
 * Licensed under the Server Side Public License, v 1.
 * Initial Developer: zhh
 */
package org.lealone.xsql.perf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

//-XX:+UnlockExperimentalVMOptions -XX:+UseZGC -Xmx800M
public abstract class PerfTest {

    public static Connection getConnection(int port, String user, String password) throws Exception {
        String url = "jdbc:postgresql://localhost:" + port + "/test";
        return getConnection(url, user, password);
    }

    public static Connection getConnection(String url, String user, String password) throws Exception {
        Properties info = new Properties();
        info.put("user", user);
        info.put("password", password);
        return DriverManager.getConnection(url, info);
    }
}
