import com.google.common.collect.Ordering;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class DIYarrayListTest {

    @Test
    @DisplayName("Test method addAll")
    public void testMethodAddAll() {
        DIYarrayList<Integer> dist = new DIYarrayList<>();
        Integer[] source = new Integer[100];
        for (int i = 0; i < source.length; i++) {
            source[i] = i;
        }

        Collections.addAll(dist, source);

        Assertions.assertEquals(0, Arrays.compare(dist.stream().toArray(Integer[]::new), source));
    }

    @Test
    @DisplayName("Test method copy")
    public void testMethodCopy() {
        DIYarrayList<Integer> dist = new DIYarrayList<>();
        DIYarrayList<Integer> source = new DIYarrayList<>();
        Integer testValue = 0;
        for (int i = 0; i < 100; i++) {
            dist.add(testValue);
            if (i < 50) {
                source.add(i);
            }
        }

        Collections.copy(dist, source);

        Integer[] distArr = dist.stream().toArray(Integer[]::new);
        Integer[] distArrCopied = new Integer[source.size];
        System.arraycopy(distArr, 0, distArrCopied, 0, source.size);

        //check copied part
        Assertions.assertEquals(0, Arrays.compare(distArrCopied, source.stream().toArray(Integer[]::new)));

        //check unaffected part
        for (int i = source.size; i < dist.size; i++) {
            Assertions.assertEquals(testValue, dist.get(i));
        }
    }

    @Test
    @DisplayName("Test method sort")
    public void testMethodSort() {
        DIYarrayList<Integer> source = new DIYarrayList<>();
        for (int i = 0; i < 100; i++) {
            source.add(i);
        }

        Collections.sort(source, Comparator.nullsFirst(Comparator.reverseOrder()));

        Assertions.assertTrue(Ordering.natural().reverse().nullsLast().isOrdered(source));
    }

}
