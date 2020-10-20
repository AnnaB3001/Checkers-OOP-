package basic.pack;

public class Checker implements Figure {
    private final boolean itsBlack;

    public Checker(boolean itsBlack) {
        this.itsBlack = itsBlack;
    }

    @Override
    public String toString() {
        if(itsBlack){
            return "b";
        }
        return "w";
    }
}
