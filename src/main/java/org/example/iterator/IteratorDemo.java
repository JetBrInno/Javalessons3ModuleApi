package org.example.iterator;

import java.util.List;
import org.instancio.Instancio;

public class IteratorDemo {

    public static void main(String[] args) {
        List<Integer> list = getDataFromDb();
        System.out.println(list);
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }

        MyIterator iterator = new MyIterator(list);

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }
    }

    private static List<Integer> getDataFromDb() {
        // представляем, что это данные из БД
        return Instancio.createList(Integer.class);
    }
}
