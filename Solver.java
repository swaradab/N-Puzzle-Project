import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;


public class Solver {

    private ArrayDeque<Board> Solution;
    private boolean isSolvable;
    private int solutionMoves;

    private class Node {
        int moves;
        Board board;
        int manhattan;
        Node prevNode;

        private Node(Board initial) {
            moves = 0;
            board = initial;
            manhattan = board.manhattan();
            prevNode = null;
        }

        private Node(Board initial, int moved, Node r) {
            moves = moved;
            board = initial;
            manhattan = board.manhattan();
            prevNode = r;
        }

        private Iterable<Node> neighbors(int moved) {
            ArrayDeque<Node> neighbors = new ArrayDeque<>();
            Iterable<Board> iter = board.neighbors();
            Iterator<Board> iterator1 = iter.iterator();
            Board b;
            while (iterator1.hasNext()) {
                b = iterator1.next();
                neighbors.add(new Node(b, moved, this));
            }
            return neighbors;
        }
    }

    private class comp implements Comparator<Node> {

        public int compare(Node o1, Node o2) {
            int com = Integer.compare(o1.manhattan + o1.moves, o2.manhattan + o2.moves);
            if (com == 0) return Integer.compare(o1.manhattan, o2.manhattan);
            else return com;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Please enter a valid argument");
        MinPQ<Node> pq = new MinPQ<>(new comp());
        MinPQ<Node> pq2 = new MinPQ<>(new comp());
        Node currentNode;
        Node currentNode2;
        pq.insert(new Node(initial));
        pq2.insert(new Node(initial.twin()));
        while (true) {
            currentNode = pq.delMin();
            currentNode2 = pq2.delMin();
            if (currentNode.board.isGoal()) break;
            if (currentNode2.board.isGoal()) break;
            Iterable<Node> iterable = currentNode.neighbors(currentNode.moves + 1);
            Iterator<Node> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                Node current = iterator.next();
                if (currentNode.prevNode == null || !current.board.equals(currentNode.prevNode.board)) {
                    pq.insert(current);
                }
            }
            Iterable<Node> iterable2 = currentNode2.neighbors(currentNode2.moves + 1);
            Iterator<Node> iterator2 = iterable2.iterator();
            while (iterator2.hasNext()) {
                Node current2 = iterator2.next();
                if (currentNode2.prevNode == null || !current2.board.equals(currentNode2.prevNode.board))
                    pq2.insert(current2);
            }
        }
        if (currentNode.board.isGoal()) {
            Deque<Node> trace = new ArrayDeque<>();
            Solution = new ArrayDeque<>();
            isSolvable = true;
            solutionMoves = currentNode.moves;
            while (currentNode != null) {
                trace.push(currentNode);
                currentNode = currentNode.prevNode;
            }
            while (!trace.isEmpty()) {
                Solution.add(trace.pop().board);
            }

        }
        if (currentNode2.board.isGoal()) {
            isSolvable = false;
            Solution = null;
            solutionMoves = -1;
        }
    }


    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        return solutionMoves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return Solution;
    }

    // test client (see below)
    /*
    public static void main(String[] args) {
        int[][] tiles = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tiles[i][j] = Integer.parseInt(args[i * 3 + j]);
            }
        }
        Board board = new Board(tiles);
        Solver s = new Solver(board);
        if (s.isSolvable()) StdOut.println("yes");
        if (!s.isSolvable()) StdOut.println("no");
        StdOut.print(s.moves());

    }

     */


}
