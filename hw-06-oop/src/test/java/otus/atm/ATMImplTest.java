package otus.atm;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
class ATMImplTest {

    private ATMImpl atm;

    @BeforeEach
    public void before() {
        atm = new ATMImpl();
        atm.receive(List.of(Nominal._10, Nominal._10, Nominal._10, Nominal._1000, Nominal._500));
    }

    @Test
    void availableAmount() {
        TreeMap<Nominal, AtomicInteger> recieved = atm.get(500);
        Assertions.assertTrue(recieved.size() == 1 && recieved.get(Nominal._500).get() == 1);

        ATMImpl atm2 = new ATMImpl();
        atm2.receive(List.of(Nominal._10, Nominal._10, Nominal._10, Nominal._1000));

        Assertions.assertTrue(atm.compare(atm2.getAvailable()));
    }

    @Test
    void notAvailableAmount() {
        Assertions.assertThrows(NoExitsNomimal.class, () -> {
            atm.get(501);
        });
    }

}