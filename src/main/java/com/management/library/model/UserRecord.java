package com.management.library.model;

import java.time.LocalDate;

public record UserRecord(
        String name,
        String firstName,
        LocalDate memberSince,
        LocalDate memberTill,
        String gender
) {
}

