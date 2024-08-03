@FunctionalInterface
public interface Callback<T> {
    void call(T result);
}
