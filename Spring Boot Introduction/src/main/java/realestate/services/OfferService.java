package realestate.services;

import realestate.domain.models.binding.OfferFindBindingModel;
import realestate.domain.models.service.OfferServiceModel;

import java.util.List;

public interface OfferService {
    void registerOffer(OfferServiceModel offer);

    void leaseApartment(OfferFindBindingModel offer);

    List<OfferServiceModel> getAllOffers();
}