package com.example.lom.controllers.metingPayload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record MetingPayload(
        @NotNull
        @Size(min = 1,max = 33, message = "Название должно быть от 1 до 33 символов")
        String title,

        @NotNull
        @Size(min = 3,max = 10 , message = "Содержание должно быть от 3 до 10 символов")
        String details,

        @NotNull(message = "Эу, дату постовь")
        Date date
) {

}