package residentevil.web.restcontrollers;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import residentevil.domain.entities.enums.Magnitude;
import residentevil.domain.entities.enums.Mutation;
import residentevil.domain.models.binding.VirusAddBindingModel;
import residentevil.domain.models.service.VirusServiceModel;
import residentevil.domain.models.view.CapitalAddViewModel;
import residentevil.domain.models.view.VirusDetailsViewModel;
import residentevil.domain.models.view.VirusListViewModel;
import residentevil.services.CapitalService;
import residentevil.services.VirusService;
import residentevil.util.ValidatorUtil;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping("/api/viruses")
public class VirusRestController {
    private VirusService virusService;
    private ModelMapper modelMapper;
    private CapitalService capitalService;
    private Gson gson;
    private ValidatorUtil validator;

    public VirusRestController(VirusService virusService, ModelMapper modelMapper, CapitalService capitalService, Gson gson, ValidatorUtil validator) {
        this.virusService = virusService;
        this.modelMapper = modelMapper;
        this.capitalService = capitalService;
        this.gson = gson;
        this.validator = validator;
    }


    @GetMapping("")
    public List<VirusListViewModel> getAllViruses() {
        return this.virusService
                .getAllViruses()
                .stream()
                .map(virus -> this.modelMapper.map(virus, VirusListViewModel.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public VirusDetailsViewModel getVirusById(@PathVariable(name = "id") String id) {
        return this.modelMapper
                .map(this.virusService.getVirusById(id),
                        VirusDetailsViewModel.class);
    }

    @GetMapping("/mutations")
    public List<String> getAvailableMutations() {
        return Mutation.getMutations();
    }

    @GetMapping("/magnitudes")
    public List<String> getAvailableMagnitudes() {
        return Magnitude.getMagnitudes();
    }

    @GetMapping("/capitals")
    public List<CapitalAddViewModel> getAvailableCapitals() {
        return this.capitalService.getAllCapitals()
                .stream()
                .map(capital -> this.modelMapper.map(capital, CapitalAddViewModel.class))
                .collect(Collectors.toList());
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> createVirus(@RequestBody String body) {
        VirusAddBindingModel virus = this.gson.fromJson(body, VirusAddBindingModel.class);

        if (this.validator.isValid(virus)) {
            VirusServiceModel virusServiceModel = this.virusService.save(this.modelMapper.map(virus, VirusServiceModel.class));

            return ResponseEntity.status(200)
                    .contentType(APPLICATION_JSON)
                    .body(String.format("{\"id\": \"%s\"}", virusServiceModel.getId()));
        }

        String errors = String.join("\r\n", this.validator.getErrors(virus));

        return ResponseEntity.status(400).body(errors);
    }
}
