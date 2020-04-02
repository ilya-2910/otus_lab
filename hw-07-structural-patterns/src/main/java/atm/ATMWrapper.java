package atm;

import java.util.List;
import java.util.TreeMap;

public class ATMWrapper implements ATM {

    private ATM atm;

    public ATMWrapper(ATM atm) {
        this.atm = atm;
    }

    @Override
    public void init(List<Nominal> nominals) {
        atm.init(nominals);
    }

    @Override
    public void receive(List<Nominal> nominals) {
        atm.receive(nominals);
    }

    @Override
    public ATMStorage get(Integer amount) {
        return atm.get(amount);
    }

    @Override
    public TreeMap<Nominal, ATMCell> getAvailable() {
        return atm.getAvailable();
    }

    @Override
    public Integer getTotalAmount() {
        return atm.getTotalAmount();
    }

    @Override
    public boolean compare(TreeMap<Nominal, ATMCell> toCompare) {
        return atm.compare(toCompare);
    }

    @Override
    public void restore() {
        atm.restore();
    }

}
