package residentevil.services;

import residentevil.domain.models.service.VirusServiceModel;

import java.util.List;

public interface VirusService {
    VirusServiceModel save(VirusServiceModel virus);

    List<VirusServiceModel> getAllViruses();

    boolean deleteVirusById(String id);

    VirusServiceModel getVirusById(String id);
}
