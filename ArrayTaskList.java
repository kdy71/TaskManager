//package ua.edu.sumdu.j2se.Khoruzhenko_D_06.tasks;

//import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
* class ArrayTaskList.
* Array of tasks.
*/
//public class ArrayTaskList  {
//public class ArrayTaskList  extends TaskList   {
//public class ArrayTaskList  extends TaskList implements Iterable<Task>  {
public class ArrayTaskList  extends TaskList implements Iterable<Task>, Cloneable  {

    private Task [] arrTasks  = new Task[10];
    private int numberTasks = 0;
    
    /**
    * Add task to the ArrayTaskList
    * @param newTask - task for add
    */
    public void add(Task newTask) {
        if (newTask==null)   { throw new IllegalArgumentException("Task can't be null!");}  
        if (numberTasks == arrTasks.length) {
            Task tmpArrTasks[] = new Task[(size() +10)];
            for (int i = 0;  i < size();  i++) {
                tmpArrTasks[i] = this.getTask(i);
            }
            arrTasks = tmpArrTasks;
            tmpArrTasks = null;
        }
        arrTasks[size()] = newTask;
        numberTasks++;
    }
    
    
    /**
    * @return  number of tasks in the ArrayTaskList
    */
    public int size() {
//        return arrTasks.length;
        return numberTasks;
    }
    
    
    /**
    * @return Task  - element of ArrayTaskList with specified index
    * @param  index - index of task in ArrayTaskList
    * First element has index=0
    */
    public Task getTask(int index) {
        String st = "";
        if (index < 0 )      {st = st + "Index can not be negative. "; }
        if (index > size() ) {st = st + "Index out of bounds. ";       }
        if (st != "")        {throw new IllegalArgumentException(st);  } 
        return arrTasks[index];
    }


    /**
     * @task1  task for push into the ArrayTaskList with specified index
     * @param  index - index of task in ArrayTaskList
     */
    public void setTask(Task task1, int index) {
        String st = "";
        if (index < 0 )      {st = st + "Index can not be negative. "; }
        if (index > size() ) {st = st + "Index out of bounds. ";       }
        if (st != "")        {throw new IllegalArgumentException(st);  }
        arrTasks[index] = task1;
    }



    /**
    * Removing specified task from ArrayTaskList
    * @return true/false - was the removal successful or not
    * @param  task1 - task for remove
    */
    public boolean remove(Task task1) {
        int oldNumberTasks = size();
        if (oldNumberTasks == 0) { return false; }
        boolean taskDeleted = false;
        int newArrIndex = 0;
        Task tmpArrTasks [] = new Task[oldNumberTasks - 1];
        for (int i = 0; i < oldNumberTasks; i++) {
            if (!taskDeleted  &&  (task1.equals(this.getTask(i))))  {  
                taskDeleted = true;
                numberTasks--;
            } else {
                if (newArrIndex < (oldNumberTasks - 1)) {
                    tmpArrTasks[newArrIndex] = this.getTask(i);
                    newArrIndex++;
                }
            }
        }
        if (!taskDeleted) { return false; }
        this.arrTasks = tmpArrTasks;
        tmpArrTasks = null;
        return taskDeleted;
    }


    public boolean removeByIndex(int index4Del) {
        int oldNumberTasks = size();
        if (oldNumberTasks == 0) {
            return false;
        }
        boolean taskDeleted = false;
        int newArrIndex = 0;
        Task tmpArrTasks[] = new Task[oldNumberTasks - 1];
        for (int i = 0; i < oldNumberTasks; i++) {
            if (!taskDeleted && (i == index4Del)) {
                taskDeleted = true;
                numberTasks--;
            } else {
                if (newArrIndex < (oldNumberTasks - 1)) {
                    tmpArrTasks[newArrIndex] = this.getTask(i);
                    newArrIndex++;
                }
            }
        }
        if (!taskDeleted) {
            return false;
        }
        this.arrTasks = tmpArrTasks;
        tmpArrTasks = null;
        return taskDeleted;
    }

    /**
    * @return ArrayTaskList - list of tasks wich planned for execution 
    *                         between "from" and "to" times
    * @param  from - the start time search 
    * @param  to   - the end   time search 
    */
 /*   public ArrayTaskList incoming(int from, int to) {
        String stErr="";
        if ((from <0) || (to<0)) {
            throw new IllegalArgumentException("time can not be negative!");
        }
        ArrayTaskList newArrTList = new ArrayTaskList();
        int tmpTime;
        int tmpNumberTasks = size();
        for (int i = 0; i < tmpNumberTasks; i++) {
            tmpTime = this.getTask(i).nextTimeAfter(from);
            if ((tmpTime != -1)  &&  (tmpTime <= to)) {
                newArrTList.add(this.getTask(i));
            }
        }
        return newArrTList;
    }
   */ 

   
    /**
    * @return ArrayTaskList - empty instance of ArrayTaskList
    */
    public ArrayTaskList createEmpty() {
        ArrayTaskList tmpATL = new ArrayTaskList();
        return tmpATL;
    }


    /**
     *  Compare two ArrayTaskList - current and "otherObject"
     *  @return  - compare result ( equals or not )
     *  @param otherObject - task for compare
     */
    // moved to  parent class - TaskList
/*    @Override
    public boolean equals(Object otherObject) {
        if (this==otherObject) {return true;}
        if (otherObject==null) { return false; }
//        if (!(otherObject instanceof ArrayTaskList)) return false;
        if (this.getClass() != otherObject.getClass()) {return false;}

        ArrayTaskList tasks2 = (ArrayTaskList) otherObject;

        if (tasks2.size()!=this.size()) {return false;}
        int arrLen=this.size();
//        for (Task currTask:this) {
        for (int i=0; i<arrLen ; i++ ) {
            if ( ! this.getTask(i).equals(tasks2.getTask(i)) ) {  return false;  }
        }
        return true;
    }
*/

    /**
     * @return integer hashCode
     */
    @Override
    public int hashCode() {
//        int result = arrTasks != null ? Arrays.hashCode(arrTasks) : 0;
        int result=11+size();
        for (Task currTask:this) { result+=currTask.hashCode(); }
        return result;
    }


    /**
     *  Make readable string from ArrayTaskList
     *  @return  - ArrayTaskList as string
     */
    @Override
    public String toString() {
        String st;
        st = "ArrayTaskList of " +this.size()+" tasks. \n";
        for (Task currTask:this) { st+=currTask.toString()+" \n"; }
        return st;
    }


    /**
     * Creates and returns a copy of this object.
     *
     * @return a clone of this instance.
     * @throws CloneNotSupportedException if the object's class does not
     *   support the {@code Cloneable} interface. Subclasses
     *   that override the {@code clone} method can also
     *   throw this exception to indicate that an instance cannot be cloned.
     * @see Cloneable
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        ArrayTaskList cloned = (ArrayTaskList) super.clone();
        cloned.arrTasks = (Task []) this.arrTasks.clone();
        for (int i=0; i<size(); i++)
            cloned.arrTasks[i] = (Task) this.arrTasks[i].clone();
//        ArrayTaskList cloned = new ArrayTaskList();
//        for (Task currTask:this) {  cloned.add((Task) currTask.clone());  }
        return cloned;
    }


    
    /**
    * For debug
    * Print all task titles of the ArrayTaskList
    * @param  st1 - string for notes
    */
    public void printAllTitles(String st1) {
        System.out.println("------ printAllTitles(" + st1 + ") -----");
        String st;
        for (int i = 0; i < this.size(); i++) {
            st = i + " - ";
            if (this.getTask(i) == null)    { st = st + "<null>"; }
            else { st = st + (String) this.getTask(i).getTitle(); }
            System.out.println(st);
        } 
    }

    public Iterator<Task> iterator() {
//        Iterator<Task> iterator();
        return new ArrTaskListIterator();
    }


//    @Override
//    public Iterator<Task> iterator() {
    private class ArrTaskListIterator implements Iterator<Task> {
        int iterIndex = -1;
        boolean wasNext = false;


//        @Override
        public Task next() {
            if ( !hasNext() ) {throw new NoSuchElementException();} // http://docs.oracle.com/javase/1.5.0/docs/api/java/util/Iterator.html
            iterIndex++;
            wasNext=true;
            return getTask(iterIndex);
        }


        @Override
        public boolean hasNext() {
            return (iterIndex < size()-1);
        }

        @Override
        public void remove() {
            if ( !wasNext ) throw new UnsupportedOperationException("can't remove without <<next>>.");
            wasNext=false;
            removeByIndex(iterIndex);
        }
    }
}