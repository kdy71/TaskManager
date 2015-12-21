//package ua.edu.sumdu.j2se.Khoruzhenko_D_06.tasks;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
* LinkedTaskList elements.
*/
class LinkedTask{
    Task task;
    LinkedTask prevLTask;
    LinkedTask nextLTask;
    
    /**
    * constructor for LinkedTask
    */
    public LinkedTask (Task task1) {
        task=task1;
    }
}


/**
* class LinkedTaskList.
* Linked list of tasks.
*/
//public class LinkedTaskList {
//public class LinkedTaskList extends TaskList  {
public class LinkedTaskList extends TaskList implements Iterable<Task>, Cloneable {

    private LinkedTask firstLTask = null;
    private LinkedTask lastLTask = null;
    private int numberTasks = 0;
    
    
    /**
    * Add task to the LinkedTaskList
    * @param newTask - task for add
    */
    public void add(Task newTask) {
        if (newTask==null)   { throw new IllegalArgumentException("Task can't be null!");}  
        LinkedTask newLT = new LinkedTask(newTask);
        if(size()==0) {firstLTask = newLT;}
        if (lastLTask != null) {lastLTask.nextLTask = newLT;}
        newLT.prevLTask = lastLTask;
        lastLTask = newLT;
        numberTasks++;
    }


    /**
    * @return  number of tasks in the LinkedTaskList
    */
    public int size() {
//        return arrTasks.length;
        return numberTasks;
    }    

    /**
    * @return Task  - element of LinkedTaskList with specified index
    * @param  index - index of task in LinkedTaskList
    * First element has index=0
    */
    public Task getTask(int index) {
        String st = "";
        if (index < 0 )      {st = st + "Index can not be negative. "; }
        if (index > size() ) {st = st + "Index out of bounds. ";       }
//        if (st != "")        {throw new IllegalArgumentException(st);  }
        if (! st.equals(""))        {throw new IllegalArgumentException(st);  }
        LinkedTask tmpLT = firstLTask;
        for (int i=0;  i<index;  i++) {
            tmpLT = tmpLT.nextLTask;
        }
        return tmpLT.task; 
    }


    /**
    * Removing specified task from LinkedTaskList
    * @return true/false - was the removal successful or not
    * @param  task1 - task for remove
    */
    public boolean remove(Task task1) {
        if (task1 == null) {throw new IllegalArgumentException("Task for remove can't be null!!!");}
        LinkedTask tmpLT = firstLTask;
        boolean result = false;
        for (int i=0; i<numberTasks; i++) {
            if (tmpLT.task.equals(task1)) {
                removeLinkedTask(tmpLT);
/*                if (tmpLT.prevLTask == null)  {firstLTask = tmpLT.nextLTask;}
                else          { tmpLT.prevLTask.nextLTask = tmpLT.nextLTask;}
                if (tmpLT.nextLTask == null)  {lastLTask = tmpLT.prevLTask;}
                else          {tmpLT.nextLTask.prevLTask = tmpLT.prevLTask;}
                numberTasks--;  */
                result = true;
                break;
            }
            tmpLT = tmpLT.nextLTask;
        }
        return result;
    }


    /**
     * Removing a task from LinkedList
     * @param lTask1 - LinkedTask for remove
     * @return  true if removing was success
     */
    public boolean removeLinkedTask(LinkedTask lTask1) {
        if (lTask1 == null) {throw new IllegalArgumentException("LinkedTask for remove can't be null!!!");}
        if (lTask1.prevLTask == null)  {firstLTask = lTask1.nextLTask;}
        else          { lTask1.prevLTask.nextLTask = lTask1.nextLTask;}
        if (lTask1.nextLTask == null)  {lastLTask = lTask1.prevLTask;}
        else          {lTask1.nextLTask.prevLTask = lTask1.prevLTask;}
        numberTasks--;
        return true;
    }


    /**
    * @return LinkedTaskList - list of tasks which planned for execution 
    *                         between "from" and "to" times
    * @param  from - the start time search 
    * @param  to   - the end   time search 
    */
 /*   public LinkedTaskList incoming(int from, int to) {
        String stErr="";
        if ((from <0) || (to<0)) {
            throw new IllegalArgumentException("time can not be negative!");
        }
        LinkedTaskList newLTL = new LinkedTaskList();
        int tmpTime;
        int tmpNumberTasks = size();
        for (int i = 0; i < tmpNumberTasks; i++) {
            tmpTime = this.getTask(i).nextTimeAfter(from);
            if ((tmpTime != -1)  &&  (tmpTime <= to)) {
                newLTL.add(this.getTask(i));
            }
        }
        return newLTL;
    }
   */ 

   
    /**
    * @return LinkedTaskList - empty instance of LinkedTaskList
    */
    public LinkedTaskList createEmpty() {
//        LinkedTaskList tmpLTL = new LinkedTaskList();
//        return tmpLTL;
        return new LinkedTaskList();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator for LinkedTaskList.
     */
//    @Override
    public Iterator<Task> iterator() {
        return new LTLIterator();
    }

    /**
     * class LTLIterator - describes an iterator for Linked Task List
     * methods: next, hasNext, remove
     */
    public class LTLIterator implements Iterator<Task> {
        boolean wasNext = false;
        LinkedTask currentLTask = firstLTask;

        /**
         * @return next task from Linked Task List
         */
        public Task next() {
//            if ( !hasNext() ) {throw new UnsupportedOperationException();}
            if ( !hasNext() ) {throw new NoSuchElementException();} // http://docs.oracle.com/javase/1.5.0/docs/api/java/util/Iterator.html
            wasNext = true;
            Task task4Return = currentLTask.task;
            currentLTask = currentLTask.nextLTask;
            return task4Return;
        }


        /**
         * @return true if LinkedTaskList has next element
         */
        @Override
        public boolean hasNext() {
//            return ( currentLTask.nextLTask != null );
            return ( currentLTask != null );
        }


        /**
         * Removing from LinkedTaskList last returned task
         */
        @Override
        public void remove() {
//            if (!wasNext)  {throw new UnsupportedOperationException("You have to call <<next>> before.");}
            if (!wasNext)  {throw new IllegalStateException("You have to call <<next>> before.");} // http://docs.oracle.com/javase/1.5.0/docs/api/java/util/Iterator.html
//            LinkedTask lTask4Del;
//            if (currentLTask==null) {lTask4Del=lastLTask;}
//            else {lTask4Del=currentLTask.prevLTask;}
            LinkedTask lTask4Del=((currentLTask==null) ?  lastLTask  :  currentLTask.prevLTask) ;
            removeLinkedTask(lTask4Del);
            wasNext=false;
        }
    }


    /**
     * @return integer hashCode
     */
    @Override
    public int hashCode() {
//        int result = arrTasks != null ? Arrays.hashCode(arrTasks) : 0;
        int result=13+size();
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
        st = "LinkedTaskList of " +this.size()+" tasks. \n";
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
        LinkedTaskList cloned = (LinkedTaskList) super.clone();

        LinkedTask tmpOrigLT = this.firstLTask;
        LinkedTask tmpLT = new LinkedTask((Task) this.firstLTask.task.clone());
        cloned.firstLTask = tmpLT;
        cloned.lastLTask = tmpLT;
        cloned.numberTasks=1;
        for (int i=1; i<numberTasks; i++) {
            tmpOrigLT = tmpOrigLT.nextLTask;
            cloned.add((Task) tmpOrigLT.task.clone());
        }
        return cloned;
    }

}