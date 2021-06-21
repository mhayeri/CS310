package data_structures.prog_3;

public class MazeSolver {
    private MazeGrid grid;
    private Queue<GridCell> myQueue;
    private Stack<GridCell> myStack;
    private int dimension;

    // The constructor. Takes a single argument, the number of rows and columns in
    // the grid. Suggested values are 25..50
    public MazeSolver(int dimension) {
        grid = new MazeGrid(this, dimension);
        myQueue = new Queue<GridCell>();
        myStack = new Stack<GridCell>();
        this.dimension = dimension;
    }

    // This method runs the breadth first traversal, and marks each reachable cell
    // in the grid with its distance from the origin Laurie Segall
    public void mark() {

        // enqueue the first cell
        GridCell firstCell = grid.getCell(0, 0);
        firstCell.setDistance(0);
        myQueue.enqueue(firstCell);
        grid.markDistance(firstCell);

        while (!myQueue.isEmpty()) {
            GridCell temp = myQueue.dequeue(); // every value dequeued is stored in temp
            int x = temp.getX(); // Information about the current dequeued cell
            int y = temp.getY();
            int distance = temp.getDistance();

            // Check for the neighboring cells and enqueue them if it is valid and not
            // visited
            // Mark the valid cells with their distance from the starting cell.
            // Check all possible directions: north, south, east, west of current cell

            // Check the North direction
            GridCell neighbor = grid.getCell(x - 1, y);
            if (grid.isValidMove(neighbor) && !neighbor.wasVisited()) {
                neighbor.setDistance(distance + 1);
                grid.markDistance(neighbor);
                myQueue.enqueue(neighbor);
            }

            // Check the East direction
            neighbor = grid.getCell(x, y + 1);
            if (grid.isValidMove(neighbor) && !neighbor.wasVisited()) {
                neighbor.setDistance(distance + 1);
                grid.markDistance(neighbor);
                myQueue.enqueue(neighbor);
            }

            // Check the South direction
            neighbor = grid.getCell(x + 1, y);
            if (grid.isValidMove(neighbor) && !neighbor.wasVisited()) {
                neighbor.setDistance(distance + 1);
                grid.markDistance(neighbor);
                myQueue.enqueue(neighbor);
            }

            // Check the West direction
            neighbor = grid.getCell(x, y - 1);
            if (grid.isValidMove(neighbor) && !neighbor.wasVisited()) {
                neighbor.setDistance(distance + 1);
                grid.markDistance(neighbor);
                myQueue.enqueue(neighbor);
            }

        } // end of while loop

    } // end of mark method

    /*
     * Does part two, indicates in the GUI the shortest path found distance =
     * terminalCell.getDistance(); if(distance == -1) return false; --> unreachable,
     * puzzle has no solution push terminal cell onto the stack while(distance != 0)
     * { get distance from each cell adjacent to the top of the stack select the
     * cell with smallest distance and push on the stack } while( stack is not empty
     * ) { pop grid cell off the stack and mark it }
     * 
     */
    public boolean move() {
        GridCell terminalCell = grid.getCell(dimension - 1, dimension - 1);
        if (terminalCell.getDistance() == -1) {
            return false;
        }

        // push the terminal cell to the stack
        myStack.push(terminalCell);

        while (myStack.peek().getDistance() != 0) { // Loop until you get to the origin
            GridCell c = myStack.peek();
            int x = c.getX();
            int y = c.getY();
            int distance = c.getDistance();

            // Check the North direction
            GridCell neighbor = grid.getCell(x - 1, y);
            if ((grid.isValidMove(neighbor)) && (neighbor.getDistance() < distance)) {
                myStack.push(neighbor);
                continue;
            }

            // Check the East direction
            neighbor = grid.getCell(x, y + 1);
            if ((grid.isValidMove(neighbor)) && (neighbor.getDistance() < distance)) {
                myStack.push(neighbor);
                continue;
            }

            // Check the South direction
            neighbor = grid.getCell(x + 1, y);
            if ((grid.isValidMove(neighbor)) && (neighbor.getDistance() < distance)) {
                myStack.push(neighbor);
                continue;
            }

            // Check the West direction
            neighbor = grid.getCell(x, y - 1);
            if ((grid.isValidMove(neighbor)) && (neighbor.getDistance() < distance)) {
                myStack.push(neighbor);
                continue;
            }
        }

        while (!myStack.isEmpty()) {
            grid.markMove(myStack.pop()); // Marks the path of the stack which contains a shortest path
        }
        return true;
    }

    // Reinitializes the puzzle. Clears the stack and queue (calls makeEmpty())
    public void reset() {
        myStack.makeEmpty();
        myQueue.makeEmpty();
    }

    public static void main(String[] args) {
        new MazeSolver(25);
    }

} // end of MazeSolver class