package org.example;

public class Test {
    public String ddd(String a, String b) {
        long num1 = Long.parseLong(a, 16);
        long num2 = Long.parseLong(b, 16);
        return String.format("%08x", num1 ^ num2);
    }
    public static void main(String[] args) {
        Test test = new Test();
        System.out.println(test.ddd("ad20177d", "2475a2b3"));
//        String hexValue = "8c";
//        char firstChar = hexValue.charAt(0);
//        char secondChar = hexValue.charAt(1);
//        int firstDecimal = Integer.parseInt(String.valueOf(firstChar), 16);
//        int secondDecimal = Integer.parseInt(String.valueOf(secondChar), 16);
//
//        // Выводим результат
//        System.out.println("Первый символ (" + firstChar + ") в десятичной системе: " + firstDecimal);
//        System.out.println("Второй символ (" + secondChar + ") в десятичной системе: " + secondDecimal);

//        String hex1 = "34755688";
//        String hex2 = "8955b5ce";
//        int num1 = Integer.parseInt(hex1, 16);
//        int num2 = Integer.parseInt(hex2, 16);
//        // Выполним операцию XOR
//        int resultOfXOR = num1 ^ num2;
//
//        // Преобразуем результат обратно в 16-ричную строку
//        String resultHex = Integer.toHexString(resultOfXOR).toUpperCase();
        System.out.println();

    }


}

