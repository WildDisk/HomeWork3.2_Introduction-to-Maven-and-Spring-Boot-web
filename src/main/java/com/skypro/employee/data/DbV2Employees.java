package com.skypro.employee.data;

import com.skypro.employee.model.V2Employee;

import java.util.ArrayList;
import java.util.List;

public class DbV2Employees implements Employees {
    private final List<V2Employee> employees;
    private final V2Employee employee;

    public DbV2Employees(Employees employees) throws Exception {
        try {
            this.employees = employees.iterable();
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
        this.employee = new V2Employee(-1, "", "", -1, 0);
    }

    public DbV2Employees(Employe employe) throws Exception {
        try {
            this.employee = employe.fetch();
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
        this.employees = new ArrayList<>();
    }

    @Override
    public List<V2Employee> iterable() {
        return V2EmployeeData.employees();
    }
}
