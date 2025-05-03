import java.util.Scanner;
import gameTool.Tictactoe;

class Main {

    static String[][][] gamePlate = new String[3][3][3];

    public static void main(String[] args) { 
        Scanner input = new Scanner(System.in);

        String winner = null;
    
        System.out.println("Welcome to 3D TICTACTOE!");
        System.out.println(" ");

        System.out.println("Who should go first Human or CPU (H/C)?");
        String turn = input.nextLine();
        
        while (!(turn.equals("H")) && !(turn.equals("C"))) {
            System.out.println("Invalid Input!");
            turn = input.nextLine();
        } 

        System.out.println("");

        while (winner == null) {
            String move = "";
            if (turn.equals("H")) {
                System.out.println("(HUMAN) Make a move:");
                move = input.nextLine();
                while (move.length() != 3) {
                    System.out.println("Invalid Move!");
                    move = input.nextLine();
                }
            } else {
                System.out.println("(COMPUTER) Make a move: ");
                move = Tictactoe.computerMove(gamePlate);
                System.out.println(move);
            }

            System.out.println("");

            gamePlate = Tictactoe.applyMove(gamePlate, move, turn);

            Tictactoe.displayPlate(gamePlate);

            if (Tictactoe.detectWin(gamePlate) != null) {
                if (Tictactoe.detectWin(gamePlate).equals("H")) {
                    winner = "H";
                } else {
                    winner = "C";
                }
            }

            turn = Tictactoe.changeTurn(turn);

            System.out.println(" ");
        }

        if (winner.equals("H")) {
            System.out.print("HUMAN WINS!");
        } else {
            System.out.print("COMPUTER WINS!");
        }

        input.close();
    }
}