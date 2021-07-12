package local;

import local.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MyPageViewHandler {


    @Autowired
    private MyPageRepository myPageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenRequested_then_CREATE_1 (@Payload Requested requested) {
        try {
            if (requested.isMe()) {
                // view 객체 생성
                MyPage myPage = new MyPage();
                // view 객체에 이벤트의 Value 를 set 함
                myPage.setOrderId(requested.getId());
                myPage.setCustNm(requested.getCustNm());
                myPage.setCafeNm(requested.getCafeNm());
                myPage.setStatus(requested.getStatus());
                // view 레파지토리에 save
                myPageRepository.save(myPage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    @StreamListener(KafkaProcessor.INPUT)
    public void whenCanceled_then_UPDATE_1(@Payload Canceled canceled) {
        try {
            if (canceled.isMe()) {
                // view 객체 조회
                List<MyPage> myPageList = myPageRepository.findByOrderId(canceled.getId());
                for(MyPage myPage : myPageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    myPage.setStatus(canceled.getStatus());
                    // view 레파지 토리에 save
                    myPageRepository.save(myPage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenForceCanceled_then_UPDATE_2(@Payload ForceCanceled forceCanceled) {
        try {
            if (forceCanceled.isMe()) {
                // view 객체 조회
                List<MyPage> myPageList = myPageRepository.findByOrderId(forceCanceled.getId());
                for(MyPage myPage : myPageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    myPage.setStatus(forceCanceled.getStatus());
                    // view 레파지 토리에 save
                    myPageRepository.save(myPage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenProductionCompleted_then_UPDATE_3(@Payload ProductionCompleted productionCompleted) {
        try {
            if (productionCompleted.isMe()) {
                // view 객체 조회
                List<MyPage> myPageList = myPageRepository.findByOrderId(productionCompleted.getOrderId());
                for (MyPage myPage : myPageList) {
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    myPage.setStatus(productionCompleted.getStatus());
                    // view 레파지 토리에 save
                    myPageRepository.save(myPage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    *        신규 개발 조직 추가로 인한 CQRS 연동
    * */

    @StreamListener(KafkaProcessor.INPUT)
    public void whenIncrementRequested_then_CREATE_2 (@Payload IncrementRequested incrementRequested) {
        try {
            if (incrementRequested.isMe()) {
                // view 객체 생성
                MyPage myPage = new MyPage();
                // view 객체에 이벤트의 Value 를 set 함
                myPage.setCafeNm(incrementRequested.getCafeNm());
                myPage.setStatus(incrementRequested.getStatus());
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}