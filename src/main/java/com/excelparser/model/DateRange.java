package com.excelparser.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateRange implements Serializable {
    private transient DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("M/d/yyyy");
    private LocalDate start;
    private LocalDate end;

    public DateRange(String startDate, String endDate) {
        LocalDate potentialStart = parseDate(startDate);
        LocalDate potentialEnd = parseDate(endDate);

        validateDateOrder(potentialStart, potentialEnd);

        this.start = potentialStart;
        this.end = potentialEnd;
    }

    private LocalDate parseDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            System.out.println(date);
            throw new IllegalArgumentException("Invalid date format. Use 'M/d/yyyy'.");
        }
    }

    private void validateDateOrder(LocalDate start, LocalDate end) {
        if (!start.isBefore(end)) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public String getFormattedStart() { return start.format(FORMATTER); }

    public String getFormattedEnd() { return end.format(FORMATTER); }

    @Override
    public String toString() {

        return "DateRange [start=" + start.format(FORMATTER) + ", end=" + end.format(FORMATTER) + "]";
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        // initialize transient variable
        FORMATTER = DateTimeFormatter.ofPattern("M/d/yyyy");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateRange dateRange = (DateRange) o;

        if (!start.equals(dateRange.start)) return false;
        return end.equals(dateRange.end);
    }

    @Override
    public int hashCode() {
        int result = start.hashCode();
        result = 31 * result + end.hashCode();
        return result;
    }
}