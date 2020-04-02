package atm.department;

import atm.ATM;

import java.util.List;

public interface Department {

    void setATMs(List<ATM> atms);

    List<ATM> getATMs();

    Integer getBalance();

    void restore();

}
