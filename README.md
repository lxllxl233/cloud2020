# SpringCloud

## 微服务架构理论入门

### 微服务架构概述

>什么是微服务
>
>>微服务由马丁富勒于2014年提出。微服务是一种架构模式，它提倡将单一应用程序划分成一组小的服务，服务之间相互协调配合，为用户提供最终价值。每个服务运行在其独立的进程中，服务与服务间采用轻量级的通信机制相互协作(通常是基于http协议的restful api)。每个服务都围绕着具体业务进行构建，并且能够被独立的部署到生产环境，类生产环境等。另外，应当尽量避免统一的，集中式的服务管理机制，对具体一个服务而言，应根据业务上下文，选择合适的语言，工具对其进行构建。
>
>![选区_054](/home/lxl/Pictures/选区_054.png)

### SpringCloud简介

>分布式微服务架构的一站式解决方案，是多种微服务架构落地技术的集合体，俗称微服务全家桶。
>
>![选区_055](/home/lxl/Pictures/选区_055.png)

### SpringCloud技术栈

>![选区_056](/home/lxl/Pictures/选区_056.png)
>
>课程涉及技术
>
>![选区_057](/home/lxl/Pictures/选区_057.png)

### 总结

![选区_058](/home/lxl/Pictures/选区_058.png)

## 技术选型

### 版本问题

>springboot2.X 和 springcloud H 版
>
>spring强烈要求升级到2.0：https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.0-Release-Notes
>
>springcloud github地址：https://github.com/spring-projects/spring-cloud/wiki
>
>springboot与springcloud的依赖关系：https://spring.io/projects/spring-cloud#overview
>
>更加详细的版本查看方法：https://start.spring.io/actuator/info

### 组件的停更升级替换

>![选区_059](/home/lxl/Pictures/选区_059.png)

## 工程构建

>约定 > 配置 > 编码
>
>父过程是 pom 工程
>
>![选区_060](/home/lxl/Pictures/选区_060.png)
>
>

### 父项目pom

>```xml
><!--
>这个标签用来声明全局依赖，并不引入实现
>-->
><dependencyManagement>
>		
></dependencyManagement>
>```
>
>**mvn install** #发布项目到maven仓库



### 模块构建

>客户端 80 ---调用---> 支付模块8001
>
>cloud-payment-order80
>
>cloud-provider-payment8001
>
>约定 > 配置 > 编码
>
>**约定**
>
>>1. 建module
>>2. 改pom
>>3. 写properties配置文件
>>4. 主启动
>>5. 业务类
>>
>>
>>
>>1. 建表 sql
>>
>>   ```mysql
>>   use cloud;
>>   create table payment(
>>       id bigint(20) not null auto_increment primary key ,
>>       serial varchar(200) default null
>>   )engine=InnoDb charset=utf8
>>   ```
>>
>>2. entities
>>
>>3. dao
>>
>>4. service
>>
>>5. controller
>>
>>配置
>>
>>>```properties
>>>spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
>>>spring.datasource.username=root
>>>spring.datasource.password=root
>>>spring.datasource.url=jdbc:mysql://localhost:3306/cloud?useUnicode=true&characterEncoding=utf-8&useSSL=false
>>>spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
>>>
>>>server.port=8001
>>>spring.application.name=cloud-payment-service
>>>
>>>mybatis.mapper-locations=classpath:mapper/*.xml
>>>mybatis.type-aliases-package=com.woqiyounai.springcloud.entities
>>>```
>
>基础工程搭建
>
>>![选区_061](/home/lxl/Pictures/选区_061.png)
>>
>>

### 热部署

>暂不

### 订单模块构建

>使用 **RestTemplate** 构建
>
>idea快速启动微服务配置
>
>在 .idea 文件夹下的 workspace.xml 文件里添加
>
>>```xml
>>  <option name="configurationTypes">
>>      <set>
>>        <option value="SpringBootApplicationConfigurationType" />
>>      </set>
>>    </option>
>>```
>
>新版idea会在service里

### 工程重构，代码抽取

>抽取公共代码建立module，使用 **mvn install** 发布jar包到本地，在其他地方使用下列 xml 引入
>
>>```xml
>><dependency>
>>    <groupId>com.woqiyounai.springcloud</groupId>
>>    <artifactId>cloud-api-common</artifactId>
>>    <version>1.0-SNAPSHOT</version>
>></dependency>
>>```

## 服务注册中心：Eureka

### 相关概念

>管理服务依赖关系，实现**服务调用，负载均衡，容错，实现服务注册与发现**
>
>Eureka Server作为服务注册的服务器，它是服务注册中心。而系统中的其他微服务，使用Eureka客户端连接到Eureka Server并**维持心跳**连接。这样系统维护人员就可以通过Eureka Service来监控系统中各个微服务是否正常运行。
>
>在服务注册与发现中，有一个注册中心。当服务器启动的时候，会把当前自己服务器的信息（通讯地址等以别名的方式注册到注册中心上）。消费者以该别名1的方式去注册中心上获取到实际的服务通讯地址，然后再实现本地RPC调用RPC远程调用框架核心设计思想：在于注册中心，因为使用注册中心管理每个服务与服务之间的依赖关系。在任何rpc远程调用框架中，都会有一个注册中心(存放相关服务信息，例如接口地址等等)。
>
>>Eureka:CS
>>
>>>消费者查找服务，找到调用远程client -- > Service端(监控微服务) <--- Client端(服务提供者注册)

### Eureka单机

>1. idea生成Eureka Server端服务注册中心
>
>   >引入 jar 包
>   >
>   >```xml
>   ><dependency>
>   >    <groupId>org.springframework.cloud</groupId>
>   >    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
>   ></dependency>
>   >```
>   >
>   >写配置
>   >
>   >```properties
>   >server.port=7001
>   >
>   >eureka.instance.hostname=localhost
>   >#不向注册中心注册自己
>   >eureka.client.register-with-eureka=false
>   >#表示自己就是注册中心
>   >eureka.client.fetch-registry=false
>   >#设置与eureka交互的地址，查询服务和注册服务都需要这个地址
>   >eureka.client.service-url.defaultZone=http://${eureka.instance.hostname}:${server.port}/eureka/
>   >```
>   >
>   >在主启动类上加 **@EnableEurekaServer** 注解
>   >
>   >打开 **localhost:7001** 看到eureka的页面就表示成功
>
>2. 注册Eureka服务提供者
>
>   >添加 pom 依赖
>   >
>   >```xml
>   ><dependency>
>   >    <groupId>org.springframework.cloud</groupId>
>   >    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
>   ></dependency>
>   >```
>   >
>   >修改配置文件
>   >
>   >```properties
>   >#向注册中心注册自己
>   >eureka.client.register-with-eureka=true
>   >eureka.client.fetch-registry=true
>   >#设置与eureka交互的地址，查询服务和注册服务都需要这个地址
>   >eureka.client.service-url.defaultZone=http://localhost:7001/eureka/
>   >```
>   >
>   >添加 **@EnableEurekaClient** 注解
>   >
>   >之后重新启动，刷新 eureka 页面发现页面报红：eureka 的自我保护机制
>
>3. 注册Eureka消费者
>
>   >改 pom
>   >
>   >加配置
>   >
>   >```properties
>   >spring.application.name=cloud-order-service
>   >#向注册中心注册自己
>   >eureka.client.register-with-eureka=true
>   >eureka.client.fetch-registry=true
>   >#设置与eureka交互的地址，查询服务和注册服务都需要这个地址
>   >eureka.client.service-url.defaultZone=http://localhost:7001/eureka/
>   >```
>   >
>   >添加 **@EnableEurekaClient** 注解
>   >
>   >去 eureka 页面刷新发现俩个微服务已经注册



微服务 RPC 远程服务调用最核心的是什么？

>高可用，集群避免单点故障
>
>1. 先启动eureka注册中心
>2. 启动服务提供者payment支付服务
>3. 支付服务启动后会把自身信息放入eureka
>4. 消费者order服务在需要调用接口时，使用服务别名去注册中心获取实际的RPC远程调用地址
>5. 消费者获得调用地址后，底层实际是利用HttpClient技术实现远程调用
>6. 消费者获得服务地址后会缓存在本地jvm内存中，默认每隔30秒更新一次服务调用地址

### Eureka集群

>7001 ---> <--- 7002 ：相互注册，相互守望，对外暴露整体
>
>1. 修改 hosts 文件：/etc/hosts ，添加如下内容
>
>   >```bash
>   >127.0.0.1	eureka7001.com
>   >127.0.0.1	eureka7002.com
>   >```
>
>2. 修改配置文件
>
>   >实例名称要写 eureka 服务端的实例名称
>   >
>   >注册地址要写其他服务端的地址
>   >
>   >```properties
>   >server.port=7001
>   >
>   >eureka.instance.hostname=eureka7001.com
>   >#不向注册中心注册自己
>   >eureka.client.register-with-eureka=false
>   >#表示自己就是注册中心
>   >eureka.client.fetch-registry=false
>   >#设置与eureka交互的地址，查询服务和注册服务都需要这个地址
>   >eureka.client.service-url.defaultZone=http://eureka7002.com:7002/eureka/
>   >```
>
>3. 将支付服务放到eureka集群里
>
>   >注册多个eureka
>   >
>   >```properties
>   >#设置与eureka交互的地址，查询服务和注册服务都需要这个地址
>   >eureka.client.service-url.defaultZone=http://localhost:7001/eureka/,http://localhost:7002/eureka/
>   >```
>
>4. 支付服务提供者8001集群环境搭建
>
>   >复制实例，修改端口
>
>5. 订单服务通过服务注册中心调用
>
>6. 将：
>
>   >```java
>   >public static final String PAYMENT_URL = "http://localhost:8001";
>   >```
>   >
>   >修改为
>   >
>   >```java
>   >public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
>   >```
>   >
>   >启动测试发现报错 **java.net.UnknownHostException: CLOUD-PAYMENT-SERVICE** ,使用 **@LoadBalanced** 注解赋予 **RestTemplate** 负载均衡的能力  **（这个就是Ribbon的负载均衡功能）**
>
>7. 修改主机名
>
>   >```properties
>   >#修改主机名
>   >eureka.instance.instance-id=payment8001
>   >```
>   >
>   >http://eureka7002.com:7001/actuator/health
>
>8. 服务发现：对于注册到eureka里面的微服务，可以通过服务发现来获得该服务的信息
>
>   >**@EnableDiscovery** 
>   >
>   >使用 
>   >
>   >```java
>   >@Autowired
>   >private DiscoveryClient discoveryClient;
>   >```
>   >
>   >通过 **DiscoveryClient** 来获取微服务的信息

### Eureka的自我保护

>看到 eureka 界面上的红字，说明 eureka 进入了保护模式
>
>>**EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. RENEWALS ARE LESSER THAN THRESHOLD AND HENCE THE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE.**
>
>一旦进入了保护模式，**eureka server**将会尝试保护其服务注册表中的信息，不再删除服务注册表中的数据，也就是不会注销任何微服务
>
>即：**某时刻某一个微服务不可用了，Eureka不会立刻清理，依旧会对该微服务的信息进行保存。属于CAP里面的AP分支**。eureka在一定时间内没有收到某个微服务的心跳，eureka就会注销该实例，默认90S。宁可错误，不会删除健康的服务实例。

#### 自我保护的关闭

>server端
>
>>关闭自我保护
>>
>>```properties
>>#关闭自我保护机制
>>eureka.server.enable-self-preservation=false
>>#设置清理时间
>>eureka.server.eviction-interval-timer-in-ms=2000
>>```
>
>client端
>
>>```properties
>>#发送心跳间隔，默认30s
>>eureka.instance.lease-renewal-interval-in-seconds=1
>>#eureka服务端在收到最后一次心跳的等待上限
>>eureka.instance.lease-expiration-duration-in-seconds=2
>>```

### zookeeper(3.4.9)（服务注册中心）

>zookeeper是一个分布式协调工具，可以实现注册中心功能。关闭linux服务器防火墙后启动zookeeper服务器，zookeeper服务器取代eureka服务器，zk作为服务注册中心。
>
>1. 关闭防火墙
>
>   >```bash
>   >systemctl stop firewalld
>   >```
>
>2. 新建工程，引入zookeeper依赖
>
>   >```xml
>   ><dependency>
>   >    <groupId>org.springframework.cloud</groupId>
>   >    <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
>   ></dependency>
>   >```
>
>3. 写配置
>
>   >```properties
>   >server.port=8004
>   >spring.application.name=cloud-payment-service
>   >spring.cloud.zookeeper.connect-string=localhost:2181
>   >```
>
>4. 主启动类添加 **@EnableDiscoveryClient** 注解
>
>5. 启动 **zookeeper** 
>
>   >```bash
>   >./zkServer.sh start
>   >./zkCli.sh
>   >```
>   >
>   >在zookeeper的交互界面测试
>   >
>   >>```bash
>   >>ls / #查看服务节点
>   >>get /zookeepwe #查看注册的服务
>   >>```
>   >>
>   >>![选区_062](/home/lxl/Pictures/选区_062.png)
>
>6. 启动微服务测试
>
>   >出现下面的结果即测试成功
>   >
>   >```bash
>   >[zk: localhost:2181(CONNECTED) 11] get /services/cloud-payment-service/decfeee0-c1f5-4e99-a6e1-88c83e05fd15
>   >{"name":"cloud-payment-service","id":"decfeee0-c1f5-4e99-a6e1-88c83e05fd15","address":"192.168.3.16","port":8004,"sslPort":null,"payload":{"@class":"org.springframework.cloud.zookeeper.discovery.ZookeeperInstance","id":"application-1","name":"cloud-payment-service","metadata":{}},"registrationTimeUTC":1594823927344,"serviceType":"DYNAMIC","uriSpec":{"parts":[{"value":"scheme","variable":true},{"value":"://","variable":false},{"value":"address","variable":true},{"value":":","variable":false},{"value":"port","variable":true}]}}
>   >
>   >```
>
>7. 订单服务入驻zookeeper
>
>   >改pom
>   >
>   >改配置
>   >
>   >主启动类加 **@EnableDiscoveryClient** 注解
>   >
>   >访问测试

### Consul(服务注册中心)

>开源的分布式服务发现和配置管理系统，由Go语言开发
>
>提供了微服务系统中的服务治理，配置中心，控制总线等功能。这些功能可以根据需要单独使用，也可以一起构建全方位的服务网格。
>
>优点：基于raft协议，比较简洁；支持健康检查，同时支持http和dns协议；支持跨数据中心的wan集群；提供图形界面；跨平台

#### 使用步骤

>1. consul官网下载，解压即安装
>
>2. 启动　：　**consul agent -dev**
>
>3. 启动后：  **http://localhost:8500**即是consul首页地址
>
>4. 新建工程，引入consul的pom依赖
>
>   >```xml
>   ><dependency>
>   >    <groupId>org.springframework.cloud</groupId>
>   >    <artifactId>spring-cloud-starter-consul-discovery</artifactId>
>   ></dependency>
>   >```
>
>5. 修改配置文件
>
>   >```properties
>   >server.port=8006
>   >spring.application.name=consul-provider-payment
>   >#consul配置
>   >spring.cloud.consul.host=localhost
>   >spring.cloud.consul.port=8500
>   >spring.cloud.consul.discovery.service-name=${spring.application.name}
>   >```
>
>6. 启动测试

### 三个注册中心的异同点

>![选区_063](/home/lxl/Pictures/选区_063.png)
>
>C(**强一致**)A(**高可用性**)P(**分区容错性**)
>
>![选区_064](/home/lxl/Pictures/选区_064.png)

## 负载均衡服务调用：Ribbon

>Ribbon 是一套**客户端负载均衡**的工具，主要功能是提供**客户端的软件负载均衡算法和服务调用**。Ribbon客户端组件提供一系列完善的配置项如连接超时，重试等。简单１的说，就是在配置文件中列出 Load Balancer(简称LB) 后面所有的机器，Ribbon会自动的帮助你基于某种规则(简单轮训，随机连接等)去连接这些机器。我们很容易使用Ribbon实现自定义的负载均衡算法。
>
>**负载均衡**：将用户的请求平摊的分配到多个服务上，从而达到系统的HA(高可用)；nginx,LVS,硬件F5
>
>1. 集中式LB(服务端LB)
>
>   >nginx是服务器负载均衡，客户端所有请求全部交给nginx，然后由nginx实现转发请求。即负载均衡是由服务端实现的。
>
>2. 进程内LB(客户端LB)
>
>   >Ribbon是本地负载均衡，在调用微服务接口时候，**会在注册中心上获取注册信息服务列表之后缓存到JVM本地**，从而在本地实现RPC远程服务调用技术。

### 再看 **restTemplate** 的使用

>getForObject：返回对象
>
>getForEntity：除了对象外，还会有code

### 自定义负载均衡算法

>查看 **IRule** 接口
>
>```java
>public interface IRule {
>    Server choose(Object var1);
>
>    void setLoadBalancer(ILoadBalancer var1);
>
>    ILoadBalancer getLoadBalancer();
>}
>```
>
>ctrl + n ：查找类
>
>ctrl + H ：查看类继承关系
>
>Ribbon 自带7种负载均衡算法

自定义配置

>**这个自定义配置类不能放在 @ComponentScan 所扫描的当前包下以及子包下，否则自定义的配置类会被所有的Ribbon客户端所共享，达不到特殊化定制的目的。**
>
>#### 自定义步骤
>
>1. 1.测试ribbon内置
>
>  >在 **@ComponentScan** 扫描不到的地方新建如下配置类
>  >
>  >```java
>  >@Configuration
>  >public class MyRule {
>  >
>  >@Bean
>  >public IRule myRule(){
>  >   return new RandomRule();
>  >}
>  >}
>  >```
>  >
>  >在主启动类上添加　**@RibbonClient** 注解
>  >
>  >```java
>  >@RibbonClient(name = "CLOUD-PAYMENT-SERVICE",configuration = MyRule.class)
>  >```
>  >
>  >指定**配置类**和**哪个微服务**使用该配置
>  >
>  >启动项目并使用Postman进行测试
>  >
>  >
>
>2. 2.理解原理
>
>  >轮询负载均衡：**实际调用服务器下标** = **rest接口第几次请求数** **%** **服务器集群数量**
>  >
>  >源码分析
>  >
>  >>```java
>  >>//检查可用的微服务
>  >>//获取下一个调用的下标，并增加请求数
>  >>int nextServerIndex = incrementAndGetModulo(serverCount);
>  >>server = allServers.get(nextServerIndex);
>  >>```
>  >>
>  >>其中 **incrementAndGetModulo** 方法：
>  >>
>  >>```java
>  >>private int incrementAndGetModulo(int modulo) {
>  >>    //自旋锁 + CAS
>  >>    for (;;) {
>  >>        int current = nextServerCyclicCounter.get();
>  >>        int next = (current + 1) % modulo;
>  >>        if (nextServerCyclicCounter.compareAndSet(current, next))
>  >>            return next;
>  >>    }
>  >>}
>  >>```
>
>3. 3.自定义负载均衡算法
>
>  >**原理 + JUC(CAS+自旋锁的复习)**
>  >
>  >注掉 **@LoadBalanced**e　注解
>  >
>  >负载均衡算法的实现：
>  >
>  >自定义负载均衡接口
>  >
>  >>```java
>  >>public interface LoadBalancer {
>  >>    ServiceInstance instances(List<ServiceInstance> serviceInstances);
>  >>}
>  >>```
>  >
>  >实现负载均衡接口
>  >
>  >>```java
>  >>@Component
>  >>public class MyLB implements LoadBalancer{
>  >>
>  >>    private AtomicInteger atomicInteger = new AtomicInteger(0);
>  >>
>  >>    public final int getAndIncrement(){
>  >>        int current;
>  >>        int next;
>  >>        do {
>  >>            current = atomicInteger.get();
>  >>            next = current >= Integer.MAX_VALUE?0:current+1;
>  >>            //比较并交换；如果当前值与内存中的值一致设置值next并返回true，不一致返回false
>  >>        }while (!this.atomicInteger.compareAndSet(current,next));
>  >>        System.out.println("******next（第几次）："+next);
>  >>        System.out.println("***current（当前值）："+current);
>  >>        return next;
>  >>    }
>  >>
>  >>    @Override
>  >>    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
>  >>        int index = getAndIncrement()%serviceInstances.size();
>  >>        return serviceInstances.get(index);
>  >>    }
>  >>}
>  >>```
>  >
>  >在 controller 层注入自己的负载均衡实现
>  >
>  >>```java
>  >>//注入自己的算法
>  >>@Autowired
>  >>private LoadBalancer loadBalancer;
>  >>@Autowired
>  >>private DiscoveryClient discoveryClient;
>  >>```
>  >
>  >测试自己的负载均衡算法
>  >
>  >>```java
>  >>@GetMapping("/api/test/loadbalance")
>  >>public CommonResponse<Payment> getPaymentLB(Integer id){
>  >>    List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
>  >>    if (null == instances || instances.size() == 0){
>  >>        return null;
>  >>    }else {
>  >>        //获取服务实例
>  >>        ServiceInstance service = loadBalancer.instances(instances);
>  >>        URI uri = service.getUri();
>  >>        return restTemplate.getForObject(uri+"/api/payment/getPaymentById?id="+id,CommonResponse.class);
>  >>    }
>  >>}
>  >>```

## 服务接口调用：OpenFeign

### openfeign是什么

>feign是一个声明式的 web 服务客户端，让编写 web 服务客户端变得非常容易，**只需要创建一个接口并在接口上添加注解即可。**
>
>在使用 Ribbon+RestTemplate 时，利用 RestTemplate 对 http 请求封装处理，形成了一套模板化的调用方法。但是在实际开发中，由于对服务依赖的调用可能不止一处，往往一个接口会被多处调用，所以通常都会针对每个微服务自行封装一些客户端类来包装这些依赖服务的调用。所以，Feign在此基础上做了进一步封装，由它来帮助我们定义和实现依赖服务接口的定义。在Feign的实现下，我们只需要创建一个接口并使用注解的方式来配置它（以前是Dao接口上面标注@Mapper注解，现在是一个微服务接口上面标注一个@Feign注解即可），即可完成对服务提供方的接口绑定，简化了Spring cloud Ribbon时，自动封装服务调用客户端的开发量。
>
>Feign集成了Ribbon
>
>(**封装对微服务的调用**)

### 使用步骤

>1. 方式： 接口+@FeignClient
>
>2. 新建模块
>
>3. 编写 pom
>
>   >```xml
>   >        <!--  open feign      -->
>   >        <dependency>
>   >            <groupId>org.springframework.cloud</groupId>
>   >            <artifactId>spring-cloud-starter-openfeign</artifactId>
>   >        </dependency>
>   >```
>
>4. 编写配置文件
>
>   >```properties
>   >server.port=8000
>   >#不向注册中心注册自己
>   >eureka.client.register-with-eureka=false
>   >#设置与eureka交互的地址，查询服务和注册服务都需要这个地址
>   >eureka.client.service-url.defaultZone=http://localhost:7001/eureka/,http://localhost:7002/eureka/
>   >```
>
>5. 主启动类上添加 **@EnableFeignClients** 注解
>
>6. 编写 Feign 接口
>
>   >```java
>   >@Component
>   >@FeignClient("CLOUD-PAYMENT-SERVICE")
>   >public interface PaymentFeignService {
>   >    @GetMapping("/api/payment/getPaymentById")
>   >    CommonResponse<Payment> getPaymentById(@RequestParam(value = "id",defaultValue = "0") Long id);
>   >}
>   >```
>
>7. 测试
>
>8. 超时控制
>
>   >使服务提供方故意超时
>   >
>   >```java
>   >public void timeOut(){
>   >    try {
>   >        TimeUnit.SECONDS.sleep(3);
>   >    } catch (InterruptedException e) {
>   >        e.printStackTrace();
>   >    }
>   >}
>   >```
>   >
>   >然后客户端调用出错
>   >
>   >**默认 Feign　客户端只等待一秒钟，但服务端处理时间超过一秒钟导致 Feign 客户端不再等待了，直接返回报错。为了避免这样的情况，我们需要设置 Feign 客户端的超时控制**
>   >
>   >添加超时配置
>   >
>   >```properties
>   >ribbon.ReadTimeout=5000
>   >ribbon.ConnectTimeout=5000
>   >```
>   >
>   >这样客户端就不再报错
>
>9. openfeign　日志打印功能
>
>   >四种级别
>   >
>   >>![选区_065](/home/lxl/Pictures/选区_065.png)
>   >
>   >开启日志配置
>   >
>   >```java
>   >@Configuration
>   >public class FeignConfig {
>   >
>   >    @Bean
>   >    public Logger.Level feignLoggerLevel(){
>   >        return Logger.Level.FULL;
>   >    }
>   >}
>   >```
>   >
>   >```properties
>   >logging.level.com.woqiyounai.feign8000.service.PaymentFeignService: debug
>   >```

## 服务降级:Hystrix断路器

>系统拆分后：调用链路过长，依赖不可避免的可能会失败。单一的后端依赖可能会导致所有服务器上的所有资源都在几秒钟内饱和。比失败更糟糕的是，这些应用程序还可能导致服务之间的延迟增加，备份队列，线程和其他系统资源紧张，导致整个系统发生更多的级联故障。这些都表示需要**对故障和延迟进行隔离和管理**，以便单个依赖的失败，不能取消整个应用程序或系统。
>
>Hystrix 是一个用于处理分布式系统 **延迟** 和 **容错** 的开源库，在分布式系统内，许多依赖不可避免的会调用失败，比如超时、异常等，Hystrix能够保证在一个依赖出问题的情况下，不会导致整体服务失败，避免级联故障，以提高分布式系统的弹性。
>
>"断路器"本身是一种开关装置，当某个服务发生故障后，通过故障监控(类似保险丝)，**向调用方返回一个符合预期的、可处理的备选相应(Fallback)，而不是长时间等待或者抛出调用方无法处理的异常**。
>
>Hystrix: **服务降级　服务熔断　接近实时的监控**

### 一些概念

>服务降级：**fallback** 对方服务不可用，给出符合预期的可处理的相应
>
>>
>
>服务熔断：**break** 达到最大访问后，直接拒绝服务，然后调用服务降级 (降级　熔断　恢复)
>
>服务限流：**limit** 秒杀高并发等操作

### 使用步骤

>1. 引入依赖
>
>   >```xml
>   ><!--引入 hystrix 依赖-->
>   ><dependency>
>   >    <groupId>org.springframework.cloud</groupId>
>   >    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
>   ></dependency>
>   >```
>
>2. 修改配置文件（复制粘贴原来的）
>
>3. 搭建环境，编写测试方法
>
>   >```java
>   >//正确方法
>   >@Override
>   >public String paymentInfo_OK(Integer id) {
>   >    return "线程池 "+Thread.currentThread().getName()+" paymentInfo_OK id="+id;
>   >}
>   >
>   >//异常方法：超时
>   >@Override
>   >public String paymentInfo_NOT_OK(Integer id) {
>   >    try {
>   >        TimeUnit.SECONDS.sleep(3);
>   >    } catch (InterruptedException e) {
>   >        e.printStackTrace();
>   >    }
>   >    return "线程池 "+Thread.currentThread().getName()+" paymentInfo_NOT_OK id="+id;
>   >}
>   >```
>   >
>   >编写controller在浏览器测试
>
>4. 高并发测试
>
>   >官网下载安装 **jmeter**
>   >
>   >开20000个线程进行测试，发现原来的OK方法访问拖慢
>   >
>   >原因分析(**tomcat的默认的工作线程数被打满了，没有多余的线程来分解压力和处理**)
>   >
>   >加入消费者再测试
>   >
>   >更加卡顿
>
>5. 问题分析
>
>   >超时导致服务器变慢（转圈）：超时不再等待
>   >
>   >出错（宕机或程序运行出错）：出错要有兜底
>   >
>   >>对方服务(8001)超时了，调用者(80)不能一直卡死等待，必须有服务降级
>   >>
>   >>对方服务(8001)宕机了，调用者(80)不能一直卡死等待，必须有服务降级
>   >>
>   >>对方服务(8001)OK，调用者(80)自己出故障或有自我要求(自己等待时间小于响应的时间)
>
>6. 加入 Hystrix 框架解决
>
>   >主启动类：**@EnableCircuitBreaker**
>   >
>   >需要兜底的方法上加：**@HystrixCommand**(fallbackMethod = "paymentInfo_TimeoutHandler")
>   >
>   >>当程序出现问题的时候，就会调用　paymentInfo_TimeoutHandler 方法（由 **fallbackMethod** 标注）
>   >>
>   >>```java
>   >>@HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",
>   >>                commandProperties = {
>   >>                    //超时 3 秒会调用兜底方法
>   >>                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
>   >>                }
>   >>                )
>   >>```
>
>7. 测试结果
>
>   >当超时时使用了兜底方法
>   >
>   >切换 1/0 异常继续调用兜底方法
>   >
>   >卡顿的原因：**tomcat默认的工作线程数被打满了，没有多余的线程来分担压力**
>
>8. 订单微服务加入兜底方法(通常用在客户端)
>
>   >8000订单微服务，也可以更好的保护自己，自己也照葫芦画瓢进行客户端降级保护
>   >
>   >配置文件
>   >
>   >>```properties
>   >>#开启服务降级
>   >>feign.hystrix.enabled=true
>   >>```
>   >
>   >**主启动类添加**
>   >
>   >>```java
>   >>@EnableHystrix
>   >>```
>   >
>   >**注解**

### 全局方法抽取

>解决代码膨胀问题：配置全局通用的 fallback 方法
>
>使用注解 **@DefaultProperties**
>
>```java
>@DefaultProperties(
>        defaultFallback = "globalMethod"
>)
>```
>
>进一步降低代码耦合度
>
>开启 feign 的服务降级
>
>```properties
>#开启服务降级
>feign.hystrix.enabled=true
>```
>
>Feign　接口
>
>```java
>@Component
>@FeignClient(value = "CLOUD-PAYMENT-HYSTRIX-SERVICE",fallback = HystrixServiceImpl.class)
>public interface HystrixService {
>
>    @GetMapping("/payment/hystrix/ok")
>    String payment_OK(@RequestParam(value = "id") Integer id);
>
>    @GetMapping("/payment/hystrix/timeout")
>    String payment_NOT_OK(@RequestParam(value = "id") Integer id);
>}
>```
>
>实现 Feign 接口
>
>```java
>@Component
>public class HystrixServiceImpl implements HystrixService{
>    @Override
>    public String payment_OK(Integer id) {
>        return "异常 - OK方法调用失败";
>    }
>
>    @Override
>    public String payment_NOT_OK(Integer id) {
>        return "异常 - not ok方法调用失败";
>    }
>}
>```
>
>在 **@FeignClient** 里配置 **fallback** 属性

服务降级：兜底方法

### 服务熔断

>类比保险丝达到最大服务访问后，直接拒绝访问，拉闸限电，然后调用服务降级１的方法并返回友好提示
>
>就是保险丝：　**服务的降级--->进而熔断-->恢复调用链路**
>
>熔断机制概述：
>
>>熔断机制是应对雪崩效应的一种微服务链路保护机制。当扇出链路的某个微服务出错不可用或者响应时间太长时，会进行服务的降级，进而熔断该节点的微服务的调用，快速返回错误的响应信息。
>>
>>**当检测到该节点微服务调用响应正常后，恢复调用链路**
>>
>>>理论，当熔断后，慢慢进入半开状态，当可以承受时，恢复调用

#### 配置与测试

>```java
>@HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",
>commandProperties = {
>        @HystrixProperty(name = "circuitBreaker.enable",value = "true"),//是否开启断路器
>        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
>        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期
>        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")//失败率达到多少后跳闸
>})
>```
>
>如上面代码，开启断路器后，若在10s内10次请求有6次失败则熔断。	
>
>在8001端口的微服务添加测试熔断的代码
>
>```java
>//测试服务熔断的方法
>@HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",
>commandProperties = {
>        @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否开启断路器
>        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
>        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期
>        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")//失败率达到多少后跳闸
>})
>@Override
>public String paymentCircuitBreaker(Integer id){
>    if (id < 0){
>        throw new RuntimeException("id 不能为负数");
>    }
>    String UUID = IdUtil.simpleUUID();
>    return Thread.currentThread().getName()+"  调用成功，流水号:"+UUID;
>}
>public String paymentCircuitBreaker_fallback(Integer id){
>    return "请求失败";
>}
>```
>
>自测发现多次传负数会导致服务熔断现象，当经过一段时间后进入正确的请求会恢复。**过了时间窗口期后慢慢恢复调用**。在断路状态下，直接调用降级 **fallback**　，

#### 熔断器的三个重要参数

>快照时间窗，请求总数阈值，错误百分比阈值
>
>**快照时间窗**
>
>>断路器确定是否打开需要统计一些请求和错误数据，而统计的时间范围就是快照时间窗，默认为最近10s
>
>**请求总数阈值**
>
>>在快照时间窗内，必须满足请求总数阈值才有资格熔断，默认为20，意味着在10秒内，如果该Hystrix命令的调用次数不足20次，即使所有的请求都超时或其他原因失败，断路器都不会打开。
>
>**错误百分比阈值**
>
>>当请求总数在快照时间窗内超过了阈值，比如发生了30次调用，如果在这30次调用中，有15次发生了超时异常，也就是超过50%的百分比，在默认设定50%阈值的情况下，这时候就会将断路器打开。

### 服务限流

>

### hystrix工作流程

>

### 图形化Dashboard服务监控仪表盘

>pom文件
>
>>```xml
>><dependency>
>>    <groupId>org.springframework.cloud</groupId>
>>    <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
>></dependency>
>>```
>
>配置文件
>
>>server.port=9001
>
>主启动类加
>
>>```java
>>@EnableHystrixDashboard
>>```
>>
>>注解
>
>启动项目后进入　**http://localhost:9001/hystrix**　看到图形化界面
>
>

## 网关:Gateway

### 概念简述

>SpringCloud Gateway 是 SpringCloud 的一个全新项目，基于 Spring5.0+Springboot2.0 和 Project Reactor 等技术开发的网关，它旨在为微服务架构提供一种简单有效的统一的 API 路由管理方式。
>
>SprinngCloud Gateway 作为 SpringCloud 生态系统中的网关，目标是替代 Zuul,在 SprinngCloud2.0 以上版本中，没有对新版本的Zuul2.0以上最新高性能版本进行集成，仍然还是使用 Zuul1.x 非 Reactor 模式的老版本。而为了提升网关的性能，SpringCloud Gateway 是基于 WebFlux 框架实现的，而 WebFlux 框架底层使用了高性能的 Reactor 模式通信框架 Netty。
>
>SpringCloud Gateway的目标提供统一的路由方式且基于 Filter 链的方式提供了网关基本的功能，例如：安全，监控/指控，限流。
>
>网关：**所有微服务的入口**，异步非阻塞模型
>
>核心逻辑：**路由转发+执行过滤器**

![选区_066](/home/lxl/Pictures/选区_066.png)

### 核心概念

>路由
>
>>路由是构建网关的基本模块，它由ID，目标URL，一系列断言器和过滤器组成，如果断言为 true 就匹配该路由。
>
>断言
>
>>参考java8的java.util.function.Predicate (断言器)
>>
>>开发人员可以匹配HTTP请求中的所有内容(例如请求头或请求参数)，如果请求与断言相匹配则进行路由。
>
>过滤器
>
>>指的是Spring框架中的Gateawy Filter的实例，使用过滤器，可以在请求被路由前或者之后对请求进行修改。

![选区_067](/home/lxl/Pictures/选区_067.png)

### 入门配置

>新建工程 cloud-gateway-gateway9527
>
>添加 pom
>
>>```xml
>><dependency>
>>    <groupId>org.springframework.cloud</groupId>
>>    <artifactId>spring-cloud-starter-gateway</artifactId>
>></dependency>
>>```
>
>properties配置文件
>
>>```properties
>>server.port=9527
>>spring.application.name=cloud-gateway
>>#向注册中心注册自己
>>eureka.client.register-with-eureka=true
>>eureka.client.fetch-registry=true
>>#设置与eureka交互的地址，查询服务和注册服务都需要这个地址
>>eureka.client.service-url.defaultZone=http://localhost:7001/eureka/,http://localhost:7002/eureka/
>>
>>spring.cloud.gateway.routes[0].id=payment_routh
>>spring.cloud.gateway.routes[0].uri=http://localhost:8001
>>spring.cloud.gateway.routes[0].predicates[0]=Path=/api/payment/get/**
>>spring.cloud.gateway.routes[1].id=payment_routh2
>>spring.cloud.gateway.routes[1].uri=http://localhost:8001
>>spring.cloud.gateway.routes[1].predicates[0]=Path=/api/payment/lb/**
>>```
>
>启动网关，然后打开浏览器 **http://localhost:9527/api/payment/get/1** 进入测试。

### 案例

>通过9527网关访问到外网的百度新闻网址
>
>```java
>@Configuration
>public class GatewayConfig {
>
>    @Bean
>    public RouteLocator routes(RouteLocatorBuilder builder){
>        RouteLocatorBuilder.Builder routes = builder.routes();
>        RouteLocatorBuilder.Builder route = routes.route("path_route_woqiyounai",
>                r -> r.path("/guonei")
>                        .uri("http://news.baidu.com/guonei"));
>        return route.build();
>    }
>}
>```
>
>通过 **http:127.0.0.1:9527/guonei** 即可访问外网

### 动态路由的配置

>默认情况下Gateway会根据注册中心注册的服务列表，以注册中心上微服务名为路径创建动态路由进行转发，从而实现动态路由的功能。
>
>开启配置
>
>```properties
>#开启动态路由
>spring.cloud.gateway.discovery.locator.enabled=true
>#使用 lb 前缀以及服务名
>spring.cloud.gateway.routes[0].uri=lb://CLOUD-PAYMENT-SERVICE
>```

### 常用Predicate

>```cobol
>2020-07-25 20:46:41.894  INFO 5582 --- [  restartedMain] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [After]
>2020-07-25 20:46:41.895  INFO 5582 --- [  restartedMain] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Before]
>2020-07-25 20:46:41.895  INFO 5582 --- [  restartedMain] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Between]
>2020-07-25 20:46:41.896  INFO 5582 --- [  restartedMain] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Cookie]
>2020-07-25 20:46:41.896  INFO 5582 --- [  restartedMain] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Header]
>2020-07-25 20:46:41.896  INFO 5582 --- [  restartedMain] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Host]
>2020-07-25 20:46:41.896  INFO 5582 --- [  restartedMain] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Method]
>2020-07-25 20:46:41.897  INFO 5582 --- [  restartedMain] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Path]
>2020-07-25 20:46:41.897  INFO 5582 --- [  restartedMain] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Query]
>2020-07-25 20:46:41.898  INFO 5582 --- [  restartedMain] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [ReadBodyPredicateFactory]
>2020-07-25 20:46:41.898  INFO 5582 --- [  restartedMain] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [RemoteAddr]
>2020-07-25 20:46:41.898  INFO 5582 --- [  restartedMain] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Weight]
>2020-07-25 20:46:41.903  INFO 5582 --- [  restartedMain] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [CloudFoundryRouteService]
>```
>
>After
>
>>时间获取
>>
>>```java
>>ZonedDateTime now = ZonedDateTime.now();
>>System.out.println(now);
>>```
>>
>>添加配置后
>>
>>```properties
>>spring.cloud.gateway.routes[0].predicates[1]=After=2020-07-25T21:00:22.299+08:00[Asia/Shanghai]
>>```
>>
>>配置接口在某个时间后才可以访问
>
>Cookie
>
>>配置
>>
>>```properties
>>spring.cloud.gateway.routes[0].predicates[2]=Cookie=username,zzyy
>>```
>>
>>使用 curl 命令
>>
>>>```bash
>>>curl http://localhost:9527/api/payment/get/1
>>>#带上 cookie 访问
>>>curl http://localhost:9527/api/payment/get/1 --cookie "username=zzyy"
>>>```
>
>Header
>
>>使用请求头校验
>>
>>```properties
>>#请求头要是整数
>>spring.cloud.gateway.routes[0].predicates[3]=Header=X-Request-Id,\\d+
>>```
>>
>>curl 命令
>>
>>```bash
>>curl http://localhost:9527/api/payment/get/1 --cookie "username=zzyy" --header "X-Request-Id:9"
>>```
>
>拓展：带host
>
>curl http://localhost:9527/api/payment/get/1 --cookie "username=zzyy" --header "X-Request-Id:9" -H "Host:  127.0.0.1"

### Filter过滤器

>在请求前后对请求进行修改，可用于修改进入的HTTP请求和返回的HTTP响应，路由过滤器只能指定路由进行使用
>
>Ｇａｔｅｗａｙ内置多种路由过滤器，他们都由**GatewayFilter**的工厂类产生
>
>使用
>
>>实现 GlobalFilter , Ordered 这俩个接口
>>
>>```java
>>@Slf4j
>>@Component
>>public class MyGatewayFilter implements GlobalFilter, Ordered {
>>
>>    @Override
>>    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
>>        log.info("****全局过滤器****" + ZonedDateTime.now());
>>        String uname = exchange.getRequest().getQueryParams().getFirst("uname");
>>        if (null == uname){
>>            log.info("用户名为空");
>>            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
>>            return exchange.getResponse().setComplete();
>>        }else {
>>            return chain.filter(exchange);
>>        }
>>    }
>>
>>    //加载过滤器的顺序，顺序越小优先级越高
>>    @Override
>>    public int getOrder() {
>>        return 0;
>>    }
>>}
>>```

## 分布式配置中心:Config

>存在的问题：application 文件太多，配置压力大。所以有了分布式配置中心，它是一个独立的微服务应用，用来连接配置服务器并为客户端提供获取配置信息，加密\解密信息等访问接口。
>
>![选区_068](/home/lxl/Pictures/选区_068.png)
>
>>1. 集中管理配置文件
>>2. 不同环境不同配置，动态化配置更新
>>3. 运行时动态调整
>>4. 配置变化时，服务不需要重启即可感知配置
>>5. 将配置信息以 REST 接口的形式暴露
>
>使用步骤
>
>>1. 用自己的账号在 github 新建一个 springcloud-config 工程 https://github.com/lxllxl233/springcloud-config.git
>>
>>2. 新建配置中心模块 ，导入 pom 依赖
>>
>>   >```xml
>>   ><dependency>
>>   >    <groupId>org.springframework.cloud</groupId>
>>   >    <artifactId>spring-cloud-config-server</artifactId>
>>   ></dependency>
>>   >```
>>
>>3. 编写配置文件
>>
>>   >```properties
>>   >server.port=3344
>>   >spring.application.name=cloud-config-center
>>   >spring.cloud.config.server.git.uri=https://github.com/lxllxl233/springcloud-config.git
>>   >spring.cloud.config.server.git.search-paths[0]=springcloud-config
>>   >spring.cloud.config.label=master
>>   >
>>   >eureka.client.register-with-eureka=true
>>   >eureka.client.fetch-registry=true
>>   >#设置与eureka交互的地址，查询服务和注册服务都需要这个地址
>>   >eureka.client.service-url.defaultZone=http://localhost:7001/eureka/,http://localhost:7002/eureka/
>>   >```
>>
>>4. 主启动类添加 **@EnableConfigServer** 注解，启动工程
>>5. 新建配置文件并用 git 提交
>>6. 浏览器打开 **http://config-3344.com:3344/master/config-dev.properties** 查看配置文件

### config客户端

>SpringCloud Config为微服务架构中的微服务提供集中化的外部配置支持，配置服务器为 **各个不同微服务应用** 的所有环境提供了一个 **中心化的外部配置**。
>
>SpringCloud Config分为　**服务端和客户端俩部分**
>
>服务端也称为 **分布式配置中心，它是一个独立的微服务应用** ,用来连接配置服务器并为客户端提供获取配置信息，加密/解密信息等访问接口
>
>客户端则是通过指定的配置中心来管理应用资源，以及与业务相关的配置内容，并在启动的时候从配置中心获取和加载配置信息配置服务器默认采用git来存储配置信息，这样就有助于对环境配置进行版本管理，并且可以通过git客户端工具来方便的管理和访问配置内容。

- 新建模块 **cloud-config-client-3355**

- 修改 pom 

  >```xml
  ><dependency>
  >    <groupId>org.springframework.cloud</groupId>
  >    <artifactId>spring-cloud-starter-config</artifactId>
  ></dependency>
  >```

- 修改配置文件

  >```properties
  >#优先级更高的配置文件 bootstrap.properties
  >server.port=3355
  >spring.application.name=config-client
  >#分支
  >spring.cloud.config.label=master
  >#名称
  >spring.cloud.config.name=config
  >#后缀
  >spring.cloud.config.profile=prod
  >#配置中心地址
  >spring.cloud.config.uri=http://localhost:3344
  >
  >#注册到 eureka
  >eureka.client.register-with-eureka=true
  >eureka.client.fetch-registry=true
  >#设置与eureka交互的地址，查询服务和注册服务都需要这个地址
  >eureka.client.service-url.defaultZone=http://localhost:7001/eureka/,http://localhost:7002/eureka/
  >```

- 启动测试

### 动态刷新配置文件

>- 修改 3355 工程
>
>  >引入图形化监控
>  >
>  >```xml
>  ><dependency>
>  >    <groupId>org.springframework.boot</groupId>
>  >    <artifactId>spring-boot-starter-actuator</artifactId>
>  ></dependency>
>  >```
>
>- 3355 添加配置
>
>  >```properties
>  >#暴露监控端点
>  >management.endpoints.web.exposure.include="*"
>  >```
>
>- 3355 的 controller 添加 **@RefreshScope** 注解
>
>- 重新启动后发现还是没有自动刷新配置文件
>
>- 需要发送一个POST请求： **curl -X POST http://localhost:3355/actuator/refresh** 即可刷新配置文件。

## 服务总线:Bus

之前的那种方式仍然需要手动的发送 POST 请求，可否广播，一次通知，处处生效？

### 什么是bus?

>SpringCloud bus是用来将分布式系统的节点与轻量级消息系统链接起来的框架，**它整合了Java的事件处理机制和消息中间件的功能**，SpringCloud bus目前支持RabbitMQ和Kafka。**管理和传播分布式系统间的消息，就像一个分布式执行器，可用于广播状态更改，事件推送等，也可以当作微服务间的通信通道。**
>
>在微服务架构的系统中，通常会使用轻量级的消息代理来构建一个共用的消息主题，并让系统中所有微服务实例都连接上来。由于该主题中产生的消息会被所有实例监听和消费，所以称它为总线。在总线上的各个实例，都可以方便的广播一些需要让其他连接该主题上的实例都知道的消息。

### mq 环境配置

>安装erlang
>
>```bash
>https://www.erlang-solutions.com/resources/download.html
>```
>
>安装 mq
>
>```bash
># sync package metadata
>sudo apt-get update
># install dependencies manually
>sudo apt-get -y install socat logrotate init-system-helpers adduser
>
># download the package
>sudo apt-get -y install wget
>wget https://github.com/rabbitmq/rabbitmq-server/releases/download/v3.8.5/rabbitmq-server_3.8.5-1_all.deb
>
># install the package with dpkg
>sudo dpkg -i rabbitmq-server_3.8.5-1_all.deb
>
>rm rabbitmq-server_3.8.5-1_all.deb
>```
>
>启动 mq
>
>```bash
>service rabbitmq-server start
>```
>
>启用图形化管理插件
>
>```bash
>rabbitmq-plugins enable rabbitmq_management
>```
>
>访问 **http://127.0.0.1:15672**，默认用户名密码guest,guest

### bus动态更新启用

>- 以3355为模板制作3366
>
>- >有俩种bus架构模型　1)通知客户端传染 2)通知服务端传染
>  >
>  >第一种不适合的原因如下
>  >
>  >>1. 打破了微服务的职责单一型，当该微服务故障时，数罪并罚，死的更惨
>  >>2. 破坏了微服务的对等性
>  >>3. 局限性，服务迁移时，需要修改多个端点

#### 使用bus动态刷新全局广播

- 添加 pom 依赖

  >```xml
  ><dependency>
  >    <groupId>org.springframework.cloud</groupId>
  >    <artifactId>spring-cloud-starter-bus-amqp</artifactId>
  ></dependency>
  >```

- 3344添加配置

  >```properties
  >#mq相关支持
  >spring.rabbitmq.host=127.0.0.1
  >spring.rabbitmq.port=5672
  >spring.rabbitmq.username=guest
  >spring.rabbitmq.password=guest
  >#mq配置,暴露bus刷新配置的端点
  >management.endpoints.web.exposure.include=bus-refresh
  >```

- 3355,3366也添加pom依赖，并加入配置

  >```properties
  >#mq相关支持
  >spring.rabbitmq.host=127.0.0.1
  >spring.rabbitmq.port=5672
  >spring.rabbitmq.username=guest
  >spring.rabbitmq.password=guest
  >```

- 启动后修改远程 git 仓库的配置

- 发送刷新请求　**curl -X POST http://localhost:3344/actuator/bus-refresh**　即可全局刷新

#### 使用bus动态刷新定点通知

>*例如只通知3355，不通知3366*
>
>*curl -X POST http://localhost:3344/actuator/bus-refresh/config-client:3355*
>
>测试发现只有 *3355* 被通知到

![选区_069](/home/lxl/Pictures/选区_069.png)

## cloud stream

消息中间件(MQ)过多，学习成本大，对接系统可能是不同的MQ                

有没有一种新技术，让我们可以不再关注具体MQ细节，只需要一种适配绑定的方式，自动的给我们在各种MQ内切换

### 什么是 *SpringCloudStream*?

>Springcloud Stream是一个构建消息驱动微服务的框架
>
>应用程序通过 *inputs* 或者 *outputs* 来与 *Spring Cloud Stream* 中的 *Binder* 对象交互
>
>通过配置我们来绑定，而SpringCloudStream中的 *Binder* 对象负责与消息中间件交互
>
>所以，我们只需要搞清楚如何与 SpringCloud Stream交互即可方便使用消息驱动的方式
>
>通过使用Spring Integratipon 来连接消息代理中间件以实现消息事件驱动
>
>SpringClouud Stream 为一些供应商的消息中间件产品提供了个性化的自动化配置实现，引用了 *发布－订阅，消费组，分区*     的三个概念
>
>目前仅仅支持 RabbitMQ Kafka

### 标准的MQ

>生产(*Input*)者/消费(*Output*)者 之间依靠消息媒介传递信息内容 *Message*
>
>消息必须走特定的通道 消息通道:*MessageChannel*
>
>消息通道里的消息如何被消费呢，谁负责收发处理 消息通道的子接口*SubscribableChannel*,由*MessageHandler*消息处理器所订阅
>
>*Channel* 是队列的一种抽象
>
>*Input -> Source -> Channel -> Binder -> MQ组件 -> Binder -> Chanel -> Sink -> Output*

### 入门案例说明

>新建三个子模块
>
>>cloud-stream-rabbitmq-provider8801  #生产者进行发布消息模块
>>
>>cloud-stream-rabbitmq-consumer-8802 #消费者接收模块
>>
>>cloud-stream-rabbitmq-consumer-8803 #消费者接收模块
>
>生产者
>
>>- pom
>>
>>  >```xml
>>  ><dependency>
>>  >    <groupId>org.springframework.boot</groupId>
>>  >    <artifactId>spring-boot-starter-web</artifactId>
>>  ></dependency>
>>  ><dependency>
>>  >    <groupId>org.springframework.cloud</groupId>
>>  >    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
>>  ></dependency>
>>  ><dependency>
>>  >    <groupId>org.springframework.cloud</groupId>
>>  >    <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
>>  ></dependency>
>>  ><dependency>
>>  >    <groupId>org.springframework.boot</groupId>
>>  >    <artifactId>spring-boot-starter-actuator</artifactId>
>>  ></dependency>
>>  >```
>>
>>- 配置文件
>>
>>  >```properties
>>  >server.port=8801
>>  >spring.application.name=cloud-stream-provider
>>  >
>>  >#配置要绑定的 mq 的服务信息
>>  >#设置消息组件类型
>>  >spring.cloud.stream.binders.defaultRabbit.type=rabbit
>>  >#设置rabbitmq的相关环境配置
>>  >spring.cloud.stream.binders.defaultRabbit.environment.spring.rabbitmq.host=localhost
>>  >spring.cloud.stream.binders.defaultRabbit.environment.spring.rabbitmq.port=5672
>>  >spring.cloud.stream.binders.defaultRabbit.environment.spring.rabbitmq.username=guest
>>  >spring.cloud.stream.binders.defaultRabbit.environment.spring.rabbitmq.password=guest
>>  >#服务整合处理
>>  >#使用 exchange 名称定义
>>  >spring.cloud.stream.bindings.output.destination=studyExchange
>>  >#设置消息类型
>>  >spring.cloud.stream.bindings.output.content-type=application/json
>>  >#设置要绑定消息服务的具体设置
>>  >spring.cloud.stream.bindings.output.binder=defaultRabbit
>>  >
>>  >#向注册中心注册自己
>>  >eureka.client.register-with-eureka=true
>>  >eureka.client.fetch-registry=true
>>  >#设置与eureka交互的地址，查询服务和注册服务都需要这个地址
>>  >eureka.client.service-url.defaultZone=http://localhost:7001/eureka/,http://localhost:7002/eureka/
>>  >#设置心跳时间间隔
>>  >eureka.instance.lease-renewal-interval-in-seconds=2
>>  >#超过5s
>>  >eureka.instance.lease-expiration-duration-in-seconds=5
>>  >#主机名称
>>  >eureka.instance.instance-id=send-8801.com
>>  >#访问路径变ip地址
>>  >eureka.instance.prefer-ip-address=true
>>  >```
>>
>>- 业务类
>>
>>  >```java
>>  >//定义消息推送管道
>>  >@Slf4j
>>  >@EnableBinding(Source.class)
>>  >public class IMessageProviderImpl implements IMessageProvider {
>>  >
>>  >    //消息发送管道
>>  >    @Autowired
>>  >    private MessageChannel output;
>>  >
>>  >    @Override
>>  >    public String send() {
>>  >        String serial = UUID.randomUUID().toString();
>>  >        output.send(MessageBuilder.withPayload(serial).build());
>>  >        log.info("*** serial ***"+serial);
>>  >        return null;
>>  >    }
>>  >}
>>  >```
>
>消费者
>
>>pom与配置文件与生产者大致相同，其中配置文件
>>
>>>修改端口号服务名以及其他相关
>>>
>>>服务整合处理相关配置由 *output* 修改为 *input*
>>
>>业务类
>>
>>>```java
>>>@Slf4j
>>>@Component
>>>@EnableBinding(Sink.class)
>>>public class ReceiveMessageController {
>>>
>>>    @Value("${server.port}")
>>>    private String serverPort;
>>>
>>>    @StreamListener(Sink.INPUT)
>>>    public void input(Message<String> message){
>>>        log.info("*** 消费者１号 *** \n"+message.getPayload()+"  port:"+serverPort);
>>>    }
>>>
>>>}
>>>```

### 分组消费与持久化

目前还有俩个问题：重复消费与消息的持久化

#### 重复消费

点击发送消息，8802与8803都可以收到消息，若8802与8803是一个整体，那么就是相当于一个整体消费了俩次消息。例如一个班的班长和副班长分别领了整个班的教材。

>在 Stream 中处于同一个 group 中的多个消费者是竞争关系，就能够保证消息只会被其中一个应用消费一次。
>
>***不同组是可以全面消费的***
>
>***同一组内会发生竞争关系，只有其中一个可以消费***

重复消费故障原因

>![选区_070](/home/lxl/Pictures/选区_070.png)
>
>默认分组 group 是不同的，组流水号不一样，被认为不同组，可以消费。

解决

>自定义配置分组；自定义配置分为同一个组，解决重复消费问题。

*不同组可以消费，同一组会有竞争关系，只有其中一个可以消费*

在配置文件里添加

```properties
spring.cloud.stream.bindings.input.group=woqiyounai-A
```

来指定分组

#### 消息持久化

例如8801持续发送消息，8802和8803宕机，在宕机过程中的消息没有被处理。

## Sleuth:分布式请求链路追踪

在微服务框架中，一个由客户端发起的请求在后端系统中会结果多个不同的服务节点调用来协同产生最后的请求结果，每一个前段请求都会形成一个复杂的分布式服务调用链路，链路中的任何一环延时或错误都会引起整个请求最后的失败。

搭建链路监控步骤

### zipkin-server

>![选区_083](/home/lxl/Pictures/选区_083.png)
>
>Trace：类似于树结构的Span集合，表示一条调用链路，存在唯一标识
>
>Span：表示调用链路来源，通俗的理解span就是一次请求信息

- 整和

  >```pom
  ><dependency>
  >    <groupId>org.springframework.cloud</groupId>
  >    <artifactId>spring-cloud-starter-zipkin</artifactId>
  ></dependency>
  >```

- 配置

  >```properties
  >#zipkin 配置监控地址
  >spring.zipkin.base-url=http://localhost:9411
  >#采样率介于 0-1 之间，1为全部采集
  >spring.zikpin.sampler.probability=1
  >```

- 调用测试查看链路

# SpringCloud Alibaba

springcloud alibaba 能干嘛

![选区_084](/home/lxl/Pictures/选区_084.png)

文档地址： ***https://github.com/alibaba/spring-cloud-alibaba/wiki*** 

## Nacos:服务注册与配置中心

Nacos - naming configuration service：一个更易于构建云原生应用的**动态服务发现**，**配置管理**和服务管理平台。服务注册，配置，Ｂｕｓ

下载安装 nacos，访问 ***http://127.0.0.1:8848/nacos***  看到界面即可。

### 使用 Nacos:作为注册中心

- 父 ***pom*** 文件

  >```xml
  ><dependencyManagement>
  >    <dependencies>
  >        <dependency>
  >            <groupId>com.alibaba.cloud</groupId>
  >            <artifactId>spring-cloud-alibaba-dependencies</artifactId>
  >            <version>2.2.0.RELEASE</version>
  >            <type>pom</type>
  >            <scope>import</scope>
  >        </dependency>
  >    </dependencies>
  ></dependencyManagement>
  >```

- 子工程 pom

  >```xml
  ><dependency>
  >    <groupId>com.alibaba.cloud</groupId>
  >    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
  ></dependency>
  >```

- 配置文件

  >```properties
  >server.port=9002
  >spring.application.name=nacos-payment-provider
  ># Nacos 配置
  >spring.cloud.nacos.discovery.server-addr=localhost:8848
  ># 监控端点
  >management.endpoints.web.exposure.include=*
  >```

- 主启动类添加 ***@EnableDiscoveryClient*** 注解

- 业务类

  >```java
  >@Value("${server.port}")
  >private String serverPort;
  >
  >@GetMapping("/api/payment/port/{id}")
  >public String getServerPort(@PathVariable("id") String id){
  >    return "Nacos - port : "+serverPort;
  >}
  >```


- 消费者注册到注册中心，使用 RestTemplate 来调用

### Nacos的cp与ap的切换

c(一致性)   a(可用性)   p(容错性)

nacos同时支持 ap 与 cp

nacoc服务模式的切换 

```bash
curl -X PUT http:127.0.0.1:8848/nacos/v1/ns/operator/switches?entry=serverMode&value=CP
```

### 使用Nacos:作为配置中心

- 新建 module

- 导入配置

  >```xml
  ><!--   nacos config     -->
  ><dependency>
  >    <groupId>com.alibaba.cloud</groupId>
  >    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
  ></dependency>
  ><!--  SpringCloud alibaba nacos    -->
  ><dependency>
  >    <groupId>com.alibaba.cloud</groupId>
  >    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
  ></dependency>
  >```

- 新建 bootstrap 配置 ...

- Nacos读取配置文件的规则 ***{prefix}-${spring.profile.active}-${file-sxtension}*** 

- 前缀-环境.后缀，当环境为空时，-连接符也将不存在

![选区_085](/home/lxl/Pictures/选区_085.png)

Namespace + Group + Data ID三者关系?为什么这么设计?

![选区_086](/home/lxl/Pictures/选区_086.png)

比如有三个环境：开发，测试，生产环境，我们可以创建三个 Namespace ，不同的 Namespace 之间是隔离的。

Data ID

>用来标示配置文件

分组 Group

>通过配置指定分组
>
>```properties
>spring.cloud.nacos.config.group=DEV_GROUP
>```

命名空间 Namespace，通过命名空间 id 来指定

>```properties
>spring.cloud.nacos.config.namespace=4f56b945-33a7-41cf-a478-e9a86d9c3e9e
>```

## Nacos集群与持久化

![选区_087](/home/lxl/Pictures/选区_087.png)

默认 nacos 使用嵌入式数据库存储数据，所以，启动多个 Nacos 是存在一致性问题的。为了解决这个问题，Nacos采用了集中式存储的方式来支持集群化部署，目前只支持 Mysql 的存储。

架构：1个nginx + 3个nacos + 1个mysql

#### nacos集群配置

修改配置脚本，在本地 3333,4444,5555分别启动nacos，形成集群。

修改 nginx 配置，nginx指定配置文件启动 ***sudo nginx -c /etc/nginx/nginx.conf*** 

启动 ***nginx*** 

![选区_088](/home/lxl/Pictures/选区_088.png)

通过 1111(nginx配置的端口)来访问

通过 nginx 访问到的 nacos 创建一个配置文件，测试访问

测试集群服务注册 (通过 1111)



## Sentinel实现熔断和限流

***sentinel*** 监控和保护你的微服务

hystrix的缺点：需要程序员自己手工搭建平台；没有一个web界面可以给我们进行更加细粒度化的配置流控，速率控制，服务熔断，服务降级。

官网下载：前台8080(用户名密码均为sentinel)，后台

- 建立 pom 文件

  >```xml
  ><!--  SpringCloud alibaba nacos    -->
  ><dependency>
  >    <groupId>com.alibaba.cloud</groupId>
  >    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
  ></dependency>
  ><!--    后续做持久化用到    -->
  ><dependency>
  >    <groupId>com.alibaba.csp</groupId>
  >    <artifactId>sentinel-datasource-nacos</artifactId>
  ></dependency>
  ><!--   sentinel     -->
  ><dependency>
  >    <groupId>com.alibaba.cloud</groupId>
  >    <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
  ></dependency>
  >```

- 配置文件

  >```properties
  >server.port=8401
  >spring.application.name=cloudalibaba-sentinel-service
  >spring.cloud.nacos.discovery.server-addr=127.0.0.1:1111
  >spring.cloud.sentinel.transport.dashboard=127.0.0.1:8080
  >#默认8719端口，假如被占用会自动从8719开始依次+1扫描，直到找到未占用的端口
  >spring.cloud.sentinel.transport.port=9719
  >management.endpoints.web.exposure.include=*
  >```

- 由于sentinel是懒加载机制，需要先访问一次接口才能显示。可以看到实时监控。

### 流控规则

通过sentinel的图形化界面进行配置

![选区_089](/home/lxl/Pictures/选区_089.png)

使用默认规则配置 QPS 为1 ,超过后出现 ***block by sentinel (flow limiting)*** 

QPS 与 线程数

>QPS 是每秒的并发量，线程数是每次同时处理的人数(使用sleep调试)

### 流控模式

>当关联的资源达到阈值时，就限流自己；当与A关联的资源B达到阈值后，就限流A自己。B惹事，A挂了。(关联模式)

![选区_090](/home/lxl/Pictures/选区_090.png)

用于微服务中间的关联服务间的配置使用。

### 流控效果（使用postman测试）

预热，使接口访问平滑增长。

公式：阈值除以coldFactor(默认值为3)，结果预热时长后才会达到阈值。

如系统初始化阈值为10,10/3=3,开始访问阈值为3,一段时间后升到10

匀速排队:让请求匀速通过，对应的是漏桶算法。用于处理间隔性的突发的流量，例如消息队列。

### sentinel降级

RT：平均响应时间

>平均响应时间 超出阈值 且 在时间窗口内通过的请求>=5，俩个条件同时满足后触发降级
>
>窗口期过后关闭断路器
>
>RT最大4900 (更大的需要通过 -Dcsp.sentinel.statistic.max.rt=XXXX 才能生效)
>
>官方文档:
>
>>平均响应时间 (`DEGRADE_GRADE_RT`)：当 1s 内持续进入 N 个请求，对应时刻的平均响应时间（秒级）均超过阈值（`count`，以 ms 为单位），那么在接下的时间窗口（`DegradeRule` 中的 `timeWindow`，以 s 为单位）之内，对这个方法的调用都会自动地熔断（抛出 `DegradeException`）。注意 Sentinel 默认统计的 RT 上限是 4900 ms，**超出此阈值的都会算作 4900 ms**，若需要变更此上限可以通过启动配置项 `-Dcsp.sentinel.statistic.max.rt=xxx` 来配置。

异常比例：

>QPS >= 5 且异常比例 (秒级统计) 超过阈值时，触发降级；时间窗口结束后，关闭降级。
>
>官方文档:
>
>>异常比例 (`DEGRADE_GRADE_EXCEPTION_RATIO`)：当资源的每秒请求量 >= N（可配置），并且每秒异常总数占通过量的比值超过阈值（`DegradeRule` 中的 `count`）之后，资源进入降级状态，即在接下的时间窗口（`DegradeRule` 中的 `timeWindow`，以 s 为单位）之内，对这个方法的调用都会自动地返回。异常比率的阈值范围是 `[0.0, 1.0]`，代表 0% - 100%。

异常数

>异常数 (分钟统计) 超过阈值时，触发降级；时间窗口结束后，关闭降级。
>
>官方文档:
>
>>异常数 (`DEGRADE_GRADE_EXCEPTION_COUNT`)：当资源近 1 分钟的异常数目超过阈值之后会进行熔断。注意由于统计时间窗口是分钟级别的，若 `timeWindow` 小于 60s，则结束熔断状态后仍可能再进入熔断状态。

sentinel 熔断降级会在调用链路中某个资源出现不稳定状态时(调用超时或异常比例升高)，对这个资源的调用进行限制，让请求快速失败，避免影响到其他的资源而导致级联错误。

当资源被降级后，在接下来的降级时间窗口之内，对该资源的调用都自动熔断。

***sentinel 的断路器是没有半开状态的***　

### 热点 key 限流

热点：热点就是经常访问的数据。很多时候我们希望统计某个热点数据中访问频次最高的 Top K 数据，并对其进行访问进行限制。

- 商品 id 为参数，统计一段时间内最常购买的商品 id 并进行限制。
- 用户 id 为参数，统计一段时间内频繁访问的用户 id 并进行限制。

***注意*** : 参数必须是基本类型或者String

***@SentinalResource*** : 

>```java
>@SentinelResource(
>    //资源名
>        value = "testHotKey", 
>    //处理错误的方法
>        blockHandler = "dealTestHotKey"
>)
>```

### 系统保护规则

总理整个系统的 QPS 

### ***SentinelResource配置***

```java
@GetMapping("/api/byResource")
@SentinelResource(value = "byResource",blockHandler = "handleException")
public CommonResponse byResource(){
    return new CommonResponse(200,"按资源名称限流测试ok",new Payment(2020L,"serial001"));
}

   @GetMapping("/api/byUrl")
    @SentinelResource(value = "byUrl",blockHandler = "handleException")
    public CommonResponse byUrl(){
        return new CommonResponse(200,"按URL限流测试ok",new Payment(2020L,"serial001"));
    }

public CommonResponse handleException(BlockException e){
    return new CommonResponse(444,e.getClass().getCanonicalName()+"\t服务不可用");
}
```

- 系统默认的，没有体现自己的业务要求
- 依照现有条件，我们自定义的处理方法又和业务代码耦合在一块，不直观
- 每个业务方法都添加一个兜底的，代码膨胀加剧
- 全局统一的处理方法没有体现

自定义，自己编写 handler 类

>```java
>public class MyHandler {
>
>    public static CommonResponse handlerException1(BlockException exception){
>        return new CommonResponse(000,"按客户自定义的处理 - 1",new Payment(2020L,"serial001"));
>    }
>
>    public static CommonResponse handlerException2(BlockException exception){
>        return new CommonResponse(000,"按客户自定义的处理 - 2",new Payment(2020L,"serial001"));
>    }
>}
>```

通过 blockHandlerClass 属性来指定哪一个类，通过 blockHandler 来指定哪一个方法。

```java
@GetMapping("/api/customerBlockHandler")
@SentinelResource(value = "customerBlockHandler",blockHandlerClass = MyHandler.class,blockHandler = "handlerException2")
public CommonResponse customerBlockHandler(){
    return new CommonResponse(200,"按U客户自定义限流测试ok",new Payment(2020L,"serial001"));
}
```

![选区_091](/home/lxl/Pictures/选区_091.png)

### sentinel整合ribbon和openfeign+fallback

>```java
>@SentinelResource(
>        value = "fallback",
>        blockHandler = "blockHandlerMethod",
>        fallback = "handlerFallbackMethod",
>        //忽略某个异常
>        exceptionsToIgnore = IllegalArgumentException.class
>)
>```
>
>使用 8000 消费者调用 (9003,9004) 服务提供者，当服务提供者挂掉后，消费者端会直接服务降级。

### 持久化

- 添加 pom

  >```xml
  ><!--    后续做持久化用到    -->
  ><dependency>
  >    <groupId>com.alibaba.csp</groupId>
  >    <artifactId>sentinel-datasource-nacos</artifactId>
  ></dependency>
  >```

- 添加配置

  >```properties
  >#持久化配置
  >spring.cloud.nacos.datasource.ds1.nacos.server-addr:127.0.0.1:8848
  >spring.cloud.nacos.datasource.ds1.nacos.dataId=cloudalibaba-sentinel-service
  >spring.cloud.nacos.datasource.ds1.nacos.groupId=DEFAULT_GROUP
  >spring.cloud.nacos.datasource.ds1.nacos.data-type=json
  >spring.cloud.nacos.datasource.ds1.nacos.rule-type=flow
  >```

- 在 Nacos 界面使用该groupId添加配置

  >```json
  >[
  >    {
  >        "resource":"/rateLimit/byUrl",
  >        "limitApp":"default",
  >        "grade":1,
  >        "count":1,
  >        "strategy":0,
  >        "controlBehavior":0,
  >        "clusterMode":false
  >    }
  >]
  >```

- ![选区_092](/home/lxl/Pictures/选区_092.png)

- 启动 8401 刷新 sentinel 后就发现有了流控规则

## ***Seata***处理分布式事务

分布式问题的提出

传统一对一项目没有这种问题。当数据库分库后会出现一对多问题，允许程序多数据源。微服务专库专连。

![选区_093](/home/lxl/Pictures/选区_093.png)

跨数据源，多数据库的统一调度。一次业务操作需要***跨多个数据源***或需要***跨多个系统***进行远程调用，就会产生分布式事务问题。

![Seata solution](https://static.oschina.net/uploads/img/201904/04074057_GRX3.jpg)

### seata是什么

>seata是一款开源的分布式事务解决方案，致力于在微服务架构下提供高性能和简单易用的分布式事务服务。
>
>官网地址：　http://seata.io/zh-cn/
>
>
>
>一个ID + 三组件概念
>
>ID:全局唯一的事务id
>
>#### TC (Transaction Coordinator) - 事务协调者
>
>维护全局和分支事务的状态，驱动全局事务提交或回滚。
>
>#### TM (Transaction Manager) - 事务管理器
>
>定义全局事务的范围：开始全局事务、提交或回滚全局事务。
>
>#### RM (Resource Manager) - 资源管理器
>
>管理分支事务处理的资源，与TC交谈以注册分支事务和报告分支事务的状态，并驱动分支事务提交或回滚。
>
>![选区_094](/home/lxl/Pictures/选区_094.png)
>
>- TM 向 TC 申请开启一个全局事务，全局事务创建成功并生成一个全局唯一的 XID
>- XID 在微服务的上下文中传播
>- RM 向 TC 注册分支事务，将其纳入全局 XID 对应全局事务
>- TM 向 TC 发起针对 XID 的全局提交或回滚决议
>- TC 调度 XID 下管辖的全部分支事务完成提交或回滚请求

### seata的使用

#### 修改fille.conf

>主要修改：自定义事务组名称+事务日志存储模式为db+数据库连接信息
>
>```json
>service {
>  #transaction service group mapping
>  vgroup_mapping.my_test_tx_group = "fsp_tx_group"
>  #only support when registry.type=file, please don't set multiple addresses
>  default.grouplist = "127.0.0.1:8091"
>  #disable seata
>  disableGlobalTransaction = false
>}
>## transaction log store, only used in seata-server
>store {
>  ## store mode: file、db
>  mode = "db"
>  ## file store property
>  file {
>    ## store location dir
>    dir = "sessionStore"
>  }
>  ## database store property
>  db {
>    ## the implement of javax.sql.DataSource, such as DruidDataSource(druid)/BasicDataSource(dbcp) etc.
>    datasource = "dbcp"
>    ## mysql/oracle/h2/oceanbase etc.
>    db-type = "mysql"
>    driver-class-name = "com.mysql.jdbc.Driver"
>    url = "jdbc:mysql://127.0.0.1:3306/seata"
>    user = "root"
>    password = "root"
>  }
>}
>```
>
>修改 registry.conf 使用 nacos

使用seata : 只需要使用一个 ***@GlobalTransactional*** 注解即可

#### 说明

三个服务（订单、库存、账户）

>当用户下单时，会在订单服务中创建一个订单，然后通过远程调用库存服务来扣减下单商品的库存
>
>再通过远程调用账户服务来扣减用户账户里面的余额
>
>最后在订单服务中修改订单状态为已完成

该操作跨越三个数据库，有俩次远程调用，会有分布式事务问题。

(下订单　扣库存　减余额)

建sql和回滚日志表

>```mysql
>CREATE DATABASE seata_order;
>USE seata_order;
>CREATE TABLE t_order(
>    id BIGINT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY ,
>    user_id BIGINT(11) DEFAULT NULL COMMENT '用户id',
>    product_id BIGINT(11) DEFAULT NULL COMMENT '产品id',
>    count INT(11) DEFAULT NULL COMMENT '数量',
>    money DECIMAL(11,0) DEFAULT NULL COMMENT '金额',
>    status INT(1) DEFAULT NULL COMMENT '订单状态：0创建中，1已完结'
>)ENGINE=InnoDB AUTO_INCREMENT=7 CHARSET=utf8;
>
>CREATE DATABASE seata_storage;
>USE seata_storage;
>CREATE TABLE t_storage(
>    id BIGINT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY ,
>    product_id BIGINT(11) DEFAULT NULL COMMENT '产品id',
>    total INT(11) DEFAULT NULL COMMENT '总库存',
>    used INT(11) DEFAULT NULL COMMENT '已用库存',
>    residue INT(11) DEFAULT NULL COMMENT '剩余库存'
>)ENGINE=InnoDB AUTO_INCREMENT=7 CHARSET=utf8;
>INSERT INTO t_storage(id, product_id, total, used, residue) VALUES(1,1,100,0,100);
>
>CREATE DATABASE seata_account;
>USE seata_account;
>CREATE TABLE t_account(
>    id BIGINT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY ,
>    user_id BIGINT(11) DEFAULT NULL COMMENT '用户id',
>    total DECIMAL(10,0) DEFAULT NULL COMMENT '总额度',
>    used DECIMAL(10,0) DEFAULT NULL COMMENT '已用额度',
>    residue DECIMAL(10,0) DEFAULT 0 COMMENT '剩余可用额度'
>)ENGINE=InnoDB AUTO_INCREMENT=7 CHARSET=utf8;
>INSERT INTO t_account(id, user_id, total, used, residue) VALUES(1,1,1000,0,1000);
>
>use seata_storage;
>CREATE TABLE `undo_log` (
>                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
>                            `branch_id` bigint(20) NOT NULL,
>                            `xid` varchar(100) NOT NULL,
>                            `context` varchar(128) NOT NULL,
>                            `rollback_info` longblob NOT NULL,
>                            `log_status` int(11) NOT NULL,
>                            `log_created` datetime NOT NULL,
>                            `log_modified` datetime NOT NULL,
>                            `ext` varchar(100) DEFAULT NULL,
>                            PRIMARY KEY (`id`),
>                            UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
>) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
>```

#### 微服务构建与测试

>略 ......
>

下订单(port:2001) --> 扣库存(port:2002) --> 减余额(port:2003) --> 更新订单状态(port:2001)

>在扣减余额时添加一个超时异常
>
>>现象：当库存和账户金额扣减后，订单状态并没有设置为已完成，没有从0改为1
>>
>>而且由于 feign 的重试机制，账户余额还可能被多次扣减
>
>但是在业务上添加 
>
>```java
>@GlobalTransactional(name = "fsp-create-order",rollbackFor = Exception.class)
>```
>
>注解，即可在出错时候完成数据的回滚。

### seata原理解析

seata:简单可拓展自治事务框架

TC:seata服务器 -> TM:添加注解``@GlobalTransactional``的事务发起方 -> RM:事务的参与方,数据库s

- TM开启分布式事务(TM向TC注册全局事务记录)
- 按业务场景，编排数据库，服务等事务内资源(RM向TC汇报资源准备状态)
- TM结束分布式事务，事务一阶段结束(TM通知TC 提交/回滚分布式事务)
- TC 汇总事务信息，决定分布式事务是提交还是回滚
- TC 通知所有 RM 提交/回滚资源，事务二阶段结束

AT模式

一阶段：业务数据和回滚日志记录在同一个本地事务中提交，释放本地锁和连接资源

>拦截业务SQL，保存before image，执行业务SQL，保存after image，生成行锁

二阶段：提交异步化，非常快速的完成；回滚一阶段的回滚日志进行反向补偿

>提交成功，则删除before image,after image,行锁完成业务
>
>提交失败，先校验脏读，再根据原SQL生成逆向SQL执行还原数据，再删除before image,after image,行锁

事了拂衣去，深藏功与名~~~

![选区_096](/home/lxl/Pictures/选区_096.png)







