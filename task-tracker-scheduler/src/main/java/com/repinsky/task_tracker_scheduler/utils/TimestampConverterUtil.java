package com.repinsky.task_tracker_scheduler.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;

import static com.repinsky.task_tracker_scheduler.constants.Constant.TODAY_END;
import static com.repinsky.task_tracker_scheduler.constants.Constant.TODAY_START;

public class TimestampConverterUtil {

    public static HashMap<String, Timestamp> convertLocalDateTimeToTimestamp() {
        HashMap<String, Timestamp> todayDiapasonTimestamp = new HashMap<>();
        LocalDateTime todayStart = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime todayEnd = LocalDateTime.now().with(LocalTime.MAX);

        Timestamp todayStartTimestamp = Timestamp.valueOf(todayStart);
        Timestamp todayEndTimestamp = Timestamp.valueOf(todayEnd);

        todayDiapasonTimestamp.put(TODAY_START.getValue(), todayStartTimestamp);
        todayDiapasonTimestamp.put(TODAY_END.getValue(), todayEndTimestamp);
        return todayDiapasonTimestamp;
    }
}
