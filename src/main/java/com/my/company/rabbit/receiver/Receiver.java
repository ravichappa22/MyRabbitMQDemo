package com.my.company.rabbit.receiver;

import org.springframework.stereotype.Component;

@Component
public class Receiver {
	
	//private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
       // latch.countDown();
    }
    
    public void receiveMessage(byte[] message) {
        System.out.println("Received <" + new String(message) + ">");
       // latch.countDown();
    }

   /* public CountDownLatch getLatch() {
        return latch;
    }*/

}
