package com.ach.achViewer.ach;

import java.util.Vector;

public class ACHEntry {
	ACHRecordEntryDetail entryDetail = null;

	Vector<ACHRecordAddenda> addendaRecs = new Vector<ACHRecordAddenda>(10, 10);

	public ACHEntry() {
		setEntryDetail(new ACHRecordEntryDetail());
		addendaRecs = new Vector<ACHRecordAddenda>(10, 10);
	}

	public void reverse() throws Exception {
		String tranCode = getEntryDetail().getTransactionCode();
		int newTranCode = Integer.parseInt(tranCode);
		if (newTranCode < 50) {
			if (tranCode.charAt(1) <= '4') {
				newTranCode += 5;
			} else {
				newTranCode -= 5;
			}
		} else {
			switch (newTranCode) {
			case 51:
				newTranCode= 52;
				break;
			case 52:
			case 53:
			case 54:
				newTranCode = 51;
				break;
			case 55:
				newTranCode = 56;
				break;
			case 56:
				newTranCode = 55;
				break;
			case 81:
				newTranCode = 82;
			case 82:
				newTranCode = 81;
			case 83:
				newTranCode = 84;
			case 84:
				newTranCode = 83;
			case 85:
				newTranCode = 86;
			case 86:
				newTranCode = 85;
			}
		}
		getEntryDetail().setTransactionCode(String.valueOf(newTranCode));
	}

	/**
	 * @return the addendaRecs
	 */
	public synchronized Vector<ACHRecordAddenda> getAddendaRecs() {
		return addendaRecs;
	}

	/**
	 * @param addendaRecs
	 *            the addendaRecs to set
	 */
	public synchronized void setAddendaRecs(Vector<ACHRecordAddenda> addendaRecs) {
		this.addendaRecs = addendaRecs;
	}

	/**
	 * @param addendaRec
	 *            the addendaRec to add
	 */
	public synchronized void addAddendaRecs(ACHRecordAddenda addendaRec) {
		this.addendaRecs.add(addendaRec);
	}

	/**
	 * @return the entryDetail
	 */
	public synchronized ACHRecordEntryDetail getEntryDetail() {
		return entryDetail;
	}

	/**
	 * @param entryDetail
	 *            the entryDetail to set
	 */
	public synchronized void setEntryDetail(ACHRecordEntryDetail entryDetail) {
		this.entryDetail = entryDetail;
	}
}
