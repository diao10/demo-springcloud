package com.diaoyn.generator.querys;

/**
 * KingbaseES 表数据查询
 *
 * @author kingbase
 * @since 2019-10-12
 */
public class KingbaseESQuery extends AbstractDbQuery {

    @Override
    public String tablesSql() {
        return "SELECT A.tablename, obj_description(relfilenode, 'sys_class') AS comments FROM sys_tables A, " +
                "sys_class B WHERE A.schemaname='%s' AND A.tablename = B.relname";
    }

    @Override
    public String tablesSql(String... tableNames) {
        return null;
    }


    @Override
    public String tableFieldsSql(String schema, String tableName) {
        return "SELECT A.attname AS name, format_type(A.atttypid, A.atttypmod) AS type,col_description(A.attrelid, A" +
                ".attnum) AS comment, (CASE C.contype WHEN 'p' THEN 'PRI' ELSE '' END) AS key " +
                "FROM sys_attribute A LEFT JOIN sys_constraint C ON A.attnum = C.conkey[1] AND A.attrelid = C" +
                ".conrelid " +
                "WHERE  A.attrelid = '" + schema + "." + tableName + "'::regclass AND A.attnum > 0 AND NOT A" +
                ".attisdropped ORDER  BY A.attnum";
    }


    @Override
    public String tableName() {
        return "tablename";
    }


    @Override
    public String tableComment() {
        return "comments";
    }


    @Override
    public String fieldName() {
        return "name";
    }


    @Override
    public String fieldType() {
        return "type";
    }


    @Override
    public String fieldComment() {
        return "comment";
    }


    @Override
    public String fieldKey() {
        return "key";
    }

}
