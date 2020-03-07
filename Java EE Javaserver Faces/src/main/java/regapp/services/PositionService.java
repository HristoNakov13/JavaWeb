package regapp.services;

import org.modelmapper.ModelMapper;
import regapp.domain.entities.Position;
import regapp.domain.models.binding.PositionBindingModel;
import regapp.repositories.PositionRepository;

import javax.inject.Inject;

public class PositionService {
    private PositionRepository positionRepository;
    private ModelMapper modelMapper;

    @Inject
    public PositionService(PositionRepository positionRepository, ModelMapper modelMapper) {
        this.positionRepository = positionRepository;
        this.modelMapper = modelMapper;
    }

    public void save(PositionBindingModel positionBindingModel) {
        Position position = this.modelMapper.map(positionBindingModel, Position.class);

        this.positionRepository.save(position);
    }
}