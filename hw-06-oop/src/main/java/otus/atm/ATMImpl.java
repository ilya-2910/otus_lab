package otus.atm;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

@Slf4j
public class ATMImpl implements ATM {

    private ATMStorage atmStorage = new ATMStorageImpl();

    public void receive(List<Nominal> rawNominals) {
        atmStorage.addNominals(rawNominals);
    }

    public ATMStorage get(Integer requiredAmount) {
        int availableAmount = atmStorage.totalAmount();
        log.info("totalAmount={}", availableAmount);
        if (availableAmount < requiredAmount) {
            throw new NoExitsNomimal();
        }

        ATMStorageImpl requiredStorage = new ATMStorageImpl();
        for (Nominal nominal : atmStorage.getNominals().keySet()) {
            while (requiredAmount >= nominal.getValue() && atmStorage.getNominals().get(nominal).getCount() > 0) {
                requiredStorage.addNominals(Arrays.asList(nominal));
                requiredAmount = requiredAmount - nominal.getValue();
            }
        }

        if (requiredAmount > 0) {
            throw new NoExitsNomimal();
        } else {
            int sum = requiredStorage.totalAmount();
            log.info("return amount={}", sum);
        }

        for (Nominal nominal : requiredStorage.getNominals().keySet()) {
            ATMCell cell = atmStorage.getNominals().get(nominal);
            if (cell != null) {
                int decreaseAmount = requiredStorage.getNominals().get(nominal).getCount();
                cell.add(-decreaseAmount);
            }
            if (cell.getCount() == 0) {
                atmStorage.getNominals().remove(nominal);
            }
        }

        return requiredStorage;
    }

    @Override
    public TreeMap<Nominal, ATMCell> getAvailable() {
        return atmStorage.getNominals();
    }

    @Override
    public boolean compare(TreeMap<Nominal, ATMCell> toCompare) {
        return atmStorage.compare(toCompare);
    }

}
