package youtube_RH;

import youtube_RH.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.security.Policy;
import java.util.StringTokenizer;

@Service
public class PolicyHandler{

    @Autowired
    AdvertisingSystemRepository avertisingSystemRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverEditedVideo_AdModified(@Payload EditedVideo editedVideo){
        if(editedVideo.isMe()){
            System.out.println("===================광고횟수 차감=====================");
            avertisingSystemRepository.findById(editedVideo.getAdId()).ifPresent(ad -> {
                ad.minusAdCnt(editedVideo.getViewCount());
                avertisingSystemRepository.save(ad);
            });
            System.out.println("##### listener AdModified : " + editedVideo.toJson());

        }

    }
}
