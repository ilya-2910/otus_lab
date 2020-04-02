package mapper;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class Person {

    public int id;
    public Integer salary;
    public String name;
    public List<String> roles;
    public Set<String> roles2;
    public List<Double> roles3;
    public String[] books;

    public Map map;

    public double height;
    public Double height2;
    public double weight2;



}
