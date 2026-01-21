package com.poly.lab8.Controller;



import com.poly.lab8.Entity.MailForm;
import com.poly.lab8.Service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class MailController {

    @Autowired
    MailService mailService;
    //   BÀI 1
    @GetMapping("/mail/send")
    @ResponseBody
    public String send() {
        mailService.send("trungle2962004@gmail.com", "Subject", "Body"); // gọi hàm mặc định
        return "Mail của bạn đã được gửi đi";
    }

    //  BÀI 2
    @GetMapping("/mail/sends")
    @ResponseBody
    public String sendQueued(){
        mailService.push("trungle2962004@gmail.com","Subject","Body"); // đẩy vào queue
        return "Mail của bạn đã được xếp vào hàng đợi";
    }

    //   BÀI 3
    @GetMapping("mail/form")
    public String mailForm(Model model,
                           @RequestParam (value = "message", required = false) String message) {
        model.addAttribute("mailForm", new MailForm());
        model.addAttribute("message", message);
        return "mail/form";
    }


    @PostMapping("/mail/send-now")
    public String sendNow(@ModelAttribute("mail") MailForm form) throws IOException {
        String filenames = saveUploads(form.getAttachments());
        mailService.send(
                MailService.Mail.builder()
                        .from(emptyToNull(form.getFrom()))
                        .to(form.getTo())
                        .cc(emptyToNull(form.getCc()))
                        .bcc(emptyToNull(form.getBcc()))
                        .subject(form.getSubject())
                        .body(form.getBody())
                        .filename(filenames)
                        .build()
        );
        return "redirect:/mail/form?message=" + url("Đã gửi trực tiếp tới " + form.getTo());
    }

    // ==== XẾP VÀO HÀNG ĐỢI (Scheduled sẽ gửi nền) ====
    @PostMapping("/mail/send-queue")
    public String sendQueue(@ModelAttribute("mail") MailForm form) throws IOException {
        String filenames = saveUploads(form.getAttachments()); //  lưu file -> chuỗi đường dẫn
        mailService.push(                                  //  chỉ xếp hàng đợi
                MailService.Mail.builder()
                        .from(emptyToNull(form.getFrom()))
                        .to(form.getTo())
                        .cc(emptyToNull(form.getCc()))
                        .bcc(emptyToNull(form.getBcc()))
                        .subject(form.getSubject())
                        .body(form.getBody())
                        .filename(filenames)
                        .build()
        );
        return "redirect:/mail/form?message=" + url("Đã xếp vào hàng đợi cho " + form.getTo());
    }

    // --- Helper: Lưu nhiều file vào thư mục /uploads và trả về chuỗi path phân tách bằng dấu phẩy ---
    private String saveUploads(MultipartFile[] files) throws IOException {
        if (files == null || files.length == 0) return null;          //  không có file -> null
        Path uploadDir = Paths.get("uploads");                         //  thư mục cùng cấp nơi chạy app
        Files.createDirectories(uploadDir);                            //  tạo nếu chưa có

        List<String> saved = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");
        String stamp = LocalDateTime.now().format(fmt);                //  tránh trùng tên

        for (MultipartFile f : files) {
            if (f != null && !f.isEmpty()) {
                String safeName = stamp + "_" + f.getOriginalFilename();   //  tên file an toàn
                Path dest = uploadDir.resolve(safeName);
                Files.copy(f.getInputStream(), dest);                      //  lưu file
                saved.add(dest.toFile().getAbsolutePath());                //  lấy path tuyệt đối
            }
        }
        return saved.isEmpty() ? null : String.join(",", saved);       // MailServiceImpl dùng chuỗi
    }

    // --- Helper: trả null nếu chuỗi rỗng (để MailServiceImpl bỏ qua cc/bcc/from) ---
    private String emptyToNull(String s) {

        return (s == null || s.trim().isEmpty()) ? null : s.trim();
    }

    // --- Helper: encode nhanh message trên URL ---
    private String url(String s) { // đơn giản: thay khoảng trắng cho readable

        return s.replace(" ", "%20");
    }
}
