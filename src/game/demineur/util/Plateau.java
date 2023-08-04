package game.demineur.util;

import java.util.StringJoiner;

public class Plateau {
    private final int width;
    private final int height;
    private final Case[][] plateau;
    private final double densite;
    private static final int BOMBE_VALUE = -1;

    public Plateau(int width, int height, double densite) {
        this.width = width;
        this.height = height;
        this.plateau = new Case[width][height];
        this.densite = densite;
    }

    public void initPlateau(){
        for(int l = 0; l < this.width; l++) {
            for(int c = 0; c < this.height; c ++) {
                if (Math.random() < this.densite) {
                    this.plateau[l][c] = new Case(true, BOMBE_VALUE);
                } else {
                    this.plateau[l][c] = new Case( false, 0);
                }
            }
        }

        calculNbBomb();
    }

    private void calculNbBomb() {
        for(int l = 0; l < this.width; l++) {
            for(int c = 0; c < this.height; c ++) {
                if (! this.plateau[l][c].isBomb()) {
                    this.plateau[l][c].setPoint(nbBomb(l, c));
                }
            }
        }
    }

    private int nbBomb(int li, int co) {
        int res = 0;
        for(int l = li-1; l < li+2; l++) {
            for(int c = co-1; c < co+2; c ++) {
                if ( this.isNotOutOfBount(l,c) && this.plateau[l][c].isBomb()) {
                    res += 1;
                }
            }
        }
        return res;
    }

    public void showCase(int l, int c) {
        this.plateau[l][c].setShow(true);

        if ( this.plateau[l][c].getPoint() == 0) {
            showCaseVoisin(l, c);
        }
    }

    private void showCaseVoisin(int li, int co) {
        for(int l = li-1; l < li+2; l++) {
            for(int c = co-1; c < co+2; c ++) {
                if ( this.isNotOutOfBount(l,c) && !this.plateau[l][c].isShow()) {
                    this.showCase(l,c);
                }
            }
        }
    }

    private boolean isNotOutOfBount(int l, int c) {
        return l < this.width && c < this.height && l > -1 && c > -1;
    }

    @Override
    public String toString() {
        StringJoiner s = new StringJoiner("|");
        s.add("| ");
        for (int c = 0; c < this.width; c++) {
            s.add(String.valueOf(c));
        }
        s.add("\n");

        for(int l = 0; l < this.width; l++) {
            s.add(String.valueOf(l));
            for(int c = 0; c < this.height; c ++) {
                s.add(this.plateau[l][c].toString());
            }
            s.add("\n");
        }
        return s.toString();
    }
}
