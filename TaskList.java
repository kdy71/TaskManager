//package ua.edu.sumdu.j2se.Khoruzhenko_D_05.tasks;
//import java.util.Date;

/**
* class ArrayTaskList.
* Array of tasks.
*/
public abstract class TaskList {
//public abstract class TaskList implements Cloneable {

    public abstract void add(Task newTask);
    public abstract int size();
    public abstract Task getTask(int index) ;
    public abstract boolean remove(Task task1);
    public abstract TaskList createEmpty();

    /**
     * @return TaskList - list of tasks which planned for execution
     *                         between "from" and "to" times
     * @param  from - the start time search
     * @param  to   - the end   time search
     */
/*  // method incoming(Date from, Date to) moved to  Tasks class   
    public TaskList incoming(Date from, Date to) {
        TaskList tmpTList = createEmpty();
        Date tmpTime;
        int tmpNumberTasks = size();
        for (int i = 0; i < tmpNumberTasks; i++) {
            tmpTime = this.getTask(i).nextTimeAfter(from);
            if ((tmpTime != null)  &&  (!tmpTime.after(to))) {
                tmpTList.add(this.getTask(i));
            }
        }
        return tmpTList;
    }
*/
/*
    public TaskList incoming(int from, int to) {
        String stErr="";
        if ((from <0) || (to<0)) {
            throw new IllegalArgumentException("time can not be negative!");
        }
        TaskList tmpTList = createEmpty();
        int tmpTime;
        int tmpNumberTasks = size();
        for (int i = 0; i < tmpNumberTasks; i++) {
            tmpTime = this.getTask(i).nextTimeAfter(from);
            if ((tmpTime != -1)  &&  (tmpTime <= to)) {
                tmpTList.add(this.getTask(i));
            }
        }
        return tmpTList;
    }

 */




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
/*    @Override
    public Object clone() throws CloneNotSupportedException {
//    public TaskList clone() throws CloneNotSupportedException {
//        TaskList cloned = (TaskList) super.clone();
        int numTasks=this.size();
        TaskList cloned = createEmpty();
        for (int i=0; i<numTasks; i++) { cloned.add((Task) this.getTask(i).clone()); }
        return cloned;
    }
*/

    /**
     *  Compare two TaskList - current and "otherObject"
     *  @return  - compare result ( equals or not )
     *  @param otherObject - task for compare
     */
    @Override
    public boolean equals(Object otherObject) {
        if (this==otherObject) {return true;}
        if (otherObject==null) { return false; }
//        if (!(otherObject instanceof ArrayTaskList)) return false;
        if (this.getClass() != otherObject.getClass()) {return false;}

        TaskList tasks2 = (TaskList) otherObject;

        if (tasks2.size()!=this.size()) {return false;}
        int arrLen=this.size();
//        for (Task currTask:this) {
        for (int i=0; i<arrLen ; i++ ) {
            if ( ! this.getTask(i).equals(tasks2.getTask(i)) ) {  return false;  }
        }
        return true;
    }

}