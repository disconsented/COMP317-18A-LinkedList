import java.util.Arrays;
import java.util.ListIterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        MLinkedList linkedList = new MLinkedList<Integer>();

        System.out.println("Is empty:" + linkedList.isEmpty());
        int limit32 = 2147483647;
        //Add x random numbers
        for (int i = 0; i < 10; i++) {
            int randomInt = getRandomInt();
            linkedList.add(randomInt);
            System.out.println("Added to LL:" + randomInt);
        }
        System.out.println("Is empty:" + linkedList.isEmpty());
        readList(linkedList);
        System.out.println("LL size:" + linkedList.size());

        //Delete a random one
        int randomRemove = getRandomInt(linkedList.size()-1);
        System.out.println("Removed position:"+randomRemove);
        System.out.println("Success:"+linkedList.remove(randomRemove));
        readList(linkedList);
        System.out.println("LL size:" + linkedList.size());

        //Contains test 1
        System.out.println("Has 0:" + linkedList.contains(0));

        //Set a random one
        System.out.println("Set random number");
        linkedList.set(getRandomInt(linkedList.size()-1), 0);
        readList(linkedList);

        //Testing contains
        System.out.println("Has 0:" + linkedList.contains(0));

        //Add in a random one
        System.out.println("Add random position");
        linkedList.add(getRandomInt(1,linkedList.size()-1), limit32);
        readList(linkedList);

        //Array
        System.out.println("Array:" + Arrays.toString(linkedList.toArray()));

        //Iterator
        ListIterator listIterator = linkedList.listIterator();
        while (listIterator.hasNext()){
            System.out.println("Iterator:"+listIterator.next());
        }


        //Iterator 2
        ListIterator listIterator2 = linkedList.listIterator(5);
        while (listIterator2.hasNext()){
            System.out.println("Iterator (partial):"+listIterator2.next());
        }

        readList(linkedList);

//        System.out.println("###QS###");
//        MLinkedList.quickSort(linkedList, 0, linkedList.size() - 1);
//        readList(linkedList);
//        System.out.println("Done");
        System.out.println("Qsort");
        linkedList.qSort();
        readList(linkedList);

    }

    private static int getRandomInt() {
        return ThreadLocalRandom.current().nextInt();
    }

    private static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    private static int getRandomInt(int max) {
        return getRandomInt(0, max);
    }

    //Quick and dirty
    private static void readList(MLinkedList linkedList){
        //Read them back
        for (int i = 0; i < linkedList.size(); i++) {
            System.out.println(
                    "Got from LL["+i+"]:" + linkedList.get(i)
            );
        }
    }
}
