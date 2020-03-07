package regapp.web.managedbeans;

import regapp.domain.models.binding.EmployeeBindingModel;
import regapp.domain.models.binding.PositionBindingModel;
import regapp.services.EmployeeService;
import regapp.services.PositionService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@RequestScoped
public class EmployeeRegisterBean {
    private EmployeeService employeeService;
    private PositionService positionService;
    private PositionBindingModel positionBindingModel;
    private EmployeeBindingModel employeeBindingModel;

    @Inject
    public EmployeeRegisterBean(EmployeeService employeeService, PositionService positionService) {
        this.positionBindingModel = new PositionBindingModel();
        this.employeeBindingModel = new EmployeeBindingModel();

        this.employeeService = employeeService;
        this.positionService = positionService;
    }

    public PositionBindingModel getPositionBindingModel() {
        return positionBindingModel;
    }

    public void setPositionBindingModel(PositionBindingModel positionBindingModel) {
        this.positionBindingModel = positionBindingModel;
    }

    public EmployeeBindingModel getEmployeeBindingModel() {
        return employeeBindingModel;
    }

    public void setEmployeeBindingModel(EmployeeBindingModel employeeBindingModel) {
        this.employeeBindingModel = employeeBindingModel;
    }

    public void registerEmployee() throws IOException {
        this.positionService.save(this.positionBindingModel);

        this.employeeBindingModel.setPosition(this.positionBindingModel);
        this.employeeService.save(this.employeeBindingModel);

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect("/");
    }
}