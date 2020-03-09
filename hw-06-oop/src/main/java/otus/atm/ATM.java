package otus.atm;

import java.util.List;
import java.util.TreeMap;

public interface ATM {

    void receive(List<Nominal> nominals);

    TreeMap<Nominal, Integer> get(Integer amount) throws NoExitsNomimal;

}
