package otus.atm;

import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public interface ATM {

    void receive(List<Nominal> nominals);

    TreeMap<Nominal, AtomicInteger> get(Integer amount);

    TreeMap<Nominal, AtomicInteger> getAvailable();

}
