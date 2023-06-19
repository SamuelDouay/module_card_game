package dutch.enums;

public enum CardColor {
    HEART("COEUR"), DIAMOND("CARREAU"), CLUB("TREFLE"), SPADE("PIQUE");

    private String value;

    CardColor(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
