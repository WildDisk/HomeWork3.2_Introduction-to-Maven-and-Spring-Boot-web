package com.skypro.employee.controller;

import com.skypro.employee.data.DbV2Employee;
import com.skypro.employee.data.DbV2Employees;
import com.skypro.employee.model.V2Employee;
import com.skypro.employee.service.SrvV2Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/V2/")
public class V2EmployeeController {
    @GetMapping("employees")
    public List<V2Employee> employees(@RequestParam String id) throws Exception {
        // По порядку, по сути это реализация паттерна декоратор
        return new DbV2Employees( //Делигируем БД найти всех сотрудников
                new SrvV2Employee( //Бд в свою очередь делигирует сервису проверить, а есть ли права на запрос таких данных
                        new DbV2Employee( //Сервису чтобы это понять, нужно найти пользователя в бд, соответственно данный объект ищет запрашиваемого сотрудника
                                new V2Employee( //Тут уже передаём объект с набором данных. По сути это DTO, который ты реализовал в EmployeeRequest
                                        Long.parseLong(id), //Берём id получаемый из реквеста. Формат обращения типа localhost:8080/V2/employees?id=1 где id это аргумент метода @RequestParam
                                        "",
                                        "",
                                        1,
                                        1
                                )
                        )//Получив объект V2Employee, DbV2Employee выполняет для SrvV2Employee метод fetch который реализует через интерфейс Employe внутри конструктора сервиса
                ) //Получив результат из БД(DbV2Employee().fetch()), сервис начинает проверять, а есть ли у данного сотрудника доступ к данным.
        ).iterable(); // Если всё хорошо, DbV2Employee идёт в БД и забирает всё необходимое, если же нет, то от сервиса он получит что доступ запрещён, а далее по цепочке передаст уже это сообщение пользователю
    }
}
