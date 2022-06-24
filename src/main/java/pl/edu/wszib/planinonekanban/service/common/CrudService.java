package pl.edu.wszib.planinonekanban.service.common;

import pl.edu.wszib.planinonekanban.service.exception.NotFoundException;

import java.util.*;

public class CrudService<T extends CrudResource<ID>, ID> {

    protected Map<ID, T> repository = new HashMap<>();
    private final Class<ID> genericType;
    private final IdGenerators.IdGenerator<ID> generator;

    public CrudService(Class<ID> clazz) {
        genericType = clazz;
        generator = generator();
    }

    public List<T> list() {
        return new ArrayList<>(repository.values());
    }

    public T get(ID id) {
        return Optional.ofNullable(repository.get(id))
                .orElseThrow(NotFoundException::new);
    }

    public T create(T newObject) {
        newObject.setId(generator.next());
        repository.put(newObject.getId(), newObject);
        return newObject;
    }

    public T update(T existingObject) {
        T toBeUpdated = get(existingObject.getId());
        repository.put(toBeUpdated.getId(), existingObject);
        return existingObject;
    }

    public void delete(ID id) {
        repository.remove(id);
    }

    private IdGenerators.IdGenerator<ID> generator() {
        if (genericType == Integer.class) {
            return (IdGenerators.IdGenerator<ID>) new IdGenerators.IntIdGenerator();
        }
        if (genericType == Long.class) {
            return (IdGenerators.IdGenerator<ID>) new IdGenerators.LongIdGenerator();
        }
        if (genericType == String.class) {
            return (IdGenerators.IdGenerator<ID>) new IdGenerators.StringIdGenerator();
        }
        throw new RuntimeException("No supported generator");
    }
}
