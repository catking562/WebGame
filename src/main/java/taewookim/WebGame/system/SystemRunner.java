package taewookim.WebGame.system;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SystemRunner implements ApplicationListener<ContextRefreshedEvent> {

    private final MainSystem mainSystem;

    public SystemRunner(MainSystem mainSystem) {
        this.mainSystem = mainSystem;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        mainSystem.run();
    }
}
