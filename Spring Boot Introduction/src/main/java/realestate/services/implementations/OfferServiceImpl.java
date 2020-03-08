package realestate.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import realestate.domain.entities.Offer;
import realestate.domain.models.binding.OfferFindBindingModel;
import realestate.domain.models.service.OfferServiceModel;
import realestate.repositories.OfferRepository;
import realestate.services.OfferService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    private OfferRepository offerRepository;
    private Validator validator;
    private ModelMapper modelMapper;

    public OfferServiceImpl(OfferRepository offerRepository, Validator validator, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }


    @Override
    public void registerOffer(OfferServiceModel offer) {
        if (this.validator.validate(offer).size() > 0) {
            throw new IllegalArgumentException("invalid fields");
        }

        Offer offerEntity = this.modelMapper.map(offer, Offer.class);
        this.offerRepository.save(offerEntity);
    }

    @Override
    public void leaseApartment(OfferFindBindingModel offer) {
        Set<ConstraintViolation<OfferFindBindingModel>> lol = this.validator.validate(offer);
        if (this.validator.validate(offer).size() > 0) {
            throw new IllegalArgumentException("invalid fields");
        }

        Offer foundOffer = this.offerRepository.findByApartmentTypeAndApartmentRateLessThanEqual(offer.getApartmentType(), offer.getFamilyBudget());

        if (foundOffer != null) {
            this.offerRepository.delete(foundOffer);
        }
    }

    @Override
    public List<OfferServiceModel> getAllOffers() {
        return this.offerRepository
                .findAll()
                .stream()
                .map(offer -> this.modelMapper.map(offer, OfferServiceModel.class))
                .collect(Collectors.toList());
    }
}