package com.ilim.app.core.util.mapper;

import org.hibernate.collection.spi.PersistentSet;
import org.modelmapper.AbstractConverter;

import java.util.HashSet;
import java.util.Set;

public class PersistentSetToSetConverter extends AbstractConverter<PersistentSet<?>, Set<?>> {

    @Override
    protected Set<?> convert(PersistentSet<?> source) {
        return new HashSet<>(source); // PersistentSet'i HashSet'e dönüştür
    }
}