import atm.*;
import atm.department.Department;
import atm.department.DepartmentImpl;
import atm.department.DepartmentProxy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
class DepartmentImplTest {

    private Department department;
    private List<Nominal> initNominals;

    @BeforeEach
    public void before() {
        department = new DepartmentProxy(new DepartmentImpl());

        initNominals = List.of(Nominal._10, Nominal._10, Nominal._10, Nominal._1000, Nominal._500);
        ATM atm = new ATMWrapper(new ATMFactory().createATM(initNominals));
        ATM atm2 = new ATMImpl(initNominals);
        department.setATMs(List.of(atm, atm2));
    }

    @Test
    void restore_recieveNominals_ShouldSameInitNominals() {
        department.getATMs().forEach(atm -> {
            atm.get(500);
        });
        department.restore();

        List<ATM> atms = department.getATMs();
        for (ATM atm : atms) {
            Assertions.assertTrue(new ATMImpl(initNominals).compare(atm.getAvailable()));
        }
    }

    @Test
    void getBalance_recieveNominals_rightBalance() {
        Integer balanceBefore = department.getBalance();
        department.getATMs().forEach(atm -> {
            atm.get(500);
        });
        Integer balanceAfter = department.getBalance();
        Assertions.assertTrue(balanceBefore - balanceAfter == 500 * department.getATMs().size());
    }

}