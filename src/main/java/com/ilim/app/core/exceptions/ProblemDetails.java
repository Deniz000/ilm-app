package com.ilim.app.core.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemDetails {
    String message;
    //kullanıcıya sistemle ilgili hata bilgisi vermek yerine bunu döndürücem
}
