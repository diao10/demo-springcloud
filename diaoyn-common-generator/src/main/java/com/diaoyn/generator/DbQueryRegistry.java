package com.diaoyn.generator;

import com.diaoyn.generator.enums.DbType;
import com.diaoyn.generator.querys.*;

import java.util.EnumMap;
import java.util.Map;

/**
 * 数据库构造器
 *
 * @author diaoyn
 * @ClassName DbQueryRegistry
 * @Date 2024/9/6 10:31
 */
public class DbQueryRegistry {

    private final Map<DbType, IDbQuery> db_query_enum_map = new EnumMap<>(DbType.class);

    public DbQueryRegistry() {
        db_query_enum_map.put(DbType.ORACLE, new OracleQuery());
        db_query_enum_map.put(DbType.SQL_SERVER, new SqlServerQuery());
        db_query_enum_map.put(DbType.POSTGRE_SQL, new PostgreSqlQuery());
        db_query_enum_map.put(DbType.DB2, new DB2Query());
        db_query_enum_map.put(DbType.MARIADB, new MariadbQuery());
        db_query_enum_map.put(DbType.H2, new H2Query());
        db_query_enum_map.put(DbType.LEALONE, new H2Query());
        db_query_enum_map.put(DbType.SQLITE, new SqliteQuery());
        db_query_enum_map.put(DbType.DM, new DMQuery());
        db_query_enum_map.put(DbType.KINGBASE_ES, new KingbaseESQuery());
        db_query_enum_map.put(DbType.MYSQL, new MySqlQuery());
        db_query_enum_map.put(DbType.GAUSS, new GaussQuery());
        db_query_enum_map.put(DbType.OSCAR, new OscarQuery());
        db_query_enum_map.put(DbType.FIREBIRD, new FirebirdQuery());
        db_query_enum_map.put(DbType.XU_GU, new XuguQuery());
        db_query_enum_map.put(DbType.CLICK_HOUSE, new ClickHouseQuery());
        db_query_enum_map.put(DbType.GBASE, new GbaseQuery());
        db_query_enum_map.put(DbType.SYBASE, new SybaseQuery());
    }

    public IDbQuery getDbQuery(DbType dbType) {
        return db_query_enum_map.get(dbType);
    }
}
