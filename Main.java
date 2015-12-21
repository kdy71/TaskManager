//package ua.edu.sumdu.j2se.Khoruzhenko_D_06.tasks;

import java.io.IOException;
import java.text.ParseException;
import java.io.*;
import java.util.*;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.util.Set;
//import java.util.SortedMap;
//import java.util.Date;


/**
* Main class
*/
public class Main {
 /*   public static final int inSeconds = 1000;
    public static final int inMinutes = inSeconds * 60;
    public static final int inHours   = inMinutes * 60;
    public static final int inDays    = inHours * 24; */

    /**
    *  Enter point+
    *  For test and debug
    *  @param args - arguments ))
     * @throws java.io.IOException
     * @throws CloneNotSupportedException 
     * @throws ParseException 
    */
    public static  void main(String [] args) throws IOException, CloneNotSupportedException, ParseException {
        int inDays = Task.inDays;
//        Task myTask1 = new  Task("some1", 10, 100, 20);
        Date dat1 = new Date();
//        Date dat2 = new Date(115, 10, 19); //   (year + 1900, month, date). Deprecated.
//        Date dat2 = new Date(1112223334445L) 	 ; //   (year + 1900, month, date). Deprecated.
        Date dat2 = new Date(dat1.getTime() + 5*inDays) 	 ; //   (year + 1900, month, date). Deprecated.
//        Date dat2 = new Date();
//        dat2.setTime(dat1.getTime() + 5*inDays) 	 ; //   (year + 1900, month, date). Deprecated.
        print("--- Task ----");
//        Task myTask1 = new  Task("some1", dat1, dat2, inDays);
        Task myTask1 = new  Task("some1", dat1);
        myTask1.setActive(true);
        Task myTask2 = new  Task("some2", dat1, dat2, inDays);
        myTask2.setActive(true);
//        Task myTask3 = new  Task("some3", dat1, dat2, 20000);
        Task myTask3 = (Task) myTask2.clone();
        if (myTask2.equals(myTask3) )
        	print("myTask2.equals(myTask3) - ok");
        else
        	print("myTask2 NOT equals(myTask3) !!!");
    //    myTask1.setActive(true);
    //    System.out.println("nextTimeAfter(30): " + myTask1.nextTimeAfter(30));
        print("--- ArrayTaskList ----");
        ArrayTaskList tList1 = new ArrayTaskList();
//        System.out.println("---size1a: "+tList1.size());
        tList1.add(myTask1);
        tList1.add(myTask2);
        tList1.add(myTask3);
        ArrayTaskList tList2 = (ArrayTaskList) tList1.clone();
        print("tList2.getClass = " + tList2.getClass().toString());
        if (tList1.equals(tList2) )
        	print("tList1.equals(tList2) - ok");
        else
        	print("tList1 NOT equals(tList2) !!!");
        tList2.getTask(0).setTitle("task_cloned");
        print(tList1.getTask(0).toString());
        print(tList2.getTask(0).toString());
//        System.out.println(tList1.getTask(0).getTitle());
//        System.out.println(tList1.getTask(1).getTitle());
//        System.out.println(tList1.getTask(2).getTitle());
//        System.out.println("---size1: "+tList1.size());
//        tList1.remove(myTask1);
//        tList1.remove(myTask1);
//        System.out.println("---size2: "+tList1.size());

        print("--- LinkedTaskList ----");
        LinkedTaskList lTL = new LinkedTaskList();
//        myTask1.setTime(new Date());
        lTL.add(myTask1);
        lTL.add(myTask2);
        lTL.add(myTask3);
        System.out.println("- - - size1 = " + lTL.size());
        LinkedTaskList lTL2 = (LinkedTaskList) lTL.clone();
//        print("===1");
//        print(lTL.toString());
//        print(lTL2.toString());
//        print("===2");
        if (lTL.equals(lTL2) )
        	print("lTL.equals(lTL2) - ok");
        else
        	print("lTL NOT equals(lTL2) !!!");
        lTL2.getTask(0).setTitle("cloned task list task");
        print(lTL.getTask(0).toString());
        print(lTL2.getTask(0).toString());
        
//        System.out.println("- - - size2 = " + lTL.size());
//        System.out.println("title 2 = " + lTL.getTask(1).getTitle());
//        print(lTL.toString());
//        lTL.remove(myTask3);
//        System.out.println("- - - size3 = " + lTL.size());
//        System.out.println("-x20");

        testNextTimeAfter();

/*
        print("--- Tasks.incoming ----");
        Date dat3 = new Date(dat1.getTime() + 1*inDays);
        Date dat4 = new Date(dat2.getTime() + 1*inDays);
//        incoming(Iterable<Task> tasks, Date start, Date end)
//        print (" "+ Tasks.incoming(lTL, dat3, dat4).toString() );
        ArrayList<Task> aL3 = (ArrayList<Task>) Tasks.incoming(lTL, dat3, dat4);
        print( " Tasks.incoming list size: " + aL3.size() );
//        print (" "+ aL3.toString() );
*/
/*
        print("--- Tasks.calendar ----_");
//      SortedMap<Date, Set<Task>>  calendar(Iterable<Task> tasks, Date start, Date end)        
        TreeMap<?, ?> tMap1 = new TreeMap<Date, HashSet<Task>>();
        tMap1 = (TreeMap<?, ?>) Tasks.calendar(lTL, dat3, dat4); 
        print(" Tasks.calendar map size: " + tMap1.size());

        System.out.println("Keys of tree map: " + tMap1.keySet());

        System.out.println("___ TreeMap ___");
//        for (SortedMap.Entry<java.util.Date, java.util.Set<Task>> e : tMap1.entrySet()) {
        for (Map.Entry<?, ?> e : tMap1.entrySet()) {
//        for (Map.Entry<Date, Set<Task>> e : tMap1.entrySet()) {
            print(Task.dat2Str((Date)e.getKey()));
            HashSet<Task> set1 = (HashSet) e.getValue();
            for(Task currTask : set1 ) {
                print("    "+currTask.toString());
            }
        }
*/
/*        System.out.println("___ write(TaskList tasks, OutputStream out) ___");
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        TaskIO.write(lTL, out);
*/
/*
        System.out.println("___ writeBinary ___");
        print(lTL.toString());
        File fl1 = new File("testFile.bin");
        TaskIO.writeBinary(lTL, fl1);
        System.out.println("___ readBinary ___");
        LinkedTaskList lTL_readed = new LinkedTaskList();
        TaskIO.readBinary(lTL_readed, fl1);
        System.out.println(lTL_readed.toString());
*/
//     bin test - from T6_TaskIOTest.java          
//        PipedInputStream in = new PipedInputStream();
//        PipedOutputStream out = new PipedOutputStream(in);
//        TaskIO.write(tasks, out);
//        TaskList result = TaskList.create();//        TaskIO.read(result, in);

        System.out.println("___ write to text file ___");
//        myTask2.setTitle("task__\"myTask2\"__");
        lTL.getTask(1).setTitle("task__\"myTask2\"__");
        lTL.getTask(1).setActive(false);;
        lTL.getTask(2).setTitle("task3");
//        print("lTL before saving: \n"+lTL.toString());
//        print(TaskIO.taskToString(myTask1));
//        print(TaskIO.taskToString(myTask2));
//        print(TaskIO.taskToString(myTask3));
//        print("myTask2 interval = "+TaskIO.timeIntervalToString(myTask2.getInterval()));
//        String st1 = TaskIO.timeIntervalToString(200000002);
//        print("test timeIntervalToString(200000002) = ["+st1+"]"); // debug
//        int i1 = TaskIO.strToTimeInterval(st1);
//        print("test strToTimeInterval : " +i1); // debug
        File flTxt = new File("testFile.txt");
        TaskIO.writeText(lTL, flTxt);

        System.out.println("___ read from text file ___");
        LinkedTaskList lTL_readed = new LinkedTaskList();
        TaskIO.readText(lTL_readed, flTxt);
        print("lTL before saving: \n"+lTL.toString());
        print("lTL_readed  after restoring: \n"+lTL_readed.toString());
        
//        print(lTL.toString());
//        print(lTL_readed.toString());
        
        System.out.println("lTL_readed.equals(lTL) - "+lTL_readed.equals(lTL));
        print(        lTL.getTask(1).toString() );
        print( lTL_readed.getTask(1).toString() );
        System.out.println("lTL.getTask(1).equals(lTL_readed.getTask(1))) - "+lTL.getTask(1).equals(lTL_readed.getTask(1)));
//        System.out.println(lTL.getTask(0).isActive());
//        System.out.println(lTL_readed.getTask(0).isActive());
        
/*
        System.out.println("___ test double quotes ___");
        myTask2.setTitle("task__\"\"myTask2\"\"__");
        System.out.println("myTask2.getTitle() - "+ myTask2.getTitle());
        String st = "task__\"\"myTask2\"\"__";
        myTask2.setTitle(st);
        System.out.println("myTask2.getTitle() - "+ myTask2.getTitle());
  */      

        testNextTimeAfter();
        print("---|____/");


    }


    /**
    * Print string ( = System.out.println(str) )
    * @param str - string to print.
    */
    public static void print(String str) {
        System.out.println(str);
    }
    
    public static void testNextTimeAfter() throws ParseException {
        print("--- test nextTimeAfter ----");
		String stDate1     = "2015-12-18 12:39:38.503";
		String stDate2     = "2015-12-28 12:39:38.503";
		String stDate1Check = "2015-12-18 12:39:37.503";
		String stDate2Check = "2015-12-18 12:39:38.503";
		Date dat1     = TaskIO.sdf.parse(stDate1);
		Date dat2     = TaskIO.sdf.parse(stDate2);
		Date dat1Check = TaskIO.sdf.parse(stDate1Check);
		Date dat2Check = TaskIO.sdf.parse(stDate2Check);
    	
    	Task task1 = new Task("task1", dat1);
    	task1.setActive(true);
    	Task task2 = new Task("task2", dat1, dat2, Task.inDays);
    	task2.setActive(true);
    	print("");
        print(TaskIO.taskToString(task1));
        Date dat11 = task1.nextTimeAfter(dat1Check);
        print("nextTimeAfter("+Task.dat2Str(dat1Check)+") = "+Task.dat2Str( dat11)  );
    	print(" --- ");

        print(TaskIO.taskToString(task2));
        Date dat12 = task2.nextTimeAfter(dat2Check);
        print("nextTimeAfter("+Task.dat2Str(dat2Check)+") = "+Task.dat2Str( dat12)  );
    	print("");
    	
    }
}