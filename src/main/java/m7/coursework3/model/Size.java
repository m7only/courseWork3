package m7.coursework3.model;

public enum Size {
    S(23),
    M(25),
    L(27),
    XL(29),
    XXL(31),
    XXXL(33);

    public static Size getSizeByNum(int num) {
        return switch (num) {
            case 23 -> S;
            case 25 -> M;
            case 27 -> L;
            case 29 -> XL;
            case 31 -> XXL;
            case 33 -> XXXL;
            default -> throw new IllegalArgumentException();
        };
    }

    private final int SIZE;

    Size(int size) {
        this.SIZE = size;
    }

    public int getSize() {
        return SIZE;
    }
}
