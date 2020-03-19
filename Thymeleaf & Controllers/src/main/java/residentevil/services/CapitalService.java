package residentevil.services;

import residentevil.domain.models.service.CapitalServiceModel;

import java.util.List;

public interface CapitalService {
    List<CapitalServiceModel> getAllCapitals();

    CapitalServiceModel findCapitalById(String id);
}
