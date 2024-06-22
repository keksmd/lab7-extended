package com.alexkekiy.server.main.managers;

import com.alexkekiy.server.data.entities.SpaceMarineEntity;
import com.alexkekiy.server.data.repositories.SpaceMarineRepository;
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


    private final PriorityQueue<SpaceMarineEntity> collection;

    @Autowired
    public CollectionRepository(PriorityQueue<SpaceMarineEntity> collection) {
        this.collection = collection;
    }

    @PostConstruct
    public void fillCollection() {
        this.collection.addAll(SpaceMarineRepository.getSpaceMarineRepository().getAll());
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
