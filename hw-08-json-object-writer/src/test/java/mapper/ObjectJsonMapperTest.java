package mapper;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

@Slf4j
class ObjectJsonMapperTest {

    private Person person;

    @BeforeEach
    void createPerson() {
        this.person = new Person();
        person.setRoles(Arrays.asList("123", "1234"));
        person.setBooks(new String[]{"123", "1234"});
        person.setRoles3(Arrays.asList(123d, 1234d));

        person.setMap(Map.of(
                "a", 123,
                "c", 123d
        ));

        person.setSalary(123);
    }

    @Test
    void toJson_withOutNull() {
        ObjectJsonMapper objectJsonMapper = new ObjectJsonMapper();
        String personJson = objectJsonMapper.toJson(person);

        Assertions.assertEquals(new Gson().toJson(person), personJson);
    }

    @Test
    void toJson_withNullFields() {
        ObjectJsonMapper objectJsonMapper = new ObjectJsonMapper();
        Person person = new Person();
        person.setRoles(Arrays.asList("123", "1234"));
        person.setRoles2(new HashSet<>() {{
            add("a");
            add("b");
            add("c");
        }});
        person.setBooks(new String[]{"123", "1234"});
        String personJson = objectJsonMapper.toJson(person);

        Assertions.assertEquals(new Gson().toJson(person), personJson);
    }

    @Test
    void toJson_withNull() {
        ObjectJsonMapper objectJsonMapper = new ObjectJsonMapper();
        String personJson = objectJsonMapper.toJson(null);

        Assertions.assertEquals(new Gson().toJson(null), personJson);
    }



}