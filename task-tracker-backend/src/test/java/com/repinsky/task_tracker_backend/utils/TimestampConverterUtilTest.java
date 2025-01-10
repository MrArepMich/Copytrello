package com.repinsky.task_tracker_backend.utils;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TimestampConverterUtilTest {

    @Test
    void testFormatTimestamp(){
        String strTimestamp = "2025-01-09 00:00:00";
        Timestamp timestamp = Timestamp.valueOf(strTimestamp);
        String formatedTimestamp = TimestampConverterUtil.formatTimestamp(timestamp);

        assertEquals("09/01/2025",formatedTimestamp);
    }

    @Test
    void nullTesting(){
        Timestamp timestamp = null;
        String formatedTimestamp = TimestampConverterUtil.formatTimestamp(timestamp);

        assertNull(formatedTimestamp, "The method must return null when passed null");
    }
}
