package com.bej.EmailNotification.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailStructure {
    private String subject;
    private String message;

    public MailStructure(String subject, String message) {
        this.subject = subject;
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MailStructure{" +
                "subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
