package residentevil.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import residentevil.domain.entities.Virus;
import residentevil.domain.models.service.VirusServiceModel;
import residentevil.repositories.VirusRepository;
import residentevil.services.VirusService;

@Service
public class VirusServiceImpl implements VirusService {

    private VirusRepository virusRepository;
    private ModelMapper modelMapper;

    public VirusServiceImpl(VirusRepository virusRepository, ModelMapper modelMapper) {
        this.virusRepository = virusRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(VirusServiceModel virus) {
        Virus virusEntity = this.modelMapper.map(virus, Virus.class);

        this.virusRepository.save(virusEntity);
    }
}
