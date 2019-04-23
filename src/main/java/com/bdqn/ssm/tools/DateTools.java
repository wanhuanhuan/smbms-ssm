package com.bdqn.ssm.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class DateTools implements Converter<String, Date>{
	
	private String[] formats;
	
	public void setFormats(String[] formats) {
		this.formats = formats;
	}



	public Date convert(String dateStr){
		SimpleDateFormat format=null;
		for (int i = 0; i < dateStr.length(); i++) {
			format=new SimpleDateFormat(formats[i]);
			try {
				Date date=format.parse(dateStr);
				return date;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				continue;
			}
		}
		return null;
	}

	
}
