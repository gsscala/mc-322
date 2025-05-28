package utils;

import java.util.Random;

public final class RandomStringGenerator {
    public static String generatePrintableRandomString(int stringSize, int numCharacters) {
        if (numCharacters < 1 || numCharacters > 95) { // 126-32+1 = 95 printable chars
            throw new IllegalArgumentException("numCharacters must be between 1 and 95");
        }
        
        Random random = new Random();
        StringBuilder sb = new StringBuilder(stringSize);
        
        for (int i = 0; i < stringSize; i++) {
            // Generate random printable character from first x printable ASCII chars (32 to 32+x-1)
            char randomChar = (char) (random.nextInt(numCharacters) + 32);
            sb.append(randomChar);
        }
        
        return sb.toString();
    }
}
