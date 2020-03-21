package residentevil.web.restcontrollers;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import residentevil.domain.models.view.CapitalShowViewModel;
import residentevil.domain.models.view.VirusAllViewModel;
import residentevil.services.CapitalService;
import residentevil.services.VirusService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class VirusRestController {
    private VirusService virusService;
    private ModelMapper modelMapper;
    private CapitalService capitalService;

    public VirusRestController(VirusService virusService, ModelMapper modelMapper, CapitalService capitalService) {
        this.virusService = virusService;
        this.modelMapper = modelMapper;
        this.capitalService = capitalService;
    }

    @GetMapping("/viruses")
    public List<VirusAllViewModel> getAllViruses() {
        return this.virusService
                .getAllViruses()
                .stream()
                .map(virus -> this.modelMapper.map(virus, VirusAllViewModel.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/capitals")
    public List<CapitalShowViewModel> getAllCapitals() {
        return this.capitalService.getAllCapitals()
                .stream()
                .map(capital -> this.modelMapper.map(capital, CapitalShowViewModel.class))
                .collect(Collectors.toList());
    }
}
