# 金房分布式数据库恢复文档

> version 2.0

## 项目目录

[参考论文：物理网服务平台中的分布式数据服务的研究与实现_陈小杰](http://kns.cnki.net/KCMS/detail/detail.aspx?dbcode=CMFD&dbname=CMFD201502&filename=1015586102.nh&v=MDI2MTMzcVRyV00xRnJDVVJMS2VadVpvRnkzblU3M0tWRjI2Rzdhd0dORE1yWkViUElSOGVYMUx1eFlTN0RoMVQ=)

| 文件夹名              | 描述                                                                                                 |
| ----------------------- | ----------------------- |
| AmoebaClient          | 分布式代理                                                                                           |
| ladpspringdemo        | 该文件夹下包含三个项目：RemoteRegistry， SptringLDAPDemo， .JETEmitters（该项目在myEclipse中会被隐藏） |
| ldapHelper.src        |                                                                                                      |
| MessageReceiver_Mongo | 与发布订阅系统通信模块                                                                               |
| MetaData              | 元数据服务器                                                                                         |
| MonjaDB               | 数据库客户端，对Eclipse开源插件MonjaDB进行修改                                                       |

## 运行配置

- myEclipse 8.5(必须，MonjaDB工程依赖org.eclipse包)
- jdk 1.6/1.7 32位
- mongodb-win32-i386-2.4.9 (需自行下载并配置)
- wso2greg-3.0.3 (项目RemoteRegistry依赖)
- 安装Amoeba服务 (必须)

## 导入项目

将上述各文件夹导入myEclipse中

### **ladpspringdemo**

#### .JETEmitters

报错：`Unbound classpath variable: 'CIMEROEDITOR' in project '.JETEmitters'`

解决办法：

- 点击 `Package Explorer` 右侧下三角 - Filters - 取消 `.*resources` 之前的勾， 确定
- `windows` - `Perferences` - `Java - Build Path - Classpath Variablies` - `New`
- 键入 `CIMEROEDITOR` ，Path选择 `文件夹路径/ladpspringdemo/.JETEmitters/src/cimeroEditor`

#### RemoteRegistry

报错：找不到包

解决办法：

- 右键该工程选择 `properties - Java Build Path` ， 将 `Libraries` 中红色的jar包删除， 选择 `Add External JARs` ，选择 `文件夹路径\wso2greg-3.0.3\webapps\ROOT\WEB-INF\plugins\common`，将其中所有jar包导入

### **MonjaDB**

- 修改 `src` 文件夹下 `info.properties` 中 `mongobin` 路径，`serverip` (根据单机部署还是多机部署决定)

### **MetaData**

- 修改 `src` 文件夹下 `info.properties` 中 `mongodbbin` 路径

## 运行项目

运行前请阅读论文，了解项目之间关系，配置信息含义

运行顺序：

1. MetaData (`Run As - Eclipse Application`)
2. 启动Amoeba服务
3. AmoebaClient (`Run As - Java Application`， 选择 `AmoebaClient`，再次启动选择 `AmoebaHandler` 检查 Amoeba服务是否启动成功)
4. MonjaDB (`Run As - Eclipse Application`)

### MetaData

正常启动后：

- `数据中心磁盘使用情况` 中要能看到信息
- AmoebaClient 启动以后，要能在 `正在amoeba状态` 中看到正在运行的amoeba信息

### AmoebaClient

正常启动后：

- 运行 `AmoebaClient` 之后，在MetaData后台可以看到一分钟一个心跳包
- 运行 `AmobaHandler` 之后，后台返回结果 `true`，表明Amoeba服务已启动

### MonjaDB

正常启动后：

- 在连接数据库界面可以选择数据节点
- 如果报错，提示XML解析失败，请检查 `MetaData` 中文配置

## 关于中文编码

所有应用窗口使用中文编码为GBK

工程 `MetaData` 中配置数据节点的文件 `mongoList.xml` 中的中文不能直接修改，需要运行工程`MetaData` 在窗口中修改

## 测试数据

在未修改配置文件的情况下，可添加以下测试数据确定 `MonjaDB` 项目是否运行正常：

- 下载MongoDB可视化管理工具 `Robo 3T`，连接MongoDB
- 在MongoDB默认数据库 `test` 中添加集合 `data`
- 在集合 `data` 中添加数据
  - 命令为 `for(var i = 0; i <= 20; i++) db.data.insert({schoolNum:0, student:"student" + i})`
- **注意**：数据库游标 `DBCursor` (Debug发现) 指向 `test.data`，query 为 `schoolNum:0`，所以测试数据中必须包含key-value：`schoolNum:0`