import java.util.ArrayList;

public class Model {
    private final ArrayList<Element> list;
    private final boolean do_output;
    double tnext, tcurr;

    public Model(ArrayList<Element> elements, boolean do_output) {
        list = elements;
        tnext = 0.0;
        tcurr = tnext;
        this.do_output = do_output;
    }

    public void simulate(double time) {
//        starting message
        System.out.println("elements in simulation: " + list.size());

        while (tcurr < time) {
//            searching for nearest event
            tnext = Double.MAX_VALUE;
            int eventId = 0;
            for (Element e : list) {
                if (e.getTnext() < tnext) {
//                    current time
                    tnext = e.getTnext();
                    eventId = e.getId();
                }
            }

//            if (do_output) System.out.println(String.format(
//                "\n"+
//                ">>>     Event in %s     <<<\n"+
//                ">>>     time: %.4f     <<<",
//                list.get(eventId).getName(), tnext));
            for (Element e : list) {
                e.doStatistics(tnext - tcurr);
            }
//            updating the current time
            tcurr = tnext;
            for (Element e : list) {
                e.setTcurr(tcurr);
            }
//            call the outAct()
            list.get(eventId).outAct();
            for (Element e : list) {
                if (e.getTnext() == tcurr) {
                    e.outAct();
                }
            }
//            if (do_output) printInfo();
        }
        if (do_output) printResult();
//        System.out.println("The simulation has ended!\n");
    }

    public void printInfo() {
        for (Element e : list) {
            e.printInfo();
        }
    }

    public void printResult() {
        System.out.println("\n-------------RESULTS-------------");
        for (Element e : list) {
            e.printResult();
            if (e instanceof Process) {
                Process p = (Process) e;
                System.out.println(String.format(
                        "Mean length of queue = %.3f\n" +
                        "Failure probability = %.3f\n",
                        p.getMeanQueue() / tcurr,
                        p.getFailure() / (double) (p.getFailure() + p.getQuantity())));
            } else {
                System.out.println();
            }
        }
    }
}