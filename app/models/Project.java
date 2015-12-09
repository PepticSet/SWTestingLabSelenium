package models;

import javax.persistence.*;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long id;

    public String name;

    public String description;

    public Double workHours;
}
