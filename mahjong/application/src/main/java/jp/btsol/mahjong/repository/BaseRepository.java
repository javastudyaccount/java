package jp.btsol.mahjong.repository;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

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
     * @param param バインドパラメータ
     * @param clazz 返却カラム型
     * @return 検索結果
     */
    public <T> T findForObject(String sql, Map<String, Object> param, Class<T> clazz) {
        if (log.isDebugEnabled()) {
            log.debug(sql.toString());
            log.debug("PARAM:" + param.toString());
        }
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
        if (log.isDebugEnabled()) {
            log.debug(sql.toString());
        }
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
    public <T> List<T> findForList(String sql, Map<String, Object> param, Class<T> clazz) {
        if (log.isDebugEnabled()) {
            log.debug(sql.toString());
            log.debug("PARAM:" + param.toString());
        }
        return template.query(sql, param, new BeanPropertyRowMapper<T>(clazz));
    }

    /**
     * 登録・更新・削除のSQLを実行します。
     * 
     * @param sql   実行対象SQL
     * @param param パラメータ
     * @return 結果
     */
    public int update(String sql, Map<String, Object> param) {
        if (log.isDebugEnabled()) {
            log.info(sql.toString());
            log.debug("PARAM:" + param.toString());
        }
        return template.update(sql, param);
    }

    /**
     * 登録・更新・削除のSQLを実行します。
     * 
     * @param sql 実行対象SQL
     * @return 結果
     */
    public int update(String sql) {
        if (log.isDebugEnabled()) {
            log.debug(sql.toString());
        }
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
        if (log.isDebugEnabled()) {
            log.debug(sql.toString());
        }
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
                        if (Objects.isNull(f.get(entity))) {
                            params.put(f.getName(), "default");
                        } else {
                            params.put(f.getName(), f.get(entity));
                        }
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

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(template.getJdbcTemplate().getDataSource());
        jdbcInsert.withTableName(tableName).usingGeneratedKeyColumns(pk);

        // execute insert
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        // convert Number to Int using ((Number) key).intValue()
        return ((Number) key).intValue();

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
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return (T) findForObject(sql.toString(), params, clazz);
    }
}
