import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class App {

//    @Autowired
    private Client client;
    private EventLogger defaultLogger;
    private Map<EventType, EventLogger> loggers;

    public App(Client client, EventLogger defaultLogger, Map<EventType, EventLogger> loggers) {
        super();
        this.client = client;
        this.defaultLogger = defaultLogger;
        this.loggers = loggers;
    }

    public static void main(String[] args) {

        // создаём контекст (контейнер) и указываем xml файл
        // контекст - управляет контейнером, в котором хранятся бины и обеспечивает доступ к ним
        ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext("spring.xml");

        // запрашиваем бин по имени (придётся кастить класс возвращ объекта)
        // (но ещё можно и по классу, по классу и имени, )
        App app = (App) appContext.getBean("app");

//        App app1 = appContext.getBean(App.class);
//        App app2 = appContext.getBean(App.class, "app");

        Event event = appContext.getBean(Event.class);

        app.logEvent(null, event, "Some event for 3");

        appContext.close();

    }

    public void logEvent(EventType eventType, Event event, String msg) {

        String message = msg.replaceAll(client.getId(), client.getFullName())+client.getGreeting();
        event.setMsg(message);

        EventLogger logger = loggers.get(eventType);
        if (logger == null) {
            logger = defaultLogger;
        }

        logger.logEvent(event);
    }
}
