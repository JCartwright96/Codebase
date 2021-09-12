package pokemon;

import java.util.Random;
import java.util.Scanner;

public class BattleCounter {

    public static void main(String[] args) {
        simulateChoice(100);
        simulateChoice(100);
        simulateChoice(100);
        simulateChoice(100);
        simulateChoice(100);
//        int count = 0;
//        System.out.println(count);
//        String text = "";
//        while(!text.equalsIgnoreCase("stop")) {
//            Scanner sc = new Scanner(System.in);
//            text = sc.nextLine();
//            count++;
//            System.out.println(count);
//        }
    }
    public static int chooseRandom() {
        Random rand = new Random();
        return rand.nextInt(49);
    }
    public static void simulateChoice(int amount) {
        for (int i = 1; i <= amount; i++) {
            int number = chooseRandom();
            if(number == 2) {
                System.out.println("TOGEPI!!!!! caught on " + i);
                break;
            }
            System.out.print(i + " ");
            if(i == 100) {
                System.out.println();
            }
        }
    }
}

