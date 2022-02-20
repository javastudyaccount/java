package jp.btsol.mahjong.utils;

import java.lang.reflect.Field;
import java.util.Objects;

import javax.persistence.Column;

public class Validator<T> {
    public void validate(T entity) {
        for (Field f : entity.getClass().getDeclaredFields()) {
            Column column = f.getAnnotation(Column.class);
            if (Objects.nonNull(column) && column.length() > 0) {
                f.setAccessible(true);
                Object val = null;
                try {
                    val = f.get(entity);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new RuntimeException(e.getLocalizedMessage());
                }
                if (Objects.nonNull(val) && String.valueOf(val).length() > column.length()) {
                    throw new RuntimeException(
                            "Lenght of column " + f.getName() + " is more than " + column.length() + ".");
                }
            }
        }
    }
}
