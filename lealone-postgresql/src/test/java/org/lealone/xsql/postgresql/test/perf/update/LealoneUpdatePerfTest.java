/*
 * Copyright Lealone Database Group.
 * Licensed under the Server Side Public License, v 1.
 * Initial Developer: zhh
 */
package org.lealone.xsql.postgresql.test.perf.update;

import java.sql.Connection;
import java.sql.Statement;

import org.lealone.xsql.postgresql.server.PgServer;

public class LealoneUpdatePerfTest extends UpdatePerfTest {

    public static void main(String[] args) throws Throwable {
        Connection conn = getConnection(PgServer.DEFAULT_PORT, "test", "test");
        Statement statement = conn.createStatement();
        statement.executeUpdate("set QUERY_CACHE_SIZE 0;");

        UpdatePerfTest.run("LealoneUpdate", statement);
        statement.close();
        conn.close();
    }
}
