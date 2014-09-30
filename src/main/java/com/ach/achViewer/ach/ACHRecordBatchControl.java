/*
 * Created on Apr 10, 2007
 *
 */
package com.ach.achViewer.ach;

public class ACHRecordBatchControl extends ACHRecord {

	String serviceClassCode = spaceFill(3);

	String entryAddendaCount = spaceFill(6);

	String entryHash = spaceFill(10);

	String totDebitDollarAmt = spaceFill(12);

	String totCreditDollarAmt = spaceFill(12);

	String companyId = spaceFill(10);

	String messageAuthCode = spaceFill(19);

	String reserved = spaceFill(6);

	String originatingDfiId = spaceFill(8);

	String batchNumber = spaceFill(7);

	public ACHRecordBatchControl() {
		super(ACHRecord.BATCH_CONTROL_TYPE);
		setServiceClassCode("220");
		setEntryAddendaCount("0");
		setEntryHash("0");
		setTotDebitDollarAmt("0");
		setTotCreditDollarAmt("0");
		setBatchNumber("0");
	}

	public ACHRecordBatchControl(String record) {
		super(record);
		parseRecord(record);
	}

	private void parseRecord(String record) {

		if (record.length() >= 4) {
			setServiceClassCode(record.substring(1, 4));
		}
		if (record.length() >= 10) {
			setEntryAddendaCount(record.substring(4, 10));
		}
		if (record.length() >= 20) {
			setEntryHash(record.substring(10, 20));
		}
		if (record.length() >= 32) {
			setTotDebitDollarAmt(record.substring(20, 32));
		}
		if (record.length() >= 44) {
			setTotCreditDollarAmt(record.substring(32, 44));
		}
		if (record.length() >= 54) {
			setCompanyId(record.substring(44, 54));
		}
		if (record.length() >= 73) {
			setMessageAuthCode(record.substring(54, 73));
		}
		if (record.length() >= 79) {
			setReserved(record.substring(73, 79));
		}
		if (record.length() >= 87) {
			setOriginatingDfiId(record.substring(79, 87));
		}
		if (record.length() >= 94) {
			setBatchNumber(record.substring(87, 94));
		}
	}

	public String toString() {

		return super.toString() + serviceClassCode + entryAddendaCount
				+ entryHash + totDebitDollarAmt + totCreditDollarAmt
				+ companyId + messageAuthCode + reserved + originatingDfiId
				+ batchNumber;
	}

	/**
	 * @return Returns the batchNumber.
	 */
	public String getBatchNumber() {
		return batchNumber;
	}

	/**
	 * @param batchNumber
	 *            The batchNumber to set.
	 */
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = formatACHDecimal(batchNumber, "0000000");
	}

	/**
	 * @return Returns the companyId.
	 */
	public String getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId
	 *            The companyId to set.
	 */
	public void setCompanyId(String companyId) {
		this.companyId = formatString(companyId, 10);
	}

	/**
	 * @return Returns the entryAddendaCount.
	 */
	public String getEntryAddendaCount() {
		return entryAddendaCount;
	}

	/**
	 * @param entryAddendaCount
	 *            The entryAddendaCount to set.
	 */
	public void setEntryAddendaCount(String entryAddendaCount) {
		this.entryAddendaCount = formatACHDecimal(entryAddendaCount, "000000");
	}

	/**
	 * @return Returns the entryHash.
	 */
	public String getEntryHash() {
		return entryHash;
	}

	/**
	 * @param entryHash
	 *            The entryHash to set.
	 */
	public void setEntryHash(String entryHash) {
		this.entryHash = formatACHDecimal(entryHash, "0000000000");
	}

	/**
	 * @return Returns the messageAuthCode.
	 */
	public String getMessageAuthCode() {
		return messageAuthCode;
	}

	/**
	 * @param messageAuthCode
	 *            The messageAuthCode to set.
	 */
	public void setMessageAuthCode(String messageAuthCode) {
		this.messageAuthCode = formatString(messageAuthCode, 19);
	}

	/**
	 * @return Returns the originatingDfiId.
	 */
	public String getOriginatingDfiId() {
		return originatingDfiId;
	}

	/**
	 * @param originatingDfiId
	 *            The originatingDfiId to set.
	 */
	public void setOriginatingDfiId(String originatingDfiId) {
		this.originatingDfiId = formatString(originatingDfiId, 8);
	}

	/**
	 * @return Returns the reserved.
	 */
	public String getReserved() {
		return reserved;
	}

	/**
	 * @param reserved
	 *            The reserved to set.
	 */
	public void setReserved(String reserved) {
		this.reserved = formatString(reserved, 6);
	}

	/**
	 * @return Returns the serviceClassCode.
	 */
	public String getServiceClassCode() {
		return serviceClassCode;
	}

	/**
	 * @param serviceClassCode
	 *            The serviceClassCode to set.
	 */
	public void setServiceClassCode(String serviceClassCode) {
		this.serviceClassCode = formatString(serviceClassCode, 3);
	}

	/**
	 * @return Returns the totCreditDollarAmt.
	 */
	public String getTotCreditDollarAmt() {
		return totCreditDollarAmt;
	}

	/**
	 * @param totCreditDollarAmt
	 *            The totCreditDollarAmt to set.
	 */
	public void setTotCreditDollarAmt(String totCreditDollarAmt) {
		this.totCreditDollarAmt = formatACHDecimal(totCreditDollarAmt,
				"000000000000");
	}

	/**
	 * @return Returns the totDebitDollarAmt.
	 */
	public String getTotDebitDollarAmt() {
		return totDebitDollarAmt;
	}

	/**
	 * @param totDebitDollarAmt
	 *            The totDebitDollarAmt to set.
	 */
	public void setTotDebitDollarAmt(String totDebitDollarAmt) {
		this.totDebitDollarAmt = formatACHDecimal(totDebitDollarAmt,
				"000000000000");
	}

}
