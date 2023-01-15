package com.skypro.employee.data;

import com.skypro.employee.model.V2Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Имитируем поведение БД. Можем что-то получить, можем что-то добавить. В целом можно реализовать удалить и изменить
 */
public class V2EmployeeData {
    private static final List<V2Employee> employees = new ArrayList<>();
    static { //Сразу инициализируем какие-то существующие внутри записи.
        var ivanov = new V2Employee("Иванов", "Иван", 1, 65000);
        addEmployee(ivanov);
        addEmployee(new V2Employee.Builder()
                .firstName("Петров")
                .lastName("Петр")
                .department(1)
                .salary(80000)
                .build()
        );
        addEmployee(
                new V2Employee.Builder(ivanov) //Обрати внимание что у него не указываю salary, потому что это значение
                        .firstName("Сидоров") //будет скопировано у ivanov.
                        .lastName("Сидор")
                        .department(2)
                        .build()
        );
    }
    public static List<V2Employee> employees() {
        return employees;
    }
    public static void addEmployee(V2Employee employee) {
        employees.add(
                new V2Employee(
                        employees.size()+1,
                        employee.firstName(),
                        employee.lastName(),
                        employee.department(),
                        employee.salary()
                )
        );
    }

    public static V2Employee findEmployee(V2Employee employee) throws Exception {
        var findEmpl = employees.stream()
                .filter(it ->
                        it.id() == employee.id()
                                || (Objects.equals(it.firstName(), employee.firstName())
                                && Objects.equals(it.lastName(), employee.lastName()))
                ) //Фильтруем коллекцию по id или по имени и фамилии
                .toList(); //Все найденые результаты приводим в формат нового листа ибо стал stream
        if (findEmpl.isEmpty()) throw new Exception("Список сотрудников пуст!"); //Проверяем пустой ли список
        else return findEmpl.get(0); //Если не пустой, то берём первый элемент списка
    }
}
