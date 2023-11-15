import utils.Consts;
import utils.FunRand;

import java.util.HashMap;
import java.util.PriorityQueue;

public class Element {
    private String name;
    private final PriorityQueue<Double> tnext;
    private double delayMean, delayDev;
    private int distribution;
    private int quantity;
    private double tcurr;
    private int state;
    private Element nextElement;
    private PriorityQueue<QueueElement> nextElementQueue;
    private HashMap<Double, Element> nextRandomElement;
    private int nextElementType;
    private int id;
    private int queue;
    private double avgLoad;

    private static int nextId = 0;

    public final int workerCount;
    public final int swapThreshold;

    public Element(double delay, int workerCount, int swapThreshold) {
        name = "Anonymous";
        tnext = new PriorityQueue<>();
        this.workerCount = workerCount;
        this.swapThreshold = swapThreshold;
        delayMean = delay;
        distribution = Consts.DistributionType.exponential;
        tcurr = 0.0;
        state = 0;
        queue = 0;
        avgLoad = 0;
        nextElement = null;
        id = nextId;
        nextId++;
        name = "element" + id;
    }

    public double getDelay() {
        switch (getDistribution()) {
            case Consts.DistributionType.exponential -> {
                return FunRand.Exp(getDelayMean());
            }
            case Consts.DistributionType.normal -> {
                return FunRand.Norm(getDelayMean(), getDelayDev());
            }
            case Consts.DistributionType.uniform -> {
                return FunRand.Unif(getDelayMean(), getDelayDev());
            }
            default -> {
                return getDelayMean();
            }
        }
    }

    public static void refreshNextId() {
        Element.nextId = 0;
    }

    public double getDelayDev() {
        return delayDev;
    }
    public void setDelayDev(double delayDev) {
        this.delayDev = delayDev;
    }
    public int getDistribution() {
        return distribution;
    }
    public void setDistribution(int distribution) {
        this.distribution = distribution;
    }
    public double getTcurr() {
        return tcurr;
    }
    public void setTcurr(double tcurr) {
        this.tcurr = tcurr;
    }
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public double getDelayMean() {
        return delayMean;
    }
    public void setDelayMean(double delayMean) {
        this.delayMean = delayMean;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getQueue() {
        return queue;
    }
    public void setQueue(int queue) {
        this.queue = queue;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Element getNextElement() {
        return nextElement;
    }
    public void setNextElement(Element nextElement) {
        this.nextElementType = NextElementType.single;
        this.nextElement = nextElement;
    }

    public PriorityQueue<QueueElement> getNextElementQueue() {
        return nextElementQueue;
    }
    public void setNextElementQueue(PriorityQueue<QueueElement> nextElementQueue) {
        this.nextElementType = NextElementType.queue;
        this.nextElementQueue = nextElementQueue;
    }
    public void addToNextElementQueue(QueueElement queueElement) {
        this.nextElementQueue.add(queueElement);
    }

    public HashMap<Double, Element> getNextRandomElementArray() {
        return nextRandomElement;
    }
    public void setNextRandomElementArray(HashMap<Double, Element> nextRandomElement) {
//        check if sum of keys is equal to 1
        double sum = 0;
        for (Double key : nextRandomElement.keySet()) {
            sum += key;
        }
        assert sum == 1;

        this.nextElementType = NextElementType.random;
        this.nextRandomElement = nextRandomElement;
    }

    public int getQuantity() {
        return quantity;
    }
    public int nextElementType(){
        return this.nextElementType;
    }

    public void inAct() {}
    public void outAct() {
        quantity++;
    }
    public void doStatistics(double delta) {}

    public double getTnext() {
        return this.tnext.peek();
    }
    public void putTnext(double tnext) {
        this.tnext.add(tnext);
    }
    public Double popTnextQueue() {
        return this.tnext.poll();
    }

    public void printResult() {
        System.out.println(getName() + " quantity = " + quantity);
    }

    public void printInfo() {
        if (tnext.peek() != Double.MAX_VALUE) {
            avgLoad = quantity / tnext.peek();
        }

        System.out.println(String.format(
                "##### %s #####",
                getName()));

        if (this instanceof Dispose) {
            System.out.println(String.format(
                    "quantity: %d",
                    quantity));
        } else {
            System.out.println(String.format(
                    "state: %d | quantity: %d | queue: %d | tnext: %5.4f | avgLoad: %.4f",
                    state, quantity, queue, tnext.peek(), avgLoad));
        }
    }
}