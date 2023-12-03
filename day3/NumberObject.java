public class NumberObject {
    int number;
    int rowPos;
    int colPos;

    public NumberObject(int number, int rowPos, int colPos) {
        this.number = number;
        this.rowPos = rowPos;
        this.colPos = colPos;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getRowPos() {
        return rowPos;
    }

    public void setRowPos(int rowPos) {
        this.rowPos = rowPos;
    }



    public int getColPos() {
        return colPos;
    }

    public void setColPos(int colPos) {
        this.colPos = colPos;
    }
}
