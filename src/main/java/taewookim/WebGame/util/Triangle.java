package taewookim.WebGame.util;

import java.util.ArrayList;

public class Triangle {

    private static final ArrayList<Double> cos = new ArrayList<>();
    private static final ArrayList<Double> sin = new ArrayList<>();

    static {
        for(int i = 0; i<3600; i++) {
            cos.add(Math.cos(Math.toRadians(i/10d)));
            sin.add(Math.sin(Math.toRadians(i/10d)));
        }
    }

    public static Double getCos(double angle) {
        int dig = (int) (angle*10);
        while(dig<0) {
            dig+=3600;
        }
        dig %= 3600;
        return cos.get(dig);
    }

    public static Double getSin(double angle) {
        int dig = (int) (angle*10);
        while(dig<0) {
            dig+=3600;
        }
        dig %= 3600;
        return -sin.get(dig);
    }

}
