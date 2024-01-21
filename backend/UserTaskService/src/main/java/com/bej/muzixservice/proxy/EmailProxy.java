package com.bej.muzixservice.proxy;

import com.bej.muzixservice.domain.MailStructure;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="email-notification-service", url="http://localhost:8085")
public interface EmailProxy {

    @PostMapping("/mail/send/{mail}")
    public String sendMail(@PathVariable String mail, @RequestBody MailStructure mailStructure);
}
