import atm.ATMImpl;
import atm.ATMStorage;
import atm.NoExitsNomimal;
import atm.Nominal;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
class ATMImplTest {

    private ATMImpl atm;

    @BeforeEach
    public void before() {
        atm = new ATMImpl(List.of(Nominal._10, Nominal._10, Nominal._10, Nominal._1000, Nominal._500));
    }

    @Test
    void availableAmount() {
        ATMStorage recieved = atm.get(500);
        Assertions.assertTrue(recieved.getNominals().size() == 1 && recieved.getNominals().get(Nominal._500).getCount() == 1);

        ATMImpl atm2 = new ATMImpl(List.of(Nominal._10, Nominal._10, Nominal._10, Nominal._1000));

        Assertions.assertTrue(atm.compare(atm2.getAvailable()));
    }

    @Test
    void notAvailableAmount() {
        Assertions.assertThrows(NoExitsNomimal.class, () -> {
            atm.get(501);
        });
    }

}