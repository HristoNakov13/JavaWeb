package residentevil.web.restcontrollers;

import com.google.gson.Gson;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import residentevil.domain.entities.enums.Magnitude;
import residentevil.domain.entities.enums.Mutation;
import residentevil.domain.models.binding.VirusAddBindingModel;
import residentevil.domain.models.binding.VirusDeleteBindingModel;
import residentevil.domain.models.binding.VirusEditBindingModel;
import residentevil.domain.models.service.CapitalServiceModel;
import residentevil.domain.models.service.VirusServiceModel;
import residentevil.domain.models.view.CapitalAddViewModel;
import residentevil.domain.models.view.VirusDetailsViewModel;
import residentevil.domain.models.view.VirusListViewModel;
import residentevil.services.CapitalService;
import residentevil.services.VirusService;
import residentevil.util.ValidatorUtil;

import java.util.List;
import java.util.Set;
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

        init();
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

            return ResponseEntity.status(201)
                    .contentType(APPLICATION_JSON)
                    .body(String.format("{\"id\": \"%s\"}", virusServiceModel.getId()));
        }

        String errors = String.join("\r\n", this.validator.getErrors(virus));

        return ResponseEntity.status(400).body(errors);
    }

    @RequestMapping(path = "/edit", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<String> editVirus(@RequestBody String body) {
        VirusEditBindingModel virus = this.gson.fromJson(body, VirusEditBindingModel.class);

        if (this.virusService.getVirusById(virus.getId()) == null) {
            return ResponseEntity.status(400).body(String.format("Virus with id %s doesnt exist", virus.getId()));
        }

        if (this.validator.isValid(virus)) {
            VirusServiceModel virusServiceModel = this.virusService.save(this.modelMapper.map(virus, VirusServiceModel.class));

            return ResponseEntity.status(200)
                    .contentType(APPLICATION_JSON)
                    .body(String.format("{\"id\": \"%s\"}", virusServiceModel.getId()));
        }

        String errors = String.join("\r\n", this.validator.getErrors(virus));

        return ResponseEntity.status(400).body(errors);
    }

    @RequestMapping(path = "", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<String> deleteVirus(@RequestBody String body) {
        VirusDeleteBindingModel virus = this.gson.fromJson(body, VirusDeleteBindingModel.class);
        virusService.deleteVirusById(virus.getId());

        return ResponseEntity.status(204).body("Deleted");
    }

    private void init() {
        Converter<Set<String>, Set<CapitalServiceModel>> capitalFetch = new Converter<Set<String>, Set<CapitalServiceModel>>() {
            @Override
            public Set<CapitalServiceModel> convert(MappingContext<Set<String>, Set<CapitalServiceModel>> context) {
                return context.getSource()
                        .stream()
                        .map(id -> capitalService.findCapitalById(id))
                        .collect(Collectors.toSet());
            }
        };

        this.modelMapper.createTypeMap(VirusEditBindingModel.class, VirusServiceModel.class)
                .addMappings(mapper ->
                        mapper.using(capitalFetch)
                                .map(VirusEditBindingModel::getCapitals,
                                        VirusServiceModel::setCapitals));
    }
}
