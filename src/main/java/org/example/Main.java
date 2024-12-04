package org.example;

public class Main {
    public static void main(String[] args) {
        AesUtils utils = new AesUtils();
        //текста
        String text = "12040811001512171100071808110419";
        String key = "2475A2B33475568831E2120013AA5487";
        String[] allKeys = utils.keyScheduleAes(key);

        //Раунд 0, необходимо провести AddRoundKey
        utils.printTextWithDelimiters("Раунд 0");
        String[][] state = utils.stringToDoubleArrayAes(text);
        System.out.println("Текст");
        utils.printStringDoubleArrayAes(state);
        System.out.println("Ключ к " + 0 + "-му раунду: " + allKeys[0]);
        state = utils.addRoundKeyAes(state, utils.stringToDoubleArrayAes(allKeys[0]));
        utils.printTextWithDelimiters("Вывод текста после операции addRoundKey");
        utils.printStringDoubleArrayAes(state);
        System.out.println();

        //Выполнение всех остальных раундов
        state = utils.executeRound(1, state, allKeys[1]);
        state = utils.executeRound(2, state, allKeys[2]);
        state = utils.executeRound(3, state, allKeys[3]);
        state = utils.executeRound(4, state, allKeys[4]);
        state = utils.executeRound(5, state, allKeys[5]);
        state = utils.executeRound(6, state, allKeys[6]);
        state = utils.executeRound(7, state, allKeys[7]);
        state = utils.executeRound(8, state, allKeys[8]);
        state = utils.executeRound(9, state, allKeys[9]);
        state = utils.executeRound(10, state, allKeys[10]);
    }
}

