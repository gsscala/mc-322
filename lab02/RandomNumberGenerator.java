import java.util.Random;

public class RandomNumberGenerator {
    private int min;
    private int max;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public RandomNumberGenerator(int min, int max){
        setMin(min);
        setMax(max);
    }

    public int generate() {
        Random random = new Random();
        return random.nextInt(getMax() - getMin() + 1) + getMin();
    }
}