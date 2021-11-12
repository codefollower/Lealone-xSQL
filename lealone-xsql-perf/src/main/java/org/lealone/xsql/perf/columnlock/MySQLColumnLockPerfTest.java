/*
 * Copyright Lealone Database Group.
 * Licensed under the Server Side Public License, v 1.
 * Initial Developer: zhh
 */
package org.lealone.xsql.perf.columnlock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class MySQLColumnLockPerfTest extends ColumnLockPerfTest {

    public static void main(String[] args) throws Throwable {
        new MySQLColumnLockPerfTest().run();
    }

    @Override
    public Connection getConnection() throws Exception {
        String db = "test";
        String user = "test";
        String password = "test";
        int port = 3306;
        String url = "jdbc:mysql://localhost:" + port + "/" + db;

        Properties info = new Properties();
        info.put("user", user);
        info.put("password", password);
        // info.put("holdResultsOpenOverStatementClose","true");
        // info.put("allowMultiQueries","true");

        // info.put("useServerPrepStmts", "true");
        // info.put("cachePrepStmts", "true");
        info.put("rewriteBatchedStatements", "true");
        info.put("useCompression", "true");
        info.put("serverTimezone", "GMT");

        Connection conn = DriverManager.getConnection(url, info);
        // conn.setAutoCommit(true);
        return conn;
    }
}
