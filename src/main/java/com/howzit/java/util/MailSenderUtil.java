package com.howzit.java.util;


import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Properties;


/**
 * Created with IntelliJ IDEA.
 * UserEntity: Ashif.Qureshi
 * Date: 3/4/13
 * Time: 6:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class MailSenderUtil {


    private static Session getSession() {
        Properties props = new Properties();
        final String username = CommonUtil.getProperty("mail.smtp.user");
        final String password = CommonUtil.getProperty("mail.smtp.password");
        props.put("mail.smtp.starttls.enable", CommonUtil.getProperty("mail.smtp.starttls.enable"));
        props.put("mail.smtp.host", CommonUtil.getProperty("mail.smtp.host"));
        props.put("mail.smtp.port", CommonUtil.getProperty("mail.smtp.port"));
        props.put("mail.smtp.auth", CommonUtil.getProperty("mail.smtp.auth"));

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        return session;
    }


    public static void send(SimpleMailMessage simpleMailMessage, VelocityEngine velocityEngine, String templateName, Map model)
            throws MessagingException, VelocityException {
        Message msg = new MimeMessage(getSession());
        msg.setFrom(new InternetAddress(CommonUtil.getProperty("mail.smtp.from.email")));
        for (String to : simpleMailMessage.getTo()) {
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        }
        msg.setSubject(simpleMailMessage.getSubject());
        String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, model);
        msg.setText(body);
        Transport.send(msg);
    }

    /*public static void test() throws IOException {
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource webResource = client.resource("http://localhost:9090/berylApp/coupons/6/steps.xml?single_access_token=6icP5qiaa-m_4Ns2WVUY");
        MultivaluedMapImpl values = new MultivaluedMapImpl();
        values.add("step[type]", "Composite");
        String file = new String(Base64.encode(FileUtils.readFileToByteArray(new File("D:\\ingenico\\project\\source\\incendo\\app\\berylApp\\web\\images\\images2.jpg"))));
        values.add("step[image]", file);
        //ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA).post(ClientResponse.class, values);
        System.out.println(file);
    }*/
}
