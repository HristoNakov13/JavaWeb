package metube.services;

import metube.domain.dtos.create.TubeCreateDto;
import metube.domain.dtos.view.AllTubesViewDto;
import metube.domain.dtos.view.TubeViewDto;
import metube.domain.entities.Tube;
import metube.repositories.TubeRepository;
import org.modelmapper.Condition;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class TubeService {
    private TubeRepository tubeRepository;
    private ModelMapper modelMapper;

    @Inject
    public TubeService(TubeRepository tubeRepository, ModelMapper modelMapper) {
        this.tubeRepository = tubeRepository;
        this.modelMapper = modelMapper;

        init();
    }

    public Tube save(TubeCreateDto tubeCreateDto) {
        Tube tube = this.modelMapper.map(tubeCreateDto, Tube.class);

        return this.tubeRepository.save(tube);
    }

    public TubeViewDto findById(String id) {
        Tube tube = this.tubeRepository.findById(id).orElse(null);

        if (tube == null) {
            return null;
        }

        return modelMapper.map(tube, TubeViewDto.class);
    }

    public List<AllTubesViewDto> getAllTubes() {
        List<Tube> allTubes = this.tubeRepository.findAll();

        return allTubes.stream()
                .map(tube -> this.modelMapper.map(tube, AllTubesViewDto.class))
                .collect(Collectors.toList());
    }

    private void init() {

        Converter<TubeCreateDto, Tube> createDtoToTube = new Converter<TubeCreateDto, Tube>() {
            @Override
            public Tube convert(MappingContext<TubeCreateDto, Tube> context) {
                Tube tube = new Tube();
                TubeCreateDto tubeCreateDto = context.getSource();

                String description = tubeCreateDto.getDescription().isEmpty() ? null : tubeCreateDto.getDescription();
                String youtubeLink = tubeCreateDto.getYoutubeLink().isEmpty() ? null : tubeCreateDto.getYoutubeLink();
                String uploader = tubeCreateDto.getUploader().isEmpty() ? null : tubeCreateDto.getUploader();

                tube.setName(tubeCreateDto.getName());
                tube.setDescription(description);
                tube.setYoutubeLink(youtubeLink);
                tube.setUploader(uploader);

                return tube;
            }
        };

        Converter<Tube, TubeViewDto> tubeToTubeViewDto = new Converter<Tube, TubeViewDto>() {
            @Override
            public TubeViewDto convert(MappingContext<Tube, TubeViewDto> context) {
                Tube source = context.getSource();
                TubeViewDto destination = new TubeViewDto();

                String description = source.getDescription() == null ? "" : source.getDescription();
                String youtubeLink = source.getYoutubeLink() == null ? "" : source.getYoutubeLink();
                String uploader = source.getUploader() == null ? "" : source.getUploader();

                destination.setName(source.getName());
                destination.setDescription(description);
                destination.setYoutubeLink(youtubeLink);
                destination.setUploader(uploader);

                return destination;
            }
        };

        this.modelMapper.createTypeMap(Tube.class, TubeViewDto.class).setConverter(tubeToTubeViewDto);
        this.modelMapper.createTypeMap(TubeCreateDto.class, Tube.class).setConverter(createDtoToTube);
    }
}