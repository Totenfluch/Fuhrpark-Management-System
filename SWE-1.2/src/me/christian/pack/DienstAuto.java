package me.christian.pack;
import java.util.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

public class DienstAuto {

	public String name;
	HashMap<Timestamp, Timestamp> belegung;

	public DienstAuto(String name){
		this.name = name;
		belegung = new HashMap<Timestamp, Timestamp>();
	}

	public String getName(){
		return name;
	}

	public boolean istBelegtInZeitraum(Timestamp start, Timestamp end){
		for(int i = 0; i < belegung.size(); i++)
			for(Timestamp belegtStart : belegung.keySet()){
				Timestamp belegtEnde = belegung.get(belegtStart);
				if (start.getTime()==belegtStart.getTime()||(belegtStart.after(start)&& belegtStart.before(end))|| end.getTime()==belegtStart.getTime() ||
						start.getTime()==belegtEnde.getTime()||(belegtEnde.after(start)&& belegtEnde.before(end))|| end.getTime()==belegtEnde.getTime()){
					Frame.buchungsLog.appendText(name + " ist belegt von " + convertToDate(start) + " bis " + convertToDate(end) + "\n");
					return true;
				}
			}
		return false;
	}

	public boolean istBelegtInZeitraumAm(Timestamp start){
		for(int i = 0; i < belegung.size(); i++)
			for(Timestamp belegtStart : belegung.keySet()){
				Timestamp belegtEnde = belegung.get(belegtStart);
				if (start.getTime() >= belegtStart.getTime() && start.getTime() <= belegtEnde.getTime())
					return true;
			}
		return false;
	}

	public boolean buchen(Timestamp a, Timestamp b){
		if(!istBelegtInZeitraum(a, b)){
			belegung.put(a, b);
			Frame.buchungsLog.appendText(name + " gebucht von " + convertToDate(a) + " bis " + convertToDate(b) + "\n");
			return true;
		}
		return false;
	}
	
	public Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell()
	{
		@Override
		public void updateItem(LocalDate item, boolean empty)
		{
			super.updateItem(item, empty);

			if(istBelegtInZeitraumAm(Timestamp.valueOf(item.atStartOfDay())))
				setStyle("-fx-background-color: #EF5350;");
			else
				setStyle("-fx-background-color: #66BB6A;");
		}
	};

	public static Date convertToDate(Timestamp timeStamp){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis( timeStamp.getTime() );
		return calendar.getTime();
	}
}