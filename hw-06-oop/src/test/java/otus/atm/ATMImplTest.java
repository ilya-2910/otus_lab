package otus.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.TreeMap;

class ATMImplTest {

    private ATMImpl atm;

    @BeforeEach
    public void before() {
        atm = new ATMImpl();
    }

    @Test
    void recieve() throws NoExitsNomimal {
        List<Nominal> nominals = List.of(Nominal._10, Nominal._10, Nominal._10, Nominal._1000, Nominal._500);
        atm.receive(nominals);

        TreeMap<Nominal, Integer> xxx = atm.get(20);

    }

    @Test
    void get() {
    }
}