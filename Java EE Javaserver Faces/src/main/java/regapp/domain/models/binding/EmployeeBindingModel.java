package regapp.domain.models.binding;

import java.math.BigDecimal;

public class EmployeeBindingModel {
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private Integer age;
    private PositionBindingModel position;

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


    public PositionBindingModel getPosition() {
        return position;
    }

    public void setPosition(PositionBindingModel position) {
        this.position = position;
    }
}