import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class JankenGame {
    private static final Scanner STDIN = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private static boolean isFirst = true;
    private static final String INTRODUCTION_MESSAGE = "じゃんけん勝負\r\nグーチョキパーを数字で入力してね";
    private static final String START_MESSAGE = "最初はぐー、じゃんけん：";
    private static final String WIN_MESSAGE = "あなたの勝ち";
    private static final String LOSE_MESSAGE = "あなたの負け";
    private static final String DRAW_MESSAGE = "あいこだよ!";
    private static final String REMATCH_MESSAGE = "あいこで：";
    private static final String HANDINDEX_MESSAGE = "%d:%s";
    private static final String READYJANKEN_MESSAGE = "%s(COM)と%s(Player)で…";
    private static final String GOO = "ぐー";
    private static final String CHOKI = "ちょき";
    private static final String PAA = "ぱー";
    private static final List<String> HANDLIST = new ArrayList<>() {
        {
            add(GOO);
            add(CHOKI);
            add(PAA);
        }
    };

    public static void main(String[] args) {
        System.out.println(INTRODUCTION_MESSAGE);
        showMenuOfHand();
        for(int i=0;i<=3;i++){
            playJanken();
            playAttimuitehoi();
        }
        showGameResultMessage();
    }

    private static void playJanken() {
        showStartMessage();
        String selectHand = receiveSelectHand();
        String opponentHand = getRandomHand();
        showJudgeMessage(selectHand, opponentHand);
        if (isDraw(selectHand, opponentHand)) {
            isFirst = false;
            showDrawMessage();
            playJanken();
        } else {
            showResultMessage(selectHand, opponentHand);
        }
    }

    private static void showResultMessage(String selectHand, String opponentHand) {
        if (isWin(selectHand, opponentHand)) {
            System.out.println(WIN_MESSAGE);
            return;
        }
        System.out.println(LOSE_MESSAGE);
    }

    private static boolean isWin(String selectHand, String opponentHand) {
        if (selectHand.equals(GOO)) {
            if (opponentHand == CHOKI) {
                return true;
            }
        }
        if (selectHand.equals(CHOKI)) {
            if (opponentHand.equals(PAA)) {
                return true;
            }
        }
        if (selectHand.equals(PAA)) {
            if (opponentHand.equals(GOO)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDraw(String selectHand, String opponentHand) {
        if (selectHand.equals(opponentHand)) {
            return true;
        }
        return false;
    }

    private static void showDrawMessage() {
        System.out.println(DRAW_MESSAGE);
        System.out.println();
    }

    private static void showMenuOfHand() {
        int count = 0;
        for (String hand : HANDLIST) {
            System.out.println(String.format(HANDINDEX_MESSAGE, count, hand));
            count++;
        }
        System.out.println();
    }

    private static void showStartMessage() {
        if (isFirst) {
            System.out.print(START_MESSAGE);
            return;
        }
        System.out.print(REMATCH_MESSAGE);
    }

    private static void showJudgeMessage(String selectHand, String opponentHand) {
        System.out.println(String.format(READYJANKEN_MESSAGE, opponentHand, selectHand));
    }

    private static String receiveSelectHand() {
        try {
            int inputNumber = receiveinput();
            String selectHand = convertToHand(inputNumber);
            return selectHand;
        } catch (Exception e) {
            return receiveSelectHand();
        }
    }

    private static String getRandomHand() {
        int handIndex = RANDOM.nextInt(HANDLIST.size());
        return HANDLIST.get(handIndex);
    }

    private static int receiveinput() {
        String inputStr;
        int inputNumber;
        inputStr = STDIN.nextLine();
        if (!isNumber(inputStr)) {
            inputNumber = receiveinput();
            return inputNumber;
        }
        inputNumber = Integer.parseInt(inputStr);
        return inputNumber;
    }

    private static boolean isNumber(String inputStr) {
        try {
            Integer.parseInt(inputStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static String convertToHand(int handIndex) {
        return HANDLIST.get(handIndex);
    }
}
