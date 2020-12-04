package converter;

import java.util.Scanner;

public class Main {

    static final String digits = "0123456789abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sourceBase = 0;
        String number = "";
        int newBase = 0;
        boolean isError = true;


        while (isError) {

            if (scanner.hasNext()) {
                String srcBase = scanner.nextLine();
                if (srcBase.matches("\\d+")) {
                    sourceBase = Integer.parseInt(srcBase);
                    if (sourceBase >= 1 && sourceBase <= 36) {
                        isError = false;
                    } else {
                        System.out.println("Error: wrong radix! Please enter a number in the range {1-36}");
                        return;
                    }
                } else {
                    System.out.println("Error: sourceBase isn't a number!");
                    return;
                }
            }

            if(scanner.hasNext()) {
                number = scanner.nextLine();

            } else {
                System.out.println("Error: isn't a number!");
                return;
            }

            if (scanner.hasNext()) {
                String nBase = scanner.nextLine();
                if (nBase.matches("\\d+")) {
                    newBase = Integer.parseInt(nBase);
                    if (newBase >= 1 && newBase <= 36) {
                        isError = false;
                    } else {
                        System.out.println("Error: wrong radix! Please enter a number in the range {1-36}");
                        return;
                    }
                } else {
                    System.out.println("Error: newBase isn't a number!");
                    return;
                }
            }
        }

        printNumbers(number, sourceBase, newBase);
    }

    private static void printNumbers(String number, int sourceBase, int newBase) {
        String[] numbers = number.split("\\.");
        if (isInRange(numbers[0], sourceBase) ) {
            String intPart = convertIntegerParts(numbers[0], sourceBase, newBase);
            if (numbers.length == 1) {
                System.out.println(intPart);
            } else {
                if (isInRange(numbers[1], sourceBase)) {
                    String num = intPart + convertFractionalParts(numbers[1], sourceBase, newBase);
                    System.out.println(num);
                }
            }
        } else {
            System.out.println("Error: not a valid number for the specified number system!");
        }
    }

    private static String convertIntegerParts(String number, int sourceBase, int newBase) {
        StringBuilder stringBuilder = new StringBuilder();
        int num;
        if (sourceBase == 1) {
            return Integer.toString(number.length(), newBase);
        } else {
            num = Integer.parseInt(number, sourceBase);
        }

        if (newBase == 1) {
            for (int i = 0; i < num; i++) {
                stringBuilder.append("1");
            }
            return stringBuilder.toString();
        } else {
            return Integer.toString(num, newBase);
        }
    }

    private static String convertFractionalParts(String number, int sourceBase, int newBase) {
        StringBuilder stringBuilder = new StringBuilder(".");
        double buf = Integer.parseInt(number, sourceBase) / Math.pow(sourceBase, number.length());

        if (newBase == 10) {
            return String.valueOf(buf);
        } else {
            int intBuf;
            for (int i = 0; i < 5; i++) {
                intBuf = (int) (buf * newBase);
                buf = (buf * newBase) % intBuf;
                stringBuilder.append(Integer.toString(intBuf, newBase));
            }
            return stringBuilder.toString();
        }
    }

    private static boolean isInRange (String num, int sourceBase) {

        boolean result = true;
        String range = digits.substring(0, sourceBase);
        if (sourceBase == 1) {
            for (int i = 0; i < num.length(); i++) {
                String x = num.substring(i, i + 1);
                if (!"1".contains(x)) {
                    result = false;
                    break;
                }
            }
        } else {
            for (int i = 0; i < num.length(); i++) {
                String x = num.substring(i, i + 1);
                if (!range.contains(x)) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }
}

