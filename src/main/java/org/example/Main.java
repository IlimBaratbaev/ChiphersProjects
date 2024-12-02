package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        AesUtils utils = new AesUtils();
        //текста
        String text = "12040811001512171100071808110419";
        String key = "2475A2B33475568831E2120013AA5487";
        String[] allKeys = utils.keyScheduleAes(key);
        //Преобразование и вывод ключа и текста
        System.out.println("Исходный текст: " + text);
        String[][] arrayText = utils.stringToDoubleArrayAes(text);
        System.out.println("После преобразования");
        utils.printStringDoubleArrayAes(arrayText);
        System.out.println("Ключ: " + allKeys[0]);
        String[][] arrayKey = utils.stringToDoubleArrayAes(key);
        System.out.println("После преобразования");
        utils.printStringDoubleArrayAes(arrayKey);

        //Раунд 0, необходимо провести AddRoundKey и KeyShedule
        utils.printTextWithDelimiters("Раунд 0");
        String[][] addRoundKey = utils.addRoundKeyAes(arrayText, arrayKey);
        utils.printTextWithDelimiters("Вывод текста после операции addRoundKey");
        utils.printStringDoubleArrayAes(addRoundKey);
        System.out.println();

        //Раунд 1
        utils.printTextWithDelimiters("Раунд 1");
        System.out.println("Операция SubBytes");
        String[][] subBytesRound1 = utils.subBytesAes(addRoundKey);
        utils.printStringDoubleArrayAes(subBytesRound1);
        System.out.println("Операция ShiftRows");
        String[][] shiftRowsRound1 = utils.shiftRowsAes(subBytesRound1);
        utils.printStringDoubleArrayAes(shiftRowsRound1);
        System.out.println("Операция MixColumns");
        String[][] mixColumnsRound1 = utils.mixColumnsAes(shiftRowsRound1);
        utils.printStringDoubleArrayAes(mixColumnsRound1);
        System.out.println("Ключ к 1-му раунду: " + allKeys[1]);
        String[][] addRoundKey2 = utils.addRoundKeyAes(mixColumnsRound1, utils.stringToDoubleArrayAes(allKeys[1]));
        utils.printStringDoubleArrayAes(addRoundKey2);
    }
}

