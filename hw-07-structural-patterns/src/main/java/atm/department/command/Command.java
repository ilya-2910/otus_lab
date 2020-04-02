package atm.department.command;

public interface Command<T> {
    T execute();
}
