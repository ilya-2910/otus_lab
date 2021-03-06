package atm;

import java.util.List;
import java.util.TreeMap;

public interface ATMStorage {

    void clear();

    void addNominals(List<Nominal> rawNominals);

    int totalAmount();

    TreeMap<Nominal, ATMCell> getNominals();

    boolean compare(TreeMap<Nominal, ATMCell> toCompare);
}
