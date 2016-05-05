package structures;

/**
 * Created by wilhelm on 05/05/16.
 */
public interface Entry {

    @Override
    int hashCode();

    boolean equals(Entry e);

    boolean sameAs(Entry e);
}
