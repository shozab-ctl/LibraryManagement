package com.management.library.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public interface DateFormatter {

    public Function<LocalDate, LocalDate> convertDate = (date) -> {
        var pattern = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        var string = date.format(pattern);
        return LocalDate.parse(string, pattern);
    };
}
