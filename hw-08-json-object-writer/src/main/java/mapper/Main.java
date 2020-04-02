package mapper;

import com.google.gson.Gson;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Gson gson = new Gson();
        String json = "[\n" +
                "  {\n" +
                "    \"id\": 8484,\n" +
                "    \"name\": \"David\",\n" +
                "    \"height\": 173.2,\n" +
                "    \"weight\": 75.42\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 8485,\n" +
                "    \"name\": \"Ronald\",\n" +
                "    \"height\": 183.73,\n" +
                "    \"weight\": 83.1\n" +
                "  }\n" +
                "]";
        Person[] persons = gson.fromJson(json, Person[].class);

        String xxx = gson.toJson(Arrays.asList(new Person()));

        for(Person person : persons)
            System.out.println(person.toString());
    }

}
