package jp.btsol.mahjong.application.repository;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.google.common.base.CaseFormat;

import lombok.extern.slf4j.Slf4j;

/**
 * BaseRepository
 * 
 */
@Repository
@Slf4j
public class BaseRepository {
    /** JDBCTemplate */
    private NamedParameterJdbcTemplate template;

    /**
     * コンストラクタ
     * 
     * @param template
     */
    @Autowired
    public BaseRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    /**
     * １件分のデータを参照します。
     * 
     * @param <T>   返却カラム型類推
     * @param sql   実行対象SQL
     * @param clazz 返却カラム型
     * @return 検索結果
     */
    public <T> T findForObject(String sql, Class<T> clazz) {
        sqlPrint(sql, null);
        return template.queryForObject(sql, new HashMap<String, Object>(), new BeanPropertyRowMapper<T>(clazz));
    }

    /**
     * １件分のデータを参照します。
     * 
     * @param <T>   返却カラム型類推
     * @param sql   実行対象SQL
     * @param param バインドパラメータ
     * @param clazz 返却カラム型
     * @return 検索結果
     */
    public <T> T findForObject(String sql, MapSqlParameterSource param, Class<T> clazz) {
        sqlPrint(sql, param);
        return template.queryForObject(sql, param, new BeanPropertyRowMapper<T>(clazz));
    }

    /**
     * 複数件分のデータを参照します。
     * 
     * @param <T>   返却カラム型類推
     * @param sql   実行対象SQL
     * @param clazz 返却カラム型
     * @return 検索結果
     */
    public <T> List<T> findForList(String sql, Class<T> clazz) {
        sqlPrint(sql, null);
        return template.query(sql, new HashMap<String, Object>(), new BeanPropertyRowMapper<T>(clazz));
    }

    /**
     * 複数件分のデータを参照します。
     * 
     * @param <T>   返却カラム型類推
     * @param sql   実行対象SQL
     * @param param バインドパラメータ
     * @param clazz 返却カラム型
     * @return 検索結果
     */
    public <T> List<T> findForList(String sql, MapSqlParameterSource param, Class<T> clazz) {
        sqlPrint(sql, param);
        return template.query(sql, param, new BeanPropertyRowMapper<T>(clazz));
    }

    /**
     * 登録・更新・削除のSQLを実行します。
     * 
     * @param sql   実行対象SQL
     * @param param パラメータ
     * @return 結果
     */
    public int update(String sql, MapSqlParameterSource param) {
        sqlPrint(sql, param);
        return template.update(sql, param);
    }

    /**
     * 登録・更新・削除のSQLを実行します。
     * 
     * @param sql 実行対象SQL
     * @return 結果
     */
    public int update(String sql) {
        sqlPrint(sql, null);
        return template.update(sql, new HashMap<String, Object>());
    }

    /**
     * 登録・更新・削除（複数件）のSQLを実行します。(一括更新)
     * 
     * @param sql       実行対象SQL
     * @param batchArgs パラメータ
     * @return 更新件数
     */
    public int[] batchUpdate(String sql, SqlParameterSource[] batchArgs) {
        sqlPrint(sql, (MapSqlParameterSource) batchArgs[0]);
        return template.batchUpdate(sql, batchArgs);
    }

    private String camel2snake(String camel) {
        String snake = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camel);
        return snake;
    }

    /**
     * エンティティを使用した登録
     * 
     * @param <T>    類推型
     * @param entity 検索エンティティ
     * @return 登録数
     */
    public <T> int insertWithSurrogateKey(T entity) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ");
        String tableName = camel2snake(entity.getClass().getSimpleName());
        sql.append(tableName);
        sql.append("( ");

        String pk = "";
        List<String> columnNames = new ArrayList<>();
        List<String> paramNames = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        for (Field f : entity.getClass().getDeclaredFields()) {
            Id id = f.getAnnotation(Id.class);
            if (Objects.nonNull(id)) {
                pk = camel2snake(f.getName());
                continue;
            }
            if (Modifier.isStatic(f.getModifiers())) {
                continue;
            }
            columnNames.add(camel2snake(f.getName()));
            paramNames.add(":" + f.getName());
            f.setAccessible(true);
            switch (f.getName()) {
                case "deleteFlg":
                    params.put(f.getName(), false);
                    break;
                case "createdTimestamp":
                case "updatedTimestamp":
                    java.util.Date date = new java.util.Date();
                    java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
                    params.put(f.getName(), timestamp);
                    break;
                case "createdUser":
                case "updatedUser":
                    try {
                        String requestId = (String) f.get(entity);
                        if (Objects.isNull(f.get(entity))) {
                            requestId = getRequestId();
                            if (Objects.isNull(requestId)) {
                                requestId = "default";
                            }
                        }

                        params.put(f.getName(), requestId);
                    } catch (IllegalArgumentException | IllegalAccessException e1) {
                        e1.printStackTrace();
                    }
                    break;
                default:
                    try {
                        params.put(f.getName(), f.get(entity));
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
            }
        }
        sql.append(String.join(", ", columnNames));
        // 登録値の生成
        sql.append(") VALUES(");
        sql.append(String.join(", ", paramNames));
        sql.append(")");

        sqlPrint(sql.toString(), null);
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(template.getJdbcTemplate().getDataSource());
        if (!StringUtils.isEmpty(pk)) {
            jdbcInsert.withTableName(tableName).usingGeneratedKeyColumns(pk);

            // execute insert
            Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
            // convert Number to Int using ((Number) key).intValue()
            return ((Number) key).intValue();
        } else {
            int count = jdbcInsert.execute(new MapSqlParameterSource(params));
            return count;
        }
    }

    /**
     * エンティティを使用した登録
     * 
     * @param <T>    類推型
     * @param entity 検索エンティティ
     * @return 登録数
     */
    public <T> int insert(T entity) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ");
        String tableName = camel2snake(entity.getClass().getSimpleName());
        sql.append(tableName);
        sql.append("( ");

        String pk = "";
        List<String> columnNames = new ArrayList<>();
        List<String> paramNames = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        for (Field f : entity.getClass().getDeclaredFields()) {
            Id id = f.getAnnotation(Id.class);
            if (Objects.nonNull(id)) {
                pk = camel2snake(f.getName());
                continue;
            }
            if (Modifier.isStatic(f.getModifiers())) {
                continue;
            }
            columnNames.add(camel2snake(f.getName()));
            paramNames.add(":" + f.getName());
            f.setAccessible(true);
            switch (f.getName()) {
                case "deleteFlg":
                    params.put(f.getName(), false);
                    break;
                case "createdTimestamp":
                case "updatedTimestamp":
                    java.util.Date date = new java.util.Date();
                    java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
                    params.put(f.getName(), timestamp);
                    break;
                case "createdUser":
                case "updatedUser":
                    try {
                        String requestId = (String) f.get(entity);
                        if (Objects.isNull(f.get(entity))) {
                            requestId = getRequestId();
                            if (Objects.isNull(requestId)) {
                                requestId = "default";
                            }
                        }

                        params.put(f.getName(), requestId);
                    } catch (IllegalArgumentException | IllegalAccessException e1) {
                        e1.printStackTrace();
                    }
                    break;
                default:
                    try {
                        params.put(f.getName(), f.get(entity));
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
            }
        }
        sql.append(String.join(", ", columnNames));
        // 登録値の生成
        sql.append(") VALUES(");
        sql.append(String.join(", ", paramNames));
        sql.append(")");

        sqlPrint(sql.toString(), null);
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(template.getJdbcTemplate().getDataSource());
//        if (!StringUtils.isEmpty(pk)) {
        jdbcInsert.withTableName(tableName).usingGeneratedKeyColumns(pk);

        // execute insert
//            Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        // convert Number to Int using ((Number) key).intValue()
//            return ((Number) key).intValue();
//        } else {
        int count = jdbcInsert.execute(new MapSqlParameterSource(params));
        return count;
//        }
    }

    /**
     * エンティティを使用した登録
     * 
     * @param <T>   類推型
     * @param id    long
     * @param clazz Class<?>
     * @return Entity
     */
    public <T> T findById(long id, Class<?> clazz) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ");
        String tName = clazz.getSimpleName();
        String tableName = camel2snake(tName);
        sql.append(tableName);
        sql.append(" where ");

        for (Field f : clazz.getDeclaredFields()) {
            Id idField = f.getAnnotation(Id.class);
            if (Objects.nonNull(idField)) {
                sql.append(camel2snake(f.getName())).append(" = :id");
                break;
            }
        }
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        sqlPrint(sql.toString(), params);
        return (T) findForObject(sql.toString(), params, clazz);
    }

    private String getRequestId() {
        try {
            return (String) RequestContextHolder.currentRequestAttributes().getAttribute("request-id",
                    RequestAttributes.SCOPE_REQUEST);
        } catch (IllegalStateException e) {
            // "リクエストを処理するスレッド以外から呼び出さないで下さい。"
        }
        return "default";
    }

    /**
     * SQL文を標準出力する
     * 
     * @param sql    SQLクエリ文字列
     * @param params パラメータ
     */
    private void sqlPrint(String sql, MapSqlParameterSource params) {
        if (Objects.nonNull(params)) {
            Map<String, Object> param = params.getValues();
            for (String key : param.keySet()) {
                sql = sql.replaceAll(":" + key, "'" + param.get(key).toString() + "'");
            }
        }
        log.info(sql);
    }
}
