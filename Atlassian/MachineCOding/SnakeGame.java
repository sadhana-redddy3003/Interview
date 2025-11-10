import java.util.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        //Cell cell = new Cell(4,4);
        //Snake snake = new Snake(cell);

        Game game = new Game(10, 10);
        Scanner sc = new Scanner(System.in);

        while(game.gameStatus != GameStatus.OVER){
            game.print();
            System.out.println("Enter Direction: W A S D");
            char ch=sc.next().charAt(0);

            switch (ch){
                case 'W': game.changeDirection(Direction.UP); break;
                case 'A': game.changeDirection(Direction.LEFT); break;
                case 'S': game.changeDirection(Direction.DOWN); break;
                case 'D': game.changeDirection(Direction.RIGHT); break;
            }

            game.play();

        }


    }
}

enum Direction{
    UP,
    DOWN,
    LEFT,
    RIGHT
}

class Snake{
    Deque<Cell> body;
    volatile Direction direction;


    public Snake(Cell cell){
        body = new LinkedList<>();
        addHead(cell);
        direction = Direction.RIGHT;
    }

    private void addHead(Cell head){
        body.addFirst(head);
        Cell snakeHead = body.peekFirst();
        snakeHead.status = cellStatus.SNAKE;

    }

    private  void removeTail(){
        Cell last = body.peekLast();
        last.status = cellStatus.EMPTY;
        body.pollLast();
    }

    public Cell next() {
        Cell snakeHead = body.peekFirst();
        int r = snakeHead.getRow();
        int c = snakeHead.getCol();

        System.out.print("current cel="+r+"col"+c);

        switch (direction) {
            case UP:
                r = r - 1; break;
            case DOWN:
                r = r + 1; break;
            case LEFT:
                c = c - 1; break;
            case RIGHT:
                c = c + 1; break;
        }
        System.out.print("nextcel="+r+"col"+c);

        return new Cell(r, c);
    }

    public void move(Cell cell, boolean isFood){
        addHead(cell);
        if(!isFood) removeTail();

    }

    public void changeDirection(Direction dir){
        System.out.print(" direction="+direction);
        System.out.print(" dir="+dir);
        if(direction == Direction.UP && dir == Direction.DOWN)
            return;
        if(direction == Direction.LEFT && dir == Direction.RIGHT)
            return;
        if(direction == Direction.DOWN && dir == Direction.UP)
            return;
        if(direction == Direction.RIGHT && dir == Direction.LEFT)
            return;

        System.out.print("updated direction="+direction);

        direction = dir;

    }

}
enum cellStatus{
    FOOD,
    EMPTY,
    SNAKE
}

class Cell{
    int row;
    int col;
    cellStatus status;


    public Cell(int row, int col){
        this.row = row;
        this.col =col;
        status = cellStatus.EMPTY;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public boolean isFood(){
        return status==cellStatus.FOOD;
    }

    public boolean isSnaekBody(){
        return status==cellStatus.SNAKE;
    }

}

enum GameStatus{
    OVER,
    INPROGRESS
}

class Game{
    Cell[][] board;
    int rows;
    int cols;
    Snake snake;

    GameStatus gameStatus;


    public Game(int rows, int cols){
        board = new Cell[rows][cols];
        this.rows = rows;
        this.cols = cols;
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                board[i][j] = new Cell(i,j);
            }
        }
        this.snake = new Snake(board[rows/4][cols/4]);
        gameStatus = GameStatus.INPROGRESS;
        //changeDirection(Direction.RIGHT);
        generateFood();

    }

    public void generateFood(){
        Random random = new Random();

        int r = random.nextInt(rows);
        int c= random.nextInt(cols);

        while(board[r][c].status!=cellStatus.EMPTY){
            r = random.nextInt(rows);
            c= random.nextInt(cols);


        }
        board[r][c].status = cellStatus.FOOD;

    }

    public void play(){
        Cell next = snake.next();
        int i = next.getRow();
        int j = next.getCol();

        if(!isGameOver(i,j)){
            boolean isFood = board[i][j].isFood();
            snake.move(board[i][j], isFood);
            if(isFood) generateFood();
        }else{
            gameStatus = GameStatus.OVER;
        }
        //print();

    }

    public void changeDirection(Direction direction){
        snake.changeDirection(direction);
    }

    private boolean isGameOver(int i, int j){
        if(i<0 || j<0 || i>=rows || j>=cols){
            return true;
        }
        return board[i][j].isSnaekBody();
    }

    public void print(){
        int rows = board.length;
        int cols = board[0].length;
        for(int i=0; i<rows; i++){
            System.out.println();
            for(int j=0; j<cols; j++){
               if(board[i][j].isFood()){
                   System.out.print('f');

               }else if(board[i][j].isSnaekBody()){
                   System.out.print('s');

               }else{
                   System.out.print('.');

               }
            }
        }
        System.out.println();

    }





}
