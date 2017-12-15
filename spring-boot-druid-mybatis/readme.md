ORM框架主要有两个阵营：hibernate和mybatis

- 传统企业比较喜欢用hibernate，因为hibernate对数据库操作封装的比较完整，可以使开发者不必写sql语句，节省时间；
- 新兴的互联网行业喜欢mybatis，因为mybatis能够灵活动态的编写sql语句，对于复杂业务可以方便编写；

今天这里讲的是mybatis的使用，许多人可能会觉得mybatis的xml比较麻烦，但在spring boot整合中有了两种方式。
## 准备工程
### Maven依赖
新建的工程pom文件中加入：
````
    <!--导入mybatis-->
	<dependency>
		<groupId>org.mybatis.spring.boot</groupId>
		<artifactId>mybatis-spring-boot-starter</artifactId>
		<version>1.3.1</version>
	</dependency>
	<!--druid连接池-->
	<dependency>
		<groupId>com.alibaba</groupId>
		<artifactId>druid-spring-boot-starter</artifactId>
		<version>1.1.5</version>
	</dependency>
	<dependency>
		<groupId>mysql</groupId>
		<artifactId>mysql-connector-java</artifactId>
		<scope>runtime</scope>
	</dependency>
	<!--pagehelper分页-->
	<dependency>
		<groupId>com.github.pagehelper</groupId>
		<artifactId>pagehelper-spring-boot-starter</artifactId>
		<version>1.2.3</version>
	</dependency>
````
### 创建数据库
````打开数据库-> 新建数据库 —> springboot -> 导入sql文件 -> 选择custome.sql -> 运行````

### 配置文件
在application.properties中加入druid连接池的配置信息
````
# 数据库访问配置
# 主数据源，默认的
#spring.datasource.druid.type=com.alibaba.druid.pool.DruidDataSource
spring.datasource.druid.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.druid.url=jdbc:mysql://localhost:3306/springboot?useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&useSSL=false
spring.datasource.druid.username=root
spring.datasource.druid.password=123456
# 下面为连接池的补充设置，应用到上面所有数据源中
# 初始化大小，最小，最大
spring.datasource.druid.initial-size=5
spring.datasource.druid.minIdle=5
spring.datasource.druid.maxActive=20
# 配置获取连接等待超时的时间
spring.datasource.druid.maxWait=60000
# 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
spring.datasource.druid.timeBetweenEvictionRunsMillis=60000
# 配置一个连接在池中最小生存的时间，单位是毫秒
spring.datasource.druid.minEvictableIdleTimeMillis=300000
spring.datasource.druid.validationQuery=SELECT 1 FROM DUAL
spring.datasource.druid.testWhileIdle=true
spring.datasource.druid.testOnBorrow=false
spring.datasource.druid.testOnReturn=false
# 打开PSCache，并且指定每个连接上PSCache的大小
spring.datasource.druid.poolPreparedStatements=true
spring.datasource.druid.maxPoolPreparedStatementPerConnectionSize=20
# 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
spring.datasource.druid.filters=stat,wall,log4j
# 通过connectProperties属性来打开mergeSql功能；慢SQL记录
spring.datasource.druid.connectionProperties=druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
# 合并多个DruidDataSource的监控数据
#spring.datasource.druid.useGlobalDataSourceStat=true

# WebStatFilter配置，说明请参考Druid Wiki，配置_配置WebStatFilter

#是否启用StatFilter默认值true
spring.datasource.druid.web-stat-filter.enabled=true
spring.datasource.druid.web-stat-filter.url-pattern=/*
spring.datasource.druid.web-stat-filter.exclusions=*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*
spring.datasource.druid.web-stat-filter.session-stat-enable=true
spring.datasource.druid.web-stat-filter.session-stat-max-count=1000
#spring.datasource.druid.web-stat-filter.principal-session-name=
#spring.datasource.druid.web-stat-filter.principal-cookie-name=
spring.datasource.druid.web-stat-filter.profile-enable=true

# StatViewServlet配置，说明请参考Druid Wiki，配置_StatViewServlet配置

#是否启用StatViewServlet默认值true
spring.datasource.druid.stat-view-servlet.enabled=true
spring.datasource.druid.stat-view-servlet.url-pattern=/druid/*
spring.datasource.druid.stat-view-servlet.reset-enable=true
spring.datasource.druid.stat-view-servlet.login-username=qfwang
spring.datasource.druid.stat-view-servlet.login-password=123456
#spring.datasource.druid.stat-view-servlet.allow=
#spring.datasource.druid.stat-view-servlet.deny=

# ========= pagehelper分页插件配置 start ===========
pagehelper.helperDialect=mysql
pagehelper.reasonable=true
pagehelper.supportMethodsArguments=true
pagehelper.params=count=countSql
# ========= pagehelper分页插件配置 end =============
````
### 创建实体类
````
/**
 * @author flyhero
 */
public class Customer implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private static final long serialVersionUID = 1L;

    //getter and setter
}
````
## 注解方式
### 配置mybatis扫描
在配置文件中添加即可。
````
# ================== Mybatis 配置 start =====================
#entity扫描的包名
mybatis.typeAliasesPackage=cn.iflyapi.entity
# ================== Mybatis 配置 end =======================
````

### 创建dao层mapper
````
@Mapper
public interface CustomerMapper {

    @Select("select * from customer where last_name = #{lastName}")
    @Results({
            @Result(property = "firstName",column = "first_name"),
            @Result(property = "lastName",column = "last_name")
    })
    Customer findByLastName(String lastName);

    @Insert("insert into customer(first_name,last_name) values (#{firstName},#{lastName})")
    void saveCustomer(Customer customer);

    @Update("update customer set first_name = #{firstName},last_name = #{lastName} where id = #{id}")
    void updateCustomer(Customer customer);

    @Delete("delete from customer where id = #{id}")
    void deleteCustomer(Long id);
}
````
### 所需注解
名称|作用
----|----
@Select|查询语句所使用的注解
@Results|修饰返回的结果集（数据库字段与实体属性对应）
@Insert|插入语句
@Update|更新语句
@Delete|删除语句
### 测试
````
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerAnnotationTests {

	@Autowired
	private CustomerMapper customerMapper;

	@Test
	public void testFindByLastName(){
		Customer customer =customerMapper.findByLastName("fei");
		Assert.assertNotNull(customer);
		System.out.println(customer.toString());
	}
	@Test
	public void testSaveCustomer(){
		Customer customer = new Customer();
		customer.setFirstName("hang");
		customer.setLastName("zhang");
		customerMapper.saveCustomer(customer);
	}
	@Test
	public void testUpdateCustomer(){
		Customer customer = new Customer();
		customer.setId(3L);
		customer.setFirstName("fei");
		customer.setLastName("zhang");
		customerMapper.updateCustomer(customer);
	}
	@Test
	public void testDeleteCustomer(){
		customerMapper.deleteCustomer(4L);
	}
}
````
## XML方式
### 创建xml文件
在resources资源目录下创建文件夹mapper -> 创建CustomerMapper.xml
````
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.dfocus.justmeeting.dao.mapper.CustomerMapper">
  <resultMap id="BaseResultMap" type="com.dfocus.justmeeting.dao.entity.Customer">
    <id column="id" jdbcType="BIGINT" property="id" />
    <result column="first_name" jdbcType="VARCHAR" property="firstName" />
    <result column="last_name" jdbcType="VARCHAR" property="lastName" />
  </resultMap>
  <sql id="Base_Column_List">
    id, first_name, last_name
  </sql>
  <select id="selectByPrimaryKey" parameterType="java.lang.Long" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from customer
    where id = #{id,jdbcType=BIGINT}
  </select>
  <select id="findAll" resultMap="BaseResultMap">
    select
    <include refid="Base_Column_List" />
    from customer
  </select>
  <delete id="deleteByPrimaryKey" parameterType="java.lang.Long">
    delete from customer
    where id = #{id,jdbcType=BIGINT}
  </delete>
  <insert id="insert" parameterType="com.dfocus.justmeeting.dao.entity.Customer">
    insert into customer (id, first_name, last_name
      )
    values (#{id,jdbcType=BIGINT}, #{firstName,jdbcType=VARCHAR}, #{lastName,jdbcType=VARCHAR}
      )
  </insert>
  <insert id="insertSelective" parameterType="com.dfocus.justmeeting.dao.entity.Customer">
    insert into customer
    <trim prefix="(" suffix=")" suffixOverrides=",">
      <if test="id != null">
        id,
      </if>
      <if test="firstName != null">
        first_name,
      </if>
      <if test="lastName != null">
        last_name,
      </if>
    </trim>
    <trim prefix="values (" suffix=")" suffixOverrides=",">
      <if test="id != null">
        #{id,jdbcType=BIGINT},
      </if>
      <if test="firstName != null">
        #{firstName,jdbcType=VARCHAR},
      </if>
      <if test="lastName != null">
        #{lastName,jdbcType=VARCHAR},
      </if>
    </trim>
  </insert>
  <update id="updateByPrimaryKeySelective" parameterType="com.dfocus.justmeeting.dao.entity.Customer">
    update customer
    <set>
      <if test="firstName != null">
        first_name = #{firstName,jdbcType=VARCHAR},
      </if>
      <if test="lastName != null">
        last_name = #{lastName,jdbcType=VARCHAR},
      </if>
    </set>
    where id = #{id,jdbcType=BIGINT}
  </update>
  <update id="updateByPrimaryKey" parameterType="com.dfocus.justmeeting.dao.entity.Customer">
    update customer
    set first_name = #{firstName,jdbcType=VARCHAR},
      last_name = #{lastName,jdbcType=VARCHAR}
    where id = #{id,jdbcType=BIGINT}
  </update>
</mapper>
````
### 配置mybatis扫描
在配置文件中添加即可。
````
# ================== Mybatis 配置 start =====================
#entity扫描的包名
mybatis.typeAliasesPackage=cn.iflyapi.entity
#Mapper.xml所在的位置
mybatis.mapperLocations=classpath:mapper/*.xml
# ================== Mybatis 配置 end =======================
````

### 创建dao层mapper
````
@Mapper
public interface CustomerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    List<Customer> findAll();
}
````
### 测试
````
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerXMLTests {

	@Autowired
	private CustomerMapper customerMapper;

	@Test
	public void testSave(){
		Customer customer = new Customer();
		customer.setFirstName("fei");
		customer.setLastName("wang");
		Assert.assertNotNull(customer);
		customerMapper.insertSelective(customer);
		System.out.println(customer.toString());
	}

	@Test
	public void testFind(){
		Customer customer = customerMapper.selectByPrimaryKey(1L);
		Assert.assertNotNull(customer);
		System.out.println(customer.toString());
	}
	
	@Test
	public void testFindAll(){
		PageHelper.startPage(1,2);
		List<Customer> list = customerMapper.findAll();
		PageInfo pageInfo = new PageInfo(list);
		Assert.assertNotNull(list);
		System.out.println(pageInfo.toString());
		list.forEach(customer -> {
			System.out.println(customer.toString());
		});
        //这里使用JDK8特性,分页操作
		PageInfo<Customer> page = PageHelper.startPage(2, 2).doSelectPageInfo(()-> customerMapper.findAll());
		Assert.assertNotNull(page);
		System.out.println(page.getPageSize());
		System.out.println(page.toString());
	}
}

````
## 对比两种方式

两种方式各有特点。
- 注解方式适合简单快速的模式，如果你的项目耦合度比较低或者你使用了微服务，可能不太会用到多表连接查询等，那么这种模式很适合。

- XML方式比适合大型项目或者牵涉统计分析业务，可以灵活的动态生成SQL，方便调整SQL，这时就考验开发者的sql功底了，进行sql吧。


