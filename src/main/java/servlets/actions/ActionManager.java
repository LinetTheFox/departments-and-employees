package servlets.actions;

import servlets.actions.impl.get.department.DeleteDepartmentAction;
import servlets.actions.impl.get.department.ForwardToDepartmentAddFormAction;
import servlets.actions.impl.get.department.ForwardToDepartmentEditFormAction;
import servlets.actions.impl.get.department.GetAllDepartmentsAction;
import servlets.actions.impl.get.employee.*;
import servlets.actions.impl.post.department.AddNewDepartmentAction;
import servlets.actions.impl.post.department.UpdateDepartmentAction;
import servlets.actions.impl.post.employee.AddNewEmployeeAction;
import servlets.actions.impl.post.employee.UpdateEmployeeAction;

/**
 * Utility class that enables getting actions from one place.
 * Contains a bunch of static methods each returning a specific
 * Action implementation instead of getting them from HashMap
 * because some of Actions require constructors with parameters so
 * initializing them properly from HashMap would be more
 * complicated.
 * @author linet
 */
public final class ActionManager {

    public static Action getAllDepartments() {
        return new GetAllDepartmentsAction();
    }

    public static Action forwardToDepartmentAddForm() {
        return new ForwardToDepartmentAddFormAction();
    }

    public static Action forwardToDepartmentEditForm(Long id) {
        return new ForwardToDepartmentEditFormAction(id);
    }

    public static Action deleteDepartment(Long id) {
        return new DeleteDepartmentAction(id);
    }

    public static Action getAllEmployees(Long id) {
        return new GetAllEmployeesAction(id);
    }

    public static Action getSingleEmployeeAction(Long id) {
        return new GetSingleEmployeeAction(id);
    }

    public static Action forwardToEmployeeAddForm() {
        return new ForwardToEmployeeAddFormAction();
    }

    public static Action forwardToEmployeeEditForm(Long id) {
        return new ForwardToEmployeeEditFormAction(id);
    }

    public static Action deleteEmployee(Long id) {
        return new DeleteEmployeeAction(id);
    }

    public static Action addNewDepartment() {
        return new AddNewDepartmentAction();
    }

    public static Action addNewEmployee() {
        return new AddNewEmployeeAction();
    }

    public static Action updateDepartment(Long id) {
        return new UpdateDepartmentAction(id);
    }

    public static Action updateEmployee(Long id) {
        return new UpdateEmployeeAction(id);
    }

}
