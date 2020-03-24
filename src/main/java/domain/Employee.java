package domain;

import java.util.Date;
import java.util.Objects;

/**
 * Domain class representing an employee.
 * Note: such class doesn't exist for departments due to redundancy. Departments are
 * represented in database just by their names and manipulating employees in context
 * of their affiliation with a department can be performed simply by having the department
 * name as a field in this class.
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


    // Overridden methods for tests and debugging

    // Not comparing date due to how it is represented within DB and in an object
    // TODO: use something else other than Date class
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(name, employee.name) &&
                Objects.equals(email, employee.email) &&
                Objects.equals(salary, employee.salary) &&
                Objects.equals(departmentName, employee.departmentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, email, salary, departmentName);
    }

    @Override
    public String toString() {
        return  "Name: " + this.name +
                "\nEmail: " + this.email +
                "\nHire date: " + this.date +
                "\nDepartment: " + this.departmentName +
                "\nSalary: " + this.salary;
    }
}
