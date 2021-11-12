/*
 * Copyright Lealone Database Group.
 * Licensed under the Server Side Public License, v 1.
 * Initial Developer: zhh
 */
package org.lealone.xsql.perf.columnlock;

import java.sql.Connection;

public class H2ColumnLockPerfTest extends ColumnLockPerfTest {

    public static void main(String[] args) throws Throwable {
        new H2ColumnLockPerfTest().run();
    }

    @Override
    public Connection getConnection() throws Exception {
        return getConnection(9511);
    }
}
