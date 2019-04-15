import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileEventLogger implements EventLogger {

    private String fileName;

    public FileEventLogger(String fileName) {
        this.fileName = fileName;
    }

    public void logEvent(String msg) {

    }

    public void logEvent(Event event) {
        try {
//            FileUtils.writeStringToFile(file, event.toString(), true);

            FileUtils.writeStringToFile(new File(fileName), event.toString(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return null;
    }

    public void init() throws IOException {
        File file = new File(fileName);
        if (!(file.canWrite() || !file.exists())) {
            throw new IOException();
        }
    }
}
