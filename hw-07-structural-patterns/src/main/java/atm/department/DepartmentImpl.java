package atm.department;

import atm.ATM;
import atm.department.command.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DepartmentImpl implements Department {

    private List<ATM> atms = new ArrayList<>();

    @Override
    public void setATMs(List<ATM> atms) {
        this.atms = atms;
    }

    @Override
    public List<ATM> getATMs() {
        return atms;
    }

    @Override
    public Integer getBalance() {
        Integer balance = 0;
        for (ATM atm : atms) {
            GetBalanceATMCommand getBalanceATMCommand = new GetBalanceATMCommand(atm);
            CommandATMEnvoker<Integer> commandATMEnvoker = new CommandATMEnvoker(getBalanceATMCommand);
            balance = balance + commandATMEnvoker.execute();
        }
        return balance;
    }

    @Override
    public void restore() {
        List<Command> commands = atms.stream().map(atm -> new RestoreATMCommand(atm)).collect(Collectors.toList());
        CommandsATMEnvoker atmEnvoker = new CommandsATMEnvoker(commands);
        atmEnvoker.execute();
    }

}
