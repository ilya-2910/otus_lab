package otus.atm;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ATMImpl implements ATM {

    /**
     * sorted by Nominal
     */
    private TreeMap<Nominal, AtomicInteger> availiableNominals = new TreeMap<>();

    public void receive(List<Nominal> rawNominals) {
        rawNominals.stream().forEach(nominal -> {
            availiableNominals.computeIfAbsent(nominal, k -> new AtomicInteger(0)).addAndGet(1);
         });
    }

    public TreeMap<Nominal, AtomicInteger> get(Integer requiredAmount) {
        int availableAmount = availiableNominals.keySet().stream().mapToInt(value -> availiableNominals.get(value).get() * value.getValue()).sum();
        log.info("totalAmount={}",availableAmount);

        if (availableAmount < requiredAmount) {
            throw new NoExitsNomimal();
        }

        TreeMap<Nominal, AtomicInteger> requiredNominals = new TreeMap<>();
        for (Nominal nominal : availiableNominals.keySet()) {
            while (requiredAmount >= nominal.getValue() && availiableNominals.get(nominal).get() > 0) {
                requiredNominals.computeIfAbsent(nominal, k -> new AtomicInteger(0)).addAndGet(1);
                requiredAmount = requiredAmount - nominal.getValue();
            }
        }

        if (requiredAmount > 0) {
            throw new NoExitsNomimal();
        } else {
            int sum = requiredNominals.keySet().stream().mapToInt(value -> requiredNominals.get(value).get() * value.getValue()).sum();
            log.info("return amount={}", sum);
        }

        for (Nominal nominal : requiredNominals.keySet()) {
            AtomicInteger count = availiableNominals.get(nominal);
            if (count != null) {
                int decreaseAmount = requiredNominals.get(nominal).get();
                count.addAndGet(-decreaseAmount);
            }
            if (count.get() == 0) {
                availiableNominals.remove(nominal);
            }
        }

        return requiredNominals;
    }

    @Override
    public TreeMap<Nominal, AtomicInteger> getAvailable() {
        return availiableNominals;
    }

    public boolean compare(TreeMap<Nominal, AtomicInteger> toCompare) {
        if (availiableNominals.size() != toCompare.size()) return false;
        for (Nominal nominal : toCompare.keySet()) {
            if (toCompare.get(nominal).get() != availiableNominals.get(nominal).get()) return false;
        }
        return true;
    }

}
