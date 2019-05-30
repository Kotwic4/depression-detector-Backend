package agh.edu.pl.depressiondetectorbackend.util;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

@Service
public class RandomService {
    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String lower = upper.toLowerCase(Locale.ROOT);

    public static final String digits = "0123456789";

    public static final String alphanum = upper + lower + digits;

    private final Random random;

    RandomService(Random random) {
        this.random = random;
    }

    RandomService() {
        this(new SecureRandom());
    }

    public int getRandomInt() {
        return random.nextInt();
    }

    public int getRandomInt(int bound){
        return random.nextInt(bound);
    }

    public String getRandomString(int length) {
        return getRandomString(length, alphanum);
    }

    public String getRandomString(int length, String symbols) {
        if (length < 1) throw new IllegalArgumentException("Random string size must be bigger than 1");
        if (symbols.length() < 2) throw new IllegalArgumentException("Random string must be from more than one char");
        char[] buf = new char[length];
        char[] symbolsArray = symbols.toCharArray();
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbolsArray[getRandomInt(symbolsArray.length)];
        return new String(buf);
    }

}