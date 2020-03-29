package atm;

import atm.department.AtmEventListener;

import java.util.List;
import java.util.TreeMap;

public interface ATM extends AtmEventListener {

    void init(List<Nominal> nominals);

    void receive(List<Nominal> nominals);

    ATMStorage get(Integer amount);

    TreeMap<Nominal, ATMCell> getAvailable();

    Integer getTotalAmount();

    boolean compare(TreeMap<Nominal, ATMCell> toCompare);
}
