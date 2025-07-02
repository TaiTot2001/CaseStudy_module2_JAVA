public interface Manager<T> {
    boolean add(T obj);

    void remove(T obj);

    void edit(int index, T obj);
}


