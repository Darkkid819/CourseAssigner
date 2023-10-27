package com.excelparser.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateRange implements Serializable {
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

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        return "DateRange [start=" + start.format(formatter) + ", end=" + end.format(formatter) + "]";
    }
}