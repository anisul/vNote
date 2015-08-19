package com.grasshopper.vnote.core;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Created by Anisul on 8/14/2015.
 */
public class EmailSender {
    private JavaMailSender mailer;
    private VelocityEngine velocityEngine;

    public EmailSender(JavaMailSender mailer, VelocityEngine velocityEngine) {
        super();
        this.mailer = mailer;
        this.velocityEngine = velocityEngine;
    }

    public JavaMailSender getMailer() {
        return mailer;
    }

    public void setMailer(JavaMailSender mailer) {
        this.mailer = mailer;
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
}
