/*
 * Created on Apr 10, 2007
 *
 */
package com.ach.achViewer.ach;

public class ACHRecordFileControl extends ACHRecord {

	String batchCount = spaceFill(6);

	String blockCount = spaceFill(6);

	String entryAddendaCount = spaceFill(8);

	String entryHash = spaceFill(10);

	String totDebitDollarAmt = spaceFill(12);

	String totCreditDollarAmt = spaceFill(12);

	String reserved = spaceFill(39);

	public ACHRecordFileControl(){
		super(ACHRecord.FILE_CONTROL_TYPE);
		setBatchCount("0");
		setBlockCount("0");
		setEntryAddendaCount("0");
		setEntryHash("0");
		setTotDebitDollarAmt("0");
		setTotCreditDollarAmt("0");
	}

	public ACHRecordFileControl(String record) {
		super(record);
		parseRecord(record);
	}

	private void parseRecord(String record) {

		if (record.length() >= 6) {
			setBatchCount(record.substring(1, 7));
		}
		if (record.length() >= 13) {
			setBlockCount(record.substring(7, 13));
		}
		if (record.length() >= 21) {
			setEntryAddendaCount(record.substring(13, 21));
		}
		if (record.length() >= 31) {
			setEntryHash(record.substring(21, 31));
		}
		if (record.length() >= 43) {
			setTotDebitDollarAmt(record.substring(31, 43));
		}
		if (record.length() >= 55) {
			setTotCreditDollarAmt(record.substring(43, 55));
		}
		if (record.length() >= 94) {
			setReserved(record.substring(55, 94));
		}
	}

	public String toString() {

		return super.toString() + batchCount + blockCount + entryAddendaCount
				+ entryHash + totDebitDollarAmt + totCreditDollarAmt + reserved;
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
		this.entryAddendaCount = formatACHDecimal(entryAddendaCount,"00000000");
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
		this.entryHash = formatACHDecimal(entryHash,"0000000000");
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
		this.reserved = formatString(reserved, 39);
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
		this.totCreditDollarAmt = formatACHDecimal(totCreditDollarAmt,"000000000000");
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
		this.totDebitDollarAmt = formatACHDecimal(totDebitDollarAmt,"000000000000");
	}

	/**
	 * @return Returns the batchCount.
	 */
	public String getBatchCount() {
		return batchCount;
	}

	/**
	 * @param batchCount
	 *            The batchCount to set.
	 */
	public void setBatchCount(String batchCount) {
		this.batchCount = formatACHDecimal(batchCount,"000000");
	}

	/**
	 * @return Returns the blockCount.
	 */
	public String getBlockCount() {
		return blockCount;
	}

	/**
	 * @param blockCount
	 *            The blockCount to set.
	 */
	public void setBlockCount(String blockCount) {
		this.blockCount = formatACHDecimal(blockCount,"000000");
	}

}
