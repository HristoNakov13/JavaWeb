package regapp.services;

import org.modelmapper.ModelMapper;
import regapp.domain.entities.Employee;
import regapp.domain.models.binding.EmployeeBindingModel;
import regapp.domain.models.view.EmployeeListViewModel;
import regapp.repositories.EmployeeRepository;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;

    @Inject
    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public void save(EmployeeBindingModel employeeBindingModel) {
        Employee employee = this.modelMapper.map(employeeBindingModel, Employee.class);

        this.employeeRepository.save(employee);
    }

    public List<EmployeeListViewModel> getAllEmployees() {
        List<Employee> employees = this.employeeRepository.findAll();

        return employees
                .stream()
                .map(employee -> this.modelMapper.map(employee, EmployeeListViewModel.class))
                .collect(Collectors.toList());
    }

    public void removeEmployeeById(String id) {
        this.employeeRepository.deleteById(id);
    }

    public BigDecimal getAverageSalary() {
        List<Employee> employees = this.employeeRepository.findAll();
        BigDecimal allSalariesSum = employees
                .stream()
                .map(Employee::getSalary)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return allSalariesSum.divide(BigDecimal.valueOf(employees.size()), RoundingMode.CEILING);
    }

}