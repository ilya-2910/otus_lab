package atm.department.command;

import atm.ATM;

public class RestoreATMCommand implements Command<Void> {

    private final ATM atm;

    public RestoreATMCommand(ATM atm) {
        this.atm = atm;
    }

    @Override
    public Void execute() {
        atm.restore();
        return null;
    }

}
