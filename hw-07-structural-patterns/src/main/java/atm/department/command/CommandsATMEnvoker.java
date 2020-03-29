package atm.department.command;

import atm.department.command.Command;

import java.util.List;

public class CommandsATMEnvoker {

    private final List<Command> commands;

    public CommandsATMEnvoker(List<Command> commands) {
        this.commands = commands;
    }

    public void execute() {
        commands.forEach(command -> command.execute());
    }


}
