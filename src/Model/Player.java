package Model;

import java.util.LinkedList;

public class Player {
    private String username;
    private boolean isMyTurn;
    private boolean saidUNO = false;
    private LinkedList<UNOCard> myCards;
    private int playedCards;

    /* 构造函数 */

    public Player(String username, boolean isMyTurn, LinkedList<UNOCard> myCards, int playedCards) {
        this.username = username;
        this.isMyTurn = isMyTurn;
        this.myCards = myCards;
        this.playedCards = playedCards;
    }

    /* getter & setter */

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isMyTurn() {
        return isMyTurn;
    }

    public void setMyTurn(boolean myTurn) {
        isMyTurn = myTurn;
    }

    public boolean isSaidUNO() {
        return saidUNO;
    }

    public void setSaidUNO(boolean saidUNO) {
        this.saidUNO = saidUNO;
    }

    public LinkedList<UNOCard> getMyCards() {
        return myCards;
    }

    public void setMyCards(LinkedList<UNOCard> myCards) {
        this.myCards = myCards;
    }

    public int getPlayedCards() {
        return playedCards;
    }

    public void setPlayedCards(int playedCards) {
        this.playedCards = playedCards;
    }

    /* 成员方法 */
    public void playCard() {
        // todo
    }

    public void drawCard() {
        // todo
    }

    public void sayUNO() {
        // todo
    }

}
