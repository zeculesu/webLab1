package backendMain.utils;

public class ValidateValue {

    public static boolean checkX(Float x) {
        if (x == null) {
            return false;
        }
        if (x.isNaN()){
            throw new NumberFormatException();
        }
        return (x == -2 || x == -1.5 || x == -1 || x == -0.5 || x == 0 || x == 0.5 || x == 1 || x == 1.5 || x == 2);
    }

    public static boolean checkY(Float y) {
        if (y == null) {
            return false;
        }
        if (y.isNaN()){
            throw new NumberFormatException();
        }
        return y > -5 && y < 5;
    }

    public static boolean checkR(Integer r) {
        if (r == null) {
            return false;
        }
        return r == 1 || r == 2 || r == 3 || r == 4 || r == 5;
    }

    public static String removeTrailingZeros(float number) {
        return number == (long) number ? String.format("%d", (long) number) : String.format("%s", number);
    }
}
