/*
 * Created on Apr 10, 2007
 *
 */
package com.ach.achViewer.ach;

public class ACHRecordEntryDetail extends ACHRecord {

	String transactionCode = spaceFill(2);

	String receivingDfiId = spaceFill(8);

	String checkDigit = spaceFill(1);

	String dfiAcctNbr = spaceFill(17);

	String amount = spaceFill(10);

	String individualIdNbr = spaceFill(15);

	String individualName = spaceFill(22);

	String discretionaryData = spaceFill(2);

	String addendaRecordInd = spaceFill(1);

	String traceNumber = spaceFill(15);

	public ACHRecordEntryDetail() {
		super(ACHRecord.ENTRY_DETAIL_TYPE);
		setTransactionCode("22");
		setReceivingDfiId("00000000");
		setCheckDigit("0");
		setAmount("0");
		setAddendaRecordInd("0");
		setTraceNumber("1");
	}

	public ACHRecordEntryDetail(String record) {
		super(record);
		parseRecord(record);
	}

	private void parseRecord(String record) {

		if (record.length() >= 3) {
			setTransactionCode(record.substring(1, 3));
		}

		if (record.length() >= 11) {
			setReceivingDfiId(record.substring(3, 11));
		}

		if (record.length() >= 12) {
			setCheckDigit(record.substring(11, 12));
		}

		if (record.length() >= 29) {
			setDfiAcctNbr(record.substring(12, 29));
		}

		if (record.length() >= 39) {
			setAmount(record.substring(29, 39));
		}

		if (record.length() >= 54) {
			setIndividualIdNbr(record.substring(39, 54));
		}

		if (record.length() >= 76) {
			setIndividualName(record.substring(54, 76));
		}

		if (record.length() >= 78) {
			setDiscretionaryData(record.substring(76, 78));
		}

		if (record.length() >= 79) {
			setAddendaRecordInd(record.substring(78, 79));
		}

		if (record.length() >= 94) {
			setTraceNumber(record.substring(79, 94));
		}

	}

	public String toString() {

		return super.toString() + transactionCode + receivingDfiId + checkDigit
				+ dfiAcctNbr + amount + individualIdNbr + individualName
				+ discretionaryData + addendaRecordInd + traceNumber;
	}

	/**
	 * @return Returns the addendaRecordInd.
	 */
	public String getAddendaRecordInd() {
		return addendaRecordInd;
	}

	/**
	 * @param addendaRecordInd
	 *            The addendaRecordInd to set.
	 */
	public void setAddendaRecordInd(String addendaRecordInd) {
		this.addendaRecordInd = formatACHDecimal(addendaRecordInd,"0");
	}

	/**
	 * @return Returns the amount.
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            The amount to set.
	 */
	public void setAmount(String amount) {
		this.amount = formatACHDecimal(amount,"0000000000");
	}

	/**
	 * @return Returns the checkDigit.
	 */
	public String getCheckDigit() {
		return checkDigit;
	}

	/**
	 * @param checkDigit
	 *            The checkDigit to set.
	 */
	public void setCheckDigit(String checkDigit) {
		this.checkDigit = formatACHDecimal(checkDigit,"0");
	}

	/**
	 * @return Returns the dfiAcctNbr.
	 */
	public String getDfiAcctNbr() {
		return dfiAcctNbr;
	}

	/**
	 * @param dfiAcctNbr
	 *            The dfiAcctNbr to set.
	 */
	public void setDfiAcctNbr(String dfiAcctNbr) {
		
		this.dfiAcctNbr = formatString(dfiAcctNbr, 17);
	}

	/**
	 * @return Returns the discretionaryData.
	 */
	public String getDiscretionaryData() {
		return discretionaryData;
	}

	/**
	 * @param discretionaryData
	 *            The discretionaryData to set.
	 */
	public void setDiscretionaryData(String discretionaryData) {
		this.discretionaryData = formatString(discretionaryData, 2);
	}

	/**
	 * @return Returns the individualIdNbr.
	 */
	public String getIndividualIdNbr() {
		return individualIdNbr;
	}

	/**
	 * @param individualIdNbr
	 *            The individualIdNbr to set.
	 */
	public void setIndividualIdNbr(String individualIdNbr) {
		this.individualIdNbr = formatString(individualIdNbr,15); 
	}

	/**
	 * @return Returns the individualName.
	 */
	public String getIndividualName() {
		return individualName;
	}

	/**
	 * @param individualName
	 *            The individualName to set.
	 */
	public void setIndividualName(String individualName) {
		
		this.individualName = formatString(individualName, 22);
	}

	/**
	 * @return Returns the receivingDfiId.
	 */
	public String getReceivingDfiId() {
		return receivingDfiId;
	}

	/**
	 * @param receivingDfiId
	 *            The receivingDfiId to set.
	 */
	public void setReceivingDfiId(String receivingDfiId) {
		this.receivingDfiId = formatACHDecimal(receivingDfiId,"00000000");
	}

	/**
	 * @return Returns the traceNumber.
	 */
	public String getTraceNumber() {
		return traceNumber;
	}

	/**
	 * @param traceNumber
	 *            The traceNumber to set.
	 */
	public void setTraceNumber(String traceNumber) {
		this.traceNumber = formatACHDecimal(traceNumber,"000000000000000");
	}

	/**
	 * @return Returns the transactionCode.
	 */
	public String getTransactionCode() {
		return transactionCode;
	}

	/**
	 * @param transactionCode
	 *            The transactionCode to set.
	 */
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = formatACHDecimal(transactionCode,"00");
	}

}
