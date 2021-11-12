/*
 * Copyright Lealone Database Group.
 * Licensed under the Server Side Public License, v 1.
 * Initial Developer: zhh
 */
package org.lealone.xsql.perf.update;

import java.sql.Connection;
import java.sql.Statement;

import org.lealone.db.Constants;
import org.lealone.xsql.postgresql.test.perf.update.UpdatePerfTest;

public class SyncLealoneUpdatePerfTest extends UpdatePerfTest {

    public static void main(String[] args) throws Throwable {
        String url = "jdbc:lealone:tcp://localhost:" + Constants.DEFAULT_TCP_PORT + "/lealone";
        Connection conn = getConnection(url, "root", "");
        Statement statement = conn.createStatement();
        statement.executeUpdate("set QUERY_CACHE_SIZE 0;");

        run("SyncLealoneUpdate", statement);
        statement.close();
        conn.close();
    }
}
