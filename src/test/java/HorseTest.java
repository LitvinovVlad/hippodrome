import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class HorseTest {
    @Test
    void testExpected_NullArgumentException(){
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 1, 1);
        });
        assertEquals("Name cannot be null.", illegalArgumentException.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = { " ", "   ", "\t", "\n" })
    void testExpected_EmptyArgumentException(String string){
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(string, 1, 1);
        });

        assertEquals("Name cannot be blank.", illegalArgumentException.getMessage());
    }

    @Test
    void expectedNegativeSecondArgumentException(){
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Horse("1", -1, 1);
        });assertEquals("Speed cannot be negative.", illegalArgumentException.getMessage());
    }

    @Test
    void expectedNegativeThirtyArgumentException() {
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Horse("1", 1, -1);
        });
        assertEquals("Distance cannot be negative.", illegalArgumentException.getMessage());
    }
    @Test
    void expectTheReturnFirstArgument() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("name", 1, 2);
        Field field = horse.getClass().getDeclaredField("name");
        field.setAccessible(true);
        String name = (String) field.get(horse);
        assertEquals("name", name);

    }

    @Test
    void expectTheReturnSecondArgument() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("name", 1, 2);
        Field field = horse.getClass().getDeclaredField("speed");
        field.setAccessible(true);
        double speed = (Double) field.get(horse);
        assertEquals(1, speed);
    }

    @Test
    void expectTheReturnThirtyArgument()
            throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("name", 1, 2);
        Field field = horse.getClass().getDeclaredField("distance");
        field.setAccessible(true);
        double distance = (Double) field.get(horse);
        assertEquals(2, distance);

    }


    @Test
    void expectNull_IfThirtyArgumentIsEmpty() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("name", 1);
        Field field = horse.getClass().getDeclaredField("distance");
        field.setAccessible(true);
        double distance = (Double) field.get(horse);
        assertEquals(0, distance);
    }

    @Test
    void expect_MoveCallGetRandomDouble() {
        try(MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)){
            new Horse("name", 1, 1).move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }
    @ParameterizedTest
    @ValueSource(doubles = {0.3, 0.7, 0.8})
    void checkResult_GetRandomDouble_Method(Double d) {
        try (MockedStatic<Horse> utilities = Mockito.mockStatic(Horse.class)) {
            utilities.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(d);
            Horse horse = new Horse("name", 2, 2);
            Double result = horse.getDistance() + horse.getSpeed() * Horse.getRandomDouble(0.2, 0.9);
            horse.move();
            assertEquals(horse.getDistance(), result);
        }
    }
}