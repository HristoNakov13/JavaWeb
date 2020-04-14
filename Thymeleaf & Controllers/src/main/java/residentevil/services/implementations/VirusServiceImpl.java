package residentevil.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import residentevil.domain.entities.Virus;
import residentevil.domain.models.service.VirusServiceModel;
import residentevil.repositories.VirusRepository;
import residentevil.services.VirusService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VirusServiceImpl implements VirusService {

    private VirusRepository virusRepository;
    private ModelMapper modelMapper;

    public VirusServiceImpl(VirusRepository virusRepository, ModelMapper modelMapper) {
        this.virusRepository = virusRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VirusServiceModel save(VirusServiceModel virus) {
        Virus virusEntity = this.modelMapper.map(virus, Virus.class);

        return this.modelMapper.map(this.virusRepository.saveAndFlush(virusEntity),
                VirusServiceModel.class);
    }

    @Override
    public List<VirusServiceModel> getAllViruses() {
        return this.virusRepository
                .findAll()
                .stream()
                .map(virus -> this.modelMapper.map(virus, VirusServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteVirusById(String id) {
        try {
            this.virusRepository.deleteById(id);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public VirusServiceModel getVirusById(String id) {
        return this.modelMapper.map(this.virusRepository.findById(id).get(), VirusServiceModel.class);
    }
}
