package residentevil.web.restcontrollers;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import residentevil.domain.entities.enums.Magnitude;
import residentevil.domain.entities.enums.Mutation;
import residentevil.domain.models.service.CapitalServiceModel;
import residentevil.domain.models.view.CapitalAddViewModel;
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

    @GetMapping("/viruses/mutations")
    public List<String> getAvailableMutations() {
        return Mutation.getMutations();
    }

    @GetMapping("/viruses/magnitudes")
    public List<String> getAvailableMagnitudes() {
        return Magnitude.getMagnitudes();
    }

    @GetMapping("/viruses/capitals")
    public List<CapitalAddViewModel> getAvailableCapitals() {
        return this.capitalService.getAllCapitals()
                .stream()
                .map(capital -> this.modelMapper.map(capital, CapitalAddViewModel.class))
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
