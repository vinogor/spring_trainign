public class ConsoleEventLogger implements EventLogger {

    public void logEvent(Event event) {
        System.out.println(event);
    }

    @Override
    public String getName() {
        return null;
    }
}
