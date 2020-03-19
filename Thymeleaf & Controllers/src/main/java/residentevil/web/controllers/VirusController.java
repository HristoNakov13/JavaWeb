package residentevil.web.controllers;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import residentevil.domain.entities.enums.Magnitude;
import residentevil.domain.entities.enums.Mutation;
import residentevil.domain.models.binding.VirusAddBindingModel;
import residentevil.domain.models.service.CapitalServiceModel;
import residentevil.domain.models.service.VirusServiceModel;
import residentevil.domain.models.view.CapitalAddViewModel;
import residentevil.services.CapitalService;
import residentevil.services.VirusService;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/viruses")
public class VirusController extends BaseController {

    private CapitalService capitalService;
    private VirusService virusService;
    private ModelMapper modelMapper;

    public VirusController(CapitalService capitalService, VirusService virusService, ModelMapper modelMapper) {
        this.capitalService = capitalService;
        this.virusService = virusService;
        this.modelMapper = modelMapper;

        this.init();
    }

    @GetMapping("/add")
    public ModelAndView addVirus(@ModelAttribute(name = "bindingModel") VirusAddBindingModel bindingModel, ModelAndView modelAndView) {
        modelAndView.addObject("bindingModel", bindingModel);

        return super.view("add-virus", this.addModelAndViewObjects(modelAndView));
    }

    @PostMapping("/add")
    public ModelAndView postAddVirus(@Valid @ModelAttribute(name = "bindingModel") VirusAddBindingModel bindingModel, BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("bindingModel", bindingModel);

            return super.view("add-virus", this.addModelAndViewObjects(modelAndView));
        }

        this.virusService.save(this.modelMapper.map(bindingModel, VirusServiceModel.class));

        return super.redirect("/", modelAndView);
    }

    private ModelAndView addModelAndViewObjects(ModelAndView modelAndView) {
        modelAndView.addObject("magnitudes", Magnitude.getMagnitudes());
        modelAndView.addObject("mutations", Mutation.getMutations());
        modelAndView.addObject("capitals",
                this.capitalService.getAllCapitals()
                        .stream()
                        .map(capital -> this.modelMapper.map(capital, CapitalAddViewModel.class))
                        .collect(Collectors.toList()));

        return modelAndView;
    }

    private void init() {
        Converter<Set<String>, Set<CapitalServiceModel>> idToCapitalFetchConverter = new Converter<Set<String>, Set<CapitalServiceModel>>() {
            @Override
            public Set<CapitalServiceModel> convert(MappingContext<Set<String>, Set<CapitalServiceModel>> context) {
                return context.getSource()
                        .stream()
                        .map(capitalId -> capitalService.findCapitalById(capitalId))
                        .collect(Collectors.toSet());
            }
        };

        this.modelMapper.createTypeMap(VirusAddBindingModel.class, VirusServiceModel.class)
                .addMappings(mapper ->
                        mapper.using(idToCapitalFetchConverter)
                                .map(VirusAddBindingModel::getCapitals, VirusServiceModel::setCapitals));
    }
}
