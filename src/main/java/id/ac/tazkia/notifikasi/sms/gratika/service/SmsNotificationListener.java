package id.ac.tazkia.notifikasi.sms.gratika.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.tazkia.notifikasi.sms.gratika.dto.SmsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SmsNotificationListener {
    private static final Logger logger = LoggerFactory.getLogger(SmsNotificationListener.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "${kafka.topic.email}", groupId = "${spring.kafka.consumer.group-id}")
    public void kafkaToSms(String message){
        try {
            SmsRequest sms = objectMapper.readValue(message, SmsRequest.class);
            logger.debug("====== SMS Request ======");
            logger.debug("From     : {}", sms.getSenderId());
            logger.debug("Password : {}", sms.getSenderPassword());
            logger.debug("To       : {}", sms.getDestinationNumber());
            logger.debug("Content  : {}", sms.getContent());
            logger.debug("====== SMS Request ======");
        } catch (Exception err) {
            logger.error(err.getMessage(), err);
        }
    }
}
