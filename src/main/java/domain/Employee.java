package domain;

import java.util.Date;

/**
 * @author linet
 */
public class Employee {

    private String name;
    private Date date;
    private String email;
    private Integer salary;
    private String departmentName;

    public Employee(String name, Date date, String email, Integer salary, String departmentName) {
        this.name = name;
        this.date = date;
        this.email = email;
        this.salary = salary;
        this.departmentName = departmentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
