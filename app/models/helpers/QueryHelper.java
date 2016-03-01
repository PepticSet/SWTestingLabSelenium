package models.helpers;


import models.Employee;
import models.Project;
import play.db.jpa.JPA;

import java.util.List;
import java.util.Objects;

public class QueryHelper {

    public static List<Employee> getEmployees() {
        return (List<Employee>) JPA.em().createQuery("select e from Employee e").getResultList();
    }

    public static List<Project> getProjects() {
        return (List<Project>) JPA.em().createQuery("select pr from Project pr").getResultList();
    }

    public static Employee findEmployee(String name) {
        for (Employee worker : getEmployees()) {
            if (name.equals(worker.name)) {
                return worker;
            }
        }
//        return null;
        return getEmployees().get(0);
    }

    public static Project findProject(String name) {
        for (Project project : getProjects()) {
            if (Objects.equals(name, project.name)) {
                return project;
            }
        }
        return null;
    }
}
