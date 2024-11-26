package com.ilim.app.core.util.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModelMapperManager implements ModelMapperService {

    private ModelMapper modelMapper;
    @Override
    public ModelMapper forResponse() {
         this.modelMapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.LOOSE);
         return this.modelMapper;
    }

    @Override
    public ModelMapper forRequest() {
        this.modelMapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STANDARD);
        return this.modelMapper;
    }
}
