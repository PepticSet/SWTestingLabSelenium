package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Employee implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long id;

    public String name;

    public Integer salary;

    @ManyToMany(cascade = {CascadeType.DETACH}, mappedBy = "employees")
    public List<Record> records = new ArrayList<Record>();
}
