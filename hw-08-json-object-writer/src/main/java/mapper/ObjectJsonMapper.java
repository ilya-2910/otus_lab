package mapper;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ObjectJsonMapper {

    @Data
    @AllArgsConstructor
    class MyField {
        private Class clazz;
        private String name;
        private Object value;
    }

    public String toJson(Object object) {
        if (null == object) return "null";

        if (isPrimitiveOrPrimitiveWrapperOrString(object.getClass())) {
            if (object.getClass() == String.class || object.getClass() == Character.class) {
                return String.format("\"%s\"", object);
            }
            return object.toString();
        }

        if (object.getClass().isArray()) {
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            int length = Array.getLength(object);
            for (int i = 0; i < length; i ++) {
                Object arrayElement = Array.get(object, i);
                addArrayElementValue(jsonArrayBuilder, arrayElement);
            }
            return jsonArrayBuilder.build().toString();
        }

        if (object instanceof Iterable) {
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            for (Object field : (Iterable)object) {
                addArrayElementValue(jsonArrayBuilder, field);
            }
            return jsonArrayBuilder.build().toString();
        }

        List<MyField> myFields = new ArrayList<>();
        Class<?> c = object.getClass();
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            field.setAccessible(true);
            try {
                myFields.add(new MyField(field.getType(), name, field.get(object)));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

        myFields.forEach(myField -> {
            Object value = myField.getValue();
            if (value != null) {
                if (value instanceof Map) {
                    JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
                    for (Map.Entry<String, String> entry : ((Map<String,String>)value).entrySet()) {
                        addObjectValue(jsonBuilder, entry.getKey(), entry.getValue());
                    }
                    jsonObjectBuilder.add(myField.getName(), jsonBuilder);
                } if (value instanceof Iterable) {
                    JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
                    for (Object field : (Iterable)value) {
                        addArrayElementValue(jsonArrayBuilder, field);
                    }
                    jsonObjectBuilder.add(myField.getName(), jsonArrayBuilder);
                } else if (myField.getClazz().isArray()) {
                    JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
                    for (Object field : (Object[])value) {
                        addArrayElementValue(jsonArrayBuilder, field);
                    }
                    jsonObjectBuilder.add(myField.getName(), jsonArrayBuilder);
                }  else {
                    addObjectValue(jsonObjectBuilder, myField.getName(), value);
                }
            }
        });

        JsonObject jsonObject = jsonObjectBuilder.build();
        return jsonObject.toString();
    }

    private boolean isPrimitiveOrPrimitiveWrapperOrString(Class<?> type) {
        return (type.isPrimitive() && type != void.class) ||
                type == Double.class || type == Float.class || type == Long.class ||
                type == Integer.class || type == Short.class || type == Character.class ||
                type == Byte.class || type == Boolean.class || type == String.class || type == Character.class;
    }

    private void addObjectValue(JsonObjectBuilder jsonObjectBuilder, String name, Object value) {
        if (value instanceof Integer) {
            jsonObjectBuilder.add(name, (Integer) value);
        } else if (value instanceof Double) {
            jsonObjectBuilder.add(name, (Double) value);
        } else if (value instanceof BigInteger) {
            jsonObjectBuilder.add(name, (BigInteger) value);
        } else if (value instanceof BigDecimal) {
            jsonObjectBuilder.add(name, (BigDecimal) value);
        } else if (value instanceof Long) {
            jsonObjectBuilder.add(name, (Long) value);
        } else if (value instanceof String) {
            jsonObjectBuilder.add(name, value.toString());
        }
    }

    private void addArrayElementValue(JsonArrayBuilder jsonObjectBuilder, Object value) {
        if (value instanceof Integer) {
            jsonObjectBuilder.add((Integer) value);
        } else if (value instanceof Double) {
            jsonObjectBuilder.add((Double) value);
        } else if (value instanceof BigInteger) {
            jsonObjectBuilder.add((BigInteger) value);
        } else if (value instanceof BigDecimal) {
            jsonObjectBuilder.add((BigDecimal) value);
        } else if (value instanceof Long) {
            jsonObjectBuilder.add((Long) value);
        } else if (value instanceof String) {
            jsonObjectBuilder.add(value.toString());
        }
    }

}
