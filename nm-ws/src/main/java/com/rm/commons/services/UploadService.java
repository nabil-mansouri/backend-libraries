package com.rm.commons.services;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import com.rm.contract.commons.beans.UploadFormBean;
import com.sun.jersey.multipart.FormDataMultiPart;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public interface UploadService {
	public String getFilename(FormDataMultiPart form, String param) throws IOException;

	public MediaType getType(FormDataMultiPart dat, String param) throws IOException;

	public int getSize(FormDataMultiPart dat, String param) throws IOException;

	public String toString(FormDataMultiPart dat, String param) throws IOException;

	public byte[] toByte(FormDataMultiPart form, String param) throws IOException;

	public UploadFormBean toDto(FormDataMultiPart form, String param) throws IOException;
}