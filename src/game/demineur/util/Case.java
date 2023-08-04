package game.demineur.util;

public class Case {
    private boolean show;
    private int point;
    private boolean isBomb;

    public Case(boolean isBomb, int point) {
        this.isBomb = isBomb;
        this.show = false;
        this.point = point;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public String toString() {
        if (this.show) {
            if (this.isBomb) {
                return "*";
            }
            return String.valueOf(this.point);
        }
        return "x";
    }
}
