package com.aeye.modules.ht.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SendEmailDTO implements Serializable {

	private  String verId;
	private  String subject;
	private  String content;
	private  String toEmail;
	private  String toName;
	private  String ccEmail;
	private  String ccName;
	private  String pwd;

}
