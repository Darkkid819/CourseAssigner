package com.excelparser.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeRange implements Serializable {
    private transient DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("h:mm a");

    private LocalTime start;
    private LocalTime end;

    public TimeRange(int h1, int m1, int h2, int m2) {
        validateTime(h1, m1);
        validateTime(h2, m2);

        LocalTime potentialStart = convertTo12Hour(h1, m1);
        LocalTime potentialEnd = convertTo12Hour(h2, m2);

        validateTimeOrder(potentialStart, potentialEnd);

        this.start = potentialStart;
        this.end = potentialEnd;
    }

    public TimeRange(LocalTime start, LocalTime end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and End times cannot be null.");
        }

        validateTimeOrder(start, end);

        this.start = start;
        this.end = end;
    }

    private LocalTime convertTo12Hour(int hour, int minute) {
        if (hour < 12) {
            return LocalTime.of(hour, minute);
        } else if (hour == 12) {
            return LocalTime.of(hour, minute);
        } else {
            return LocalTime.of(hour - 12, minute);
        }
    }

    private void validateTime(int hour, int minute) {
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Invalid hour or minute value.");
        }
    }

    private void validateTimeOrder(LocalTime start, LocalTime end) {
        if (!start.isBefore(end)) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }

    public String getFormattedStart() {
        return start.format(FORMATTER);
    }

    public String getFormattedEnd() {
        return end.format(FORMATTER);
    }

    @Override
    public String toString() {
        return "TimeRange [start=" + start.format(FORMATTER) + ", end=" + end.format(FORMATTER) + "]";
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        // initialize transient variable
        FORMATTER = DateTimeFormatter.ofPattern("h:mm a");
    }
}