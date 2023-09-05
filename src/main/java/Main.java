import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Main {


    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());


    public static void main(String[] args) throws Exception {
        List<Horse> horses = List.of(
                new Horse("Bucephalus", 2.4),
                new Horse("Ace of Spades", 2.5),
                new Horse("Zephyr", 2.6),
                new Horse("Blaze", 2.7),
                new Horse("Lobster", 2.8),
                new Horse("Pegasus", 2.9),
                new Horse("Cherry", 3)
        );
        Hippodrome hippodrome = new Hippodrome(horses);
        LOGGER.info("[" + java.time.LocalDateTime.now() + "] INFO Main: Начало скачек. Количество участников: " + horses.size());

        for (int i = 0; i < 100; i++) {
            hippodrome.move();
            watch(hippodrome);
            TimeUnit.MILLISECONDS.sleep(200);
        }
        String winnerName = hippodrome.getWinner().getName();
        System.out.println(winnerName + " wins!");
        LOGGER.info("[" + java.time.LocalDateTime.now() + "] INFO Main: Окончание скачек. Победитель: " + winnerName);
    }

    private static void watch(Hippodrome hippodrome) throws Exception {
        hippodrome.getHorses().stream()
                .map(horse -> ".".repeat((int) horse.getDistance()) + horse.getName())
                .forEach(System.out::println);
        System.out.println("\n".repeat(10));
    }
}
