
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class App {

    private Client client;

    private EventLogger defaultLogger;

    private Map<EventType, EventLogger> loggers;

    private String startupMessage;

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext(
                "spring.xml", "loggers.xml", "db.xml");
        App app = (App) ctx.getBean("app");

        System.out.println(app.startupMessage);

        Client client = ctx.getBean(Client.class);
        System.out.println("Client says: " + client.getGreeting());

        app.logEvents(ctx);

        ctx.close();
    }

    public void logEvents(ApplicationContext ctx) {
        Event event = ctx.getBean(Event.class);
        logEvent(EventType.INFO, event, "Some event for 1");

        event = ctx.getBean(Event.class);
        logEvent(EventType.INFO, event, "One more event for 1");

        event = ctx.getBean(Event.class);
        logEvent(EventType.INFO, event, "And one more event for 1");

        event = ctx.getBean(Event.class);
        logEvent(EventType.ERROR, event, "Some event for 2");

        event = ctx.getBean(Event.class);
        logEvent(null, event, "Some event for 3");
    }

    public App() {}

    public App(Client client, EventLogger eventLogger, Map<EventType, EventLogger> loggers) {
        super();
        this.client = client;
        this.defaultLogger = eventLogger;
        this.loggers = loggers;
    }

    private void logEvent(EventType eventType, Event event, String msg) {
        String message = msg.replaceAll(client.getId(), client.getFullName());
        event.setMsg(message);

        EventLogger logger = loggers.get(eventType);
        if (logger == null) {
            logger = defaultLogger;
        }

        logger.logEvent(event);
    }

    public void setStartupMessage(String startupMessage) {
        this.startupMessage = startupMessage;
    }

    public EventLogger getDefaultLogger() {
        return defaultLogger;
    }

}


//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import java.util.Map;
//
//public class App {
//
////    @Autowired
//    private Client client;
//    private EventLogger defaultLogger;
//    private Map<EventType, EventLogger> loggers;
//
//    public App(Client client, EventLogger defaultLogger, Map<EventType, EventLogger> loggers) {
//        super();
//        this.client = client;
//        this.defaultLogger = defaultLogger;
//        this.loggers = loggers;
//    }
//
//    public static void main(String[] args) {
//
//        // создаём контекст (контейнер) и указываем xml файл
//        // контекст - управляет контейнером, в котором хранятся бины и обеспечивает доступ к ним
//        ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext("spring.xml");
//
//        // запрашиваем бин по имени (придётся кастить класс возвращ объекта)
//        // (но ещё можно и по классу, по классу и имени, )
//        App app = (App) appContext.getBean("app");
//
////        App app1 = appContext.getBean(App.class);
////        App app2 = appContext.getBean(App.class, "app");
//
//        Event event = appContext.getBean(Event.class);
//
//        app.logEvent(null, event, "Some event for 3");
//
//        appContext.close();
//
//    }
//
//    public void logEvent(EventType eventType, Event event, String msg) {
//
//        String message = msg.replaceAll(client.getId(), client.getFullName())+client.getGreeting();
//        event.setMsg(message);
//
//        EventLogger logger = loggers.get(eventType);
//        if (logger == null) {
//            logger = defaultLogger;
//        }
//
//        logger.logEvent(event);
//    }
//}
