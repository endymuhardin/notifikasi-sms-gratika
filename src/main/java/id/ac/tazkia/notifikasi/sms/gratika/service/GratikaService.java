package id.ac.tazkia.notifikasi.sms.gratika.service;

import id.ac.tazkia.notifikasi.sms.gratika.dto.SmsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Service
public class GratikaService {

    private static final Logger logger = LoggerFactory.getLogger(GratikaService.class);

    @Value("${gratika.url}")
    private String gratikaUrl;
    private WebClient webClient;

    @PostConstruct
    public void initWebClient(){
        webClient = WebClient.create(gratikaUrl);
    }

    public void sendSms(SmsRequest request){
        try {
            logger.debug("Send request to : {}", gratikaUrl);
            Mono<String> result = webClient.get()
                    .uri("/sendSms.do?username={username}&password={password}&senderid={senderid}&msisdn={destination}&pesan={content}",
                            request.getUsername(),
                            request.getPassword(),
                            request.getSenderId(),
                            request.getDestinationNumber(),
                            request.getContent())
                    .retrieve()
                    .bodyToMono(String.class);
            String response = result.block();
            logger.debug("Response : {}", response);
            logger.info("Send sms to {} [{}]", request.getDestinationNumber(), response);
        } catch (Exception err){
            logger.error(err.getMessage(), err);
        }
    }
}
