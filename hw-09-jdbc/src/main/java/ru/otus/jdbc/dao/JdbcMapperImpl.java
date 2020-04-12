package ru.otus.jdbc.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {

    @Data
    @AllArgsConstructor
    class FieldEntity {
        private Class clazz;
        private String name;
        private Object value;
    }

    @Data
    @AllArgsConstructor
    class EntityInfo {
        FieldEntity id;
        List<FieldEntity> fieldEntities;

        String getFieldsNames() {
            return this.getFieldEntities().stream().map(fieldEntity -> fieldEntity.getName()).collect(Collectors.joining(", "));
        }

        String getAllFieldsNames() {
            return id.getName() + "," + this.getFieldEntities().stream().map(fieldEntity -> fieldEntity.getName()).collect(Collectors.joining(", "));
        }
    }

    @Override
    public Query getSelectQuery(long id, Class<T> userClass) {
        EntityInfo entityInfo = getEntityInfo(null, userClass);
        String query = "select " + entityInfo.getAllFieldsNames() + " from " + userClass.getSimpleName() + " where " + entityInfo.getId().name + " = ?";
        return new Query(query, Collections.EMPTY_LIST);
    }

    @Override
    public Query getInsertQuery(Object entity) {
        EntityInfo entityInfo = getEntityInfo(entity, null);
        String fieldNames = entityInfo.getFieldsNames();
        String placeHolders = entityInfo.getFieldEntities().stream().map(fieldEntity -> "?").collect(Collectors.joining(", "));
        String query = "insert into " + entity.getClass().getSimpleName() + "(" + fieldNames + ")" + "values (" + placeHolders + ")";

        List<String> fieldValues = entityInfo.getFieldEntities().stream().map(fieldEntity -> getValueString(fieldEntity.getValue())).collect(Collectors.toList());

        return new Query(query, fieldValues);
    }

    private String getValueString(Object object) {
        if (object.getClass() == String.class || object.getClass() == Character.class) {
            return String.format("\"%s\"", object);
        }
        return object.toString();
    }

    private EntityInfo getEntityInfo(Object object, Class type) {
        List<FieldEntity> fieldEntities = new ArrayList<>();
        Class<?> clazz = object != null ? object.getClass() : type;
        Field[] fields = clazz.getDeclaredFields();

        FieldEntity id = null;
        for (Field field : fields) {
            String name = field.getName();
            field.setAccessible(true);
            try {
                if (field.isAnnotationPresent(Id.class)) {
                    if (id == null) {
                        id = new FieldEntity(field.getType(), name, object != null ? field.get(object) : null);
                    } else {
                        throw new IllegalStateException("id already defined for class " + type);
                    }
                } else {
                    fieldEntities.add(new FieldEntity(field.getType(), name, object != null ? field.get(object) : null));
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return new EntityInfo(id, fieldEntities);
    }

    @Override
    public T getEntity(Class<T> clazz, ResultSet rst) {
        try {
            T object = clazz.getDeclaredConstructor().newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                String name = field.getName();
                field.setAccessible(true);
                Object value = rst.getObject(name);
                Class<?> type = field.getType();
                if (isPrimitive(type)) {
                    Class<?> boxed = boxPrimitiveClass(type);
                    value = boxed.cast(value);
                }
                field.set(object, value);
            }
            return object;
        } catch (Exception e) {
            throw new RuntimeException("Unable to get the records: " + e.getMessage(), e);
        }
    }

    /* https://dzone.com/articles/method-to-get-jdbc-result-set-into-given-generic-c */
    public static boolean isPrimitive(Class<?> type) {
        return (type == int.class || type == long.class || type == double.class || type == float.class
                || type == boolean.class || type == byte.class || type == char.class || type == short.class);
    }

    public static Class<?> boxPrimitiveClass(Class<?> type) {
        if (type == int.class) {
            return Integer.class;
        } else if (type == long.class) {
            return Long.class;
        } else if (type == double.class) {
            return Double.class;
        } else if (type == float.class) {
            return Float.class;
        } else if (type == boolean.class) {
            return Boolean.class;
        } else if (type == byte.class) {
            return Byte.class;
        } else if (type == char.class) {
            return Character.class;
        } else if (type == short.class) {
            return Short.class;
        } else {
            String string = "class '" + type.getName() + "' is not a primitive";
            throw new IllegalArgumentException(string);
        }
    }

}
