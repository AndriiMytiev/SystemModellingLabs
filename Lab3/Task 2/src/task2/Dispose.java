package task2;

public class Dispose extends Element {

    public Dispose() {
        super(0, 0, Integer.MAX_VALUE);
        super.putTnext(Double.MAX_VALUE);
    }

    @Override
    public void inAct() {
        this.outAct();
    }

    @Override
    public void outAct() {
        super.outAct();
        switch (super.nextElementType()) {
            case Consts.NextElementType.single -> {
                super.getNextElement().inAct();
            }
        }
    }

    @Override
    public void printInfo() {
        super.printInfo();
    }
}