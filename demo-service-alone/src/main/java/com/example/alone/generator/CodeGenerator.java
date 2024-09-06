package com.example.alone.generator;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.alone.generator.dto.FieldDto;
import com.example.alone.generator.dto.TableDto;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author diaoyn
 * @ClassName Codeeg
 * @Date 2024/9/4 13:31
 */
public class CodeGenerator {

    private static final String CREATE_USER_KEY = "CREATE_USER";

    private static final String CREATE_TIME_KEY = "CREATE_TIME";

    private static final String UPDATE_USER_KEY = "UPDATE_USER";

    private static final String UPDATE_TIME_KEY = "UPDATE_TIME";
    private static final String DELETE_FLAG_KEY = "DELETE_FLAG";

//
//    public static Connection getConn(String url, String username, String password) throws Exception {
////        Class.forName("com.mysql.cj.jdbc.Driver");
//        return DriverManager.getConnection(url, username, password);
//    }
//
//    public static void excute(String url, String username, String password) throws Exception{
//        DataSource dataSource =
//        Connection connection =  getConn(url, username, password);
//        connection.nativeSQL();
//    }

    public static void execute(String url, String user, String password, String packageName, String... tableNames) {
        try {

            GroupTemplate groupTemplate = new GroupTemplate(new ClasspathResourceLoader("backend2"),
                    Configuration.defaultConfiguration());
            Template templateBackend = groupTemplate.getTemplate("Entity.java.btl");
            List<TableDto> tableDtoList = DataSourceHuTool.getTable(url, user, password, tableNames);
            for (TableDto tableDto : tableDtoList) {
                List<FieldDto> fieldDtoList = DataSourceHuTool.getField(url, user, password, tableDto.getTableName());
                Map<String, Object> bindMap = initBinding(packageName, tableDto, fieldDtoList);
                templateBackend.binding(bindMap);



                System.out.println("templateBackend.render() = " + templateBackend.render());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> initBinding(String packageName, TableDto tableDto, List<FieldDto> fieldDtoList) {
        Map<String, Object> map = new HashMap<>();
        // 代码模块名
//        map.put("moduleName", genBasic.getModuleName());
        // 功能名
//        map.put("functionName", genBasic.getFunctionName());
        // 业务名
//        map.put("busName", genBasic.getBusName());
        // 包名
        map.put("packageName", packageName);


//        // 类首字母小写名
//        map.put("classNameFirstLower", StrUtil.lowerFirst(genBasic.getClassName()));
//        // 主键名
//        map.put("dbTableKey", genBasic.getDbTableKey());
//        // 主键Java类型
//        map.put("dbTableKeyJavaType", "String");
//        // 主键名驼峰
//        map.put("dbTableKeyCamelCase", StrUtil.toCamelCase(genBasic.getDbTableKey().toLowerCase()));
//        // 主键首字母大写名
//        map.put("dbTableKeyFirstUpper", StrUtil.upperFirst(StrUtil.toCamelCase(genBasic.getDbTableKey().toLowerCase
//        ())));
//        // 主键注释
//        map.put("dbTableKeyRemark", genBasic.getDbTableKey());
//        // 表单布局
//        map.put("formLayout", genBasic.getFormLayout());
//        // 使用栅格
//        map.put("gridWhether", genBasic.getGridWhether().equalsIgnoreCase(GenYesNoEnum.Y.getValue()));
//        // 父菜单ID
//        map.put("parentId", genBasic.getMenuPid());
//        // 菜单ID
//        map.put("menuId", IdWorker.getIdStr());
//        // 菜单编码
//        map.put("menuCode", RandomUtil.randomString(10));
//        // 菜单路径
//        map.put("menuPath", StrUtil.SLASH + genBasic.getModuleName() + StrUtil.SLASH + genBasic.getBusName());
//        // 菜单组件
//        map.put("menuComponent", genBasic.getModuleName() + StrUtil.SLASH + genBasic.getBusName() + StrUtil.SLASH +
//        "index");
//        // 模块ID
//        map.put("moduleId", genBasic.getModule());
//        // 移动端模块ID
//        map.put("mobileModuleId", genBasic.getMobileModule());
//        // 添加按钮ID
//        map.put("addButtonId", IdWorker.getIdStr());
//        // 编辑按钮ID
//        map.put("editButtonId", IdWorker.getIdStr());
//        // 删除按钮ID
//        map.put("deleteButtonId", IdWorker.getIdStr());
//        // 批量删除按钮ID
//        map.put("batchDeleteButtonId", IdWorker.getIdStr());
        // 作者
        map.put("authorName", "diaoyn");
        // 生成时间
        map.put("createTime", DateUtil.now());
        // 表名
        map.put("tableName", tableDto.getTableName());
        // 注释名
        map.put("tableComment", tableDto.getTableComment());
        // 类名
        map.put("className", StrUtil.upperFirst(StrUtil.toCamelCase(tableDto.getTableName())));
        // 定义配置详情列表
        List<JSONObject> configList = CollectionUtil.newArrayList();
        fieldDtoList.forEach(fieldDto -> {
            JSONObject configItem = JSONUtil.createObj();
            // 字段驼峰名
            configItem.set("fieldNameCamelCase", StrUtil.toCamelCase(fieldDto.getFieldName()));
            // 主键
            configItem.set("needTableId", false);
            if (StrUtil.isNotBlank(fieldDto.getFieldKey())) {
                configItem.set("needTableId", true);
            }
            // 实体类型
            configItem.set("fieldJavaType", fieldDto.getFieldType());
            // 字段注释
            configItem.set("fieldComment", fieldDto.getFieldComment());
            // 是否需要逻辑删除
            configItem.set("needLogicDelete", DELETE_FLAG_KEY.equalsIgnoreCase(fieldDto.getFieldName()));
            // 是否需要自动插入
            configItem.set("needAutoInsert",
                    CREATE_USER_KEY.equalsIgnoreCase(fieldDto.getFieldName()) || CREATE_TIME_KEY.equalsIgnoreCase(fieldDto.getFieldName()));
            // 是否需要自动更新
            configItem.set("needAutoUpdate",
                    UPDATE_USER_KEY.equalsIgnoreCase(fieldDto.getFieldName()) || UPDATE_TIME_KEY.equalsIgnoreCase(fieldDto.getFieldName()));
            configList.add(configItem);
        });
        // 配置信息
        map.put("configList", configList);

//        // 定义是否有排序字段
//        AtomicBoolean hasSortCodeField = new AtomicBoolean(false);
//        genConfigService.list(new LambdaQueryWrapper<GenConfig>().eq(GenConfig::getBasicId, genBasic.getId()))
//                .forEach(genConfig -> {
//                    // 定义字段信息
//                    JSONObject configItem = JSONUtil.createObj();
//                    if(genConfig.getFieldName().equalsIgnoreCase(SORT_CODE_KEY)) {
//                        hasSortCodeField.set(true);
//                    }
//                    // 如果是主键，则无需作为添加参数，需要作为编辑参数，需要主键注解
//                    if(genConfig.getFieldName().equalsIgnoreCase(genBasic.getDbTableKey())) {
//                        configItem.set("needAdd", false);
//                        configItem.set("needEdit", true);
//                        configItem.set("needPage", false);
//                        configItem.set("needPageType", "none");
//                        configItem.set("required", true);
//                        map.put("dbTableKeyJavaType", genConfig.getFieldJavaType());
//                        map.put("dbTableKeyRemark", genConfig.getFieldRemark());
//                    } else {
//                        // 排除删除标志
//                        String logicDeleteField = mybatisPlusProperties.getGlobalConfig().getDbConfig()
//                        .getLogicDeleteField();
//                        if(ObjectUtil.isEmpty(logicDeleteField)) {
//                            logicDeleteField = "DELETE_FLAG";
//                        }
//                        if(genConfig.getFieldName().equalsIgnoreCase(logicDeleteField)) {
//                            configItem.set("needAdd", false);
//                            configItem.set("needEdit", false);
//                            configItem.set("needPage", false);
//                            configItem.set("needPageType", "none");
//                            configItem.set("required", false);
//                            configItem.set("needTableId", false);
//                        } else {
//                            boolean needAddAndUpdate = genConfig.getWhetherAddUpdate().equalsIgnoreCase
//                            (GenYesNoEnum.Y.getValue());
//                            configItem.set("needAdd", needAddAndUpdate);
//                            configItem.set("needEdit", needAddAndUpdate);
//                            configItem.set("needPage", genConfig.getQueryWhether().equalsIgnoreCase(GenYesNoEnum.Y
//                            .getValue()));
//                            configItem.set("needPageType", genConfig.getQueryType());
//                            configItem.set("required", genConfig.getWhetherRequired().equalsIgnoreCase(GenYesNoEnum
//                            .Y.getValue()));
//                            configItem.set("needTableId", false);
//                        }
//                    }
//                    // 列显示
//                    configItem.set("whetherTable", genConfig.getWhetherTable().equalsIgnoreCase(GenYesNoEnum.Y
//                    .getValue()));
//                    // 列省略
//                    configItem.set("whetherRetract", genConfig.getWhetherRetract().equalsIgnoreCase(GenYesNoEnum.Y
//                    .getValue()));
//                    // 增改
//                    configItem.set("whetherAddUpdate", genConfig.getWhetherAddUpdate().equalsIgnoreCase
//                    (GenYesNoEnum.Y.getValue()));
//                    // 作用类型
//                    configItem.set("effectType", genConfig.getEffectType());
//                    // 字典值
//                    configItem.set("dictTypeCode", genConfig.getDictTypeCode());
//                    // 实体类型
//                    configItem.set("fieldJavaType", genConfig.getFieldJavaType());

//                    // 字段驼峰首字母大写名
//                    configItem.set("fieldNameCamelCaseFirstUpper", StrUtil.upperFirst(StrUtil.toCamelCase(genConfig
//                    .getFieldName().toLowerCase())));


//                    configList.add(configItem);
//
//                });
//        // 有排序字段
//        map.put("hasSortCodeField", hasSortCodeField.get());
        return map;

    }


    public static void main(String[] args) {

        CodeGenerator.execute("jdbc:mysql://localhost:3306/demo?serverTimezone=GMT%2B8&useUnicode=true" +
                "&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&rewriteBatchedStatements" +
                "=true", "root", "root", "com.example.alone.entity");
    }
}