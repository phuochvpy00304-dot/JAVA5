package web.model;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Staff {

    @NotBlank(message = "Chưa nhập Email")
    @Email(message = "Email không đúng định dạng")
    private String id;

    @NotBlank(message = "Chưa nhập họ và tên")
    private String fullname;

    @Builder.Default
    private String photo = "photo.jpg";
    @NotNull(message = "Chưa chọn giới tính")
    @Builder.Default
    private Boolean gender = true;

    @NotNull(message = "Chưa nhập ngày sinh")
    @Past(message = "Ngày sinh phải trước hiện tại")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Builder.Default
    private Date birthday = new Date();

    @NotNull(message = "Chưa nhập lương")
    @Min(value = 1000, message = "Lương tối thiểu là 1000")
    @Builder.Default
    private Double salary = 12345.6789;

    @Builder.Default
    private Integer level = 2;
}
