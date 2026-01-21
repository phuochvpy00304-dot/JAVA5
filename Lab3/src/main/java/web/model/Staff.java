package web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Staff {
    private String id;                        // email
    private String fullname;

    @Builder.Default
    private String photo = "photo.jpg";

    @Builder.Default
    private Boolean gender = true;

    @Builder.Default
    private Date birthday = new Date();

    @Builder.Default
    private Double salary = 12345.6789;

    @Builder.Default
    private Integer level = 0; // 0: Úy, 1: Tá, 2: Tướng
}
