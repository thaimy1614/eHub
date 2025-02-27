package com.datn.school_service.Dto.Respone;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    @Builder.Default
    private int code = 1000;
    @Builder.Default // nghĩa là khi ko có giá trị khác truyền vào thì nó gán successed dô mk nghĩa z
    private String message = "successed";


    private T result;

    @Builder.Default
    private String path = null;

    @Builder.Default
    private long timestamp = System.currentTimeMillis();
}