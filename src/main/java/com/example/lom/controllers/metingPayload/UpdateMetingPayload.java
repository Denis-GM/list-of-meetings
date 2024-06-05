package com.example.lom.controllers.metingPayload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Date;


public record UpdateMetingPayload(
        @NotNull
        @Size(min = 1,max = 33, message = "Название должно быть от 1 до 33 символов")
        String title,

        @NotNull
        @Size(min = 3,max = 10, message = "Содержание должно быть от 3 до 10 символов")
        String details,

        @NotNull(message = "Содержание должно быть от 3 до 10 символов")
        Date date,

        String place

        ,
        @NotNull(message = "Людей-то скока А-Л-О???")
        int totalNumberSeats,

        Integer availableSeats) {
}
