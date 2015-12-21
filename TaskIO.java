//package ua.edu.sumdu.j2se.Khoruzhenko_D_06.tasks;

//import java.math.BigInteger;
//import java.nio.CharBuffer;
//import java.util.Arrays;
//import java.io.InputStream;
//import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.*;


/**
 * Created by khoruzh on 26.11.2015.
 */
public class TaskIO {
//    DataInput / DataOutput
//    private String flName = "flTasks.bin";
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");


    /**
     * Write tasks from list to binary stream
     * @param tasks - list of tasks
     * @param out - OutputStream
     */
    public static void write(TaskList tasks, OutputStream out) throws IOException  {
    	int numTasks = tasks.size();
    	out.write(numTasks);    //   1) number of tasks
        long tmpLong;
    	for (int i=0; i<numTasks; i++) {
//            System.out.println("--- write "+i+" ---");   // debug
    		out.write(tasks.getTask(i).getTitle().length()); // 2) task title length
    		out.write(tasks.getTask(i).getTitle().getBytes()); // 3) task title (as byte array)
            out.write(tasks.getTask(i).isActiveAsInt()); // 4) task active ? (1/0)
            int repInterval =  tasks.getTask(i).getInterval();
            byte[] bArr =  intToByteArray(repInterval);
            out.write(bArr); // 5) task repeat interval
            if ( tasks.getTask(i).isRepeated() ) {
                tmpLong = tasks.getTask(1).getStartTime().getTime() ;
//                System.out.println("datStart = " + tasks.getTask(1).getStartTime()); // debug
//                System.out.println("tmpLong = " + tmpLong); // debug
//                byte[] bArr3 = longToByteArray(tmpLong);
//                long tmpLong2 = byteArrayToLong(bArr3);
//                System.out.println("tmpLong2= " + tmpLong2); // debug
//                Date dat2 = new Date(tmpLong2);
//                System.out.println("datStart2=" + dat2); // debug
                out.write(longToByteArray(tmpLong));  //  ??? 6) task start time

                tmpLong = tasks.getTask(1).getEndTime().getTime() ;
                out.write(longToByteArray(tmpLong) );    // 7) task end time
            } else {
                tmpLong = tasks.getTask(1).getTime().getTime() ;
//                System.out.println("non-repeated  tmpLong=" + tmpLong); // debug
                out.write(longToByteArray(tmpLong));  // 6) task start time
            }
    	}
    }

    /**
     * Read tasks from stream to specified TaskList
     * @param tasks - created list of tasks
     * @param in   - InputStream
     */
    public static void read(TaskList tasks, InputStream in) throws IOException {   
    	int numTasks = in.read();  // 1) number of tasks
        long tmpLong;
        byte[] bArr = new byte[4];
        byte[] bArrDate2 = new byte[8];
        int titleLen;
        boolean active;
        boolean repeated;
        Date taskTime;       // task start time for non-repeated task
        Date startTime;      // the first  start time of the task ( for repeated task )
        Date endTime;        // the latest start time of the task ( for repeated task )
        int repeatInterval;  // repeat interval for repeated task
    	for (int i=0; i<numTasks; i++) {
//            System.out.println("--- read "+i+" ---");   // debug
    		titleLen = in.read();			 	// 2) task title length
            byte[] bArr1 = new byte[titleLen];
    		in.read(bArr1, 0, titleLen);
    		String title = new String(bArr1);  	// 3) task title (from byte array)
//            System.out.println("title=" + title); // debug+
    		active = (in.read() == 1);		 	// 4) task active ? (1/0)
//            System.out.println("active=" + active); // debug
    		in.read(bArr, 0, 4);			// 5) task repeat interval
    		repeatInterval = byteArrayToInt(bArr);
//            System.out.println("repeatInterval=" + repeatInterval); // debug
            Task t;
            if (repeatInterval==0) {
                byte[] bArrDate1 = new byte[8];
                in.read(bArrDate1, 0, 8);
                tmpLong = byteArrayToLong(bArrDate1);
                taskTime = new Date(tmpLong);  // 6) task start time - for non-repeated task
                t = new Task(title, taskTime);
            } else {
                byte[] bArrDate1 = new byte[8];
                in.read(bArrDate1, 0, 8);
//                System.out.println("bArrDate1 =" + bArrDate1); // debug
                tmpLong = byteArrayToLong(bArrDate1);
//                System.out.println("tmpLong =" + tmpLong); // debug
                startTime = new Date(tmpLong);  // 6) task start time - for repeated task
//                System.out.println("repeated: startTime =" + startTime); // debug
                in.read(bArrDate2, 0, 8);
                tmpLong = byteArrayToLong(bArrDate1);
                endTime = new Date(tmpLong);    // 7) task end time  - for repeated task
//                System.out.println("repeated: endDate - tmpLong =" + tmpLong); // debug
                t = new Task(title, startTime, endTime, repeatInterval);
            }
            t.setActive(active);
            tasks.add(t);
    	}
    }

    /**
     * write TaskList to file
     * @param tasks - list of tasks
     * @param file - file for writing
     */
    public static void writeBinary(TaskList tasks, File file) throws IOException {
        DataOutputStream dos = new DataOutputStream( new BufferedOutputStream( new FileOutputStream(file)));
        try {
            write(tasks, dos);
        }
        finally { dos.close(); }
    }

    /**
     * Read TaskList from file
     * @param tasks  - TaskList created from file
     * @param file - data file with tasks
     */
    public static void readBinary(TaskList tasks, File file) throws IOException {
        DataInputStream dis = new DataInputStream( new BufferedInputStream( new FileInputStream(file)) );
        try {
            read(tasks, dis);
        }
        finally {  dis.close();  }
    }

/*
    long content = 212000607777l;
    byte[] numberByte = Longs.toByteArray(content);
    logger.info(Longs.fromByteArray(numberByte));
*/

/*
// http://stackoverflow.com/questions/4485128/how-do-i-convert-long-to-byte-and-back-in-java
    public byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

    public long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(bytes);
        buffer.flip();//need flip
        return buffer.getLong();
    }
*/


    public static byte[] intToByteArray(int value) {
        byte[] byteArr = new byte[4];      // http://stackoverflow.com/questions/5399798/byte-array-and-int-conversion-in-java
        byteArr[3] = (byte) (value >> 24);
        byteArr[2] = (byte) (value >> 16);   
        byteArr[1] = (byte) (value >> 8);   
        byteArr[0] = (byte) value;
        return byteArr;
    }
/*
    public static byte[] longToByteArray(long value) {
        byte[] byteArr = new byte[8];
        byteArr[7] = (byte) (value >> 56);
        byteArr[6] = (byte) (value >> 48);
        byteArr[5] = (byte) (value >> 40);
        byteArr[4] = (byte) (value >> 32);
        byteArr[3] = (byte) (value >> 24);
        byteArr[2] = (byte) (value >> 16);
        byteArr[1] = (byte) (value >> 8);
        byteArr[0] = (byte) value;
        return byteArr;
    }
*/

    public static byte[] longToByteArray(long data) {    // https://www.daniweb.com/programming/software-development/code/216874/primitive-types-as-byte-arrays
        return new byte[] {
                (byte)((data >> 56) & 0xff),
                (byte)((data >> 48) & 0xff),
                (byte)((data >> 40) & 0xff),
                (byte)((data >> 32) & 0xff),
                (byte)((data >> 24) & 0xff),
                (byte)((data >> 16) & 0xff),
                (byte)((data >> 8) & 0xff),
                (byte)((data >> 0) & 0xff),
        };
    }


    public static int byteArrayToInt (byte[] bArr) {
    	int i = (bArr[3] << 24) | (bArr[2] << 16) | (bArr[1] << 8) | bArr[0];    // http://www.sql.ru/forum/419204/byte-to-int
        return i;
    }
/*
    public static long byteArrayToLong (byte[] b) {
        long i = (b[7] << 56) | (b[6] << 48) | (b[5] << 40) | (b[4] << 32) | (b[3] << 24) | (b[2] << 16) | (b[1] << 8) | b[0];
        return i;
    }
*/


    public static long byteArrayToLong(byte[] data) {    // https://www.daniweb.com/programming/software-development/code/216874/primitive-types-as-byte-arrays
        if (data == null || data.length != 8) return 0x0;
        // ----------
        return (long)(
                // (Below) convert to longs before shift because digits
                //         are lost with ints beyond the 32-bit limit
                (long)(0xff & data[0]) << 56  |
                        (long)(0xff & data[1]) << 48  |
                        (long)(0xff & data[2]) << 40  |
                        (long)(0xff & data[3]) << 32  |
                        (long)(0xff & data[4]) << 24  |
                        (long)(0xff & data[5]) << 16  |
                        (long)(0xff & data[6]) << 8   |
                        (long)(0xff & data[7]) << 0
        );
    }

  
    /**
     * convert int to byte array
     * http://stackoverflow.com/questions/5399798/byte-array-and-int-conversion-in-java
     * @param value  - 
     * @return
     */
    public static byte[] intToByteArray2(int value) {
        byte[] byteArr = new byte[Integer.SIZE / Byte.SIZE];
        byteArr[3] = (byte) (value >> Byte.SIZE * 3);
        byteArr[2] = (byte) (value >> Byte.SIZE * 2);   
        byteArr[1] = (byte) (value >> Byte.SIZE);   
        byteArr[0] = (byte) value;
        return byteArr;
    }
    
    /**
     * convert  byte array  to int
     * from http://stackoverflow.com/questions/5399798/byte-array-and-int-conversion-in-java
     * @param byteArr  - byte array for converted
     * @return  int value
     */
    public static int byteArrayToInt2(byte[] byteArr) {
        int value = (byteArr[3] << (Byte.SIZE * 3));
        value |= (byteArr[2] & 0xFF) << (Byte.SIZE * 2);
        value |= (byteArr[1] & 0xFF) << (Byte.SIZE * 1);
        value |= (byteArr[0] & 0xFF);
        return value;
    }

//    int i = (b[0] << 24) | (b[1] << 16) | (b[2] << 8) | b[3];    // http://www.sql.ru/forum/419204/byte-to-int

// --------- text IO -----------

    /**
     * Write TaskList into text stream
     * @param tasks - task list for writing
     * @param out   - text stream (Writer)
     */
    public static void write(TaskList tasks, Writer out) throws IOException {
//        for (Task currTask : tasks) {
        for (int i=0; i<tasks.size(); i++) {
//            String st1 = taskToString(tasks.getTask(i));
            StringBuffer sb1 = new StringBuffer(taskToString(tasks.getTask(i))) ;
            sb1.append( i==(tasks.size()-1) ? "." : ";" );
            sb1.append("\n");
            out.write(sb1.toString());
        }
    }

    /**
     * Convert task to string for serialization
     * @param task - task for converted
     * @return - task as string
     */
    public static String taskToString(Task task){
//        StringBuffer stTask = new StringBuffer("\""+task.getTitle()+"\"");
        StringBuffer stTask = new StringBuffer(task.getTitle());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        int  i1=stTask.indexOf("\"");
        while (i1>0) {
            stTask.replace(i1, i1+1, "\"\"");
            i1 = stTask.indexOf("\"", i1+2);
        }
        stTask.insert(0, "\"");
        stTask.append("\"");
        if (task.isRepeated()) {
            stTask.append(" from [").append(sdf.format(task.getStartTime()));
            stTask.append("] to [").append(sdf.format(task.getEndTime())).append("]");
            stTask.append(" every [").append(timeIntervalToString(task.getInterval())).append("]");
        } else {
            stTask.append(" at [").append(sdf.format(task.getTime())).append("]");
        }
        if ( !task.isActive() ) {stTask.append(" inactive");}
        return stTask.toString();
    }

    
    /**
     * Convert (time interval as integer) to (time interval as string)
     * @param interval - time interval as integer
     * @return - time interval as string
     */
    public static String timeIntervalToString( int interval){
        StringBuffer sb = new StringBuffer("");
        int days = interval / Task.inDays;
        int rest = interval - (days * Task.inDays);
        int hours = rest / Task.inHours;
        rest = rest - (hours * Task.inHours);
        int minutes = rest / Task.inMinutes;
        rest = rest - (minutes * Task.inMinutes);
        int seconds = rest / Task.inSeconds;
        int mseconds = rest - (seconds * Task.inSeconds);
        if (days  > 0    ) {sb.append(days    ).append(days== 1    ? " day "         : " days "        );}
        if (hours > 0    ) {sb.append(hours   ).append(hours==1    ? " hour "        : " hours "       );}
        if (minutes > 0  ) {sb.append(minutes ).append(minutes==1  ? " minute "      : " minutes "     );}
        if (seconds > 0  ) {sb.append(seconds ).append(seconds==1  ? " second "      : " seconds "     );}
        if (mseconds > 0 ) {sb.append(mseconds).append(mseconds==1 ? " millisecond " : " milliseconds ");}
        if (sb.length() > 1)  { sb.deleteCharAt(sb.length()-1);}
        return sb.toString();
    }


    /**
     * Read Task List from text stream
     * @param tasks - task list for reading
     * @param in text stream (Reader)
     * @throws IOException 
     * @throws ParseException 
     */
    public static void read(TaskList tasks, Reader in) throws IOException, ParseException  {
    	char[] arrChar = new char[100];
        StringBuffer sb = new StringBuffer("");
        int i = in.read(arrChar);
        while (i != -1) {
        	sb.append(arrChar, 0, i);
            i = in.read(arrChar);
        }
        String stAll = sb.toString();
    	System.out.println("-------------");  	// debug
    	System.out.print(stAll);				  // debug
    	System.out.println("-------------");	  // debug
    	
    	String arrTasks[] = stAll.split("\n");
    	for (String stCurrTask : arrTasks ) {
    		Task currTask = strToTask(stCurrTask);
    		tasks.add(currTask);
    	}
    }


    /**
     * Write TaskList into text file
     * @param tasks - task list for writing
     * @param file - text file for writing
     * @throws IOException 
     */
    public static void writeText(TaskList tasks, File file) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        try {
            write(tasks, bw);
        } finally {
            bw.close();
        }
    }


    /**
     * Read TaskList from the text file
     * @param tasks - task list for reading
     * @param file - text file for reading
     * @throws IOException 
     * @throws ParseException 
     */
    public static void readText(TaskList tasks, File file) throws IOException, ParseException {
    	BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            read(tasks, br);
        } finally {
            br.close();
        }
    }

    
    /**
     * Convert (task as string) to (Task object)
     * @param taskAsSttring - task as string
     * @return Task object
     * @throws IOException 
     * @throws ParseException 
     */
    public static Task strToTask (String taskAsSttring) throws IOException, ParseException {
		String stTitle;
		String stNoTitle;
		Date dat1; 
		Date dat2;// = null;
		boolean repeated;
		boolean active;
		Task resultTask;
		int interval = 1;
    	System.out.println("readed taskAsString: "+taskAsSttring);				  // debug
// ----- get task title -----		
    	Pattern patTitle = Pattern.compile("\".+\""); 	// any text between quotes   
		Matcher matTitle = patTitle.matcher(taskAsSttring);
		if (matTitle.find()) {
    		stTitle = matTitle.group().substring(1, matTitle.end()-1);  // delete quotes
    		stTitle = stTitle.replaceAll("\"\"", "\"");    // replace "" -> "
        	stNoTitle = taskAsSttring.substring(matTitle.end());
//        	System.out.println(stNoTitle);				  // debug
		} else { throw new ParseException(" Task title not found.", 0); }
// ----- get task repeated, active -----		
		repeated = (stNoTitle.indexOf("from") > 0);
		active   = (stNoTitle.indexOf("inactive") < 0) ;

// ----- get task dates -----		
		Pattern patDate = Pattern.compile("([0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}.[0-9]{3})"); 	// date and time
		Matcher matDate = patDate.matcher(stNoTitle);
		if (matDate.find()) {   	// find date 1
			String stDate1 = matDate.group();
    		dat1 = sdf.parse(stDate1);
		} 
		else { throw new ParseException(" Task date start not found.", 0); }
		
		if (repeated) {  
			if (matDate.find()) {  // find date 2
				String stDate2 = matDate.group();
	    		dat2 = sdf.parse(stDate2);
			} 
			else { throw new ParseException(" Task date end not found.", 0); }

// ------- get task repeat interval (for repeated tasks ) -----		
			int iIntervalStart = stNoTitle.indexOf("every [");
			if (iIntervalStart < 1) {throw new ParseException(" Task interval not found.", 0);}
			int iIntervalEnd  = stNoTitle.indexOf("]", iIntervalStart+1);
			if (iIntervalEnd < 1) {throw new ParseException(" Task interval not found.", 0);}
			String stInterval = stNoTitle.substring(iIntervalStart+7, iIntervalEnd);
//	    	System.out.println("stInterval : "+ stInterval);				  // debug
	    	interval = strToTimeInterval(stInterval);
	    	
// --------- create repeated task  -----		
	    	resultTask = new Task(stTitle, dat1, dat2, interval );
		} 
		else {
// --------- create non-repeated task  -----		
			resultTask = new Task(stTitle, dat1);
		}
    	resultTask.setActive(active);
    	return resultTask;
    } 
    
    
    /**
     * Convert (time interval as string) to (time interval as integer)
     * @param stInterval - time interval as string
     * @return  - time interval as integer
     * @throws ParseException
     */
    public static int strToTimeInterval(String stInterval) throws ParseException {
    	Map<String, Integer> myMap = new HashMap<String, Integer>();
    	try {
        	String[] mapElements = stInterval.split(" ");
        	for (int i=0; i<mapElements.length; i=i+2) {
        		int     intValueElem =  Integer.parseInt(mapElements[i]);
        		String  strKeyElem =  mapElements[i+1];
        		String lastChar = strKeyElem.substring(strKeyElem.length()-1);
        		if ( !lastChar.equals("s")) {strKeyElem += "s";}
        		myMap.put(strKeyElem, intValueElem);
        	}
        	Integer days         = myMap.get("days"        );
        	Integer hours        = myMap.get("hours"       );
        	Integer minutes      = myMap.get("minutes"     );
        	Integer seconds      = myMap.get("seconds"     );
        	Integer milliseconds = myMap.get("milliseconds");
        	if (days        ==null) {days         = 0;}
        	if (hours       ==null) {hours        = 0;}
        	if (minutes     ==null) {minutes      = 0;}
        	if (seconds     ==null) {seconds      = 0;}
        	if (milliseconds==null) {milliseconds = 0;}
        	
        	int result = (days    * Task.inDays   ) + 
        				 (hours   * Task.inHours  ) + 
        				 (minutes * Task.inMinutes) + 
        				 (seconds * Task.inSeconds) + 
        				 (milliseconds            );
        	return result; 
    	}
    	catch (Exception e) { 
    		throw new ParseException("Can't convert time interval to integer.\n"+e.getMessage(), 0); 
    	}
    }
    
}
