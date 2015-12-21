//package ua.edu.sumdu.j2se.Khoruzhenko_D_05.tasks;

import java.util.*;
import java.io.Serializable;

/**
 * Created by khoruzh on 15.11.2015.
 */
public class Tasks implements Serializable {

    private static final long serialVersionUID = 200L;

    /**
     * @return  list of tasks which planned for execution
     *                         between "start" and "end" times
     * @param  start - the start time search
     * @param  end   - the end   time search
     */
    public static Iterable<Task> incoming(Iterable<Task> tasks, Date start, Date end) {
        List<Task> resultTaskList = new ArrayList<Task>();
//        List<Task> resultTaskList = <TaskList> createEmpty();
        Date tmpTime;
        for (Task currTask : tasks) {
            tmpTime = currTask.nextTimeAfter(start);
            if ((tmpTime != null) && (!tmpTime.after(end))) { resultTaskList.add(currTask); }
        }
        return resultTaskList;
    }
/*
    public TaskList incoming(Date from, Date to) {
        String stErr="";
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


    /**
     * @return  calendar of tasks (task date + title ) wich planned for execution
     *                         between "start" and "end" times
     * @param  start - the start time search
     * @param  end   - the end   time search
     */
    public static SortedMap<Date, Set<Task>> calendar(Iterable<Task> tasks, Date start, Date end) {
        SortedMap<Date, Set<Task>> resultSortedMap = new TreeMap();
        Date tmpTaskDat;
        String st; // debug
        for (Task currTask : tasks ) {
            tmpTaskDat = currTask.nextTimeAfter(start);
//     	    st = Task.dat2Str(start) + " <= " + Task.dat2Str(tmpTaskDat)+" < " + Task.dat2Str(end); // debug
            while ((tmpTaskDat != null) && (tmpTaskDat.before(end))) {
//         	    st = Task.dat2Str(start) + " <= " + Task.dat2Str(tmpTaskDat)+" < " + Task.dat2Str(end); // debug
                Set<Task> currSet  = resultSortedMap.get(tmpTaskDat);
                if (currSet == null)  
                	currSet = new HashSet();
                currSet.add(currTask);
                resultSortedMap.put(tmpTaskDat, currSet);
                tmpTaskDat = currTask.nextTimeAfter(tmpTaskDat);
            }
        }
        return resultSortedMap;
    }


}
