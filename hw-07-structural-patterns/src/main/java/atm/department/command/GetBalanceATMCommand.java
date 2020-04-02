package atm.department.command;

import atm.ATM;

public class GetBalanceATMCommand implements Command<Integer> {

    private final ATM atm;

    public GetBalanceATMCommand(ATM atm) {
        this.atm = atm;
    }

    @Override
    public Integer execute() {
        return atm.getTotalAmount();
    }

}
