package com.poly.lab8.Entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MailForm {

    private String from;
    private String to;          // người nhận chính (bắt buộc)
    private String cc;          // cc (tùy chọn, có thể để rỗng)
    private String bcc;         // bcc (tùy chọn, có thể để rỗng)
    private String subject;     // tiêu đề
    private String body;        // nội dung (HTML ok)
    private MultipartFile[] attachments; // nhiều file đính kèm
}
