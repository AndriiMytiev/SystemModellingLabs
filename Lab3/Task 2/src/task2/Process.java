package task2;

import java.util.*;

public class Process extends Element {
    private int maxqueue, failure;
    private double meanQueue;

    public double stateSum;
    public int swapCount;
    public LinkedList<Patient> nextPatients;
    public int currentPatientType;

    public Process(double delay, int workerCount, int swapThreshold) {
        super(delay, workerCount, swapThreshold);
        maxqueue = Integer.MAX_VALUE;
        meanQueue = 0.0;
        nextPatients = new LinkedList<>();
        super.putTnext(Double.MAX_VALUE);
    }

    @Override
    public void inAct() {
        if (super.getState() < super.workerCount) {
            double delay = 0;

            super.setState(super.getState() + 1);
            currentPatientType = nextPatients.peek().type;

            if (super.getName().equals("Reception")) {
                switch (currentPatientType) {
                    case Consts.PatientTypes.toHospitalRoom -> delay = 15;
                    case Consts.PatientTypes.toLab2 -> delay = 40;
                    case Consts.PatientTypes.toLab3 -> delay = 30;
                }
            } else {
                delay = super.getDelay();
            }
            super.putTnext(super.getTcurr() + delay);
        } else {
            if (getQueue() < getMaxqueue())
                setQueue(getQueue() + 1);
            else
                failure++;
        }
    }

    @Override
    public void outAct() {
        super.outAct();
        super.setState(super.getState() - 1);
        if (getQueue() > 0) {
            setQueue(getQueue() - 1);
            super.setState(super.getState() + 1);
            super.putTnext(super.getTcurr() + super.getDelay());
        }

        assert nextPatients.size() != 0;
        Patient nextPatient = nextPatients.poll();
        Element nextElement = null;

        switch (super.nextElementType()) {
            case Consts.NextElementType.single ->
                    nextElement = super.getNextElement();
            case Consts.NextElementType.typed ->
                    nextElement = super.getNextTypedElementArray().get(nextPatient.type);
        }

//        send patient to the next process
        if (nextElement instanceof Process) {
            Process nextProcess = (Process) nextElement;
            if (super.getName().equals("Take analyses")
                    && nextPatient.type == Consts.PatientTypes.toLab2) {
                nextPatient.type = Consts.PatientTypes.toHospitalRoom;
                nextProcess.nextPatients.add(nextPatient);
            }
            else
                nextProcess.nextPatients.add(nextPatient);
            nextProcess.inAct();
        } else if (nextElement instanceof Dispose) {
            nextPatient.timeInLine = super.getTcurr() - nextPatient.startTime;
            System.out.println(String.format(
                    ">>>     Patient #%d spent %.4f time in the hospital     <<<",
                    nextPatient.id, nextPatient.timeInLine));
            nextElement.inAct();
        }
        super.popTnextQueue();
    }

    public int getFailure() {
        return failure;
    }

    public int getMaxqueue() {
        return maxqueue;
    }

    public void setMaxqueue(int maxqueue) {
        this.maxqueue = maxqueue;
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("failure: " + this.getFailure());
    }

    @Override
    public void doStatistics(double delta) {
        stateSum += delta * getState();
        meanQueue = getMeanQueue() + super.getQueue() * delta;
    }

    public double getMeanQueue() {
        return meanQueue;
    }
}