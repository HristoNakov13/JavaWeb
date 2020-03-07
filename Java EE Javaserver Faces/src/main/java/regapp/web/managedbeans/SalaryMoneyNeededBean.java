package regapp.web.managedbeans;

import regapp.services.EmployeeService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;

@Named
@RequestScoped
public class SalaryMoneyNeededBean {
    private final BigDecimal TOTAL_NEEDED_SUM = BigDecimal.valueOf(17000);

    private EmployeeService employeeService;
    private BigDecimal averageSalary;

    @Inject
    public SalaryMoneyNeededBean(EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.averageSalary = this.employeeService.getAverageSalary();
    }

    public BigDecimal getTotalNeededSum() {
        BigDecimal remainingNeeded = TOTAL_NEEDED_SUM.subtract(averageSalary);

        return remainingNeeded.signum() > 0
                ? remainingNeeded
                : BigDecimal.ZERO;
    }

    public BigDecimal getAverageSalary() {
        return this.averageSalary;
    }
}