package id.ac.tazkia.notifikasi.sms.gratika.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.tazkia.notifikasi.sms.gratika.dto.SmsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SmsNotificationListener {
    private static final Logger logger = LoggerFactory.getLogger(SmsNotificationListener.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired private GratikaService gratikaService;

    @KafkaListener(topics = "${kafka.topic.email}", groupId = "${spring.kafka.consumer.group-id}")
    public void kafkaToSms(String message){
        try {
            SmsRequest sms = objectMapper.readValue(message, SmsRequest.class);
            logger.debug("====== SMS Request ======");
            logger.debug("Username : {}", sms.getUsername());
            logger.debug("Password : {}", sms.getPassword());
            logger.debug("From     : {}", sms.getSenderId());
            logger.debug("To       : {}", sms.getDestinationNumber());
            logger.debug("Content  : {}", sms.getContent());
            logger.debug("====== SMS Request ======");

            gratikaService.sendSms(sms);
        } catch (Exception err) {
            logger.error(err.getMessage(), err);
        }
    }
}
