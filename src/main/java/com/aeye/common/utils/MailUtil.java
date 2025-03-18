package com.aeye.common.utils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

public class MailUtil {

    public static void sendMsg(String[] to , String[] toName ,
                               String[] cc ,  String[] ccName ,
                               String pp , String subject , String content, File file) throws Exception{
        // 创建属性文件
        Properties props = new Properties();
        // 设置主机地址   smtp.qq.com    smtp.sina.com  使用的本地易邮服务器
        props.setProperty("mail.smtp.host", "mail.smtp.host");
        // 认证，提供用户名和密码进行校验
        props.setProperty("mail.smtp.auth", "true");
        //2.产生一个用于邮件发送的Session对象，连接服务器主机
        Session session = Session.getInstance(props);
        //3.产生一个邮件的消息对象
        MimeMessage message = new MimeMessage(session);
        //4.设置消息的发送者
        Address fromAddr = new InternetAddress("heniandong@a-eye.cn");
        message.setFrom(fromAddr);

        //5.设置消息的接收者 TO 直接发送  CC抄送    BCC密送
        Address toAdds[] = new Address[to.length];
        for (int i=0;i<to.length;i++){
            toAdds[i] = new InternetAddress(to[i],toName[i]);
        }
        message.setRecipients(RecipientType.TO, toAdds);

        if (cc!=null && cc.length>0){
            Address ccAdds[] = new Address[cc.length];
            for (int i=0;i<cc.length;i++){
                ccAdds[i] = new InternetAddress(cc[i],ccName[i]);
            }
            message.setRecipients(RecipientType.CC, ccAdds);
        }
        //6.设置主题CC
        message.setSubject(subject);
        //7.设置正文
        message.setText("设置正文");

        //准备正文的数据
        MimeBodyPart text=new MimeBodyPart();
        text.setContent(content,"text/html;charset=UTF-8");

        //=================================准备附件数据

        MimeMultipart mm=new MimeMultipart();
        MimeBodyPart body= new MimeBodyPart();
        if(file != null){
            body.setDataHandler(new DataHandler(new FileDataSource(file)));
            body.setFileName(file.getName());
            mm.addBodyPart(body);
        }
        mm.addBodyPart(text);

        //=================================准备图片数据
        MimeBodyPart image=new MimeBodyPart();
        //图片需要经过数据化的处理
        File fileImg = new File("D:\\aeye\\aeyelog.png");
        if(fileImg.exists()){
            DataHandler dh=new DataHandler(new FileDataSource(fileImg));
            //在part中放入这个处理过图片的数据
            image.setDataHandler(dh);
            //给这个part设置一个ID名字
            image.setContentID("aeyelog.png");
            mm.addBodyPart(image);
        }

        //设置到消息中，保存修改
        message.setContent(mm);
        message.saveChanges();

        //8.准备发送，得到火箭
        Transport transport = session.getTransport("smtp");
        //9.设置火箭的发射目标
        transport.connect("mail.a-eye.cn", "heniandong@a-eye.cn", "Hend.1100");
        //10.发送
        transport.sendMessage(message, message.getAllRecipients());
        //11.关闭
        transport.close();
    }

}