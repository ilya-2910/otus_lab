package atm.department;

import atm.ATM;

import java.util.List;

public class DepartmentProxy implements Department {

    private final Department department;

    public DepartmentProxy(Department department) {
        this.department = department;
    }

    @Override
    public void setATMs(List<ATM> atms) {
        this.department.setATMs(atms);
    }

    @Override
    public List<ATM> getATMs() {
        return this.department.getATMs();
    }

    @Override
    public Integer getBalance() {
        return this.department.getBalance();
    }

    @Override
    public void restore() {
        this.department.restore();
    }

}
