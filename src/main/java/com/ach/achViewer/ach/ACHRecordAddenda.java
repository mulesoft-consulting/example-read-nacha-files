/*
 * Created on Apr 10, 2007
 *
 */
package com.ach.achViewer.ach;

public class ACHRecordAddenda extends ACHRecord {

	String addendaTypeCode = spaceFill(2);

	String paymentRelatedInfo = spaceFill(80);

	String addendaSeqNbr = spaceFill(4);

	String entryDetailSeqNbr = spaceFill(7);

	public ACHRecordAddenda() {
		super(ACHRecord.ADDENDA_TYPE);
		setAddendaSeqNbr("1");
		setEntryDetailSeqNbr("1");
	}

	public ACHRecordAddenda(String record) {
		super(record);
		parseRecord(record);
	}

	private void parseRecord(String record) {

		if (record.length() >= 3) {
			setAddendaTypeCode(record.substring(1, 3));
		}
		if (record.length() >= 83) {
			setPaymentRelatedInfo(record.substring(3, 83));
		}
		if (record.length() >= 87) {
			setAddendaSeqNbr(record.substring(83, 87));
		}
		if (record.length() >= 94) {
			setEntryDetailSeqNbr(record.substring(87, 94));
		}
	}

	public String toString() {

		return super.toString() + addendaTypeCode + paymentRelatedInfo
				+ addendaSeqNbr + entryDetailSeqNbr;
	}

	/**
	 * @return Returns the addendaSeqNbr.
	 */
	public String getAddendaSeqNbr() {
		return addendaSeqNbr;
	}

	/**
	 * @param addendaSeqNbr
	 *            The addendaSeqNbr to set.
	 */
	public void setAddendaSeqNbr(String addendaSeqNbr) {
		this.addendaSeqNbr = formatACHDecimal(addendaSeqNbr, "0000", 1);
	}

	/**
	 * @return Returns the addendaTypeCode.
	 */
	public String getAddendaTypeCode() {
		return addendaTypeCode;
	}

	/**
	 * @param addendaTypeCode
	 *            The addendaTypeCode to set.
	 */
	public void setAddendaTypeCode(String addendaTypeCode) {

		this.addendaTypeCode = formatACHDecimal(addendaTypeCode, "00");
	}

	/**
	 * @return Returns the entryDetailSeqNbr.
	 */
	public String getEntryDetailSeqNbr() {
		return entryDetailSeqNbr;
	}

	/**
	 * @param entryDetailSeqNbr
	 *            The entryDetailSeqNbr to set.
	 */
	public void setEntryDetailSeqNbr(String entryDetailSeqNbr) {
		this.entryDetailSeqNbr = formatACHDecimal(entryDetailSeqNbr, "0000000");
	}

	/**
	 * @return Returns the paymentRelatedInfo.
	 */
	public String getPaymentRelatedInfo() {
		return paymentRelatedInfo;
	}

	/**
	 * @param paymentRelatedInfo
	 *            The paymentRelatedInfo to set.
	 */
	public void setPaymentRelatedInfo(String paymentRelatedInfo) {
		this.paymentRelatedInfo = formatString(paymentRelatedInfo, 80);
	}

}
