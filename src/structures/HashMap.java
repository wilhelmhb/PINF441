package structures;

import java.util.*;

/**
 * Created by wilhelm on 05/05/16.
 */
public class HashMap<Entry> implements Map<Entry> {

    List<Entry>[] l;

    long size;

    public HashMap(int i) {
        l = new ArrayList[i];
        size = 0;
    }

    private Entry searchList(Entry e, List list) {
        Iterator<Entry> iter = list.iterator();
        while (iter.hasNext()) {
            Entry element = iter.next();
            if(element.equals(e)) {
                return element;
            }
        }
        return null;
    }

    @Override
    public Entry exists(Entry e) {
        int n = l.length;
        int k = ((e.hashCode() % n) + n) % n;
        return searchList(e, l[k]);
    }

    @Override
    public boolean remove(Entry e) {
        int n = l.length;
        int k = ((e.hashCode() % n) + n) % n;
        return l[k].remove(e);
    }

    @Override
    public Entry add(Entry e) {
        int n = l.length;
        int k = ((e.hashCode() % n) + n) % n;
        Entry entry = searchList(e, l[k]);
        if(entry != null) {
            return entry;
        }
        else {
            l[k].add(e);
            return e;
        }
    }
}
