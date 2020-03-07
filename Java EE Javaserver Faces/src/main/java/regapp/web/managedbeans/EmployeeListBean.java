package regapp.web.managedbeans;

import regapp.domain.models.view.EmployeeListViewModel;
import regapp.services.EmployeeService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class EmployeeListBean {
    private EmployeeService employeeService;
    private List<EmployeeListViewModel> employees;

    @Inject
    public EmployeeListBean(EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.employees = this.employeeService.getAllEmployees();
    }
}