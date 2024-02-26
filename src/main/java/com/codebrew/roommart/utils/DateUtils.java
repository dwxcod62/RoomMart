package com.codebrew.roommart.utils;

import java.time.LocalDate;
import java.time.Period;

public class DateUtils {

    public  String formatDate(String dateString) {
        String[] lst_date = dateString.split("-");

        return "Ngày " + lst_date[2] + " Tháng " + lst_date[1] + " Năm " + lst_date[0];
    }

    public  String countYear(String startdate, String endate) {
        LocalDate startDate = LocalDate.parse(startdate);
        LocalDate endDate = LocalDate.parse(endate);

        Period period = Period.between(startDate, endDate);
        int totalDays = period.getDays();
        int totalMonths = period.getMonths();
        int totalYears = period.getYears();

        int years = totalDays / 365;
        int months = (totalDays % 365) / 30;
        int days = (totalDays % 365) % 30;

        int totalYearsCorrected = totalYears + years;
        int totalMonthsCorrected = totalMonths + months;
        int totalDaysCorrected = days;

        return  totalYearsCorrected + " năm, " + totalMonthsCorrected + " tháng và " + totalDaysCorrected + " ngày";
    }
}
