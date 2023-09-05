import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import static java.util.Objects.isNull;

public class Hippodrome {
    private static final Logger logger = Logger.getLogger(Hippodrome.class.getName());
    private final List<Horse> horses;

    public Hippodrome(List<Horse> horses) throws IllegalArgumentException{
        if (isNull(horses)) {
            logger.severe("[" + java.time.LocalDateTime.now() + "] ERROR Hippodrome: Horses list is null");
            throw new IllegalArgumentException("Horses cannot be null.");
        } else if (horses.isEmpty()) {
            logger.severe("[" + java.time.LocalDateTime.now() + "] ERROR Hippodrome: Horses list is empty");
            throw new IllegalArgumentException("Horses cannot be empty.");
        }

        this.horses = horses;
        logger.fine("[" + java.time.LocalDateTime.now() + "] DEBUG Hippodrome: Создание Hippodrome, лошадей [" + horses.size() + "]");

    }
    public List<Horse> getHorses() {
        return Collections.unmodifiableList(horses);
    }

    public void move() {
        horses.forEach(Horse::move);
    }

    public Horse getWinner() {
        return horses.stream()
                .max(Comparator.comparing(Horse::getDistance))
                .get();
    }
}
