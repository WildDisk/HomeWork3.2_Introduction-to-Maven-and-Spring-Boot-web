package com.skypro.employee.data;

import com.skypro.employee.model.V2Employee;

public class DbV2Employee implements Employe {
    private V2Employee employee;

    /**
     * Вторичнйы конструктор
     * @param employe
     * @throws Exception
     */
    public DbV2Employee(Employe employe) throws Exception {
        try {
            this.employee = employe.fetch();
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Первичный конструктор
     * @param employee
     */
    public DbV2Employee(V2Employee employee) {
        this.employee = employee;
    }
    @Override
    public V2Employee fetch() {
        return V2EmployeeData.findEmployee(this.employee);
    }
}
