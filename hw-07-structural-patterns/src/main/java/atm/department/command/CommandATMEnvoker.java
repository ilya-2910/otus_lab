package atm.department.command;

import atm.department.command.Command;

public class CommandATMEnvoker<T> {

    private final Command<T> command;

    public CommandATMEnvoker(Command<T> command) {
        this.command = command;
    }

    public T execute() {
        return command.execute();
    }

}
