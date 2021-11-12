/*
 * Copyright Lealone Database Group.
 * Licensed under the Server Side Public License, v 1.
 * Initial Developer: zhh
 */
package org.lealone.xsql.perf.columnlock;

import java.sql.Connection;

public class PgColumnLockPerfTest extends ColumnLockPerfTest {

    public static void main(String[] args) throws Throwable {
        new PgColumnLockPerfTest().run();
    }

    @Override
    public Connection getConnection() throws Exception {
        return getConnection(5432);
    }
}
