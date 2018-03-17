
import java.util.*;
import org.joda.*;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Hours;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.util.Map.Entry;
import java.util.function.ToLongFunction;
import java.util.stream.Stream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.*;
import java.util.stream.*;

public class MonitoredData {
	private Date startTime;
	private Date endTime;
	private String activityLabel;
	
	MonitoredData(){
		Date startTime = new Date();
		Date endTime= new Date();
		String activityLabel= new String();
	}
    
	
	public Date getStartTime() {
		return startTime;
	}
	
	@SuppressWarnings("deprecation")
	public int getStartTimeDay() {
		return startTime.getDate();
	}
	
	public long getEndTimeInt() {
		return endTime.getTime();
	}
	public long getStartTimeInt() {
		return startTime.getTime();
	}

	public Long getDuration(){
		
		DateTime x= new DateTime(this.getEndTime());
		DateTime y= new DateTime(this.getStartTime());
		
		Duration z = new Duration(y,x);
		
		return Math.abs(z.getMillis());
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public String getActivityLabel() {
		return activityLabel;
	}


	public void setActivityLabel(String activityLabel) {
		this.activityLabel = activityLabel;
	}


	public static void main(String args[]) throws IOException {
		FileWriter writer = null;
		try {
			writer = new FileWriter("op.txt");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		List <MonitoredData> a = new ArrayList<MonitoredData>();
				
		
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		Stream<String> stream = null;
		try {
			stream= Files.lines(Paths.get("Activities.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		a =stream.map(line->line.split("		"))
				  .map(temp->{
					  MonitoredData m = new MonitoredData();
					  try {
						
						m.setStartTime(ft.parse(temp[0]));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  try {
						m.setEndTime(ft.parse(temp[1]));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  m.setActivityLabel(temp[2]);
					  return m;
				  })
				  .collect(Collectors.toList());
			
		@SuppressWarnings("deprecation")
		long x = a.stream().map(y -> y.startTime.getDate())
						.distinct()
						.count();
		
		System.out.println("1. Distinct days: "+ Long.toString(x));	
		writer.write("1. Distinct days: "+ Long.toString(x)+"\n");
		
		Map<String, Long> act = new HashMap<String,Long>();
			act=	a.stream().collect(Collectors.groupingBy(y-> y.getActivityLabel(),
																Collectors.counting()	));
		System.out.println("\n2. Each distinct action types' the number of occurrences:\n");
		writer.write("\n2. Each distinct action types' the number of occurrences:\n");
		for(Map.Entry<String, Long> entry : act.entrySet()){
			writer.write(entry.getKey()+ "	" + entry.getValue()+"\n");
			System.out.println(entry.getKey()+ "	" + entry.getValue());
		}
		
		Map<Integer, Map<String, Long>> act2;
		act2=	a.stream().collect(Collectors.groupingBy( MonitoredData::getStartTimeDay,Collectors.groupingBy( MonitoredData::getActivityLabel ,Collectors.counting())));
	
		System.out.println();
		System.out.println("3. Activity count for each day of the log: ");
		writer.write("3. Activity count for each day of the log: \n");
		
		for(Entry<Integer, Map<String, Long>> entry : act2.entrySet()){
			writer.write(entry.getKey());
			System.out.println(entry.getKey());
			for(Entry<String, Long> entry2 : entry.getValue().entrySet()){
				writer.write("	" + entry2.getKey()+ "   " + entry2.getValue()+"\n");
				System.out.println("	" + entry2.getKey()+ "   " + entry2.getValue());
			}
		}
		
		
		 Map<String, Long> m ;
		 m=a.stream().collect(Collectors.groupingBy(MonitoredData::getActivityLabel,
					Collectors.summingLong( MonitoredData::getDuration )));
		 writer.write("\n4. Total Duration of the activities <10h \n");
		 System.out.println("\n4. Total Duration of the activities <10h \n");
			for(Map.Entry<String, Long> entry : m.entrySet()){
				Duration xx = new Duration(Math.abs(entry.getValue()));
				if(xx.isLongerThan(Duration.standardHours(10))){
					System.out.println(entry.getKey()+ "	" + xx.toPeriod());
					writer.write(entry.getKey()+ "	" + xx.toPeriod()+"\n");
				}
			}
		 
		List <String> b = a.stream().collect
									(Collectors.collectingAndThen
									(Collectors.groupingBy(MonitoredData::getActivityLabel,Collectors.averagingLong( MonitoredData::getDuration )), 
									t-> {
											List<String> s = new ArrayList<String>();
											for(Entry<String, Double> xx : t.entrySet()){
												if((xx.getValue()/10)*9<30000){
													s.add(xx.getKey());
												}
											}
											return s;
										}));
		writer.write("5. Activities that are 90% or more, than 5m:\n"+b.toString());
		System.out.println("5. Activities that are 90% or more, less than 5m:");
		System.out.println(b.toString());
		
		writer.flush();
		writer.close();		
	}
	
}
