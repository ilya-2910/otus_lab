package otus.atm;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ATMImpl implements ATM {

    private TreeMap<Nominal, AtomicInteger> nominals = new TreeMap<>();

    public void receive(List<Nominal> rawNominals) {
        rawNominals.stream().forEach(nominal -> {
            nominals.computeIfAbsent(nominal, k -> new AtomicInteger(0)).addAndGet(1);
         });
    }

    public TreeMap<Nominal, Integer> get(Integer amountInt) throws NoExitsNomimal {
        int totalAmount = nominals.keySet().stream().mapToInt(value -> nominals.get(value).get() * value.getValue()).sum();
        log.info("totalAmount={}",totalAmount);

        if (totalAmount < amountInt) {
            throw new NoExitsNomimal();
        }

//        int twothousandrupees = 0;
//        int initialtwothousandrupees = nominals.get(Nominal._1000).get();

        TreeMap<Nominal, AtomicInteger> result = new TreeMap<>();
        for (Nominal nominal : nominals.keySet()) {
            while (amountInt >= nominal.getValue() && nominals.get(nominal).get() > 0) {
                result.computeIfAbsent(nominal, k -> new AtomicInteger(0)).addAndGet(1);
                amountInt = amountInt - nominal.getValue();
            }
        }

        if (amountInt > 0) {
            throw new NoExitsNomimal();
        } else {
            int sum = result.keySet().stream().mapToInt(value -> result.get(value).get() * value.getValue()).sum();
            log.info("return amount={}", sum);
        }



        checkExistNominals();
        return null;
    }

    private void checkExistNominals() throws NoExitsNomimal {

    }

}
