import com.google.common.collect.Ordering;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HelloOtus {

    public static void main(String[] args) {
        List<String> myList = Arrays.asList("999", "444", "666", null);
        Ordering<String> ordering = Ordering.from(String.CASE_INSENSITIVE_ORDER).nullsFirst();
        Collections.sort(myList, ordering);

        myList.forEach(System.out::println);
    }

}
