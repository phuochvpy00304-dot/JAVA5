package com.poly.lab8.Service;

import lombok.Builder;
import lombok.Data;

public interface MailService {
    @Data
    @Builder
    public static class Mail{
        @Builder.Default
        private String from ="trungle2962004@gmail.com";
        private String to, cc, bcc;
        private String subject;
        private String body;
        private String filename;

    }
    void send(Mail mail);

    default void send(String to, String subject, String body) {
        this.send(Mail.builder().to(to).subject(subject).body(body).build());
    }
    //  Đẩy mail vào hàng đợi (không gửi ngay)
    void push(Mail mail);

    //  Đẩy nhanh vào hàng đợi
    default void push(String to, String subject, String body) {
        this.push(Mail.builder().to(to).subject(subject).body(body).build());
    }
}
