package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class PushService {

	@Scheduled(fixedDelay = 3000)
	public void sendSseEventsToUI() { //your model class
		List<SseEmitter> sseEmitterListToRemove = new ArrayList<>();
        for(SseEmitter emitter : PushController.emitters) {
        	try {
                emitter.send(UUID.randomUUID().toString(), MediaType.APPLICATION_JSON);
        	} catch (Throwable e) {
        		sseEmitterListToRemove.add(emitter);
        		emitter.complete();
        		// e.printStackTrace();
        	}
        };
        PushController.emitters.removeAll(sseEmitterListToRemove);
    }
	
}
