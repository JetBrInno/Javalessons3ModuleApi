package org.example.iterator;

import java.util.Collection;
import java.util.List;

public class MyIterator {

    private Collection<Integer> data;

    private int index;

    public MyIterator(Collection<Integer> data){
        this.data = data;
        this.index = -1;
    }

    public Integer next(){
        index++;
        return (Integer)data.toArray()[index];
    }

    public boolean hasNext(){
        try {
            Integer i = (Integer) data.toArray()[index + 1];
            return true;
        }
        catch (IndexOutOfBoundsException exception)
        {
            return false;
        }
    }
}
