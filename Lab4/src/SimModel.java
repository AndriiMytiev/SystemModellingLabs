import utils.Consts;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

public class SimModel {
    public static void main(String[] args) {
        System.out.println("---------- Simulate ----------");

        for (int n = 100; n < 1001; n += 100) {
            Element.refreshNextId();
            ArrayList<Element> list = getV1Elements(n);

            Model model = new Model(list, false);
            System.out.println("Step #" + n / 100);
            long startTime = System.currentTimeMillis();
            model.simulate(10000);
            long endTime = System.currentTimeMillis();
            System.out.println("simulation time: " + (endTime - startTime) / 1_000.0 + "s\n");
        }
    }

    private static ArrayList<Element> getV1Elements(int n) {
        Create c = new Create(10, 1);
        c.setName("Create");
        c.setDistribution(Consts.DistributionType.exponential);

        ArrayList<Element> list = new ArrayList<>();
        list.add(c);

        Element previousElement = c;
        for (int i = 0; i < n; i++) {
            Process p = new Process(10, 1, Integer.MAX_VALUE);
            p.setName("Processor #" + (i+1));
            p.setDistribution(Consts.DistributionType.exponential);
            previousElement.setNextElement(p);
            list.add(p);
            previousElement = p;
        }
        return list;
    }

    private static ArrayList<Element> getV2Elements(int n) {
        Create c = new Create(10, 1);
        c.setName("Create");
        c.setDistribution(Consts.DistributionType.exponential);

        ArrayList<Element> list = new ArrayList<>();
        list.add(c);
        for (int i = 0; i < n; i++) {
            Process p = new Process(10, 1, Integer.MAX_VALUE);
            p.setName("Processor #" + i);
            p.setDistribution(Consts.DistributionType.exponential);

            Element randomElement = list.get(new Random().nextInt(list.size()));
            if (randomElement.getNextElementQueue() == null)
                randomElement.setNextElementQueue(new PriorityQueue<>());
            randomElement.getNextElementQueue().add(new QueueElement(p, 0));

            list.add(p);
        }
        return list;
    }
}
