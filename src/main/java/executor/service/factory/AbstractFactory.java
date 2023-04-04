package executor.service.factory;

public interface AbstractFactory {
    <T> T create(Class<T> clazz);
    <T> T create(TypeReference<T> typeReference);
}
