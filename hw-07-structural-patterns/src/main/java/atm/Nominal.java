package atm;

public enum Nominal {

    _5000(5000),

    _1000(1000),

    _500(500),

    _200(200),

    _100(100),

    _50(100),

    _10(10);

    private Integer value;

    Nominal(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
