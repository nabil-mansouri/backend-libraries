package com.rm.ws.admin.commons;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rm.commons.services.UploadService;
import com.rm.contract.commons.ImagesFormDto;
import com.rm.contract.commons.beans.UploadFormBean;
import com.rm.contract.commons.constants.ImagesMode;
import com.rm.soa.commons.SoaImages;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.FormDataParam;

/**
 * 
 * @author Nabil
 * 
 */
@Component
@Path("commons/images")
public class ImagesWS {
	@Autowired
	private SoaImages soaImages;
	@Autowired
	private UploadService uploadService;
	private ObjectMapper mapper = new ObjectMapper();

	@POST()
	@Path("{mode}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public ImagesFormDto uploadPdf(FormDataMultiPart form, @FormDataParam("file") FormDataContentDisposition header,
			@PathParam("mode") ImagesMode mode) throws FileNotFoundException, IOException {
		// TODO check if images
		ImagesFormDto images = mapper.readValue(form.getField("bean").getEntityAs(String.class), ImagesFormDto.class);
		UploadFormBean upload = uploadService.toDto(form, "file");
		soaImages.push(images, upload, mode);
		return images;
	}
}
