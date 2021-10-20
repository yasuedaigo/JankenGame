import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class JankenGame {
    private static final Scanner STDIN = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private static final String INTRODUCTION_MESSAGE = "じゃんけん勝負\r\nグーチョキパーを数字で入力してね";
    private static final String JANKENSTART_MESSAGE = "最初はぐー、じゃんけん：";
    private static final String OUTOFBOUNDS_MESSAGE = "正しい数字を入力してください";
    private static final String NUMBERERROR_MESSAGE = "数字ではありません";
    private static final String ATTIMUITEHOISTART_MESSAGE = "%s:あっちむいてほい : ";
    private static final String JUDGE_MESSAGE = "%s(%s)と%s(%s)で…";
    private static final String DRAWATTIMUITEHOI_MESSAGE = "引き分け";
    private static final String WINJANKEN_MESSAGE = "あなたの勝ち";
    private static final String LOSEJANKEN_MESSAGE = "あなたの負け";
    private static final String DRAWJANKEN_MESSAGE = "あいこだよ!";
    private static final String REMATCHJANKEN_MESSAGE = "あいこで：";
    private static final String MOVEMENU_MESSAGE = "%d:%s";
    private static final String GAMERESULT_MESSAGE = "%d勝 %d敗 %d引き分け";
    private static final String GOO = "ぐー";
    private static final String CHOKI = "ちょき";
    private static final String PAA = "ぱー";
    private static final String UE = "上";
    private static final String SITA = "下";
    private static final String MIGI = "右";
    private static final String HIDARI = "左";
    private static int winCount = 0;
    private static int loseCount = 0;
    private static int drowCount = 0;

    private static enum Player {
        あなた, COM,
    };

    private static final List<String> HANDLIST = new ArrayList<>() {
        {
            add(GOO);
            add(CHOKI);
            add(PAA);
        }
    };
    private static final List<String> DIRECTIONLIST = new ArrayList<>() {
        {
            add(UE);
            add(SITA);
            add(MIGI);
            add(HIDARI);
        }
    };

    public static void main(String[] args) {
        System.out.println(INTRODUCTION_MESSAGE);
        for (int i = 0; i < 3; i++) {
            boolean isFirstJanken = true;
            Player jankenWinPlayer;
            jankenWinPlayer = playJanken(isFirstJanken);
            playAttimuitehoi(jankenWinPlayer);
        }
        showGameResultMessage();
    }

    private static Player playJanken(boolean isFirstJanken) {
        showMenuOfMove(HANDLIST);
        showJankenStartMessage(isFirstJanken);
        String playerHand = receivePlayerMove(HANDLIST);
        String comHand = getRandomMove(HANDLIST);
        showJudgeMessage(playerHand, comHand);
        if (isDrawJanken(playerHand, comHand)) {
            showDrawMessage();
            isFirstJanken = false;
            Player jankenWinPlayer = playJanken(isFirstJanken);
            return jankenWinPlayer;
        } else {
            showJankenResultMessage(playerHand, comHand);
        }
        return getJankenWinPlayer(playerHand, comHand);
    }

    private static void playAttimuitehoi(Player winJankenPlayer) {
        showMenuOfMove(DIRECTIONLIST);
        showAttimuitehoiStartMessage(winJankenPlayer);
        String playerDirection = receivePlayerMove(DIRECTIONLIST);
        String comDirection = getRandomMove(DIRECTIONLIST);
        showJudgeMessage(playerDirection, comDirection);
        showAttimuitehoiResultMessage(comDirection, playerDirection, winJankenPlayer);
        calcScore(comDirection, playerDirection, winJankenPlayer);
    }

    private static void showJankenResultMessage(String playerHand, String comHand) {
        if (isWinJanken(playerHand, comHand)) {
            System.out.println(WINJANKEN_MESSAGE);
            return;
        }
        System.out.println(LOSEJANKEN_MESSAGE);
    }

    private static boolean isWinJanken(String playerHand, String comHand) {
        if (playerHand.equals(GOO)) {
            if (comHand == CHOKI) {
                return true;
            }
        }
        if (playerHand.equals(CHOKI)) {
            if (comHand.equals(PAA)) {
                return true;
            }
        }
        if (playerHand.equals(PAA)) {
            if (comHand.equals(GOO)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isWinJanken(Player winJankenPlayer) {
        if (winJankenPlayer == Player.あなた) {
            return true;
        }
        return false;
    }

    private static void calcScore(String comDirection, String playerDirection, Player winJankenPlayer) {
        if (isDrowAttimuitehoi(comDirection, playerDirection)) {
            drowCount++;
            return;
        }
        if (isWinJanken(winJankenPlayer)) {
            winCount++;
            return;
        }
        loseCount++;
    }

    private static boolean isDrawJanken(String playerHand, String comHand) {
        if (playerHand.equals(comHand)) {
            return true;
        }
        return false;
    }

    private static void showDrawMessage() {
        System.out.println(DRAWJANKEN_MESSAGE);
        System.out.println();
    }

    private static void showMenuOfMove(List<String> moveList) {
        int count = 0;
        for (String move : moveList) {
            System.out.println(String.format(MOVEMENU_MESSAGE, count, move));
            count++;
        }
        System.out.println();
    }

    private static void showJankenStartMessage(boolean isFirst) {
        if (isFirst) {
            System.out.print(JANKENSTART_MESSAGE);
            return;
        }
        System.out.print(REMATCHJANKEN_MESSAGE);
    }

    private static void showAttimuitehoiStartMessage(Player winJankenPlayer) {
        System.out.print(String.format(ATTIMUITEHOISTART_MESSAGE, winJankenPlayer.toString()));
    }

    private static void showJudgeMessage(String playerHand, String comHand) {
        System.out.println(
                String.format(JUDGE_MESSAGE, playerHand, Player.あなた.toString(), comHand, Player.COM.toString()));
    }

    private static void showGameResultMessage() {
        System.out.println(String.format(GAMERESULT_MESSAGE, winCount, loseCount, drowCount));
    }

    private static void showAttimuitehoiResultMessage(String comDirection, String playerDirection,
            Player winJankenPlayer) {
        if (isDrowAttimuitehoi(comDirection, playerDirection)) {
            System.out.println(DRAWATTIMUITEHOI_MESSAGE);
            return;
        }
        if (isWinJanken(winJankenPlayer)) {
            System.out.println(WINJANKEN_MESSAGE);
            return;
        }
        System.out.println(LOSEJANKEN_MESSAGE);
    }

    private static boolean isDrowAttimuitehoi(String comDirection, String playerDirection) {
        if (comDirection.equals(playerDirection)) {
            return false;
        }
        return true;
    }

    private static int receiveinput() {
        String inputStr;
        int inputIndex;
        inputStr = STDIN.nextLine();
        if (!isNumber(inputStr)) {
            System.out.println(NUMBERERROR_MESSAGE);
            inputIndex = receiveinput();
            return inputIndex;
        }
        inputIndex = Integer.parseInt(inputStr);
        return inputIndex;
    }

    private static String receivePlayerMove(List<String> moveList) {
        try {
            int inputIndex = receiveinput();
            String selectMove = convertToMove(inputIndex, moveList);
            return selectMove;
        } catch (IndexOutOfBoundsException e) {
            System.out.println(OUTOFBOUNDS_MESSAGE);
            return receivePlayerMove(moveList);
        }
    }

    private static String getRandomMove(List<String> moveList) {
        int Index = RANDOM.nextInt(moveList.size());
        return moveList.get(Index);
    }

    private static boolean isNumber(String inputStr) {
        try {
            Integer.parseInt(inputStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static String convertToMove(int index, List<String> moveList) {
        return moveList.get(index);
    }

    private static Player getJankenWinPlayer(String playerHand, String comHand) {
        if (isWinJanken(playerHand, comHand)) {
            return Player.あなた;
        }
        return Player.COM;
    }
}
