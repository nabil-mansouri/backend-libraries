package com.rm.commons.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import com.rm.contract.commons.beans.UploadFormBean;
import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.FormDataMultiPart;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
@Service
public class UploadServiceImpl implements UploadService {
	public MediaType getType(FormDataMultiPart form, String param) {
		return form.getField(param).getMediaType();
	}

	public String getFilename(FormDataMultiPart form, String param) throws IOException {
		return form.getField(param).getContentDisposition().getFileName();
	}

	public int getSize(FormDataMultiPart form, String param) throws IOException {
		return (int) form.getField(param).getContentDisposition().getSize();
	}

	public byte[] toByte(FormDataMultiPart form, String param) throws IOException {
		BodyPartEntity bpe = (BodyPartEntity) form.getField(param).getEntity();
		InputStream stream = bpe.getInputStream();
		return IOUtils.toByteArray(stream);
	}

	public UploadFormBean toDto(FormDataMultiPart parts, String param) throws IOException {
		UploadFormBean form = new UploadFormBean();
		form.setFile(toByte(parts, param));
		form.setType(getType(parts, param).toString());
		form.setFileName(getFilename(parts, param));
		form.setSize(getSize(parts, param));
		return form;
	}

	public String toString(FormDataMultiPart form, String param) throws IOException {
		BodyPartEntity bpe = (BodyPartEntity) form.getField(param).getEntity();
		StringBuilder sb = new StringBuilder();
		InputStream stream = bpe.getInputStream();
		InputStreamReader reader = new InputStreamReader(stream);
		char[] buffer = new char[2048];
		while (true) {
			int n = reader.read(buffer);
			if (n < 0) {
				break;
			}
			sb.append(buffer, 0, n);
		}
		return sb.toString();
	}
}
