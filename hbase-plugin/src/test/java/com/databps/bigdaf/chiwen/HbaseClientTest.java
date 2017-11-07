package com.databps.bigdaf.chiwen;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

/**
 * @author shibingxin
 * @create 2017-09-14 上午10:19
 */
public class HbaseClientTest {

  private static String SERIES = "s";

  private static String TABLENAME = "AF_TABLE7";

  private static Connection conn;

  private static String hbaseIp = "p1";

//  private static String hbaseIp = "sbx1,sbx2,sbx3";

  @Before
  public void init() throws IOException {

    Configuration config = HBaseConfiguration.create();
    config.set("hbase.zookeeper.quorum", hbaseIp);
    config.set("hbase.zookeeper.property.clientport", "2181");
    System.setProperty("HADOOP_USER_NAME", "root");
    try {
      conn = ConnectionFactory.createConnection(config);
      createTable(TABLENAME, SERIES);
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      //conn.close();
    }

  }

  //创建表

  public void createTable(String tableName, String seriesStr)
      throws IllegalArgumentException, IOException {

    Admin admin = null;

    TableName table = TableName.valueOf(tableName);

    try {

      admin = conn.getAdmin();

      if (!admin.tableExists(table)) {

        System.out.println(tableName + " table not Exists");

        HTableDescriptor descriptor = new HTableDescriptor(table);

        String[] series = seriesStr.split(",");

        for (String s : series) {

          descriptor.addFamily(new HColumnDescriptor(s.getBytes()));

        }

        admin.createTable(descriptor);

      }

    } finally {

      IOUtils.closeQuietly(admin);

    }

  }

  //添加数据32295

  public void add(String rowKey, Map<String, Object> columns) throws IOException {

    Table table = null;

    try {

      table = conn.getTable(TableName.valueOf(TABLENAME));

      Put put = new Put(Bytes.toBytes(rowKey));

      for (Map.Entry<String, Object> entry : columns.entrySet()) {

        put.addColumn(SERIES.getBytes(), Bytes.toBytes(entry.getKey()), new byte[11]);

      }

      table.put(put);

    } finally {

      IOUtils.closeQuietly(table);

    }

  }

  //根据rowkey获取数据

  public Map<String, String> getAllValue(String rowKey)
      throws IllegalArgumentException, IOException {

    Table table = null;

    Map<String, String> resultMap = null;

    try {

      table = conn.getTable(TableName.valueOf(TABLENAME));

      Get get = new Get(Bytes.toBytes(rowKey));

      get.addFamily(SERIES.getBytes());

      Result res = table.get(get);

      Map<byte[], byte[]> result = res.getFamilyMap(SERIES.getBytes());

      Iterator<Entry<byte[], byte[]>> it = result.entrySet().iterator();

      resultMap = new HashMap<String, String>();

      while (it.hasNext()) {

        Entry<byte[], byte[]> entry = it.next();

        resultMap.put(Bytes.toString(entry.getKey()), Bytes.toString(entry.getValue()));

      }

    } finally {

      IOUtils.closeQuietly(table);

    }

    return resultMap;

  }

  //根据rowkey和column获取数据

  public String getValueBySeries(String rowKey, String column)
      throws IllegalArgumentException, IOException {

    Table table = null;

    String resultStr = null;

    try {

      table = conn.getTable(TableName.valueOf(TABLENAME));

      Get get = new Get(Bytes.toBytes(rowKey));

      get.addColumn(Bytes.toBytes(SERIES), Bytes.toBytes(column));

      Result res = table.get(get);

      byte[] result = res.getValue(Bytes.toBytes(SERIES), Bytes.toBytes(column));

      resultStr = Bytes.toString(result);

    } finally {

      IOUtils.closeQuietly(table);

    }

    return resultStr;

  }

  //根据table查询所有数据

  public void getValueByTable() throws Exception {

    Map<String, String> resultMap = null;

    Table table = null;

    try {

      table = conn.getTable(TableName.valueOf(TABLENAME));

      ResultScanner rs = table.getScanner(new Scan());

      for (Result r : rs) {

        System.out.println("获得到rowkey:" + new String(r.getRow()));

        for (KeyValue keyValue : r.raw()) {

          System.out.println(

              "列：" + new String(keyValue.getFamily()) + "====值:" + new String(keyValue.getValue()));

        }

      }

    } finally {

      IOUtils.closeQuietly(table);

    }

  }

  //删除表

  public  void dropTable(String tableName) throws IOException {

    Admin admin = null;

    TableName table = TableName.valueOf(tableName);

    try {

      admin = conn.getAdmin();

      if (admin.tableExists(table)) {

        admin.disableTable(table);

        admin.deleteTable(table);

      }

    } finally {

      IOUtils.closeQuietly(admin);

    }

  }
  @Test
  public void testhbase() throws Exception {
//    init();
    //创建表
    createTable(TABLENAME, "");
    //添加数据1
    String rowKey1 = "u1001c1001";
    Map<String, Object> columns = new HashMap<String, Object>();
    columns.put("original_data", "original_data_u1001c1001_1");
    columns.put("original_data", "original_data_u1001c1001_2");
    add(rowKey1, columns);
    //添加数据2
    String rowKey2 = "u1001c1001";
    Map<String, Object> columns2 = new HashMap<String, Object>();
    columns2.put("original_data", "original_data_u1001c1002_1");
    columns2.put("original_data", "original_data_u1001c1002_2");
    add(rowKey2, columns2);
    //查询数据1-1
    Map<String, String> map1 = getAllValue(rowKey1);
    for (Map.Entry<String, String> entry : map1.entrySet()) {
      System.out.println("map1-" + entry.getKey() + ":" + entry.getValue());
    }
    //查询数据1-2
    Map<String, String> map2 = getAllValue(rowKey2);
    for (Map.Entry<String, String> entry : map2.entrySet()) {
      System.out.println("map2-" + entry.getKey() + ":" + entry.getValue());
    }
    //查询数据2
    String original_data_value = getValueBySeries(rowKey1, "original_data");
    System.out.println("original_data_value->" + original_data_value);
    //查看表中所有数据
    getValueByTable();
  }
}