package backendMain.utils;

public class ValidateValue {

    public static boolean checkX(Float x) {
        if (x == null) {
            return false;
        }
        if (x.isNaN()) {
            throw new NumberFormatException();
        }
        return (x == -2 || x == -1.5 || x == -1 || x == -0.5 || x == 0 || x == 0.5 || x == 1 || x == 1.5 || x == 2);
    }

    public static boolean checkY(String y) {
        if (y == null) {
            return false;
        }
        try {
            float yNumber = convertToFloatY(y);
            if (Float.isNaN(yNumber)) {
                throw new NumberFormatException();
            }
            return yNumber > -5 && yNumber < 5;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean checkR(Integer r) {
        if (r == null) {
            return false;
        }
        return r == 1 || r == 2 || r == 3 || r == 4 || r == 5;
    }

    public static Float convertToFloatY(String number) {
        if (number.length() > 8) {
            number = number.substring(0, 8);
        }
        return Float.parseFloat(number);
    }
}
