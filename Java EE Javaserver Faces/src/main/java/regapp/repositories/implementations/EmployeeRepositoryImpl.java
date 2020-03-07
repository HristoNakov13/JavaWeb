package regapp.repositories.implementations;

import regapp.domain.entities.Employee;
import regapp.repositories.EmployeeRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class EmployeeRepositoryImpl extends GenericRepositoryImpl<Employee, String> implements EmployeeRepository {

    @Inject
    public EmployeeRepositoryImpl(EntityManager entityManager) {
        super(entityManager, Employee.class);
    }
}
