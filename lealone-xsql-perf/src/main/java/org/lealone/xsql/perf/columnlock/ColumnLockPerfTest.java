/*
 * Copyright Lealone Database Group.
 * Licensed under the Server Side Public License, v 1.
 * Initial Developer: zhh
 */
package org.lealone.xsql.perf.columnlock;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.lealone.xsql.postgresql.test.perf.PerfTest;

public abstract class ColumnLockPerfTest extends PerfTest {

    private int count = 1000;
    private int columnCount = 99;
    private String[] sqls = new String[columnCount];

    public void run() throws Exception {
        init();
        run(4);
    }

    public abstract Connection getConnection() throws Exception;

    public static Connection getConnection(int port) throws Exception {
        String url = "jdbc:postgresql://localhost:" + port + "/test";
        return getConnection(url, "test", "test");
    }

    private String getName() {
        return getClass().getSimpleName();
    }

    private void init() throws Exception {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        statement.executeUpdate("drop table if exists ColumnLockPerfTest");

        StringBuilder buff = new StringBuilder();
        buff.append("create table if not exists ColumnLockPerfTest(pk int primary key");
        for (int i = 1; i <= columnCount; i++) {
            buff.append(",f").append(i).append(" int");
        }
        buff.append(")");
        statement.executeUpdate(buff.toString());

        for (int row = 1; row <= 9; row++) {
            buff = new StringBuilder();
            buff.append("insert into ColumnLockPerfTest values(").append(row);
            for (int i = 1; i <= columnCount; i++) {
                buff.append(",").append(i * 10);
            }
            buff.append(")");
            statement.executeUpdate(buff.toString());
        }
        for (int i = 1; i <= columnCount; i++) {
            buff = new StringBuilder();
            buff.append("update ColumnLockPerfTest set f").append(i).append(" = ").append(i * 100)
                    .append(" where pk=5");
            sqls[i - 1] = buff.toString();
        }
        close(statement, conn);
    }

    private void run(int threadCount) throws Exception {
        UpdateThread[] threads = new UpdateThread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            Connection conn = getConnection();
            threads[i] = new UpdateThread(i, conn);
        }
        for (int i = 0; i < threadCount; i++) {
            threads[i].warmUp();
        }
        for (int i = 0; i < threadCount; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threadCount; i++) {
            threads[i].join();
        }
    }

    private class UpdateThread extends Thread {
        Connection conn;
        Statement stmt;
        String sql;

        UpdateThread(int id, Connection conn) throws Exception {
            super("UpdateThread-" + id);
            this.conn = conn;
            this.stmt = conn.createStatement();
            this.sql = sqls[id];
        }

        @Override
        public void run() {
            try {
                executeUpdate(stmt, sql);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(stmt, conn);
            }
        }

        public void warmUp() throws Exception {
            for (int i = 0; i < count * 2; i++)
                stmt.executeUpdate(sql);
        }
    }

    private void executeUpdate(Statement statement, String sql) throws Exception {
        int loop = 20;
        for (int j = 0; j < loop; j++) {
            long t1 = System.nanoTime();
            for (int i = 0; i < count; i++)
                statement.executeUpdate(sql);
            long t2 = System.nanoTime();
            System.out.println(getName() + ": " + TimeUnit.NANOSECONDS.toMicros(t2 - t1) / count);
        }
        System.out.println();
        System.out.println("time: 微秒");
        System.out.println("loop: " + loop + " * " + count);
        System.out.println("sql : " + sql);
    }

    private static void close(Statement stmt, Connection conn) {
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
