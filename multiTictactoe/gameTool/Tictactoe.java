package gameTool;

public class Tictactoe {
    public static void displayPlate(String[][][] plate) {
        System.out.println("TOP MID BOT");

        for (int row = 0; row < 3; row++) {
            for (int type = 0; type < 3; type++) {
                for (int col = 0; col < 3; col++) {
                    if (plate[type][row][col] != null) {
                        System.out.print(plate[type][row][col]);
                    } else {
                        System.out.print("-");
                    }
                }
                System.out.print(" ");
            }
            System.out.println("");
        }
    }

    public static String[][][] applyMove(String[][][] plate, String input, String currentTurn) {
        int plateType = Integer.parseInt(input.substring(0, 1)) - 1;
        plateType = Math.min(plateType, 2);

        int location = Integer.parseInt(input.substring(2));

        int row = (int) Math.floor((location-1)/3);
        int col = location - (3*row) - 1;


        if (plate[plateType][row][col] == null) {
            if (currentTurn.equals("H")) {
                plate[plateType][row][col] = "O";
            } else {
                plate[plateType][row][col] = "X";
            }
        }

        return plate;
    }

    public static String changeTurn(String currentTurn) {
        if (currentTurn.equals("H")) {
            return "C";
        } else  {
            return "H";
        }
    }

    public static int[][][] generateAllWinningLines() {
        int[][][] lines = new int[55][3][3];
        int index = 0;
    
        for (int t = 0; t < 3; t++) {
            for (int i = 0; i < 3; i++) {
                lines[index++] = new int[][] { {t,i,0}, {t,i,1}, {t,i,2} };
                lines[index++] = new int[][] { {t,0,i}, {t,1,i}, {t,2,i} };
                lines[index++] = new int[][] { {0,i,t}, {1,i,t}, {2,i,t} };
            }
    
            lines[index++] = new int[][] { {0,t,0}, {1,t,1}, {2,t,2} };
            lines[index++] = new int[][] { {0,t,2}, {1,t,1}, {2,t,0} };
    
            lines[index++] = new int[][] { {0,0,t}, {1,1,t}, {2,2,t} };
            lines[index++] = new int[][] { {0,2,t}, {1,1,t}, {2,0,t} };
    
            lines[index++] = new int[][] { {t,0,0}, {t,1,1}, {t,2,2} };
            lines[index++] = new int[][] { {t,0,2}, {t,1,1}, {t,2,0} };

            lines[index++] = new int[][] { {t,0,0}, {t,1,1}, {t,2,2} };
            lines[index++] = new int[][] { {t,0,2}, {t,1,1}, {t,2,0} };
        }
    
        lines[index++] = new int[][] { {0,0,0}, {1,1,1}, {2,2,2} };
        lines[index++] = new int[][] { {0,0,2}, {1,1,1}, {2,2,0} };
        lines[index++] = new int[][] { {0,2,0}, {1,1,1}, {2,0,2} };
        lines[index++] = new int[][] { {0,2,2}, {1,1,1}, {2,0,0} };
    
        return lines;
    }

    public static String detectWin(String[][][] plate) {
        int[][][] allWinCase = generateAllWinningLines(); 

        for (int[][] line : allWinCase) {
            String firstThing = plate[line[0][0]][line[0][1]][line[0][2]];
    
            if (firstThing == null) {
                continue;
            }
    
            boolean allSame = true;
            for (int i = 1; i < 3; i++) {
                int[] pos = line[i];
                if (!firstThing.equals(plate[pos[0]][pos[1]][pos[2]])) {
                    allSame = false;
                    break;
                }
            }
    
            if (allSame) {
                return giveWinner(firstThing);
            }
        }
        
        if (isPlateFilled(plate)) {
            return "TIE";
        } else {
            return null;
        }
    }

    public static boolean isPlateFilled(String[][][] plate) {
        for (int i = 0; i < plate.length; i++) {
            for (int j = 0; j < plate[i].length; j++) {
                for (int k = 0; k < plate[i][j].length; k++) {
                    if (plate[i][j][k] == null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static String giveWinner(String location) {
        if (location.equals("O")) {
            return "H";
        } else {
            return "C";
        }
    }

    public static String computerMove(String[][][] plate) {
        System.out.println("COMPUTING...");

        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];

        int emergencyMove = 0;
        for (int type = 0; type < 3; type++) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (plate[type][row][col] == null) {
                        plate[type][row][col] = "X";
                        if (detectWin(plate) != null && detectWin(plate).equals("C")) {
                            emergencyMove += 100; 

                            if (emergencyMove > 0) {
                                bestMove[0] = type + 1; 
                                bestMove[1] = row * 3 + col + 1;
                            }
                        } 
                        plate[type][row][col] = null;

                        plate[type][row][col] = "O";
                        if (detectWin(plate) != null && detectWin(plate).equals("H")) {
                            emergencyMove -= 1;
 
                            if (emergencyMove < 0) {
                                bestMove[0] = type + 1; 
                                bestMove[1] = row * 3 + col + 1;
                            }
                        }
                        plate[type][row][col] = null;
                    }
                }
            }
        }

        if (emergencyMove != 0) {
            return Integer.toString(bestMove[0]) + "-" + Integer.toString(bestMove[1]);
        }


        for (int type = 0; type < 3; type++) {
            if (plate[1][1][1] == null) {
                bestMove[0] = 2; 
                bestMove[1] = 5;
                return Integer.toString(bestMove[0]) + "-" + Integer.toString(bestMove[1]);
            }

            if (plate[type][1][1] == null) {
                if (plate[type + ((type + 1) % 3)][1][1] != null && plate[type + ((type + 2) % 3)][1][1] != null) {
                    break;
                } else {
                    bestMove[0] = type + 1; 
                    bestMove[1] = 5;
                    return Integer.toString(bestMove[0]) + "-" + Integer.toString(bestMove[1]);
                }
            }
        }


        for (int type = 0; type < 3; type++) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (plate[type][row][col] == null) {
                        plate[type][row][col] = "X";
                        int score = predictGame(plate, Integer.MIN_VALUE, Integer.MAX_VALUE, "H", 6);
                        plate[type][row][col] = null;

                        if (score > bestScore) {
                            bestScore = score;
                            bestMove[0] = type + 1; 
                            bestMove[1] = row * 3 + col + 1;
                        }
                    }
                }
            }
        }
        return Integer.toString(bestMove[0]) + "-" + Integer.toString(bestMove[1]);
    }

    public static int predictGame(String[][][] plate, int a, int b, String turn, int depth) {
        String result = detectWin(plate);
        if (result != null) {
            if (result.equals("C")) {
                return 1;
            } else if (result.equals("H")) {
                return -1;
            } else if (result.equals("TIE")) {
                return 0;
            }
        }

        if (depth == 0) {
            return evaluateBoard(plate);
        }

        if (turn.equals("C")) {
            int bestScore = Integer.MIN_VALUE;
            outerLoop:
            for (int type = 0; type < 3; type++) {
                for (int row = 0; row < 3; row++) {
                    for (int col = 0; col < 3; col++) {
                        if (plate[type][row][col] == null) {
                            plate[type][row][col] = "X";
                            int score = predictGame(plate, a, b, "H", depth - 1);
                            plate[type][row][col] = null;

                            bestScore = Math.max(score, bestScore);

                            a = Math.max(bestScore, a); 
                            if (a >= b) {
                                break outerLoop;
                            }
                        }
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            outerLoop:
            for (int type = 0; type < 3; type++) {
                for (int row = 0; row < 3; row++) {
                    for (int col = 0; col < 3; col++) {
                        if (plate[type][row][col] == null) {
                            plate[type][row][col] = "O";
                            int score = predictGame(plate, a, b, "C", depth - 1);
                            plate[type][row][col] = null;

                            bestScore = Math.min(score, bestScore);

                            b = Math.min(bestScore, b);
                            if (b <= a) {
                                break outerLoop;
                            }
                        }
                    }
                }
            }
            return bestScore;
        }
    }

    public static int evaluateBoard(String[][][] plate) {
        int score = 0;
    
        String me = "X";
        String opponent = "O";

        if (me.equals(plate[1][1][1])) {
            score += 30;
        } else if (opponent.equals(plate[1][1][1])) {
            score -= 30;
        }
    
        int[][][] lines = generateAllWinningLines();
    
        for (int[][] line : lines) {
            int myCount = 0;
            int oppCount = 0;
            int emptyCount = 0;
    
            for (int[] pos : line) {
                String cell = plate[pos[0]][pos[1]][pos[2]];
                if (me.equals(cell)) {
                    myCount++;
                } else if (opponent.equals(cell)) {
                    oppCount++;
                } else {
                    emptyCount++;
                }
            }
            
            if (myCount == 2 && emptyCount == 1) {
                score += 100;
            } else if (oppCount == 2 && emptyCount == 1) {
                score -= 100;
            } else if (myCount == 1 && emptyCount == 2) {
                score += 10;
            } else if (oppCount == 1 && emptyCount == 2) {
                score -= 10;
            }
        }
    
        return score;
    }
}
