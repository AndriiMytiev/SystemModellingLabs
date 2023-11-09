package task2;

import java.util.Random;

public class Create extends Element {
    private double lastPatientArrivalTime = 0;

    public Create(double delay, int workerCount) {
        super(delay, workerCount, Integer.MAX_VALUE);
        super.putTnext(0.0);
    }

    @Override
    public void outAct() {
        super.outAct();
        super.putTnext(super.getTcurr() + super.getDelay());

        Patient patient = new Patient(
                super.getQuantity(), new Random().nextDouble(), super.getTcurr());
        System.out.println(String.format(
                ">>>     Patient #%d arrived after %.4f time since the last one     <<<",
                patient.id, super.getTcurr() - lastPatientArrivalTime));
        lastPatientArrivalTime = super.getTcurr();

        switch (super.nextElementType()) {
            case Consts.NextElementType.single -> {
                Process nextElement = (Process) super.getNextElement();
                nextElement.nextPatients.add(patient);
                nextElement.inAct();
            }
        }
        super.popTnextQueue();
    }
}
