package regapp.repositories.implementations;

import regapp.domain.entities.Employee;
import regapp.repositories.EmployeeRepository;

public class EmployeeRepositoryImpl extends GenericRepositoryImpl<Employee, String> implements EmployeeRepository {
    public EmployeeRepositoryImpl() {
        super(Employee.class);
    }
}
