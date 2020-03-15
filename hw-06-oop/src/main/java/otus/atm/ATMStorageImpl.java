package otus.atm;

import java.util.List;
import java.util.TreeMap;

public class ATMStorageImpl implements ATMStorage {

    /**
     * sorted by Nominal
     */
    private TreeMap<Nominal, ATMCell> nominals = new TreeMap<>();

    @Override
    public void addNominals(List<Nominal> rawNominals) {
        rawNominals.stream().forEach(nominal -> nominals.computeIfAbsent(nominal, k -> new ATMCell(0)).add(1));
    }

    @Override
    public int totalAmount() {
        return nominals.keySet().stream().mapToInt(value -> nominals.get(value).getCount() * value.getValue()).sum();
    }

    public TreeMap<Nominal, ATMCell> getNominals() {
        return nominals;
    }

    public boolean compare(TreeMap<Nominal, ATMCell> toCompare) {
        if (nominals.size() != toCompare.size()) return false;
        for (Nominal nominal : toCompare.keySet()) {
            if (toCompare.get(nominal).getCount() != nominals.get(nominal).getCount()) return false;
        }
        return true;
    }

}
