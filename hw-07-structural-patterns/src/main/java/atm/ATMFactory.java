package atm;

import java.util.List;

public class ATMFactory {

    public ATMImpl createATM(List<Nominal> initNominals) {
        return new ATMImpl(initNominals);
    }
}
