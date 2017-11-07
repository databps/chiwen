package com.databps.bigdaf.chiwen;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.security.User;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author merlin
 * @create 2017-09-11 下午6:53
 */
public class HbaseTest {


  private Admin admin;
  private HBaseAdmin hBaseAdmin;
  final private static String USER_NAME = "hbase";
  final private static String[] GROUP_NAMES = {"hadoop"};
  private String table1 = "user";
  private String table2 = "emp2";
  private String cf1 = "info";
  private String cf2 = "pwd2";

  private Connection connection;

  @Before
  public void beforeAllTests() throws IOException {
    System.setProperty("hadoop.home.dir", "/home/example");
    Configuration conf = HBaseConfiguration.create();

    conf.set("hbase.zookeeper.property.clientport", "2181");
    conf.set("hbase.zookeeper.quorum", "mot1,mot2,mot3");
    conf.set("zookeeper.znode.parent", "/hbase");
    User userGroupInfo =
        User.createUserForTesting(conf, USER_NAME, GROUP_NAMES);

    //hbase客户端代码有线程安全的问题，此测试没有验证这个问题，如果需要谨慎使用
    connection = ConnectionFactory.createConnection(conf, userGroupInfo);
    admin = connection.getAdmin();
    hBaseAdmin = new HBaseAdmin(connection);
  }

  @After
  public void afterAllTests() throws Exception {
  }

  /**
   * 创建表
   */
  @Test
  public void createTable() throws Exception {
    TableName tableName = TableName.valueOf(table2);
    HTableDescriptor tableDesc = new HTableDescriptor(tableName);
    HColumnDescriptor columnDesc = new HColumnDescriptor("info");
    tableDesc.addFamily(columnDesc);
    admin.createTable(tableDesc);

  }

  @Test
  public void listTables() throws Exception {

    HTableDescriptor[] tableDescriptor = admin.listTables();
    for (int i = 0; i < tableDescriptor.length; i++) {
      System.out.println(tableDescriptor[i].getNameAsString());
    }
  }

  @Test
  public void disableTable() throws Exception {
    hBaseAdmin.disableTable("emp");
  }

  @Test
  public void disable_all() throws Exception {
    admin.disableTables("user.*");

  }

  @Test
  public void isTableDisabled() throws Exception {
    TableName tableName = TableName.valueOf("user");
    System.out.println(admin.isTableDisabled(tableName));

  }

  @Test
  public void enableTable() throws Exception {
    TableName tableName = TableName.valueOf("user");
    admin.enableTable(tableName);

  }

  /**
   * delete之前 必须disable
   */
  @Test
  public void deleteTable() throws Exception {
    hBaseAdmin.deleteTable("emp");
  }

  /**
   * describe
   */
  @Test
  public void getTableDescriptor() throws Exception {
    TableName tableName = TableName.valueOf("user");
    HTableDescriptor tableDescripto = admin.getTableDescriptor(tableName);

    System.out.println(tableDescripto.toString());

  }

  /**
   * alter 't1', NAME ⇒ 'f1', VERSIONS ⇒ 5
   */
  @Test
  public void modifyColumn() throws IOException {
    HColumnDescriptor columnDesc = new HColumnDescriptor(cf1);
    columnDesc.setMaxVersions(5);
    hBaseAdmin.modifyColumn(table1, columnDesc);

  }

  /**
   * alter 't1', READONLY(option)
   */
  @Test
  public void modifyColumn2() throws IOException {
    HTableDescriptor tableDescriptor = new HTableDescriptor(table1);
    tableDescriptor.setReadOnly(true);
    hBaseAdmin.modifyTable(table1, tableDescriptor);

  }

  /**
   * scan 'employee'
   */
  @Test
  public void scan() throws IOException {
    TableName tableName = TableName.valueOf(table1);
    Table table = null;
    try {
      table = connection.getTable(tableName);
      Scan scan = new Scan();
      ResultScanner scanner = table.getScanner(scan);
      // Reading values from scan result
      for (Result result = scanner.next(); result != null; result = scanner.next()) {
        System.out.println("Found row : " + result);
      }

      scanner.close();

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        table.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Test
  public void addColumn() throws IOException {
    HColumnDescriptor columnDesc = new HColumnDescriptor(cf2);
    hBaseAdmin.addColumn(table1, columnDesc);

  }

  @Test
  public void deleteColumn() throws IOException {
    hBaseAdmin.deleteColumn(table1, "cf2");

  }

  /**
   * exists 'emp'
   */
  @Test
  public void tableExists() throws IOException {
    boolean bool = hBaseAdmin.tableExists(table1);
    System.out.println(bool);

  }

  /**
   * ./bin/stop-hbase.sh
   */
  @Test
  public void shutdown() throws IOException {
    hBaseAdmin.shutdown();
  }

  /**
   * put ’<table name>’,’row1’,’<colfamily:colname>’,’<value>’
   */
  @Test
  public void put() throws IOException {
    TableName tableName = TableName.valueOf(table2);
    Table table = connection.getTable(tableName);
    Put put = new Put(Bytes.toBytes("2"));
    put.addColumn(Bytes.toBytes(cf1), Bytes.toBytes("fn"), Bytes.toBytes("三"));
    put.addColumn(Bytes.toBytes(cf1), Bytes.toBytes("ln"), Bytes.toBytes("张"));
    put.addColumn(Bytes.toBytes(cf2), Bytes.toBytes("pw"), Bytes.toBytes("222"));
    table.put(put);
    table.close();
  }

  private static final Log LOG = LogFactory.getLog(HbaseTest.class);


  /**
   * 流程 1.创建表 emp,personal data:name,personal data:city;professional data:designation,professional
   * data:salary 2.添加数据 3.查看结果scan
   */
  @Test
  public void comprehensive() {
    String personalCFName = "personal data";
    String professionalCFName = "professional data";
    TableName tableName = TableName.valueOf(table2);
    HTableDescriptor tableDesc = new HTableDescriptor(tableName);
    HColumnDescriptor personalCF = new HColumnDescriptor(personalCFName);
    HColumnDescriptor professionalCF = new HColumnDescriptor(professionalCFName);
    tableDesc.addFamily(personalCF);
    tableDesc.addFamily(professionalCF);
    boolean isCreated = true;
    try {
      hBaseAdmin.createTable(tableDesc);
    } catch (IOException e) {
      e.printStackTrace();
      isCreated = false;
    }
    if (isCreated) {
      Table table = null;
      try {
        table = connection.getTable(tableName);
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (table != null) {
        Put put1 = new Put(Bytes.toBytes("1"));
        put1.addColumn(Bytes.toBytes(personalCFName), Bytes.toBytes("name"), Bytes.toBytes("raju"));
        put1.addColumn(Bytes.toBytes(personalCFName), Bytes.toBytes("city"),
            Bytes.toBytes("hyderabad"));
        put1.addColumn(Bytes.toBytes(professionalCFName), Bytes.toBytes("designation"),
            Bytes.toBytes("manager"));
        put1.addColumn(Bytes.toBytes(professionalCFName), Bytes.toBytes("salary"),
            Bytes.toBytes("50000"));

        Put put2 = new Put(Bytes.toBytes("2"));
        put2.addColumn(Bytes.toBytes(personalCFName), Bytes.toBytes("name"), Bytes.toBytes("ravi"));
        put2.addColumn(Bytes.toBytes(personalCFName), Bytes.toBytes("city"),
            Bytes.toBytes("chennai"));
        put2.addColumn(Bytes.toBytes(professionalCFName), Bytes.toBytes("designation"),
            Bytes.toBytes("sr.engineer"));
        put2.addColumn(Bytes.toBytes(professionalCFName), Bytes.toBytes("salary"),
            Bytes.toBytes("300000"));

        Put put3 = new Put(Bytes.toBytes("3"));
        put3.addColumn(Bytes.toBytes(personalCFName), Bytes.toBytes("name"),
            Bytes.toBytes("rajesh"));
        put3.addColumn(Bytes.toBytes(personalCFName), Bytes.toBytes("city"),
            Bytes.toBytes("delhi"));
        put3.addColumn(Bytes.toBytes(professionalCFName), Bytes.toBytes("designation"),
            Bytes.toBytes("jr.engineer"));
        put3.addColumn(Bytes.toBytes(professionalCFName), Bytes.toBytes("salary"),
            Bytes.toBytes("25000"));
        boolean isInserted = true;
        try {
          table.put(put1);
          table.put(put2);
          table.put(put3);

        } catch (IOException e) {
          isInserted = false;
          e.printStackTrace();
        }
        if (isInserted) {
          Assert.assertTrue(isCreated);
          Scan scan = new Scan();
          scan.addFamily(Bytes.toBytes(personalCFName));
          ResultScanner resultScanner = null;
          try {
            resultScanner = table.getScanner(scan);

            for (Iterator<Result> it = resultScanner.iterator(); it.hasNext(); ) {
              Result result = it.next();
              List<Cell> cells = result.listCells();
              for (Cell cell : cells) {
                String qualifier = new String(CellUtil.cloneQualifier(cell));
                String value = new String(CellUtil.cloneValue(cell), "UTF-8");
                LOG.info(qualifier + "\t" + value);
              }
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }

      }

    }
  }

  /**
   * put ‘table name’,’row ’,'Column family:column name',’new value’
   */
  @Test
  public void updateCell() {
    String personalCFName = "personal data";

    TableName tableName = TableName.valueOf(table2);
    Table table = null;
    try {
      table = connection.getTable(tableName);

      Put put1 = new Put(Bytes.toBytes("1"));
      put1.addColumn(Bytes.toBytes(personalCFName), Bytes.toBytes("name"),
          Bytes.toBytes("xiaoming"));
      table.put(put1);

      Scan scan = new Scan();
      scan.addFamily(Bytes.toBytes(personalCFName));
      ResultScanner resultScanner = table.getScanner(scan);
      for (Iterator<Result> it = resultScanner.iterator(); it.hasNext(); ) {
        Result result = it.next();
        List<Cell> cells = result.listCells();
        for (Cell cell : cells) {
          String qualifier = new String(CellUtil.cloneQualifier(cell));
          String value = new String(CellUtil.cloneValue(cell), "UTF-8");
          LOG.info(qualifier + "\t" + value);
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        table.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * get ’<table name>’,’row1’
   */
  @Test
  public void getByRowKey() {

    TableName tableName = TableName.valueOf(table2);
    Table table = null;
    try {
      table = connection.getTable(tableName);
      Get get = new Get(Bytes.toBytes("1"));
      Result result = table.get(get);
      List<Cell> cells = result.listCells();
      for (Cell cell : cells) {
        String qualifier = new String(CellUtil.cloneQualifier(cell));
        String value = new String(CellUtil.cloneValue(cell), "UTF-8");
        LOG.info(qualifier + "\t" + value);
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        table.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * get 'table name', ‘rowid’, {COLUMN ⇒ ‘column family:column name ’}
   */
  @Test
  public void getSpecificColumnByRowKey() {

    TableName tableName = TableName.valueOf(table2);
    Table table = null;
    try {
      table = connection.getTable(tableName);
      Get get = new Get(Bytes.toBytes("1"));
      get.addColumn(Bytes.toBytes("personal data"), Bytes.toBytes("name"));
      Result result = table.get(get);
      List<Cell> cells = result.listCells();

      //byte[] value = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("name"));
      //byte[] value1 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("city"));

      for (Cell cell : cells) {
        String qualifier = new String(CellUtil.cloneQualifier(cell));
        String value = new String(CellUtil.cloneValue(cell), "UTF-8");
        LOG.info(qualifier + "\t" + value);
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        table.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * delete ‘<table name>’, ‘<row>’, ‘<column name >’, ‘<time stamp>’
   */
  @Test
  public void deleteCell() {

    TableName tableName = TableName.valueOf(table2);
    Table table = null;
    try {
      table = connection.getTable(tableName);
      Delete delete = new Delete(Bytes.toBytes("1"));
      delete.addColumn(Bytes.toBytes("personal data"), Bytes.toBytes("name"));
      //delete.addFamily(Bytes.toBytes("personal data"));
      table.delete(delete);
      Get get = new Get(Bytes.toBytes("1"));
      Result result = table.get(get);
      List<Cell> cells = result.listCells();
      for (Cell cell : cells) {
        String qualifier = new String(CellUtil.cloneQualifier(cell));
        String value = new String(CellUtil.cloneValue(cell), "UTF-8");
        LOG.info(qualifier + "\t" + value);
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        table.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * count ‘<table name>’ count的行数
   */
  @Test
  public void count() {

    TableName tableName = TableName.valueOf(table2);
    Table table = null;
    try {
      table = connection.getTable(tableName);
      Scan scan = new Scan();
      ResultScanner scanner = table.getScanner(scan);

      System.out.println( scanner.next().size());

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        table.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Truncate the table by removing META and the HDFS files and recreating it.
   * drops and recreates a table
   * truncate 'table name'
   * If the 'preserveSplits' option is set to true, the region splits are preserved on recreate.
   */
  @Test
  public void truncate() {
    TableName tableName = TableName.valueOf(table2);
    try {
      //hBaseAdmin.disableTable(tableName);
      hBaseAdmin.truncateTable(tableName,true);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
