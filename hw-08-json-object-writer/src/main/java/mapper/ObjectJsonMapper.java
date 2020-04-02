package mapper;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ObjectJsonMapper {

    @Data
    @AllArgsConstructor
    class MyField {
        private Class clazz;
        private String name;
        private Object value;
    }

    public String toJson(Object o) {
        if (null == o) return "null";
        ArrayList<MyField> myFields = new ArrayList<>();
        Class<? extends Object> c = o.getClass();
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            field.setAccessible(true);
            try {
                myFields.add(new MyField(field.getType(), name, field.get(o)));
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
                } if (value instanceof Collection) {
                    JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
                    for (Object field : (Collection)value) {
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
