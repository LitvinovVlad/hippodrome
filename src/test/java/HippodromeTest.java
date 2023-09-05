import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {
    @Test
    void testExpected_NullArgumentException(){
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        });
        assertEquals("Horses cannot be null.", illegalArgumentException.getMessage());
    }
    @Test
    void testExpected_EmptyArgumentException() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(Collections.emptyList());
        });
        assertEquals("Horses cannot be empty.", illegalArgumentException.getMessage());
    }

    @Test
    void expectTheReturnArgument() throws NoSuchFieldException, IllegalAccessException {
        List<Horse> horseList = new ArrayList<>(30);
        for (int i = 0; i < 30; i++) {
            horseList.add(new Horse("name", Math.random(), Math.random()));
        }
        Hippodrome hippodrome = new Hippodrome(horseList);
        Field field = hippodrome.getClass().getDeclaredField("horses");
        field.setAccessible(true);
        List<Horse> list = (List<Horse>) field.get(hippodrome);
        assertEquals(horseList, list);
    }
    @Test
    void testMoveAllHorses() {
        List<Horse> list = new ArrayList<>(50);
        for (int i = 0; i < 50; i++) {
            Horse horse = mock(Horse.class);
            list.add(horse);
        }
        Hippodrome hippodrome = new Hippodrome(list);
        hippodrome.move();
        for (Horse horse:list) {
            verify(horse).move();
        }
    }

    @Test
    void testGetMaxDistanceHorse() {
        List<Horse> horses = new ArrayList<>();
        Horse horse1 = mock(Horse.class);

        when(horse1.getDistance()).thenReturn(10.0);
        Horse horse2 = mock(Horse.class);
        when(horse2.getDistance()).thenReturn(20.0);
        Horse horse3 = mock(Horse.class);
        when(horse3.getDistance()).thenReturn(30.0);

        horses.add(horse1);
        horses.add(horse2);
        horses.add(horse3);

        Hippodrome hippodrome = new Hippodrome(horses);
        Horse winnerHorse = hippodrome.getWinner();
        assertEquals(horse3, winnerHorse);
    }
}