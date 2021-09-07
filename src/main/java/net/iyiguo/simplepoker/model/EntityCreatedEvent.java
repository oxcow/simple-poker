package net.iyiguo.simplepoker.model;

import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

/**
 * @author leeyee
 * @date 2021/08/08
 */
public class EntityCreatedEvent<T> extends ApplicationEvent implements ResolvableTypeProvider {
    public EntityCreatedEvent(T entity) {
        super(entity);
    }

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(getSource()));
    }

    @Override
    public String toString() {
        return "EntityCreatedEvent{" +
                "source=" + source +
                '}';
    }
}
