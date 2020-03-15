package otus.atm;

import java.util.List;
import java.util.TreeMap;

public interface ATM {

    void receive(List<Nominal> nominals);

    ATMStorage get(Integer amount);

    TreeMap<Nominal, ATMCell> getAvailable();

    boolean compare(TreeMap<Nominal, ATMCell> toCompare);
}
