import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        MLinkedList linkedList = new MLinkedList<Integer>();
        int limit32 = 2147483647;
        //Add x random numbers
        for (int i = 0; i < 10; i++) {
            int randomInt = getRandomInt();
            linkedList.add(randomInt);
            System.out.println("Added to LL:" + randomInt);
        }
        readList(linkedList);
        System.out.println("LL size:" + linkedList.size());

        //Delete a random one
        int randomRemove = getRandomInt(linkedList.size());
        System.out.println("Removed position:"+randomRemove);
        System.out.println("Success:"+linkedList.remove(randomRemove));
        readList(linkedList);
        System.out.println("LL size:" + linkedList.size());

        //Set a random one
        linkedList.set(getRandomInt(linkedList.size()), 0);
        readList(linkedList);

        //Add in a random one
        linkedList.add(getRandomInt(), limit32);
        readList(linkedList);

        //Array
        System.out.println("Array:" + Arrays.toString(linkedList.toArray()));

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
