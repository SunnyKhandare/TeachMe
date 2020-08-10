package com.teachme.teachme.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ContractLogDTO {

    @NotBlank(message = "Log message can not be empty")
    private String logMessage;


    @NotNull(message = "createdDate timestamp can not be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm")
    @Past
    private LocalDateTime createdDate;

    @NotNull(message = "endDate  can not be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm")
    @Past
    private LocalDateTime endDate;

}
