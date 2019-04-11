public class ConsoleEventLogger implements EventLogger {

    private void logEventLogger(String msg) {
        System.out.println(msg);
    }

    public void logEvent(String msg) {
        logEventLogger(msg);
    }

    public void logEvent(Event event) {
        System.out.println(event);
    }
}
