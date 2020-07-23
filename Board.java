import java.util.ArrayDeque;

public class Board {
    private int[][] board;
    private int n;

    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles[0].length;
        board = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = tiles[i][j];
            }
        }

    }

    private void exchange(int x1, int y1, int x2, int y2) {
        int temp = board[x1][y1];
        board[x1][y1] = board[x2][y2];
        board[x2][y2] = temp;
    }

    // string representation of this board
    public String toString() {
        String s = n + "\n";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s = s + board[i][j] + " ";
            }
            s = s + "\n";
        }
        return s;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int[][] Goal = new int[n][n];
        int tile = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == n - 1 && i == n - 1) {
                    Goal[i][j] = 0;
                    break;
                }
                Goal[i][j] = tile;
                tile++;
            }
        }
        int ham = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != Goal[i][j] && board[i][j] != 0) ham++;
            }
        }
        return ham;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int[][] Goal = new int[n][n];
        int tile = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == n - 1 && i == n - 1) {
                    Goal[i][j] = 0;
                    break;
                }
                Goal[i][j] = tile;
                tile++;
            }
        }
        int manhattan = 0;
        int m;
        int p;
        int j;
        for (int i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                for (m = 0; m < n; m++) {
                    for (p = 0; p < n; p++) {
                        if (Goal[m][p] == board[i][j] && Goal[m][p] != 0) {
                            manhattan = manhattan + Math.abs(m - i) + Math.abs(p - j);
                            break;
                        }
                        if (p == n - 1) break;
                    }
                    if (Goal[m][p] == board[i][j]) break;
                    if (m == n - 1) break;
                }
            }

        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?

    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() != Board.class) return false;
        Board o2 = (Board) y;
        if (o2.dimension() != dimension()) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != o2.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }


    private ArrayDeque<Board> neighbouring() {
        ArrayDeque<Board> queue = new ArrayDeque<>();
        int i = 0;
        int j = 0;
        for (; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (board[i][j] == 0) break;
                if (j == n - 1) break;
            }
            if (board[i][j] == 0) break;
        }
        if (i == 0) {
            if (j == 0) {
                Board board1 = new Board(board);
                board1.exchange(i, j, i, j + 1);
                queue.add(board1);
                Board board2 = new Board(board);
                board2.exchange(i, j, i + 1, j);
                queue.add(board2);
            } else if (j == n - 1) {
                Board board1 = new Board(board);
                board1.exchange(i, j, i, j - 1);
                queue.add(board1);
                Board board2 = new Board(board);
                board2.exchange(i, j, i + 1, j);
                queue.add(board2);
            } else {
                Board board1 = new Board(board);
                board1.exchange(i, j, i, j - 1);
                queue.add(board1);
                Board board2 = new Board(board);
                board2.exchange(i, j, i, j + 1);
                queue.add(board2);
                Board board3 = new Board(board);
                board3.exchange(i, j, i + 1, j);
                queue.add(board3);
            }


        } else if (i == n - 1) {
            if (j == 0) {
                Board board1 = new Board(board);
                board1.exchange(i, j, i - 1, j);
                queue.add(board1);
                Board board2 = new Board(board);
                board2.exchange(i, j, i, j + 1);
                queue.add(board2);
            } else if (j == n - 1) {
                Board board1 = new Board(board);
                board1.exchange(i, j, i, j - 1);
                queue.add(board1);
                Board board2 = new Board(board);
                board2.exchange(i, j, i - 1, j);
                queue.add(board2);
            } else {
                Board board1 = new Board(board);
                board1.exchange(i, j, i, j - 1);
                queue.add(board1);
                Board board2 = new Board(board);
                board2.exchange(i, j, i, j + 1);
                queue.add(board2);
                Board board3 = new Board(board);
                board3.exchange(i, j, i - 1, j);
                queue.add(board3);
            }

        } else if (j == 0) {
            Board board1 = new Board(board);
            board1.exchange(i, j, i, j + 1);
            queue.add(board1);
            Board board2 = new Board(board);
            board2.exchange(i, j, i - 1, j);
            queue.add(board2);
            Board board3 = new Board(board);
            board3.exchange(i, j, i + 1, j);
            queue.add(board3);
        } else if (j == n - 1) {
            Board board1 = new Board(board);
            board1.exchange(i, j, i, j - 1);
            queue.add(board1);
            Board board2 = new Board(board);
            board2.exchange(i, j, i - 1, j);
            queue.add(board2);
            Board board3 = new Board(board);
            board3.exchange(i, j, i + 1, j);
            queue.add(board3);

        } else {
            Board board1 = new Board(board);
            board1.exchange(i, j, i, j - 1);
            queue.add(board1);
            Board board2 = new Board(board);
            board2.exchange(i, j, i, j + 1);
            queue.add(board2);
            Board board3 = new Board(board);
            board3.exchange(i, j, i - 1, j);
            queue.add(board3);
            Board board4 = new Board(board);
            board4.exchange(i, j, i + 1, j);
            queue.add(board4);
        }
        return queue;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayDeque<Board> neighbours = neighbouring();
        return neighbours;
    }

/*
    public void print() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                StdOut.print(Board[i][j]);
                StdOut.print(" ");
            }
            StdOut.println();
        }
        StdOut.println();
    }





    public void Goalprint() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                StdOut.print(Goal[i][j]);
                StdOut.print(" ");
            }
            StdOut.println();
        }
    }


     */

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twinboard = new Board(board);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (board[i][j] != 0 && board[i][j + 1] != 0) {
                    twinboard.exchange(i, j, i, j + 1);
                    return twinboard;
                }
            }
        }
        return twinboard;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tiles[i][j] = Integer.parseInt(args[i * 3 + j]);
            }
        }
        Board board = new Board(tiles);
        /*
         int[][] tiles2 = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tiles2[i][j] = Integer.parseInt(args[9 + (i * 3 + j)]);
            }
        }
        Board board2 = new Board(tiles2);
        if (board2.equals(board)) StdOut.println("true");

        board.exchange(0, 0, 0, 1);
        Iterable<Board> iter = board.neighbors();
        Iterator<Board> iterator1 = iter.iterator();
        Board b;
        while (iterator1.hasNext()) {
            StdOut.println("41");
            b = iterator1.next();
            b.print();
        }

         */

    }

}


