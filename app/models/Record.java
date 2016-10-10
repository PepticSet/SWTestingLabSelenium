package models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import models.helpers.QueryHelper;
import play.Logger;
import play.data.format.Formats;
import views.formdata.RecordFormData;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Record implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> workers) {
        this.employees = workers;
    }

    public Project getJob() {
        return job;
    }

    public void setJob(Project job) {
        this.job = job;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @ManyToMany(cascade = {CascadeType.DETACH})
    @JsonBackReference
    public List<Employee> employees = new ArrayList<Employee>();

    @OneToOne
    @MapsId
    public Project job;

    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date startTime;

    @Formats.DateTime(pattern="dd/MM/yyyy")
    public Date endTime;

    public String location;

    public String notes;



    public Record() {}

    public static Record makeInstance(RecordFormData formData) {
        Record record = new Record();

        for (String worker : formData.workers) {
            Logger.info("yay");
            record.employees.add(QueryHelper.findEmployee(worker));
        }

        record.job = QueryHelper.findProject(formData.job);

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            record.startTime = formatter.parse(formData.startTime);
            record.endTime = formatter.parse(formData.endTime);
        }
        catch (Exception e) {
            Logger.error("Bad date");
        }

        record.location = formData.location;
        record.notes = formData.notes;

        return record;
    }
}
