package com.repinsky.task_tracker_backend.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppError {
    private String code;
    private String value;
}
