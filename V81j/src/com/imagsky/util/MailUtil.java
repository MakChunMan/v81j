package com.imagsky.util;

import com.imagsky.util.logger.PortalLogger;
import com.imagsky.common.PropertiesConstants;
import java.io.*;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class MailUtil {

    private String host = null;
    private String toAddress = null;
    private String fromAddress = null;
    private String bccAddress = null;
    private String subject = null;
    private String htmlContent = null;
    private String fromName = null;
    
    private String gmailUser = null;
    private String gmailPwd = null;
    
    public MailUtil() {
        host = PropertiesConstants.get(PropertiesConstants.smtp);
        fromAddress = PropertiesConstants.get(PropertiesConstants.salesAddress);
        bccAddress = PropertiesConstants.get(PropertiesConstants.salesAddress);
        if(host.indexOf("gmail")>=0){
            gmailUser = PropertiesConstants.get("email_smtp_user");
            gmailPwd = PropertiesConstants.get("email_smtp_pwd");
        }
    }
    
    public void setFromName(String fromName){
        this.fromName = fromName;
    }

    public void setToAddress(String toAddr) {
        this.toAddress = toAddr;
    }

    public void setFromAddress(String fromAddr) {
        this.fromAddress = fromAddr;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.htmlContent = content;
    }

    public boolean send() {
        if (!V81Util.isEmailOn()) {
            return true;
        }

        // SUBSTITUTE YOUR EMAIL ADDRESSES HERE!!!
        String to = toAddress;
        String from = fromAddress;
        // SUBSTITUTE YOUR ISP'S MAIL SERVER HERE!!!
        String host = this.host;

        // Create properties for the Session
        Properties props = new Properties();

        // If using static Transport.send(),
        // need to specify the mail server here
        props.put("mail.smtp.host", host);
        // To see what is going on behind the scene
        //props.put("mail.debug", "true");

        // Get a session
        // 2013-02-01 Change SMTP to gmail
        Session session;
        
        if(CommonUtil.isNullOrEmpty(gmailUser)){
            session = Session.getInstance(props);
        } else {
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.port", "587");
            session = Session.getInstance(props,new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(gmailUser, gmailPwd);
			}
		  });
        }

        try {
            // Get a Transport object to send e-mail
            Transport bus = session.getTransport("smtp");

            // Connect only once here
            // Transport.send() disconnects after each send
            // Usually, no username and password is required for SMTP
            bus.connect();
            //bus.connect("smtpserver.yourisp.net", "username", "password");

            // Instantiate a message
            MimeMessage msg = new MimeMessage(session);

            // Set message attributes
            if(CommonUtil.isNullOrEmpty(fromName)){
                msg.setFrom(new InternetAddress(from));
            } else {
                msg.setFrom(new InternetAddress(fromName + "<" + from+">"));
            }
            
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            // Parse a comma-separated list of email addresses. Be strict.
            //msg.setRecipients(Message.RecipientType.CC,
            //                    InternetAddress.parse(to, true));
            // Parse comma/space-separated list. Cut some slack.
            msg.setRecipients(Message.RecipientType.BCC,
                    InternetAddress.parse(bccAddress, false));


            try {
                msg.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
            } catch (UnsupportedEncodingException e) {
            }
            //msg.setSubject(subject,"utf-8");
            msg.setHeader("Content-Type", "text/plain; charset=UTF-8");
            msg.setSentDate(new Date());

            // Set message content and send
            /*
             * setTextContent(msg); msg.saveChanges(); bus.sendMessage(msg,
             * address);
             */
            /*
             * setMultipartContent(msg); msg.saveChanges(); bus.sendMessage(msg,
             * address);
             */
            /*
             * setFileAsAttachment(msg, "C:/WINDOWS/CLOUD.GIF");
             * msg.saveChanges(); bus.sendMessage(msg, address);
             */

            setHTMLContent(msg, htmlContent);
            msg.saveChanges();
            bus.sendMessage(msg, address);

            bus.close();
            return true;

        } catch (MessagingException mex) {
            PortalLogger.error("Mail Sending Error: " + mex.getMessage());
            // Prints all nested (chained) exceptions as well
            mex.printStackTrace();
            // How to access nested exceptions
            while (mex.getNextException() != null) {
                // Get next exception in chain
                Exception ex = mex.getNextException();
                PortalLogger.error("Mail Sending Error: " + ex.getMessage());
                if (!(ex instanceof MessagingException)) {
                    break;
                } else {
                    mex = (MessagingException) ex;
                }
            }
            return false;
        }
    }

    // A simple, single-part text/plain e-mail.
    public static void setTextContent(Message msg) throws MessagingException {
        // Set message content
        String mytxt = "This is a test of sending a "
                + "plain text e-mail through Java.\n"
                + "Here is line 2.";
        msg.setText(mytxt);

        // Alternate form
        msg.setContent(mytxt, "text/plain");

    }

    // A simple multipart/mixed e-mail. Both body parts are text/plain.
    public static void setMultipartContent(Message msg) throws MessagingException {
        // Create and fill first part
        MimeBodyPart p1 = new MimeBodyPart();
        p1.setText("This is part one of a test multipart e-mail.");

        // Create and fill second part
        MimeBodyPart p2 = new MimeBodyPart();
        // Here is how to set a charset on textual content
        p2.setText("This is the second part", "us-ascii");

        // Create the Multipart.  Add BodyParts to it.
        Multipart mp = new MimeMultipart();
        mp.addBodyPart(p1);
        mp.addBodyPart(p2);

        // Set Multipart as the message's content
        msg.setContent(mp);
    }

    // Set a file as an attachment.  Uses JAF FileDataSource.
    public static void setFileAsAttachment(Message msg, String filename)
            throws MessagingException {

        // Create and fill first part
        MimeBodyPart p1 = new MimeBodyPart();
        p1.setText("This is part one of a test multipart e-mail."
                + "The second part is file as an attachment");

        // Create second part
        MimeBodyPart p2 = new MimeBodyPart();

        // Put a file in the second part
        FileDataSource fds = new FileDataSource(filename);
        p2.setDataHandler(new DataHandler(fds));
        p2.setFileName(fds.getName());

        // Create the Multipart.  Add BodyParts to it.
        Multipart mp = new MimeMultipart();
        mp.addBodyPart(p1);
        mp.addBodyPart(p2);

        // Set Multipart as the message's content
        msg.setContent(mp);
    }

    // Set a single part html content.
    // Sending data of any type is similar.
    public static void setHTMLContent(Message msg, String htmlcontent) throws MessagingException {

        String html = "<html><head><title>"
                + msg.getSubject()
                + "</title></head><body>" + htmlcontent
                + "</body></html>";

        // HTMLDataSource is an inner class
        msg.setDataHandler(new DataHandler(new HTMLDataSource(html)));
    }

    /*
     * Inner class to act as a JAF datasource to send HTML e-mail content
     */
    static class HTMLDataSource implements DataSource {

        private String html;

        public HTMLDataSource(String htmlString) {
            html = htmlString;
        }

        // Return html string in an InputStream.
        // A new stream must be returned each time.
        public InputStream getInputStream() throws IOException {
            if (html == null) {
                throw new IOException("Null HTML");
            }
            return new ByteArrayInputStream(html.getBytes("UTF-8"));
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("This DataHandler cannot write HTML");
        }

        public String getContentType() {
            return "text/html;charset=utf-8";
        }

        public String getName() {
            return "JAF text/html dataSource to send e-mail only";
        }
    }
} //End of class