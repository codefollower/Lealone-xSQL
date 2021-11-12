/*
 * Copyright Lealone Database Group.
 * Licensed under the Server Side Public License, v 1.
 * Initial Developer: zhh
 */
package org.lealone.xsql.perf.update;

import java.sql.Connection;
import java.sql.Statement;

import org.lealone.xsql.postgresql.test.perf.update.UpdatePerfTest;

public class H2UpdatePerfTest extends UpdatePerfTest {

    public static void main(String[] args) throws Throwable {
        Connection conn = getConnection(9511, "test", "test");
        Statement statement = conn.createStatement();

        UpdatePerfTest.run("H2Update", statement);
        statement.close();
        conn.close();
    }
}
