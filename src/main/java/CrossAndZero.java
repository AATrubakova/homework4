import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class CrossAndZero {
    static String[][] field;
    static int winNum = 4;
    static int playerX;
    static int playerY;

    public static void main(String[] args) {
        initField();
        showField();
        while (!isFinishedGame()) {
            move();
            showField();
            if (!isFinishedGame()) {
                movePC();
                showField();
            }
            else break;
        }
    }

    public static void initField() {
        field = new String[5][5];
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field[0].length; j++)
                field[i][j] = ".";
    }

    public static void showField() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void move() {
        Scanner sc = new Scanner(System.in);
        boolean isNotFinished = true;
        while (isNotFinished) {
            System.out.println("Выберите строку и столбец, куда нужно ходить:");
            try {
                int x = sc.nextInt() - 1;
                int y = sc.nextInt() - 1;
                if (x >= 0 && x < field.length && y >= 0 && y < field.length) {
                    if (field[x][y].equals(".")) {
                        field[x][y] = "X";
                        playerX = x;
                        playerY = y;
                        isNotFinished = false;
                    } else {
                        System.out.println("Тут уже сделан ход!");
                        return;
                    }
                } else {
                    System.out.println("Вы ушли за пределы поля!");
                }
            } catch (Exception e) {
                System.out.println("Вы ввели неправильные данные");
            }
        }
    }

    public static void movePC() {
        if (isThisSymbol(playerX-1, playerY-1, "X") && isThisSymbol(playerX+1, playerY+1, ".") ) {
            field[playerX+1][playerY+1] = "O";
        } else if(isThisSymbol(playerX, playerY-1, "X") && isThisSymbol(playerX, playerY+1, ".") ) {
            field[playerX][playerY+1] = "O";
        } else if(isThisSymbol(playerX+1, playerY-1, "X") && isThisSymbol(playerX-1, playerY+1, ".") ) {
            field[playerX-1][playerY+1] = "O";
        } else if(isThisSymbol(playerX+1, playerY, "X") && isThisSymbol(playerX-1, playerY, ".") ) {
            field[playerX-1][playerY] = "O";
        } else if(isThisSymbol(playerX+1, playerY+1, "X") && isThisSymbol(playerX-1, playerY-1, ".") ) {
            field[playerX-1][playerY-1] = "O";
        } else if(isThisSymbol(playerX, playerY+1, "X") && isThisSymbol(playerX, playerY-1, ".") ) {
            field[playerX][playerY-1] = "O";
        } else if(isThisSymbol(playerX-1, playerY+1, "X") && isThisSymbol(playerX+1, playerY-1, ".") ) {
            field[playerX+1][playerY-1] = "O";
        } else if(isThisSymbol(playerX-1, playerY, "X") && isThisSymbol(playerX+1, playerY, ".") ) {
            field[playerX+1][playerY] = "O";
        } else {
            Random random = new Random();

            boolean isNotFinished = true;

            while (isNotFinished) {
                int x = random.nextInt(field.length);
                int y = random.nextInt(field.length);
                if (field[x][y].equals(".")) {
                    field[x][y] = "O";
                    isNotFinished = false;
                }
            }
        }
        System.out.println("Компьютер сделал ход");
    }

    public static boolean isFinishedGame() {

        // проверка победы
        if (isWin("X")) {
            System.out.println("игрок победил");
            return true;
        } else if (isWin("0")) {
            System.out.println("компьютер победил");
            return true;
        }

        // проверка ничьи
        int countFreeSpace = 0;
        for (String[] arr : field)
            for (String elem : arr)
                if (elem.equals("."))
                    countFreeSpace += 1;
        if (countFreeSpace == 0) {
            System.out.println("Ничья");
            return true;
        }

        // игра не закончена
        return false;
    }

    public static boolean isWin(String symbol) {
        // проверка диагонали 1
        int num = 0;
        for (int i = 0; i < field.length; i++) {
            if (field[i][i].equals(symbol)) {
                num += 1;
                if (num == winNum) {
                    return true;
                }
            } else {
                num = 0;
            }
        }

        // проверка диагонали 2
        num = 0;
        for (int i = 0; i < field.length; i++) {
            if (field[i][field.length - i - 1].equals(symbol)) {
                num += 1;
                if (num == winNum) {
                    return true;
                }
            } else {
                num = 0;
            }
        }

        // проверка строк
        for (int i = 0; i < field.length; i++) {
            num = 0;
            for (int j = 0; j < field.length; j++) {
                if (field[i][j].equals(symbol)) {
                    num += 1;
                    if (num == winNum) {
                        return true;
                    }
                } else {
                    num = 0;
                }
            }
        }

        // проверка столбцов
        for (int j = 0; j < field.length; j++) {
            num = 0;
            for (int i = 0; i < field.length; i++) {
                if (field[i][j].equals(symbol)) {
                    num += 1;
                    if (num == winNum) {
                        return true;
                    }
                } else {
                    num = 0;
                }
            }
        }

        // не выиграл
        return false;
    }
    public static boolean isThisSymbol(int x, int y, String symbol) {
        if (x < 0 || y < 0 || x >= field.length || y >= field.length) {
            return false;
        } else {
            return (field[x][y] == symbol);
        }
    }
}