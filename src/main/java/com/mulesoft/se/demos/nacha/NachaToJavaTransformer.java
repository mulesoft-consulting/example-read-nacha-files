package com.mulesoft.se.demos.nacha;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.config.i18n.Message;
import org.mule.transformer.AbstractMessageTransformer;

import com.ach.achViewer.ach.ACHFile;

public class NachaToJavaTransformer extends AbstractMessageTransformer {
	
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding)
			throws TransformerException {
		String contents = (String)message.getPayload();
		ACHFile file = new ACHFile();
		try {
			file.parseString(contents);
		} catch (Exception e) {
			
		}
		return file;
	}
}
