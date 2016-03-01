package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long id;

    public String name;

    public String description;

    public Double workHours;

}
