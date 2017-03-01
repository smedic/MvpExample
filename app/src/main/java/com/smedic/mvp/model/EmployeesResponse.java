
package com.smedic.mvp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmployeesResponse {

    @SerializedName("employees")
    @Expose
    private List<Employee> employees = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public EmployeesResponse() {
    }

    /**
     * 
     * @param employees
     */
    public EmployeesResponse(List<Employee> employees) {
        super();
        this.employees = employees;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

}
