package bean;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

public class TimeBlock {
    private int weedDay;
    private ArrayList<Integer> periods;
    private Time startTime;
    private Time endTime;

    public TimeBlock(int weedDay, ArrayList<Integer> periods, Time startTime, Time endTime) {
        this.weedDay = weedDay;
        this.periods = periods;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public TimeBlock(int weedDay, ArrayList<Integer> periods) {
        this.weedDay = weedDay;
        this.periods = periods;
    }

    public int getWeedDay() {
        return weedDay;
    }

    public ArrayList<Integer> getPeriods() {
        return periods;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setWeedDay(int weedDay) {
        this.weedDay = weedDay;
    }

    public void setPeriods(ArrayList<Integer> periods) {
        this.periods = periods;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        String periodStr = "";
        for (int i = 0; i < this.periods.size() - 1; ++i) {
            periodStr = periodStr + this.periods.get(i).toString() + ",";
        }
        periodStr += this.periods.get(this.periods.size() - 1).toString();
        return String.format("%d_%s", this.weedDay, periodStr);
    }
}
