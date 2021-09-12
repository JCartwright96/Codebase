package programmingproblems;

import java.util.Scanner;

public class JavaLoops2 {
    public static void main(String []argh){
        Scanner in = new Scanner(System.in);
        int t=in.nextInt();
        StringBuilder answer = new StringBuilder();
        for(int i=0;i<t;i++){
            int a = in.nextInt();
            int b = in.nextInt();
            int n = in.nextInt();
            answer.append(doCalculation(a, b, n)).append("\n");
        }
        in.close();
        System.out.println(answer);
    }
    public static String doCalculation(int a, int b, int n) {
        StringBuilder answer = new StringBuilder();
        int temp = a + (int) Math.pow(2, 0) * b;
        answer.append(temp);
        for(int j = 1; j < n; j++) {
            temp = temp + (int) Math.pow(2, j) * b;
            answer.append(" ").append(temp);
        }
        return answer.toString();
    }
}
