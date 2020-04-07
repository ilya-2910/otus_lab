package mapper;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        assertEquals(new Gson().toJson(person), personJson);
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

        assertEquals(new Gson().toJson(person), personJson);
    }

    @Test
    void toJson_withNull() {
        ObjectJsonMapper objectJsonMapper = new ObjectJsonMapper();
        String personJson = objectJsonMapper.toJson(null);

        assertEquals(new Gson().toJson(null), personJson);
    }

    @Test
    public void test() {
        Gson gson = new Gson();
        ObjectJsonMapper serializer = new ObjectJsonMapper();
        assertEquals(gson.toJson(null), serializer.toJson(null));
        assertEquals(gson.toJson((byte)1), serializer.toJson((byte)1));
        assertEquals(gson.toJson((short)1f), serializer.toJson((short)1f));
        assertEquals(gson.toJson(1), serializer.toJson(1));
        assertEquals(gson.toJson(1L), serializer.toJson(1L));
        assertEquals(gson.toJson(1f), serializer.toJson(1f));
        assertEquals(gson.toJson(1d), serializer.toJson(1d));
        assertEquals(gson.toJson("aaa"), serializer.toJson("aaa"));
        assertEquals(gson.toJson('a'), serializer.toJson('a'));
        assertEquals(gson.toJson(new int[] {1, 2, 3}), serializer.toJson(new int[] {1, 2, 3}));
        assertEquals(gson.toJson(List.of(1, 2 ,3)), serializer.toJson(List.of(1, 2 ,3)));
        assertEquals(gson.toJson(Collections.singletonList(1)), serializer.toJson(Collections.singletonList(1)));
    }



}