package com.jrj.pay.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class UserCookieKey {
	@Value("${usecookie}")
	private  String usecookie;

}
