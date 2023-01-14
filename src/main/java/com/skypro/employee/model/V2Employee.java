package com.skypro.employee.model;

/**
 * Начиная с 17 или 18 джамбы появились класс-рекорды которые отчасти напоминают data class в Котлине, только хуже )
 * суть в том что ты primary конструктор пишешь сразу на уровне класса, а не где-то внутри. Более того свойства рекорда
 * всегда иммутабельные, то есть final.<br><br>
 * На счёт методов. Почему не get, потому что, а зачем? В условиях мутабильности, это понятно, необходимо разделять,
 * что у тебя берёт значение, а что его сетит. В данном случае ты значения всегда только получаешь, а значит нету смысла
 * и в явном указывании get.
 * @param id
 * @param firstName
 * @param lastName
 * @param department
 * @param salary
 */
public record V2Employee(
        long id, //long потому что в БД id может быть очень большим и в int он не поместится
        String firstName,
        String lastName,
        int department,
        double salary //логично double ибо вряд ли ты зп получаешь без учёта копеек ))))
) {
    /**
     * Вторичный конструктор с дефолтной инициализацией id. Почему -1? Потому что в БД такого точно быть не может и при
     * этом мы точно уверены что не выхватим NPE(Null Pointer Exception)
     * @param firstName
     * @param lastName
     * @param department
     * @param salary
     */
    public V2Employee(String firstName, String lastName, int department, double salary) {
        this(-1, firstName, lastName, department, salary);
    }
    public long id() {
        return this.id;
    }
    public String firstName() {
        return this.firstName;
    }
    public String lastName() {
        return this.lastName;
    }
    public int department() {
        return this.department;
    }
    public double salary() {
        return this.salary;
    }

    /**
     * Вот здесь особо внимания можешь не обращать, просто показываю какие ещё есть способы сборки модели, в том числе
     * делать копирование.<br>
     * Вданном случае это реализация паттерна билдер. Сразу обращаю внимание, что все методы здесь кроме билда, это по
     * сути сеттеры. Билд это метод сборщик который создаёт новый экземпляр класса V2Employee.<br>
     * Зачем объектные типы данных. Для проверки на null. Допускать создание NPE это плохая идея, но сейчас просто
     * демонстрирую варианты того, как можно делать. В условиях базовых типов, условия проверки во время билда пришлось
     * бы делать гораздо сложнее.
     */
    public static class Builder {
        private String firstName;
        private String lastName;
        private Integer department;
        private Double salary;
        private final V2Employee employee;

        /**
         * Вторичный конструктор для реализации копирования объекта. Суть в том что ты вкладываешь
         * уже существующий объект и далее вызываешь методы которые будут формировать новый объект с
         * учётом уже имевшихся значений у предыдущего.
         * @param employee
         */
        public Builder(V2Employee employee) {
            this.employee = employee;
        }

        /**
         * Тоже секондори конструктор который помогает сделать копию но уже не с V2, а с Employee.
         * @param employee
         */
        public Builder(Employee employee) {
            this.employee = new V2Employee(
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getDepartment(),
                    employee.getSalary()
            );
        }

        /**
         * Ну собственно коструктор по умолчанию.
         */
        public Builder() {
            this.employee = new V2Employee("", "", 0, 0);
        }

        /**
         * Обрати внимание что все методы возвращают this, то есть самого себя, а именно Builder. Это позволяет по
         * цепочке вызывать методы не обращаясь постоянно руками к екземпляру.
         * @param firstName
         * @return Builder
         */
        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder department(int department) {
            this.department = department;
            return this;
        }

        public Builder salary(double salary) {
            this.salary = salary;
            return this;
        }

        public V2Employee build() {
            if (this.firstName == null) this.firstName = this.employee.firstName;
            if (this.lastName == null) this.lastName = this.employee.lastName;
            if (this.department == null) this.department = this.employee.department;
            if (this.salary == null) this.salary = this.employee.salary;
            return new V2Employee(this.firstName, this.lastName, this.department, this.salary);
        }
    }
}
