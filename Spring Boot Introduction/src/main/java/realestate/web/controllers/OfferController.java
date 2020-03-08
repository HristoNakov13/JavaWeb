package realestate.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import realestate.domain.models.binding.OfferFindBindingModel;
import realestate.domain.models.binding.OfferRegisterBindingModel;
import realestate.domain.models.service.OfferServiceModel;
import realestate.services.OfferService;

@Controller
public class OfferController {
    private OfferService offerService;
    private ModelMapper modelMapper;

    @Autowired
    public OfferController(OfferService offerService, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register.html";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute(name = "model") OfferRegisterBindingModel model) {
        try {
            this.offerService.registerOffer(this.modelMapper.map(model, OfferServiceModel.class));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();

            return "redirect:/register";
        }
        return "redirect:/";
    }

    @GetMapping("/find")
    public String getFind() {
        return "find.html";
    }

    @PostMapping("/find")
    public String postFind(@ModelAttribute(name = "model") OfferFindBindingModel model) {
        try {

            this.offerService.leaseApartment(model);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();

            return "redirect:/find";
        }
        return "redirect:/";
    }
}
