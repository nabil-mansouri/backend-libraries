package com.nm.comms.senders;

import java.util.Collection;
import java.util.Date;

import com.google.common.collect.Lists;
import com.nm.app.history.DtoHistoryDefault;
import com.nm.app.history.SoaHistory;
import com.nm.comms.constants.CommunicationType;
import com.nm.comms.constants.CommunicationType.CommunicationTypeDefault;
import com.nm.comms.constants.MessageBoxType;
import com.nm.comms.constants.MessagePartType.MessagePartTypeDefault;
import com.nm.comms.dtos.CommunicationActorDtoImpl;
import com.nm.comms.dtos.MessageDto;
import com.nm.comms.dtos.MessageDtoAsync;
import com.nm.comms.extractors.MessageExtract;
import com.nm.comms.extractors.MessageExtractPart;
import com.nm.comms.history.DtoHistoryActorComm;
import com.nm.comms.history.DtoHistorySubjectComm;
import com.nm.comms.models.CommunicationActor;
import com.nm.comms.models.Message;
import com.nm.datas.constants.AppDataContentKind;
import com.nm.datas.dtos.AppDataDtoImpl;
import com.nm.utils.ByteUtils;
import com.nm.utils.dtos.OptionsList;

/**
 * 
 * @author Nabil MANSOURI
 * 
 */
public class MessageSenderWeb implements MessageSender {
	private SoaHistory soaHistory;

	public void setSoaHistory(SoaHistory soaHistory) {
		this.soaHistory = soaHistory;
	}

	public boolean accept(CommunicationType type) {
		if (type instanceof CommunicationTypeDefault) {
			return type.equals(CommunicationTypeDefault.Web);
		}
		return false;
	}

	public void after(MessageExtract m, Message comm) throws Exception {

	}

	public void before(MessageExtract m, Message comm) throws Exception {

	}

	private class InnerProcess {
		String title;
		String content;
		Collection<byte[]> joined = Lists.newArrayList();

		public InnerProcess(MessageExtract m, CommunicationActor actor) throws Exception {
			title(m, actor);
			content(m, actor);
			joined(m, actor);
		}

		public void hydrate(DtoHistoryDefault dto, MessageBoxType type, Message mesage, CommunicationActor actor) {
			DtoHistoryActorComm ac = new DtoHistoryActorComm();
			ac.setActor(new CommunicationActorDtoImpl());
			ac.getActor().setId(actor.getId());
			dto.setActor(ac);
			dto.setWhen(new Date());
			//
			MessageDto meDto = new MessageDtoAsync().setId(mesage.getId());
			DtoHistorySubjectComm subDto = new DtoHistorySubjectComm().setType(type).setMessage(meDto);
			dto.setSubject(subDto);
			//
			subDto.getContent().put(MessagePartTypeDefault.Title,
					new AppDataDtoImpl().setText(title).setKind(AppDataContentKind.Text));
			subDto.getContent().put(MessagePartTypeDefault.Content,
					new AppDataDtoImpl().setText(content).setKind(AppDataContentKind.Text));
			subDto.getJoined().clear();
			for (byte[] d : joined) {
				subDto.getJoined().add(new AppDataDtoImpl().setFile(d));
			}
		}

		private void title(MessageExtract m, CommunicationActor actor) throws Exception {
			this.title = "";
			if (m.hasContent(MessagePartTypeDefault.Title)) {
				title = ByteUtils.toStrings(m.getContent(MessagePartTypeDefault.Title).getBy(actor).getContent());
			}

		}

		private void content(MessageExtract m, CommunicationActor actor) throws Exception {
			this.content = "";
			if (m.hasContent(MessagePartTypeDefault.Content)) {
				content = ByteUtils.toStrings(m.getContent(MessagePartTypeDefault.Content).getBy(actor).getContent());
			}
		}

		private void joined(MessageExtract m, CommunicationActor actor) throws Exception {
			for (MessageExtractPart part : m.getJoinded(actor)) {
				this.joined.add(part.getBy(actor).getContent());
			}
		}
	}

	public void send(MessageExtract m, Message comm, CommunicationActor actor) throws Exception {
		InnerProcess pro = new InnerProcess(m, actor);
		//
		{
			DtoHistoryDefault dto = new DtoHistoryDefault();
			pro.hydrate(dto, MessageBoxType.Out, comm, comm.getSender());
			soaHistory.saveOrUpdate(dto, new OptionsList());
		}
		{
			DtoHistoryDefault dto = new DtoHistoryDefault();
			pro.hydrate(dto, MessageBoxType.In, comm, actor);
			soaHistory.saveOrUpdate(dto, new OptionsList());
		}
	}
}
