package com.skypro.employee.service;

import com.skypro.employee.data.Employe;
import com.skypro.employee.data.Employees;
import com.skypro.employee.model.V2Employee;

import java.util.ArrayList;
import java.util.List;

public class SrvV2Employee implements Employees {
    private final List<V2Employee> employees;
    private final V2Employee employee;

    /**
     * Вторичный конструктор
     * @param employees
     * @throws Exception
     */
    public SrvV2Employee(Employees employees) throws Exception {
        try {
            this.employees = employees.iterable();
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
        this.employee = new V2Employee("", "", -1, 0);
    }

    /**
     * Вторичный конструктор
     */
    public SrvV2Employee(Employe employe) throws Exception {
        try {
            this.employee = employe.fetch();
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
        this.employees = new ArrayList<>();
    }

    /**
     * Первичный конструктор
     * @param employees
     * @param employee
     */
    public SrvV2Employee(List<V2Employee> employees, V2Employee employee) {
        this.employees = employees;
        this.employee = employee;
    }
    @Override
    public List<V2Employee> iterable() throws Exception {
        if (employee.id() == 1) return this.employees;
        else throw new Exception("Доступ запрещён!");
    }
}
