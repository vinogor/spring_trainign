import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    private Client client;
    private EventLogger eventLogger;
    private static Event event;

    public App(Client client, EventLogger eventLogger) {
        this.client = client;
        this.eventLogger = eventLogger;
    }

    public static void main(String[] args) {

        // создаём контекст (контейнер) и указываем xml файл
        // контекст - управляет контейнером, в котором хранятся бины и обеспечивает доступ к ним
        ApplicationContext appContext = new ClassPathXmlApplicationContext("spring.xml");

        // запрашиваем бин по имени (придётся кастить класс возвращ объекта)
        // (но ещё можно и по классу, по классу и имени, )
        App app = (App) appContext.getBean("app");
//        App app1 = appContext.getBean(App.class);
//        App app2 = appContext.getBean(App.class, "app");

        event = (Event) appContext.getBean("event");

        app.logEvent("Some event for 1");
        app.logEvent("Some event for 2");

        event.setMsg("Ку ку!");
        app.logEvent(event);
        app.logEvent(event);
        ((ClassPathXmlApplicationContext) appContext).close();
    }

    public void logEvent(String msg) {
        String message = msg.replaceAll(client.getId(), client.getFullName());
        eventLogger.logEvent(message);
    }

    public void logEvent(Event event) {
        eventLogger.logEvent(event);
    }


}
