public class AsterixObject {
    int position;
    int numberOfNeighbours;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    int value;
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getNumberOfNeighbours() {
        return numberOfNeighbours;
    }

    public void setNumberOfNeighbours(int numberOfNeighbours) {
        this.numberOfNeighbours = numberOfNeighbours;
    }

    public AsterixObject(int position, int numberOfNeighbours, int value) {
        this.position = position;
        this.numberOfNeighbours = numberOfNeighbours;
        this.value = value;
    }
}
