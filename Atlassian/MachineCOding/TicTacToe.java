import java.util.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        Player p1 = new Player("Sadhana", 'X');
        Player p2 = new Player("swaroop", 'O');
        Game game = new Game(p1, p2);
        Scanner sc = new Scanner(System.in);

        while(game.getStatus()!=GameStatus.WON && game.getStatus()!=GameStatus.DRAW){
            System.out.println(game.getCurrPlayerName() +" please enter row and col");
            int x = sc.nextInt();
            int y = sc.nextInt();
            game.play(x,y);

        }

        if(game.getStatus()==GameStatus.WON){
            System.out.println(game.getCurrPlayerName()+" wonn the game");
        }
        if(game.getStatus()==GameStatus.DRAW){
            System.out.println("DRAW match");
        }


    }
}

enum GameStatus{
    START,
    INVALID,
    INPROGRESS,
    WON,
    DRAW
}

class Board{
    char[][] board;
    public Board(){
        board = new char[3][3];
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                board[i][j]= '_';
            }
        }

    }

    public void print(){
        for(int i=0; i<3; i++){
            System.out.println();
            for(int j=0; j<3; j++){
               System.out.print(board[i][j]);
            }
        }
        System.out.println();

    }

    public boolean isValidMove(int i, int j){
        if(i<0 || j<0 || i>=3 || j>=3 || board[i][j]!='_')
            return false;

        return true;
    }

    public void move(int i, int j, Player p ){
        board[i][j] = p.symbol;

    }

    public boolean isWinner(){
        if(board[0][0]!='_' && board[0][0]==board[1][1] && board[1][1]==board[2][2])
            return true;
        if(board[0][2]!='_' && board[0][2]==board[1][1] && board[1][1]==board[2][0])
            return  true;

        for(int i=0; i<3; i++){
            if(board[i][0]!='_' && board[i][0]==board[i][1] && board[i][1]==board[i][2])
                return true;
            if(board[0][i]!='_' && board[0][i]==board[1][i] && board[1][i]==board[2][i])
                return  true;

        }

        return false;

    }

    public boolean isGameOver(){
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
               if(board[i][j]=='_') return false;
            }
        }
        return true;

    }
}

class Player{
    String name;
    char symbol;
    public  Player(String name, char symbol){
     this.name = name;
     this.symbol = symbol;
    }
}

class Game{
    Player p1;
    Player p2;
    Player currPlayer;

    Board board;

    GameStatus status;

    public GameStatus getStatus(){
        return status;
    }
    public Game(Player p1, Player p2){
        this.p1 =p1;
        this.p2=p2;
        currPlayer =p1;
        board = new Board();
        status = GameStatus.START;
        board.print();
    }

    public void switchPlayer(){
        currPlayer = (currPlayer == p1)? p2: p1;

    }


    public String getCurrPlayerName() {
        return currPlayer.name;
    }

    void play(int x, int y){
            if(!board.isValidMove(x,y)){
                System.out.println("Invalid Move!");
                status = GameStatus.INVALID;
                return;
            }

            board.move(x,y,currPlayer);
            board.print();

            if(board.isWinner()){
                System.out.println(currPlayer.name + "deom codeWins!");
                status = GameStatus.WON;
                return;
            }

            if(board.isGameOver()){
                System.out.println("from Draw!!");
                status = GameStatus.DRAW;
                return;
            }
        status = GameStatus.INPROGRESS;
        switchPlayer();
    }


}
