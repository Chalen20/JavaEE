package com.example.demo.DB;

public class Utils {

    public static boolean isValidISBN(String isbn) {
        if(isbn.length() != 13) {
            return false;
        }
        try {
            long isbnNumber = Long.parseLong(isbn);
            return getSum(isbnNumber) % 10 == 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int getSum(long isbn) {
        int count = 1;
        int sum = 0;
        while (isbn > 0) {
            sum += count % 2 == 0 ? 3 * (isbn % 10) : isbn % 10;
            isbn /= 10;
            ++count;
        }
        return sum;
    }
}
