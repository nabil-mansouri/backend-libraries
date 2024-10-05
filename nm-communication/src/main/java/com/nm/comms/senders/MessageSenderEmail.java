package com.nm.comms.senders;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.Lists;
import com.nm.app.mail.EmailBuilderContractDefault;
import com.nm.app.mail.SoaEmail;
import com.nm.comms.constants.CommunicationType;
import com.nm.comms.constants.CommunicationType.CommunicationTypeDefault;
import com.nm.comms.constants.MessageMetaExtractorKey.MessageMetaExtractorKeyDefault;
import com.nm.comms.constants.MessagePartType.MessagePartTypeDefault;
import com.nm.comms.extractors.MessageExtract;
import com.nm.comms.extractors.MessageExtractPart;
import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.Message;
import com.nm.utils.ByteUtils;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class MessageSenderEmail implements MessageSender {

	protected SoaEmail soaEmail;

	public void setSoaEmail(SoaEmail soaEmail) {
		this.soaEmail = soaEmail;
	}

	public boolean accept(CommunicationType type) {
		if (type instanceof CommunicationTypeDefault) {
			return type.equals(CommunicationTypeDefault.Email);
		}
		return false;
	}

	public void after(MessageExtract m, Message comm) throws Exception {

	}

	public void before(MessageExtract m, Message comm) throws Exception {

	}

	public void send(final MessageExtract m, Message comm, final CommunicationActor actor) throws Exception {
		soaEmail.send(new EmailBuilderContractDefault() {

			public String subject() throws Exception {
				if (m.hasContent(MessagePartTypeDefault.Title)) {
					return ByteUtils.toStrings(m.getContent(MessagePartTypeDefault.Title).getBy(actor).getContent());
				}
				return null;
			}

			public String body() throws Exception {
				if (m.hasContent(MessagePartTypeDefault.Content)) {
					return ByteUtils.toStrings(m.getContent(MessagePartTypeDefault.Content).getBy(actor).getContent());
				}
				return null;
			}

			public Collection<String> to() {
				String to = m.getOrCreateMeta(actor).getBy(MessageMetaExtractorKeyDefault.MailTo);
				return Arrays.asList(to);
			}

			@Override
			public Collection<File> joined() throws Exception {
				Collection<File> f = Lists.newArrayList();
				for (MessageExtractPart part : m.getJoinded(actor)) {
					File temp = File.createTempFile("part_email", ".txt");
					FileUtils.writeByteArrayToFile(temp, part.getBy(actor).getContent());
					f.add(temp);
				}
				return f;
			}
		});
	}

}
