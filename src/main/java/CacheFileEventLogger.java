import java.util.ArrayList;
import java.util.List;

public class CacheFileEventLogger extends FileEventLogger  {

    public CacheFileEventLogger(String fileName, int cacheSize) {
        super(fileName);
        this.cacheSize = cacheSize;
    }

    private int cacheSize;
    private List<Event> cache = new ArrayList<>();


    @Override
    public void logEvent(Event event) {
            cache.add(event);

            if (cache.size() == cacheSize) {
                writeEventsFromCache();
                cache.clear();
            }
    }

    private void writeEventsFromCache() {
        for (int i = 0; i < cache.size(); i++) {
            super.logEvent(cache.get(i));
        }
    }

    private void destroy() {
        if (!cache.isEmpty()) {
            writeEventsFromCache();
        }
    }
}
