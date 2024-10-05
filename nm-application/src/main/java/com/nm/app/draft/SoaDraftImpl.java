package com.nm.app.draft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.nm.utils.hibernate.NoDataFoundException;

/**
 * 
 * @author Nabil
 * 
 */
public class SoaDraftImpl implements SoaDraft {

	private DaoDraft daoDraft;

	public void setDaoDraft(DaoDraft daoDraft) {
		this.daoDraft = daoDraft;
	}

	@org.springframework.transaction.annotation.Transactional
	public DraftDto saveDraft(DraftType type, String payload) {
		Draft draft = new Draft();
		draft.setPayload(payload);
		draft.setType(type);
		daoDraft.saveOrUpdate(draft);
		return build(draft);
	}

	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public Collection<DraftDto> getDrafts(DraftType type) {
		List<Draft> drafts = daoDraft.getDraftsByType(type);
		Collection<DraftDto> coll = new ArrayList<DraftDto>();
		for (Draft draft : drafts) {
			coll.add(build(draft));
		}
		return coll;
	}

	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public DraftDto getLastModifiedDraft(DraftType type) throws NoDataFoundException {
		return build(daoDraft.getLastModifiedDraft(type));
	}

	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public DraftDto getDraft(Long id) throws NoDataFoundException {
		return build(daoDraft.loadById(id));
	}

	@org.springframework.transaction.annotation.Transactional
	public void remove(Long idDraft) {
		try {
			Draft draft = daoDraft.loadById(idDraft);
			daoDraft.delete(draft);
		} catch (Exception e) {

		}
	}

	public DraftDto saveOrUpdate(DraftType type, String payload, Long id) throws NoDataFoundException {
		if (id == null) {
			return saveDraft(type, payload);
		} else {
			return update(type, payload, id);
		}
	}

	public DraftDto update(DraftType type, String payload, Long id) throws NoDataFoundException {
		Draft draft = daoDraft.loadById(id);
		draft.setPayload(payload);
		daoDraft.saveOrUpdate(draft);
		return build(draft);
	}

	protected DraftDto build(Draft draft) {
		DraftDto bean = new DraftDto();
		bean.setCreated(draft.getCreatedAt());
		bean.setPayload(draft.getPayload());
		bean.setUpdated(draft.getUpdatedAt());
		bean.setId(draft.getId());
		return bean;
	}

}
