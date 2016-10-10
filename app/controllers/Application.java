package controllers;

import models.Employee;
import models.Project;
import models.Record;
import play.*;
import play.Logger;
import play.api.*;
import play.mvc.*;
import play.db.jpa.*;
import views.formdata.RecordFormData;
import views.html.*;
import models.Person;
import play.data.Form;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static play.libs.Json.*;

public class Application extends Controller {

    public Result index() {
        return ok(index.render());
    }

    public Result employee() { return ok(employee.render()); }

    @Transactional
    public Result addPerson() {
        Person person = Form.form(Person.class).bindFromRequest().get();
        JPA.em().persist(person);
        return redirect(routes.Application.index());
    }

    @Transactional(readOnly = true)
    public Result getPersons() {
        List<Person> persons = (List<Person>) JPA.em().createQuery("select p from Person p").getResultList();
        return ok(toJson(persons));
    }

    @Transactional
    public Result addEmployee() {
        Employee employee = Form.form(Employee.class).bindFromRequest().get();
        JPA.em().persist(employee);
        return redirect(routes.Application.employee());
    }

    @Transactional
    public Result getEmployees() {
        List<Employee> employees = (List<Employee>) JPA.em().createQuery("select e from Employee e").getResultList();
        return ok(toJson(employees));
    }

    @Transactional
    public Result deleteEmployee(Long id) {
        Employee employee = JPA.em().find(Employee.class, id);
        JPA.em().remove(employee);
        return redirect(routes.Application.employee());
    }

    @Transactional
    public Result getProjects() {
        List<Project> projects = (List<Project>) JPA.em().createQuery("select pr from Project pr").getResultList();
        return ok(project.render(projects));
    }

    @Transactional
    public Result deleteProject(Long id) {
        Project project = JPA.em().find(Project.class, id);
        JPA.em().remove(project);
        return redirect(routes.Application.getProjects());
    }

    @Transactional
    public Result editProject(Long id) {
        // is this a copy paste bug? :O
        Project project = JPA.em().find(Project.class, id);
        JPA.em().remove(project);
        return redirect(routes.Application.getProjects());
    }

    @Transactional
    public Result addProject() {
        Project project = Form.form(Project.class).bindFromRequest().get();
        JPA.em().persist(project);
        return redirect(routes.Application.getProjects());
    }

    @Transactional
    public Result getRecords() {
        List<Record> records = (List<Record>) JPA.em().createQuery("select re from Record re").getResultList();
        List<Project> projects =(List<Project>) JPA.em().createQuery("select pr from Project pr").getResultList();
        List<Employee> employees = (List<Employee>) JPA.em().createQuery("select e from Employee e").getResultList();
        return ok(record.render(records, employees, projects));
    }

    @Transactional
    public Result addRecord() {
        Form<RecordFormData> formData = Form.form(RecordFormData.class).bindFromRequest();
        Record record = Record.makeInstance(formData.get());
        try {
            JPA.em().persist(record);
        } catch (Exception e) {
            Logger.error(e.getMessage());
        }
//        JPA.em().persist(record);
        return redirect(routes.Application.getRecords());
    }

    @Transactional
    public Result deleteRecord(Long id) {
        Record record= JPA.em().find(Record.class, id);
        JPA.em().remove(record);
        return redirect(routes.Application.getRecords());
    }

    @Transactional(readOnly = true)
    public Result getStats() {
        List<Record> records = (List<Record>) JPA.em().createQuery("select re from Record re").getResultList();
        List<Project> projects =(List<Project>) JPA.em().createQuery("select pr from Project pr").getResultList();
        List<Employee> employees = (List<Employee>) JPA.em().createQuery("select e from Employee e").getResultList();

        List<Double> projectCosts = new ArrayList<>();
        List<Long> projectDurations = new ArrayList<>();
        List<String> projectNames = new ArrayList<>();
        for(Record record : records) {
            double currentProjectCost = 0.0;
            for(Employee employee : record.getEmployees()) {
                currentProjectCost += employee.salary * record.getJob().workHours / record.getEmployees().size();
            }
            projectCosts.add(currentProjectCost);
            projectNames.add(record.getJob().name);

            // in seconds
            projectDurations.add(
                    TimeUnit.MILLISECONDS.toSeconds(
                            record.endTime.getTime() - record.startTime.getTime()
                    )
            );
        }


        return ok(statistics.render(projectNames, projectCosts, projectDurations));
    }
}
