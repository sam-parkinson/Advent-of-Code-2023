import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class PipeMaze {
    char[][][] maze;
    int[] start;
    int[] end;
    int maxSteps;
    int insideCount;

    public PipeMaze(String address) {
        parseInput(address);
        this.start = findStart();
        findEnd(start);
        findInsideCount();
    }

    public int getMaxSteps() {
        return this.maxSteps;
    }

    public int getInsideCount() {
        return this.insideCount;
    }

    private void parseInput(String address) {
        ArrayList<String> arr = new ArrayList<String>();

        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {
                arr.add(stdin.nextLine());
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        this.maze = new char[arr.size()][][];
        for (int i = 0; i < maze.length; i++) {
            char[] charArr = arr.get(i).toCharArray();

            char[][] lineArr = new char[charArr.length][];

            for (int j = 0; j < lineArr.length; j++) {
                char[] position = new char[] {charArr[j], 'f'};
                lineArr[j] = position;
            }

            maze[i] = lineArr;
        }
    }

    private int[] findStart() {
        int[] result = new int[2];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze.length; j++) {
                if (maze[i][j][0] == 'S') {
                    result[0] = i;
                    result[1] = j;
                    maze[i][j][1] = 't';
                    break;
                }    
            }
        }
        return result;
    }

    private void findEnd(int[] start) {
        // y = 0; x = 1
        int[] alpha = new int[2];
        int[] beta = new int[2];

        // u, r, l, d
        char alphaDir = ' ';
        char betaDir = ' ';

        int sY = start[0];
        int sX = start[1];

        // find two valid pipes adjacent to start
        if (sY > 0) {
            if (maze[sY - 1][sX][0] == '|' || maze[sY - 1][sX][0] == 'F' || maze[sY - 1][sX][0] == '7') {
                alpha = new int[] {sY - 1, sX};
                alphaDir = 'u';
            }        
        }
        if (sX > 0) {
            if (maze[sY][sX - 1][0] == 'F' || maze[sY][sX - 1][0] == 'L' || maze[sY][sX - 1][0] == '-') {
                if (alphaDir == ' ') {
                    alpha = new int[] {sY, sX - 1};
                    alphaDir = 'l';
                } else {
                    beta = new int[] {sY, sX - 1};
                    betaDir = 'l';
                }
            }
        }
        if (sY < maze.length - 1) {
            if (maze[sY + 1][sX][0] == '|' || maze[sY + 1][sX][0] == 'J' || maze[sY + 1][sX][0] == 'L') {
                if (alphaDir == ' ') {
                    alpha = new int[] {sY + 1, sX};
                    alphaDir = 'd';
                } else {
                    beta = new int[] {sY + 1, sX};
                    betaDir = 'd';
                }
            }    
        }
        if (sX < maze[0].length - 1) {
            if (maze[sY][sX + 1][0] == 'J' || maze[sY][sX + 1][0] == '7' || maze[sY][sX - 1][0] == '-') {
                beta = new int[] {sY, sX + 1};
                betaDir = 'r';
            }
        }

        this.maxSteps = 0;

        while (maze[alpha[0]][alpha[1]][1] == 'f') {
            maxSteps++;
            alphaDir = scanPipe(alpha, alphaDir);
            betaDir = scanPipe(beta, betaDir);
        }
        maze[alpha[0]][alpha[1]][1] = 't'; 
    }

    private char scanPipe(int[] position, char direction) {
        maze[position[0]][position[1]][1] = 't';
        char c = maze[position[0]][position[1]][0];

        switch (c) {
            case '7':
                if (direction == 'u') {
                    position[1]--;
                    return 'l';
                } else {
                    position[0]++;
                    return 'd';
                }
            case 'F':
                if (direction == 'u') {
                    position[1]++;
                    return 'r';
                } else {
                    position[0]++;
                    return 'd';
                }
            case 'J':
                if (direction == 'd') {
                    position[1]--;
                    return 'l';
                } else {
                    position[0]--;
                    return 'u';
                }
            case 'L':
                if (direction == 'd') {
                    position[1]++;
                    return 'r';
                } else {
                    position[0]--;
                    return 'u';
                }
            case '-':
                if (direction == 'r') {
                    position[1]++;
                    return 'r';
                } else {
                    position[1]--;
                    return 'l';
                }
            case '|':
                if (direction == 'd') {
                    position[0]++;
                    return 'd';
                } else {
                    position[0]--;
                    return 'u';
                }
            default:
                return ' ';
        }    
    }

    private void findInsideCount() {
        this.insideCount = 0;

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j][1] == 'f')
                    findInside(i, j);
            }
        }
    }

    private void findInside(int y, int x) {
        char rail = ' ';
        int count = 0;
        for (int i = y; i >= 0; i--) {
            if (maze[i][x][1] == 't') {
                if (maze[i][x][0] == '-')
                    count++;
                else if (rail == ' ') { 
                    if (maze[i][x][0] == 'J' || maze[i][x][0] == 'L') {
                        rail = maze[i][x][0];
                    }
                } else if (rail == 'J') {
                    if (maze[i][x][0] == 'F') {
                        rail = ' ';
                        count++;
                    } else if (maze[i][x][0] == '7')
                        rail = ' ';
                } else if (rail == 'L') {
                    if (maze[i][x][0] == '7') {
                        rail = ' ';
                        count++;
                    } else if (maze[i][x][0] == 'F')
                        rail = ' ';
                }
            }            
        }
        insideCount += count % 2;
    }
}
