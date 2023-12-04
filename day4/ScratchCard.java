public class ScratchCard {

    int gameNumber;
    int numberOfWins;
    boolean wasProcessed;

    public ScratchCard(int gameNumber, int numberOfWins, boolean wasProcessed) {
        this.gameNumber = gameNumber;
        this.numberOfWins = numberOfWins;
        this.wasProcessed = wasProcessed;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public boolean isWasProcessed() {
        return wasProcessed;
    }

    public void setWasProcessed(boolean wasProcessed) {
        this.wasProcessed = wasProcessed;
    }
}
