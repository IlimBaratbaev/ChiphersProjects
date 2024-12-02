package org.example;

public class AESSubBytes {

    // Константа для неприводимого полинома (0x11B в шестнадцатеричной форме)
    private static final int IRREDUCIBLE_POLYNOMIAL = 0x11B;

    // Матрица для преобразования (0x63 — фиксированный байт для SubBytes)
    private static final int[] AFFINE_MATRIX = {1, 1, 0, 0, 0, 1, 1, 1};
    private static final int AFFINE_CONSTANT = 0x63;

    public static void main(String[] args) {
        // Пример байта для преобразования
        int inputByte = 0x53; // Например, 83 в десятичной форме
        int outputByte = subByte(inputByte);
        System.out.printf("Input: 0x%02X, Output: 0x%02X%n", inputByte, outputByte);
    }

    // Основная функция SubBytes
    public static int subByte(int byteValue) {
        // 1. Вычисляем мультипликативную инверсию в поле GF(2^8)
        int inverse = multiplicativeInverse(byteValue);

        // 2. Преобразуем байт через аффинное преобразование
        return affineTransform(inverse);
    }

    // Вычисление мультипликативной инверсии в поле GF(2^8)
    private static int multiplicativeInverse(int byteValue) {
        if (byteValue == 0) {
            return 0; // Инверсия 0 остаётся 0
        }

        int a = byteValue;
        int b = IRREDUCIBLE_POLYNOMIAL;
        int x0 = 0, x1 = 1;

        // Расширенный алгоритм Евклида
        while (a != 1) {
            int q = a / b;
            int temp = a;
            a = b;
            b = temp % b;

            int xTemp = x0 ^ multiply(q, x1);
            x0 = x1;
            x1 = xTemp;
        }

        return x1 & 0xFF; // Возвращаем результат в пределах 1 байта
    }

    // Аффинное преобразование
    private static int affineTransform(int byteValue) {
        int result = 0;

        // Побитовая операция сдвига и сложения по модулю 2 (GF(2))
        for (int i = 0; i < 8; i++) {
            int bit = 0;

            for (int j = 0; j < 8; j++) {
                bit ^= ((byteValue >> j) & 1) * AFFINE_MATRIX[(i + j) % 8];
            }

            result |= (bit << i);
        }

        return result ^ AFFINE_CONSTANT; // Применяем константу (0x63)
    }

    // Умножение в поле GF(2^8)
    private static int multiply(int a, int b) {
        int result = 0;

        while (b != 0) {
            if ((b & 1) != 0) {
                result ^= a; // Добавляем a, если младший бит b = 1
            }

            a <<= 1; // Сдвиг a влево
            if ((a & 0x100) != 0) {
                a ^= IRREDUCIBLE_POLYNOMIAL; // Редукция по модулю полинома
            }

            b >>= 1; // Сдвиг b вправо
        }

        return result;
    }
}
