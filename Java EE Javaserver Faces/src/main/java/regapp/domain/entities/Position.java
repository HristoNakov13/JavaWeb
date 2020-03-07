package regapp.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "positions")
public class Position extends BaseEntity {
    private String name;
    private Set<Employee> employees;

    public Position() {
    }

    @Column(name = "name", unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "position", fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}