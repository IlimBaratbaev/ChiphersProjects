package org.example;

import java.util.Arrays;

public class AesUtils {
    String[][] sBox = {
            {"63", "7c", "77", "7b", "f2", "6b", "6f", "c5", "30", "01", "67", "2b", "fe", "d7", "ab", "76"},
            {"ca", "82", "c9", "7d", "fa", "59", "47", "f0", "ad", "d4", "a2", "af", "9c", "a4", "72", "c0"},
            {"b7", "fd", "93", "26", "36", "3f", "f7", "cc", "34", "a5", "e5", "f1", "71", "d8", "31", "15"},
            {"04", "c7", "23", "c3", "18", "96", "05", "9a", "07", "12", "80", "e2", "eb", "27", "b2", "75"},
            {"09", "83", "2c", "1a", "1b", "6e", "5a", "a0", "52", "3b", "d6", "b3", "29", "e3", "2f", "84"},
            {"53", "d1", "00", "ed", "20", "fc", "b1", "5b", "6a", "cb", "be", "39", "4a", "4c", "58", "cf"},
            {"d0", "ef", "aa", "fb", "43", "4d", "33", "85", "45", "f9", "02", "7f", "50", "3c", "9f", "a8"},
            {"51", "a3", "40", "8f", "92", "9d", "38", "f5", "bc", "b6", "da", "21", "10", "ff", "f3", "d2"},
            {"cd", "0c", "13", "ec", "5f", "97", "44", "17", "c4", "a7", "7e", "3d", "64", "5d", "19", "73"},
            {"60", "81", "4f", "dc", "22", "2a", "90", "88", "46", "ee", "b8", "14", "de", "5e", "0b", "db"},
            {"e0", "32", "3a", "0a", "49", "06", "24", "5c", "c2", "d3", "ac", "62", "91", "95", "e4", "79"},
            {"e7", "c8", "37", "6d", "8d", "d5", "4e", "a9", "6c", "56", "f4", "ea", "65", "7a", "ae", "08"},
            {"ba", "78", "25", "2e", "1c", "a6", "b4", "c6", "e8", "dd", "74", "1f", "4b", "bd", "8b", "8a"},
            {"70", "3e", "b5", "66", "48", "03", "f6", "0e", "61", "35", "57", "b9", "86", "c1", "1d", "9e"},
            {"e1", "f8", "98", "11", "69", "d9", "8e", "94", "9b", "1e", "87", "e9", "ce", "55", "28", "df"},
            {"8c", "a1", "89", "0d", "bf", "e6", "42", "68", "41", "99", "2d", "0f", "b0", "54", "bb", "16"}
    };

    int[][] mixColumnsBox = {
            {0x02, 0x03, 0x01, 0x01},
            {0x01, 0x02, 0x03, 0x01},
            {0x01, 0x01, 0x02, 0x03},
            {0x03, 0x01, 0x01, 0x02}
    };

    int[] rCons = {
            0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1B, 0x36
    };

    public String[][] stringToDoubleArrayAes(String text) {
        int length = text.length();
        int col = 0, row = 0;
        String[][] result = new String[4][4];
        for (int i = 0; i < length - 1; i += 2) {
            if (row == 4) {
                row = 0;
                col++;
            }
            result[row][col] = text.substring(i, i + 2).toUpperCase();
            row++;
        }
        return result;
    }

    public void printStringDoubleArrayAes(String[][] array) {
        Arrays.stream(array)
                .forEach(arr -> System.out.println(Arrays.toString(arr)));
    }

    public void printTextWithDelimiters(String text) {
        System.out.println("\n******************************************************************");
        System.out.println(text);
        System.out.println("******************************************************************\n");
    }

    public String[][] addRoundKeyAes(String[][] text, String[][] roundKey) {
        String[][] result = new String[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String hex1 = text[i][j];
                String hex2 = roundKey[i][j];
                String resultHex = xorHex2(hex1, hex2);
                result[i][j] = resultHex.toUpperCase();
            }
        }
        return result;
    }

    public String[][] subBytesAes(String[][] text) {
        String[][] result = new String[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String hexValue = text[i][j];
                char firstChar = hexValue.charAt(0);
                char secondChar = hexValue.charAt(1);
                int firstDecimal = Integer.parseInt(String.valueOf(firstChar), 16);
                int secondDecimal = Integer.parseInt(String.valueOf(secondChar), 16);
                result[i][j] = sBox[firstDecimal][secondDecimal].toUpperCase();
            }
        }
        return result;
    }

    public String[][] shiftRowsAes(String[][] text) {
        String[][] result = new String[4][4];
        // Копируем первую строку без изменений
        for (int i = 0; i < 4; i++) {
            result[0][i] = text[0][i];
        }

        // Для второй строки сдвигаем на 1
        for (int i = 0; i < 4; i++) {
            result[1][i] = text[1][(i + 1) % 4];  // сдвиг на 1
        }

        // Для третьей строки сдвигаем на 2
        for (int i = 0; i < 4; i++) {
            result[2][i] = text[2][(i + 2) % 4];  // сдвиг на 2
        }

        // Для четвёртой строки сдвигаем на 3
        for (int i = 0; i < 4; i++) {
            result[3][i] = text[3][(i + 3) % 4];  // сдвиг на 3
        }

        return result;
    }

    public String[][] mixColumnsAes(String[][] text) {
        // Результирующая матрица
        String[][] result = new String[4][4];

        // Проход по каждому столбцу
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                int mixColumnValue = 0;

                // Умножение строки mixColumnsBox на столбец text
                for (int k = 0; k < 4; k++) {
                    mixColumnValue ^= galoisMultiplication(
                            mixColumnsBox[row][k],
                            Integer.parseInt(text[k][col], 16) // Преобразование из HEX в int
                    );
                }
                // Конвертируем результат обратно в HEX строку
                result[row][col] = String.format("%02x", mixColumnValue).toUpperCase();
            }
        }

        return result;
    }

    // Умножение в поле Галуа GF(2^8)
    private int galoisMultiplication(int a, int b) {
        int result = 0;

        while (b > 0) {
            if ((b & 1) != 0) { // Если младший бит равен 1
                result ^= a; // Выполняем XOR
            }

            // Сдвигаем b вправо
            b >>= 1;

            // Умножаем a на x (проверяем переполнение)
            a <<= 1;
            if ((a & 0x100) != 0) { // Если a >= 256, выполняем модуль по 0x11B
                a ^= 0x11B;
            }
        }

        return result & 0xFF; // Оставляем младшие 8 бит
    }

    //Расширение ключей
    /* (i mod 4) != 0, wi = wi-1 xor wi-4
     (i mod 4) = 0, wi = t xor wi-4
     t = SubWord(Rotword(wi-1)) xor RCon i/4*/
    public String[] keyScheduleAes(String key) {
        String[] allKeys = new String[11]; //ключи от 0 раунда до 10 раунда allkeys[5] = ключ 5 раунда
        allKeys[0] = key;

        String[] words = new String[44];
        for (int i = 0; i < 4; i++) {
            words[i] = key.substring(i * 8, (i + 1) * 8); //первые 4 слова
        }
        for (int i = 4; i < 44; i++) {
//            if (i >= 28) {
//                System.out.println("now at " + i);
//            } писал для решения проблемы
            String previousWord = words[i - 1];
            if (i % 4 == 0) {
                previousWord = subWord(rotWord(previousWord));
                previousWord = xorHex8(previousWord, rConHex(i / 4 - 1));
            }
            // Вычисляем текущее слово: words[i] = words[i-4] ^ temp
            words[i] = xorHex8(words[i - 4], previousWord);
        }
        // Формируем allKeys из words (каждые 4 слова = 1 раундовый ключ)
        for (int i = 0; i < 11; i++) {
            allKeys[i] = String.join("", words[i * 4], words[i * 4 + 1], words[i * 4 + 2], words[i * 4 + 3]);
//            System.out.println("Раунд " + i + " = " + allKeys[i]); писал для решения проблемы

        }
        return allKeys;
    }

    public String rotWord(String word) {
        //слово состоит из 4 байт пример RotWord(13AA5487) -> AA548713
        return word.substring(2) + word.substring(0, 2);
    }

    public String subWord(String word) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < word.length(); i += 2) {
            int row = Integer.parseInt(word.substring(i, i + 1), 16);
            int col = Integer.parseInt(word.substring(i + 1, i + 2), 16);
            result.append(sBox[row][col]);
        }
        return result.toString();
    }

    private String xorHex8(String a, String b) {
        long num1 = Long.parseLong(a, 16);
        long num2 = Long.parseLong(b, 16);
        return String.format("%08x", num1 ^ num2);
    }

    private String xorHex2(String a, String b) {
        long num1 = Long.parseLong(a, 16);
        long num2 = Long.parseLong(b, 16);
        return String.format("%02x", num1 ^ num2);
    }


    private String rConHex(int round) {
        return String.format("%08X", rCons[round]) + "000000";
    }


    public String[][] executeRound(int roundNumber, String[][] state, String roundKey) {
        printTextWithDelimiters("Раунд " + roundNumber);
        System.out.println("Операция SubBytes");
        state = subBytesAes(state);
        printStringDoubleArrayAes(state);
        System.out.println("Операция ShiftRows");
        state = shiftRowsAes(state);
        printStringDoubleArrayAes(state);
        if (roundNumber < 10) { // MixColumns выполняется до последнего раунда
            System.out.println("Операция MixColumns");
            state = mixColumnsAes(state);
            printStringDoubleArrayAes(state);
        }
        System.out.println("Операция AddRoundKey");
        System.out.println("Ключ к " + roundNumber + "-му раунду: " + roundKey);
        System.out.println("Состояние текста после " + roundNumber + "-го раунда");
        state = addRoundKeyAes(state, stringToDoubleArrayAes(roundKey));
        printStringDoubleArrayAes(state);
        return state;
    }
}


