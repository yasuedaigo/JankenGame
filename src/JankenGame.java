import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class JankenGame {
    private static final Scanner STDIN = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private static Boolean isFirst = true;
    private static final String INTRODUCTION_MESSAGE = "じゃんけん勝負\r\nグーチョキパーを数字で入力してね";
    private static final String START_MESSAGE = "最初はぐー、じゃんけん：";
    private static final String WIN_MESSAGE = "あなたの勝ち";
    private static final String LOSE_MESSAGE = "あなたの負け";
    private static final String DRAW_MESSAGE = "あいこだよ!";
    private static final String REMATCH_MESSAGE = "あいこで：";

    private static final List<String> HANDLIST = new ArrayList<>() {
        {
            add("グー");
            add("チョキ");
            add("パー");
        }
    };

    public static void main(String[] args) {
        System.out.println(INTRODUCTION_MESSAGE);
        showMenuOfHand();
        playJanken();

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
        if (selectHand == "グー") {
            if (opponentHand == "チョキ") {
                return true;
            }
        }
        if (selectHand == "チョキ") {
            if (opponentHand == "パー") {
                return true;
            }
        }
        if (selectHand == "パー") {
            if (opponentHand == "グー") {
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
            System.out.println(String.format("%d:%s", count, hand));
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
        System.out.println(String.format("%s(COM)と%s(Player)で…", opponentHand, selectHand));
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
        int handInt = RANDOM.nextInt(HANDLIST.size());
        return HANDLIST.get(handInt);
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

    private static String convertToHand(int handInt) {
        return HANDLIST.get(handInt);
    }
}
