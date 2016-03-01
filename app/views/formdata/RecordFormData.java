package views.formdata;

import models.Employee;
import models.Project;
import play.Logger;
import play.api.data.validation.ValidationError;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecordFormData {
    public List<String> workers = new ArrayList<>();
    public String job = "";
    public String startTime = "";
    public String endTime = "";
    public String location = "";
    public String notes = "";

    public RecordFormData() {}

    public RecordFormData(List<Employee> workers, Project job, Date startTime, Date endTime,
                          String location, String notes) {
        for(Employee employee : workers) {
            this.workers.add(employee.name);
        }
        this.job = job.name;

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.startTime = formatter.format(startTime);
            this.endTime = formatter.format(endTime);
        }
        catch (Exception e) {
            Logger.error("Bad date");
        }

        this.location = location;
        this.notes = notes;
    }

    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();

        //TODO: implement validation

        if(errors.size() > 0)
            return errors;

        return null;
    }
}
