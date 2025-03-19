import java.util.Random;

public class RandomNumberGenerator {
    int min;
    int max;

    public RandomNumberGenerator(int min, int max){
        this.min = min;
        this.max = max;
    }

    public int generate() {
        Random random = new Random();
        return random.nextInt(this.max - this.min + 1) + this.min;
    }
}