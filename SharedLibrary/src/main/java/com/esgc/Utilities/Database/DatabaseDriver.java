package com.esgc.Utilities.Database;

import com.esgc.Utilities.Environment;
import lombok.Data;

import java.sql.*;
import java.util.*;
import java.util.function.Supplier;

@Data
public class DatabaseDriver {

    //DB Credentials
    public static final String DB_HOST = Environment.DB_HOST;
    public static final String DB_USERNAME = Environment.DB_USERNAME;
    public static final String DB_PASSWORD = Environment.DB_PASSWORD;
    public static final String DB_WAREHOUSE = Environment.DB_WAREHOUSE;
    public static final String DB_DATABASE = Environment.DB_DATABASE;
    public static final String DB_SCHEMA = Environment.DB_SCHEMA;
    public static final String DB_ROLE = Environment.DB_ROLE;

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;
    private static ResultSetMetaData resultSetMetaData;

    //properties for db connection with JDBC driver
    private static final Properties props = ((Supplier<Properties>) () -> {
        Properties props = new Properties();
        props.put("db", DB_DATABASE);
        props.put("user", DB_USERNAME);
        props.put("password", DB_PASSWORD);
        props.put("warehouse", DB_WAREHOUSE);
        return props;
    }).get();

    public static void createDBConnection() {

        System.out.println("CONNECTING TO DEFAULT DATABASE...");
        Properties props = new Properties();
        props.put("db", DB_DATABASE);
        props.put("user", DB_USERNAME);
        props.put("password", DB_PASSWORD);
        props.put("warehouse", DB_WAREHOUSE);
        props.put("schema", DB_SCHEMA);
        props.put("role", DB_ROLE);
        try {
            connection = DriverManager.getConnection(DB_HOST, props);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("DATABASE CONNECTION SUCCESSFUL!");

    }

    /**
     * @param query
     * @return returns a single cell value. If the results in multiple rows and/or
     * columns of data, only first column of the first row will be returned.
     * The rest of the data will be ignored
     */
    public static Object getCellValue(String query) {
        return getQueryResultList(query).get(0).get(0);
    }

    /**
     * @param query
     * @return returns a list of Strings which represent a row of data. If the query
     * results in multiple rows and/or columns of data, only first row will
     * be returned. The rest of the data will be ignored
     */
    public static List<Object> getRowList(String query) {
        return getQueryResultList(query).get(0);
    }

    /**
     * @param query
     * @return returns a map which represent a row of data where key is the column
     * name. If the query results in multiple rows and/or columns of data,
     * only first row will be returned. The rest of the data will be ignored
     */
    public static Map<String, Object> getRowMap(String query) {
        return getQueryResultMap(query).get(0);
    }

    /**
     * @param query
     * @return returns query result in a list of lists where outer list represents
     * collection of rows and inner lists represent a single row
     */
    public static List<List<Object>> getQueryResultList(String query) {
        executeQuery(query);
        List<List<Object>> rowList = new ArrayList<>();
        ResultSetMetaData rsmd;
        try {
            rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                List<Object> row = new ArrayList<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    row.add(resultSet.getObject(i));
                }
                rowList.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowList;
    }

    /**
     * @param query
     * @param column
     * @return list of values of a single column from the result set
     */
    public static List<Object> getColumnData(String query, String column) {
        executeQuery(query);
        List<Object> rowList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                rowList.add(resultSet.getObject(column));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowList;
    }

    /**
     * @param query
     * @return returns query result in a list of maps where the list represents
     * collection of rows and a map represents represent a single row with
     * key being the column name
     */
    public synchronized static List<Map<String, Object>> getQueryResultMap(String query) {
        executeQuery(query);
        List<Map<String, Object>> rowList = new ArrayList<>();
        ResultSetMetaData rsmd;
        try {
            rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                Map<String, Object> colNameValueMap = new HashMap<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    colNameValueMap.put(rsmd.getColumnName(i), resultSet.getObject(i));
                }
                rowList.add(colNameValueMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowList;
    }


    public synchronized static List<String> getQueryResultListMap(String query) {
        executeQuery(query);
        List<String> colNameValueList = new ArrayList<>();
        ;
        try {
         //   rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                colNameValueList.add((String) resultSet.getObject("RELATED_DOMAIN"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colNameValueList;
    }

    public synchronized static List<String> getQueryResultListUnderlyingData(String query) {
        executeQuery(query);
        List<String> colNameValueList = new ArrayList<>();

        /**
         * When getting a date field from snowflake, try to use resultSet.getDate
         * when you use getObject, then you may get different date due to difference between
         * JVM local date snowflake local date.
         */
        try {
            while (resultSet.next()) {
                colNameValueList.add((resultSet.getDate("UPDATEDATE")).toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colNameValueList;
    }

    public synchronized static List<String> getQueryResultListUDGreenShare(String query) {
        executeQuery(query);
        List<String> colNameValueList = new ArrayList<>();

        /**
         * When getting a date field from snowflake, try to use resultSet.getDate
         * when you use getObject, then you may get different date due to difference between
         * JVM local date snowflake local date.
         */
        try {
            while (resultSet.next()) {
                colNameValueList.add((resultSet.getObject("SOURCE")).toString());
                colNameValueList.add((resultSet.getObject("MAIN")).toString());
                colNameValueList.add((resultSet.getObject("SCALE")).toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colNameValueList;
    }

    public synchronized static List<String> getQueryResultListUDBrownShare(String query) {
        executeQuery(query);
        List<String> colNameValueList = new ArrayList<>();

        /**
         * When getting a date field from snowflake, try to use resultSet.getDate
         * when you use getObject, then you may get different date due to difference between
         * JVM local date snowflake local date.
         */
        try {
            while (resultSet.next()) {
                colNameValueList.add((resultSet.getObject("THRESHOLD")).toString());
                colNameValueList.add((resultSet.getObject("SOURCE")).toString());
                colNameValueList.add((resultSet.getObject("SERVICES")).toString());
                colNameValueList.add((resultSet.getObject("METHANE_HYDRATES")).toString());
                colNameValueList.add((resultSet.getObject("LIQUEFIED_NATURAL_GAS")).toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colNameValueList;
    }

    public synchronized static List<String> getQueryResultListUnderlyingDataTempAligment(String query) {
        executeQuery(query);
        List<String> colNameValueList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                colNameValueList.add((resultSet.getObject("UPDATEDATE")).toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colNameValueList;
    }


    public synchronized static List<String> getQueryResultLisDisclosure(String query) {
        executeQuery(query);
        List<String> colNameValueList = new ArrayList<>();
        ;
        try {
            //   rsmd = resultSet.getMetaData();
            while (resultSet.next()) {
                colNameValueList.add((String) resultSet.getObject("DISCLOSURE_RATE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colNameValueList;
    }

    public synchronized static List<String> getQueryResultListESGScores(String query) {
        executeQuery(query);
        List<String> colNameValueList = new ArrayList<>();
        ;
        try {
            while (resultSet.next()) {
                colNameValueList.add((String) resultSet.getObject("VALUE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return colNameValueList;
    }

    /**
     * @param query
     * @return List of columns returned in result set
     */
    public static List<String> getColumnNames(String query) {
        executeQuery(query);
        List<String> columns = new ArrayList<>();
        ResultSetMetaData rsmd;
        try {
            rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                columns.add(rsmd.getColumnName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columns;
    }

    public static void executeQuery(String query) {
        try {
            statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            statement.executeQuery("ALTER SESSION SET JDBC_QUERY_RESULT_FORMAT='JSON'");
            resultSet = statement.executeQuery(query);

        } catch (SQLException e) {
            System.out.println("ERROR OCCURRED WHILE RUNNING THE QUERY!");
            System.out.println("query = " + query);
            e.printStackTrace();
        }
    }

    public static int getRowCount() throws Exception {
        resultSet.last();
        int rowCount = resultSet.getRow();
        return rowCount;
    }

}
