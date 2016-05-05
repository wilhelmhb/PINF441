package structures;

public interface Map<Entry> {

    Entry exists(Entry e);

    boolean remove(Entry e);

    Entry add(Entry e);

}
