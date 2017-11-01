package id.ac.tazkia.notifikasi.sms.gratika.dto;

import lombok.Data;

@Data
public class SmsRequest {
    private String destinationNumber;
    private String content;
}