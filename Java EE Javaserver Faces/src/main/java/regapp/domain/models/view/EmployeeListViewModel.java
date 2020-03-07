package regapp.domain.models.view;

import java.math.BigDecimal;

public class EmployeeListViewModel {
    private String id;
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private Integer age;
    private PositionListViewModel position;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public PositionListViewModel getPosition() {
        return position;
    }

    public void setPosition(PositionListViewModel position) {
        this.position = position;
    }
}
