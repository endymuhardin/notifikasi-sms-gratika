package id.ac.tazkia.notifikasi.sms.gratika.dto;

import lombok.Data;

@Data
public class SmsRequest {
    private String senderId;
    private String username;
    private String password;
    private String destinationNumber;
    private String content;
}