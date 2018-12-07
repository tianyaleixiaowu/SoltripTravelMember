package code.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CaseFormat;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * @author Bai
 * 代码生成器，根据数据表名称生成对应的Model、Mapper、Service、Controller简化开发。
 */
public class CodeGenerator {

    // JDBC配置，请修改为你项目的实际配置
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/yg";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "root";
    private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    // 模板位置
    private static final String TEMPLATE_FILE_PATH = "src/test/java/resources/template/generator";
    private static final String JAVA_PATH = "src/test/java"; // java文件路径
    private static final String RESOURCES_PATH = "src/test/resources";// 资源文件路径
    // 生成的Service存放路径
    private static final String PACKAGE_PATH_SERVICE = packageConvertPath(ProjectConstant.SERVICE_PACKAGE);
    // 生成的Controller存放路径
    private static final String PACKAGE_PATH_CONTROLLER = packageConvertPath(ProjectConstant.CONTROLLER_PACKAGE);
    //生成的domain存放路径
    private static final String PACKAGE_PATH_MODEL = packageConvertPath(ProjectConstant.MODEL_PACKAGE);


    // @author
    private static final String AUTHOR = "Bai";
    // @date
    private static final String DATE = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date());

    /**
     * genCode("输入表名");
     *
     * @param args
     */
    public static void main(String[] args) {
        genCode("m_companytravelsetting");
    }

    /**
     * 通过数据表名称生成代码，Model 名称通过解析数据表名称获得，下划线转大驼峰的形式。
     * 如输入表名称 "t_user_detail" 将生成
     * TUserDetail、TUserDetailMapper、TUserDetailService ...
     *
     * @param tableNames 数据表名称...
     */
    public static void genCode(String... tableNames) {
        for (String tableName : tableNames) {
            genCode(tableName);
        }
    }

    /**
     * 通过数据表名称生成代码 如输入表名称 "user_info"
     * 将生成 UserInfo、UserInfoMapper、UserInfoService ...
     *
     * @param tableName 数据表名称
     */
    public static void genCode(String tableName) {
        genModelAndMapper(tableName);
        genService(tableName);
        genController(tableName);
    }

    public static void genModelAndMapper(String tableName) {
        Context context = getContext();
        //连接
        JDBCConnectionConfiguration jdbcConnectionConfiguration = getJDBCConnectionConfiguration();
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        //java模型
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = getJavaModelGeneratorConfiguration();
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);
        //生成domain
        ModelDomain(tableName);
        VO(tableName);
        //生成xml
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = getSqlMapGeneratorConfiguration();
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);
        //生成mapxml对应client，也就是接口dao
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = getJavaClientGeneratorConfiguration();
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);
        //设置是否生成注释
        CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
        commentGeneratorConfiguration.addProperty("suppressAllComments", "true");
        commentGeneratorConfiguration.addProperty("suppressDate", "true");
        //对应数据库的实体注释
        commentGeneratorConfiguration.setConfigurationType("code.example.demo.MyCommentGenerator");
        context.setCommentGeneratorConfiguration(commentGeneratorConfiguration);
        //是否使用bigDecimal， false可自动转化以下类型（Long, Integer, Short, etc.）
        JavaTypeResolverConfiguration javaTypeResolverConfiguration = new JavaTypeResolverConfiguration();
        javaTypeResolverConfiguration.addProperty("forceBigDecimals", "false");
        context.setJavaTypeResolverConfiguration(javaTypeResolverConfiguration);


        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableName);
        tableConfiguration.setDomainObjectName(null);
        //去除Example动态实体
        tableConfiguration.setCountByExampleStatementEnabled(false);
        tableConfiguration.setUpdateByExampleStatementEnabled(false);
        tableConfiguration.setDeleteByExampleStatementEnabled(false);
        tableConfiguration.setSelectByExampleStatementEnabled(false);
        context.addTableConfiguration(tableConfiguration);


        List<String> warnings;
        MyBatisGenerator generator;
        try {
            Configuration config = new Configuration();
            config.addContext(context);
            config.validate();
            boolean overwrite = true;
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            warnings = new ArrayList<>();
            generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);
        } catch (Exception e) {
            throw new RuntimeException("生成Model和Mapper失败", e);
        }

        if (generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
            throw new RuntimeException("生成Model和Mapper失败：" + warnings);
        }
        String modelName = tableNameConvertUpperCamel(tableName);
        System.out.println(modelName + ".java 生成成功");
        System.out.println(modelName + "Mapper.java 生成成功");
        System.out.println(modelName + "Mapper.xml 生成成功");
        System.out.println(modelName + "Domain.xml 生成成功");
    }

    /**
     * 模板生成service 注释为实现类生成可以不需要
     *
     * @param tableName 表名称
     */
    public static void genService(String tableName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = tableNameConvertUpperCamel(tableName);
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", ProjectConstant.BASE_PACKAGE);
            data.put("basePackageService", ProjectConstant.SERVICE_PACKAGE);
            data.put("basePackageModel", ProjectConstant.MODEL_PACKAGE);
            data.put("basePackageDao", ProjectConstant.MAPPER_PACKAGE);

            File file = new File(JAVA_PATH + PACKAGE_PATH_SERVICE + modelNameUpperCamel + "Service.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("service.ftl").process(data, new FileWriter(file));
            System.out.println(modelNameUpperCamel + "Service.java 生成成功");

        } catch (Exception e) {
            throw new RuntimeException("生成Service失败", e);
        }
    }

    /**
     * 模板生成controller
     *
     * @param tableName 表名
     */
    public static void genController(String tableName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = tableNameConvertUpperCamel(tableName);
            data.put("baseRequestMapping", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", ProjectConstant.BASE_PACKAGE);
            data.put("basePackageController", ProjectConstant.CONTROLLER_PACKAGE);
            data.put("basePackageService", ProjectConstant.SERVICE_PACKAGE);
            data.put("basePackageModel", ProjectConstant.MODEL_PACKAGE);

            File file = new File(JAVA_PATH + PACKAGE_PATH_CONTROLLER + modelNameUpperCamel + "Controller.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("controller.ftl").process(data, new FileWriter(file));

            System.out.println(modelNameUpperCamel + "Controller.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Controller失败", e);
        }

    }

    /**
     * 生成初始化类
     */
    private static void ModelDomain(String tableName) {
        try {
            freemarker.template.Configuration cfg = getConfiguration();
            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = tableNameConvertUpperCamel(tableName);
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", ProjectConstant.BASE_PACKAGE);
            data.put("basePackageModel", ProjectConstant.MODEL_PACKAGE);

            File file = new File(JAVA_PATH + PACKAGE_PATH_MODEL + modelNameUpperCamel + "Domain.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("domain.ftl").process(data, new FileWriter(file));
            System.out.println(modelNameUpperCamel + "Domain.java 生成成功");

        } catch (Exception e) {
            throw new RuntimeException("生成Domain失败", e);
        }
    }

    private static Context getContext() {
        Context context = new Context(ModelType.FLAT);
        context.setId("Potato");
        //设置targetRuntime=MyBatis3。如果targetRuntime=MyBatis3Simple将不会生成sample动态代码.
        context.setTargetRuntime("MyBatis3");
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");
        return context;
    }

    private static JDBCConnectionConfiguration getJDBCConnectionConfiguration() {
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
        jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
        jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
        jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
        return jdbcConnectionConfiguration;
    }

    /**
     * java 实体 生成
     *
     * @return
     */
    private static JavaModelGeneratorConfiguration getJavaModelGeneratorConfiguration() {
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(JAVA_PATH);
        javaModelGeneratorConfiguration.setTargetPackage(ProjectConstant.MODEL_PACKAGE);
        javaModelGeneratorConfiguration.addProperty("enableSubPackages", "false");
        javaModelGeneratorConfiguration.addProperty("trimStrings", "true");
        return javaModelGeneratorConfiguration;
    }

    /**
     * mapper.xml生成
     *
     * @return
     */
    private static SqlMapGeneratorConfiguration getSqlMapGeneratorConfiguration() {
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(RESOURCES_PATH);
        sqlMapGeneratorConfiguration.setTargetPackage("mybatis/mapper");//路径配置
        sqlMapGeneratorConfiguration.addProperty("enableSubPackages", "false");
        return sqlMapGeneratorConfiguration;
    }

    /**
     * mapper生成
     *
     * @return
     */
    private static JavaClientGeneratorConfiguration getJavaClientGeneratorConfiguration() {
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetProject(JAVA_PATH);
        javaClientGeneratorConfiguration.setTargetPackage(ProjectConstant.MAPPER_PACKAGE);
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        javaClientGeneratorConfiguration.addProperty("enableSubPackages", "false");
        return javaClientGeneratorConfiguration;
    }

    /**
     * 模板引擎
     *
     * @return
     * @throws IOException
     */
    private static freemarker.template.Configuration getConfiguration() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new File(TEMPLATE_FILE_PATH));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }
    /*
    驼峰命名转换
     */
    private static String tableNameConvertUpperCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());
    }
    //转换.和/
    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }

    /*
    生成vo实体
     */
    private static void VO(String tableName){
        try {
            Class.forName(JDBC_DIVER_CLASS_NAME);
            Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, "%", tableName, "%");

            List<ColumnClass> columnClassList = new ArrayList<>();
            ColumnClass columnClass = null;
            while(resultSet.next()){
                //id字段略过
                if(resultSet.getString("COLUMN_NAME").equals("id")) continue;
                columnClass = new ColumnClass();
                //获取字段名称
                columnClass.setColumnName(resultSet.getString("COLUMN_NAME"));
                //获取字段类型
                columnClass.setColumnType(resultSet.getString("TYPE_NAME"));
                //转换字段名称，如 sys_name 变成 SysName
                columnClass.setChangeColumnName(tableNameConvertUpperCamel(resultSet.getString("COLUMN_NAME")));
                //字段在数据库的注释
                columnClass.setColumnComment(resultSet.getString("REMARKS"));
                columnClassList.add(columnClass);
            }
            String s = JSONObject.toJSONString(columnClassList);
            System.err.println(s);
            freemarker.template.Configuration cfg = getConfiguration();
            Map<String, Object> data = new HashMap<>();
            data.put("model_column",columnClassList);
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = tableNameConvertUpperCamel(tableName);
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("basePackage", ProjectConstant.BASE_PACKAGE);
            data.put("basePackageModel", ProjectConstant.MODEL_PACKAGE);

            File file = new File(JAVA_PATH + PACKAGE_PATH_MODEL + modelNameUpperCamel + "VO.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("vo.ftl").process(data, new FileWriter(file));
            System.out.println(modelNameUpperCamel + "VO.java 生成成功");

        } catch (SQLException e) {
            throw new RuntimeException("数据连接获取失败", e);
        }catch (IOException e) {
            throw new RuntimeException("生成vo失败", e);
        }catch (TemplateException e) {
            throw new RuntimeException("生成vo失败", e);
        }catch (Exception e){
            throw new RuntimeException("className错误",e);
        }

    }


}
