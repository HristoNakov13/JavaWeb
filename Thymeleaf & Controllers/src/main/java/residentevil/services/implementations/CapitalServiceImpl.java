package residentevil.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import residentevil.domain.models.service.CapitalServiceModel;
import residentevil.repositories.CapitalRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CapitalServiceImpl implements residentevil.services.CapitalService {

    private CapitalRepository capitalRepository;
    private ModelMapper modelMapper;

    public CapitalServiceImpl(CapitalRepository capitalRepository, ModelMapper modelMapper) {
        this.capitalRepository = capitalRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<CapitalServiceModel> getAllCapitals() {
        return this.capitalRepository
                .findAllAndOrderByName()
                .stream()
                .map(capital -> this.modelMapper.map(capital, CapitalServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CapitalServiceModel findCapitalById(String id) {
        return this.modelMapper.map(this.capitalRepository.findById(id).get(), CapitalServiceModel.class);
    }
}
