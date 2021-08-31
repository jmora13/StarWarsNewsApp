package com.example.phunapp.utilities

import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object Utilities {

    //PARSE DATE FROM ISO 8601
     fun parseDate(dateTime: String?): String?{ //TURNS INTO GREGORIAN CALENDAR FORMAT
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        var zoneDT = ZonedDateTime.parse(dateTime, formatter.withZone(ZoneId.of("GMT")))
        var adjustedTimeWithOffSet = zoneDT.withZoneSameInstant(ZoneId.systemDefault()).format(
            DateTimeFormatter.ISO_OFFSET_DATE_TIME).toString()
        var yearMonthDayFormat = SimpleDateFormat("yyyy-MM-dd")
        var date: Date? = null
        try {
            date = yearMonthDayFormat.parse(adjustedTimeWithOffSet)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        var monthDayYear = SimpleDateFormat("MMM dd, yyyy")
        return monthDayYear.format(date)
    }

    //CONVERTS ISO 8601 TO 12H FORMAT
     fun parseTime(dateTime: String?): String{
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        var zoneDT = ZonedDateTime.parse(dateTime, formatter.withZone(ZoneId.of("GMT")))
        var adjustedTimeWithOffSet = zoneDT.withZoneSameInstant(ZoneId.systemDefault()).format(
            DateTimeFormatter.ISO_OFFSET_DATE_TIME).toString()
        var timeFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        var date: Date? = null
        try {
            date = timeFormatter.parse(adjustedTimeWithOffSet)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        timeFormatter = SimpleDateFormat("h:mma")
        var finalDT = timeFormatter.format(date)
        var meridiem = finalDT.substring(finalDT.lastIndex-1, finalDT.lastIndex+1)
        meridiem = meridiem.lowercase()
        finalDT = finalDT.removeRange(finalDT.lastIndex-1, finalDT.lastIndex+1)
        return "$finalDT$meridiem"
    }
}