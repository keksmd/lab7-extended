package com.alexkekiy.server.data.repositories;

import com.alexkekiy.server.data.entities.SpaceMarineEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.PriorityQueue;
import java.util.stream.Stream;

/**
 * менеджер коллекции объектов,инкапсулирующий методы коллекции
 */
@Repository
public class CollectionRepository {
    SpaceMarineRepository spaceMarineRepository;


    private final PriorityQueue<SpaceMarineEntity> collection;

    @Autowired
    public CollectionRepository(PriorityQueue<SpaceMarineEntity> collection,SpaceMarineRepository spaceMarineRepository) {
        this.collection = collection;
        this.spaceMarineRepository =spaceMarineRepository;
    }

    @PostConstruct
    public void fillCollection() {
        this.collection.addAll(spaceMarineRepository.getAll());
    }

    public SpaceMarineEntity poll() {
        return collection.poll();
    }

    public SpaceMarineEntity peek() {
        return collection.peek();
    }

    public void add(SpaceMarineEntity spm) {
        collection.add(spm);
    }

    public void remove(SpaceMarineEntity spm) {
        collection.remove(spm);
    }

    public Stream<SpaceMarineEntity> getCollectionStream() {
        return collection.stream();

    }

    public int getCollectionSize() {
        return collection.size();
    }
}
