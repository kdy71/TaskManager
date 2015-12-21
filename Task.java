//package ua.edu.sumdu.j2se.Khoruzhenko_D_06.tasks;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
* class Task.
* Planned task.
* Can be repeating or not, active or not.
* All tasks have "title" - task title.
* Non-repeating task have "time" attribute - start time.
* Repeating task have "startTime" - start time, "endTime" - end time,
*                   "interval" - repeating interval
*/
//public class Task  {
public class Task implements Cloneable, Serializable {

    public static final int inSeconds = 1000;
    public static final int inMinutes = inSeconds * 60;  // = 60 000
    public static final int inHours   = inMinutes * 60;  // = 3 600 000
    public static final int inDays    = inHours * 24;    // = 86 400 000

    private String title;       // task title
    private boolean active;     
    private boolean repeated;
//    private int taskTime;       // task start time for non-repeated task
//    private int startTime;      // the first  start time of the task ( for repeated task )
//    private int endTime;        // the latest start time of the task ( for repeated task )
    private Date taskTime;       // task start time for non-repeated task
    private Date startTime;      // the first  start time of the task ( for repeated task )
    private Date endTime;        // the latest start time of the task ( for repeated task )
    private int repeatInterval; // repeat interval for repeated task
                                //  ??? if  repeatInterval in milliseconds  !!!
    private static final long serialVersionUID = 100L;
    
  
    
   /*
    private void print(String str) {
        System.out.println(str);
    }
*/
    
    
    /**
    * Constructor for inactive non-repeated task
    * @param title - task title
    * @param time  - execution task time
    */
    public Task(String title, Date time) {
        this.title = title;
        this.taskTime = time;
        this.active = false;
        this.repeated = false;
        checkData();
    }

    /**
    * Constructor for inactive repeated task
    * @param title - task title
    * @param start  - start time
    * @param end - task end time
    * @param interval  - repeat interval
    */
    public Task(String title, Date start, Date end, int interval) {
        this.title = title;
        this.startTime = start;
        this.endTime = end;
        this.repeatInterval = interval;
        this.active = false;
        this.repeated = true;
        checkData();
    }

    
    public boolean isRepeated()    { return repeated;   }
    public boolean isActive()      { return active;     }
    public int isActiveAsInt()     { return ( active ? 1 : 0);   }
    public String  getTitle()      { return title;      }
    public int getInterval()       { return repeatInterval; }

    public void setTitle(String title1)          { title = title1; }
    public void setStartTime(Date startTime1)     { startTime = startTime1; }
    public void setEndTime(Date endTime1)         { endTime = endTime1; }
    public void setInterval(int repeatInterval1) { repeatInterval = repeatInterval1; }
    public void setRepeated(boolean repeated1)   { repeated = repeated1; }
    public void setActive(boolean active1)       { active = active1; }

   
    /**
    * @return taskTime  - for non-repeating task
    *         startTime - for repeating task     
    */
    public Date getTime() {
        if (repeated) { return startTime; } 
        else          { return taskTime;  }
    }

    
    /**
    *  @return startTime  - for repeating task
    *          taskTime  - for non-repeating task     
    */
    public Date  getStartTime()      {
        if (repeated) { return startTime; } 
        else          { return taskTime;  }
    }
    
    
    /**
    *  @return endTime  - for repeating task
    *          taskTime - for non-repeating task     
    */
    public Date  getEndTime() {
        if (repeated) { return endTime;   } 
        else          { return taskTime;  }
    }

    
    /**
    *  @return RepeatInterval  - for repeating task
    *                       0  - for non-repeating task     
    */
    public int getRepeatInterval() { 
        if (repeated) { return repeatInterval;  } 
        else          { return 0;               }
    }
    

    /**
    *  set task time for non-repeating task
    *  if task is repeating - it must become non-repeating
    *  @param time1 - task start time for non-repeated task
    */
    public void setTime(Date time1) {
        taskTime = time1;
//        if ( repeated ) { repeated = false; }
        repeatInterval = 0;
        repeated = false; 
        checkData();
    }


    /**
    *  set start time, end time, repeat interval  for repeating task
    *  if task is non-repeating - it must become repeating
    *  @param start  - start time
    *  @param end - task end time
    *  @param interval  - repeat interval
    */
    public void setTime(Date start, Date end, int interval) {
        startTime = start;
        endTime = end;
        repeatInterval = interval;
        repeated = true; 
        checkData();
    }
    
    
    /**
    *  @param current  - time point for check "next time after"
    *  @return  next start time of the task ( after "current" point )
    */
    public Date nextTimeAfter(Date current) {
        if (!active) { return null; }
        if (!repeated) {
              if (current.before(taskTime))  { return taskTime;  }
                                 else        { return null;      }
        }  else {
              if (current.before(startTime)) { return startTime; }
              if (!current.before(endTime))  { return null;      }
              Date nextPoint = new Date( startTime.getTime()  + repeatInterval );   // ??? if  repeatInterval in milliseconds  !!!
//              String st = dat2Str(nextPoint);  // debug
              while (!nextPoint.after(this.endTime)) {  // while (nextPoint <= this.endTime)
//            	  st = dat2Str(iterationPoint) + " <= " + dat2Str(current)+" < " + dat2Str(endTime); // debug
//                  if (!nextPoint.before(current)) // if ((current >=  nextPoint)
                  if (nextPoint.after(current)) // if ((current >  nextPoint)
                          { return nextPoint ; }
                  nextPoint.setTime(nextPoint.getTime()+repeatInterval );
              }
            return null;
        }
    }
/*
    public int nextTimeAfter(int current) {
        if (!active) { return -1; }
        if (!repeated) {
                if (current < taskTime)  { return taskTime;  }
                                   else  { return -1;        }
        }  else {
                if (current <  startTime) { return startTime; }
                if (current >= endTime)   { return -1;        }
                int iterationPoint = startTime;
                int nextPoint = startTime + repeatInterval;
                while (nextPoint <= this.endTime) {
                    if ((current >=  iterationPoint)
                        &&  (current < nextPoint))  { return nextPoint; }
                    iterationPoint = nextPoint;
                    nextPoint     += repeatInterval;
                }
            return -1;
        }
    }
 */


    
    private void checkData(){
        String st = "";
        if (title == "" ) {st = st + "Title must be not empty! ";};
        if (repeated) {
//            if (startTime < 0 ) {st = st + "Start time can not be negative! ";};
//            if (endTime < 0 ) {st = st + "End time can not be negative! ";};
            if (repeatInterval <= 0 ) {st = st + "Interval must be greater than zero! ";};
        }
        else if (!repeated) {
//            if (taskTime < 0 ) {st = st + "Time must be greater than zero! ";};
        }
        if (!st.equals(""))
            throw new IllegalArgumentException(st); 
    };
    
    
    
    /**
    *  Compare two tasks - current and "otherObject"
    *  @return  - compare result ( equals or not )
    *  @param otherObject - task for compare
    */
    @Override
//    public boolean equals(Task task2) {
    public boolean equals(Object otherObject) {
        if (this==otherObject) {return true;}
        if (otherObject==null) { return false; }
//        if (task4Compare==null) { return (this==null ); } // why (task4Compare==null) is always false???
        if (this.getClass() != otherObject.getClass()) {return false;}
//        if ( !(otherObject instanceof Task) ) {return false;}
        boolean result=false;
        Task task2 = (Task) otherObject;
        result=((this.repeated==task2.repeated        )
                    && (this.active==task2.active     )
                    && (this.title.equals(task2.title)));
        if (this.repeated) {
            result =  ((result                                     )
                    && (this.startTime.equals(task2.startTime)     )
                    && (this.endTime.equals(task2.endTime)         )
                    && (this.repeatInterval == task2.repeatInterval));
        } else {
            result =  ((result                                     )
                    && (this.taskTime.equals(task2.taskTime)));
        }
/*        if ((this.title == task2.title)
                && (this.active == task2.active)
                && (this.repeated == task2.repeated)
                && (this.taskTime == task2.taskTime)
                && (this.startTime == task2.startTime)
                && (this.endTime == task2.endTime)
                && (this.repeatInterval == task2.repeatInterval)) {
            return true; 
        }  else  { return false; } */
        return result;
    }


     /**
     * @return integer hashCode
     */
    @Override
    public int hashCode() {
        int result = title.hashCode();
        result += 41 * result + (active ? 1 : 0);
        result += 41 * result + (repeated ? 1 : 0);
/*        result += 41 * result + taskTime;
        result += 41 * result + startTime;
        result += 41 * result + endTime;
        result += 41 * result + repeatInterval; */
        if (this.repeated) {
            result += 41 * result + startTime.hashCode();
            result += 41 * result + endTime.hashCode();
            result += 41 * result + repeatInterval;
        } else {
            result += 41 * result + taskTime.hashCode();
        }
        return result;
    }


    /**
    *  Make readable string from task parameters
    *  @return  - task as string
    */
    @Override
    public String toString() {
        String st;
//      st = "Task <<"+title + ">> [taskTime:" + taskTime + ";  startTime=" + startTime;
//      st = st + ";  endTime=" + endTime + ";  repeatInterval=" + repeatInterval;
        st = "Task <<"+title + ">> [taskTime:" + dat2Str(taskTime); 
        st+= ";  startTime=" + dat2Str(startTime);
        st+= ";  endTime=" + dat2Str(endTime) + ";  repeatInterval=" + repeatInterval;
        st+= (active ? ";  active" : ";  not active");
        st+= (repeated ? ";  repeated]" : ";  not repeated]");
        return st;
    }

    /**
     * Creates and returns a copy of this object.  The precise meaning
     * of "copy" may depend on the class of the object. The general
     * intent is that, for any object {@code x}, the expression:
     * <blockquote>
     * <pre>
     * x.clone() != x</pre></blockquote>
     * will be true, and that the expression:
     * <blockquote>
     * <pre>
     * x.clone().getClass() == x.getClass()</pre></blockquote>
     * will be {@code true}, but these are not absolute requirements.
     * While it is typically the case that:
     * <blockquote>
     * <pre>
     * x.clone().equals(x)</pre></blockquote>
     * will be {@code true}, this is not an absolute requirement.
     *
     * By convention, the returned object should be obtained by calling
     * {@code super.clone}.  If a class and all of its superclasses (except
     * {@code Object}) obey this convention, it will be the case that
     * {@code x.clone().getClass() == x.getClass()}.
     *
     * By convention, the object returned by this method should be independent
     * of this object (which is being cloned).  To achieve this independence,
     * it may be necessary to modify one or more fields of the object returned
     * by {@code super.clone} before returning it.  Typically, this means
     * copying any mutable objects that comprise the internal "deep structure"
     * of the object being cloned and replacing the references to these
     * objects with references to the copies.  If a class contains only
     * primitive fields or references to immutable objects, then it is usually
     * the case that no fields in the object returned by {@code super.clone}
     * need to be modified.
     *
     * The method {@code clone} for class {@code Object} performs a
     * specific cloning operation. First, if the class of this object does
     * not implement the interface {@code Cloneable}, then a
     * {@code CloneNotSupportedException} is thrown. Note that all arrays
     * are considered to implement the interface {@code Cloneable} and that
     * the return type of the {@code clone} method of an array type {@code T[]}
     * is {@code T[]} where T is any reference or primitive type.
     * Otherwise, this method creates a new instance of the class of this
     * object and initializes all its fields with exactly the contents of
     * the corresponding fields of this object, as if by assignment; the
     * contents of the fields are not themselves cloned. Thus, this method
     * performs a "shallow copy" of this object, not a "deep copy" operation.
     *
     * The class {@code Object} does not itself implement the interface
     * {@code Cloneable}, so calling the {@code clone} method on an object
     * whose class is {@code Object} will result in throwing an
     * exception at run time.
     *
     * @return a clone of this instance.
     * @throws CloneNotSupportedException if the object's class does not
     *                                    support the {@code Cloneable} interface. Subclasses
     *                                    that override the {@code clone} method can also
     *                                    throw this exception to indicate that an instance cannot
     *                                    be cloned.
     * @see Cloneable
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        Task cloned = (Task) super.clone();
        if (cloned.startTime != null)  
        	cloned.startTime = (Date) this.startTime.clone();
        if (cloned.endTime != null)  
            cloned.endTime = (Date) this.endTime.clone();
        if (cloned.taskTime != null)  
            cloned.taskTime = (Date) this.taskTime.clone();
        return cloned;
    }
    

    /**
     * @param dat1 - Date for formatting
     * @return date as String in format "dd.MM.yyyy HH:mm:ss"  or String "null" if date is null
     */
    public static String dat2Str(Date dat1) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    	if (dat1 == null)
    		return "null";
    	else
    		return dateFormat.format(dat1);
    }
    
    
    
}