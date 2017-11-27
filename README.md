# chiwen
Big Data Application Firewall

关于我们： chiwen 是由《北京观数科技有限公司》开发的一款大数据生态组件的安全产品，致力于解决组件级别安全防护，如：账号认证，访问授权，账号管理，规则配置，安全事件预警等。chiwen还能提供丰富的大数据组件日志审计功能，使每一个用户或组件行为都逃不过你的眼睛。


插件安装及配置：

下载chiwen项目代码，并执行 mvn clean package -Dskiptest命令对项目 进行编译，编译完成后会生成相应jar包。

1.hdfs配置流程

1)下载源代码用maven编译生成hbase-plugin-0.1.0.jar plugins-common-0.1.0.jar
2）把hdfs-agent-0.1.0.jar plugins-common-0.1.0.jar 放在/hadoop-2.7.1/share/hadoop/common目录下
3)配置hdfs-site.xml

dfs.permissions.enabled 设置为true
dfs.namenode.inode.attributes.provider.class配置为
com.databps.bigdaf.chiwen.plugin.ChiWenHdfsAuthorizer


4)修改hadoop.env.sh
增加如下内容：
export ADMIN_URL="https://xxx.xxx.xxx.xxx:8085”(xxx.xxx.xxx.xxx 是chiwen管理平台部署地址)


2.hbase配置流程

1)下载源代码用maven编译生成hbase-plugin-0.1.0.jar plugins-common-0.1.0.jar
2)把hbase-plugin-0.1.0.jar plugins-common-0.1.0.jar 放在/hbase-1.2.1/lib目录里面
3)配置hbase-site.xml
hbase.security.authorization设置为true

hbase.coprocessor.master.classes配置为
com.databps.bigdaf.chiwen.plugin.ChiWenAuthorizationCoprocessor

hbase.coprocessor.regionserver.classes配置为
com.databps.bigdaf.chiwen.plugin.ChiWenAuthorizationCoprocessor


4)配置 hbase-env.sh
export ADMIN_URL="https://xxx.xxx.xxx.xxx:8085”(xxx.xxx.xxx.xxx 是chiwen管理平台部署地址)




