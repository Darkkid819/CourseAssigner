package com.excelparser.model;

import java.time.LocalTime;

public class TimeRange {
    private LocalTime start;
    private LocalTime end;

    public TimeRange(int h1, int m1, int h2, int m2) {
        validateTime(h1, m1);
        validateTime(h2, m2);

        LocalTime potentialStart = LocalTime.of(h1, m1);
        LocalTime potentialEnd = LocalTime.of(h2, m2);

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

    @Override
    public String toString() {
        return "TimeRange [start=" + start + ", end=" + end + "]";
    }
}