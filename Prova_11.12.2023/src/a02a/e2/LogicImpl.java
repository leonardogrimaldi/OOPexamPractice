package a02a.e2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;

public class LogicImpl implements Logic {

    private static final Map<Direction, Function<Pair<Integer,Integer>,Pair<Integer,Integer>>> nextCell = new HashMap<>(
        Map.of(
            Direction.UP, i -> new Pair<Integer, Integer>(i.getX() - 1, i.getY()),
            Direction.DOWN, i -> new Pair<Integer, Integer>(i.getX() + 1, i.getY()),
            Direction.LEFT, i -> new Pair<Integer, Integer>(i.getX(), i.getY() - 1),
            Direction.RIGHT, i -> new Pair<Integer, Integer>(i.getX(), i.getY() + 1)
        )
    );

    private final int size;
    private int counter;
    private boolean consumed;
    private List<Pair<Integer,Integer>> usedCellsList = new ArrayList<>();
    private enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
    private Direction direction = Direction.UP;
    private final Map<Direction, Predicate<Pair<Integer,Integer>>> nextDirectionValid;

    public LogicImpl(final int size) {
        this.size = size;
        this.nextDirectionValid = new HashMap<>(Map.of(
            Direction.UP, i -> i.getX() - 1 < 0,
            Direction.RIGHT, i -> i.getY() + 1 >= size,
            Direction.DOWN, i -> i.getX() + 1 >= size,
            Direction.LEFT, i -> i.getY() - 1 < 0
        ));
    }

    @Override
    public Pair<Integer, Pair<Integer, Integer>> toggle() {
        Pair<Integer, Integer> p;
        if (!consumed) {
            /**
             * This portion of code is executed only once, the first time the user calls toggle().
             */
            p = new Pair<>(new Random().nextInt(size), new Random().nextInt(size));
            //p = new Pair<>(0, 6);
            usedCellsList.add(p);
            consumed = true;
        } else {
            direction = nextDirection();
            var lastPairAdded = usedCellsList.get(usedCellsList.size() - 1);
            p = nextCell.get(direction).apply(lastPairAdded);
            usedCellsList.add(p);
        }
        System.out.println("Counter: " + counter);
        System.out.println(p.toString());
        return new Pair<>(counter++, p);
    }

    @Override
    public boolean isOver() {
        boolean isOver = true;
        for (Direction d : Direction.values()) {
                if (isDirectionValid(d)) {
                    isOver = false;
                }
        }
        return isOver;
    }

    private boolean isDirectionValid(final Direction d) {
        var lastPairAdded = Objects.requireNonNull(usedCellsList.get(usedCellsList.size() - 1));
        
        if (nextDirectionValid.get(d).test(lastPairAdded)) {
            return false;
        }
        if (!isNextCellEmpty(d)) {
            return false;
        }
        return true;
    }

    private boolean isNextCellEmpty(final Direction d) {
        var lastPairAdded = Objects.requireNonNull(usedCellsList.get(usedCellsList.size() - 1));
        /**
         * Usando una funzione associata alla direzione calcolo il nuovo pair da aggiungere
         */
        var nextPairToAdd = nextCell.get(d).apply(lastPairAdded);

        return !usedCellsList.contains(nextPairToAdd);
    }
    
    /**
     * If the already set direction is valid it returns the same direction, else it iterates through the enum finding
     * a new direction
     * @return new direction 
     * @throws IllegalStateException if it was not possible to find a new direction
     */
    private Direction nextDirection() {
        if (isDirectionValid(direction)) {
            return direction;
        } else {
            for (Direction d : Direction.values()) {
                if (isDirectionValid(d)) {
                    return d;
                }
            }
            throw new IllegalStateException("It is not possible to continue to a further nextDirection. The game should be over");
        }
    }
}