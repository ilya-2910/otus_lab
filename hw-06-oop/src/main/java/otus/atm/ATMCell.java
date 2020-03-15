package otus.atm;

import lombok.Data;

@Data
public class ATMCell {

    Integer count;

    public ATMCell(int value) {
        this.count = value;
    }

    public void add(int addCount) {
        count = count + addCount;
    }

}
