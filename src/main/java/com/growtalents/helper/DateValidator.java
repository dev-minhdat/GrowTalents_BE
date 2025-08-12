package com.growtalents.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for date validation and parsing
 */
public class DateValidator {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    /**
     * Validates if a date string is in dd/MM/yyyy format
     * @param dateString Date string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidDateFormat(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return false;
        }
        
        try {
            LocalDate.parse(dateString, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    /**
     * Parses date string in dd/MM/yyyy format to LocalDate
     * @param dateString Date string to parse
     * @return LocalDate object
     * @throws DateTimeParseException if format is invalid
     */
    public static LocalDate parseDate(String dateString) throws DateTimeParseException {
        return LocalDate.parse(dateString, DATE_FORMATTER);
    }
    
    /**
     * Formats LocalDate to dd/MM/yyyy string
     * @param date LocalDate to format
     * @return Formatted date string
     */
    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }
    
    /**
     * Validates date range
     * @param startDate Start date
     * @param endDate End date
     * @return true if start date is before or equal to end date
     */
    public static boolean isValidDateRange(LocalDate startDate, LocalDate endDate) {
        return !startDate.isAfter(endDate);
    }
}
