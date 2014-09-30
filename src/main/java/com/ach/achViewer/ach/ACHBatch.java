package com.ach.achViewer.ach;

import java.util.Vector;

public class ACHBatch {
	ACHRecordBatchHeader batchHeader = null;

	ACHRecordBatchControl batchControl = null;

	Vector<ACHEntry> entryRecs = new Vector<ACHEntry>(10, 10);

	public ACHBatch() {
		setBatchHeader(new ACHRecordBatchHeader());
		setBatchControl(new ACHRecordBatchControl());
		entryRecs = new Vector<ACHEntry>(10, 10);
	}

	private class EntryAddendaTotals {
		public long entryAddendaCount = 0;

		public long debitDollarAmount = 0;

		public long creditDollarAmount = 0;

		public long entryHash = 0;
	}

	/**
	 * @return the batchControl
	 */
	public synchronized ACHRecordBatchControl getBatchControl() {
		return batchControl;
	}

	/**
	 * @param batchControl
	 *            the batchControl to set
	 */
	public synchronized void setBatchControl(ACHRecordBatchControl batchControl) {

		this.batchControl = batchControl;
	}

	public EntryAddendaTotals getEntryTotals() throws Exception {
		EntryAddendaTotals retValue = new EntryAddendaTotals();
		try {
			retValue.entryAddendaCount = entryRecs.size();

			for (int i = 0; i < entryRecs.size(); i++) {
				retValue.entryAddendaCount += entryRecs.get(i).getAddendaRecs()
						.size();
				retValue.entryHash += Long.parseLong(entryRecs.get(i)
						.getEntryDetail().getReceivingDfiId());
				if (entryRecs.get(i).getEntryDetail().getTransactionCode()
						.charAt(1) <= '4') {
					retValue.creditDollarAmount += Long.parseLong(entryRecs
							.get(i).getEntryDetail().getAmount());
				} else {
					retValue.debitDollarAmount += Long.parseLong(entryRecs.get(
							i).getEntryDetail().getAmount());

				}
			}
		} catch (Exception ex) {
			throw ex;
		}
		return retValue;
	}

	public boolean reverse() {
		for (int i = 0; i < entryRecs.size(); i++) {
			try {
				entryRecs.get(i).reverse();
			} catch (Exception ex) {
				System.err.println("Unable to reverse entry in batch "
						+ getBatchHeader().getBatchNumber() + " Entry number "
						+ i + " Entry trace "
						+ entryRecs.get(i).getEntryDetail().getTraceNumber());
				ex.printStackTrace();
			}
		}
		return recalc();
	}

	public boolean recalc() {
		boolean retvalue = false;
		try {
			EntryAddendaTotals totals = getEntryTotals();

			getBatchControl().setEntryAddendaCount(
					String.valueOf(totals.entryAddendaCount));
			getBatchControl().setTotDebitDollarAmt(
					String.valueOf(totals.debitDollarAmount));
			getBatchControl().setTotCreditDollarAmt(
					String.valueOf(totals.creditDollarAmount));
			getBatchControl().setEntryHash(String.valueOf(totals.entryHash));
			retvalue = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			retvalue = false;
		}
		return retvalue;
	}

	public Vector<String> validate() {

		Vector<String> retvalue = new Vector<String>(10, 10);
		long entryAddendaCount = 0;
		long entryDebitDollarAmount = 0;
		long entryCreditDollarAmount = 0;
		long entryHash = 0;
		try {
			try {
				entryAddendaCount = Long.parseLong(batchControl
						.getEntryAddendaCount());
				entryDebitDollarAmount = Long.parseLong(batchControl
						.getTotDebitDollarAmt());
				entryCreditDollarAmount = Long.parseLong(batchControl
						.getTotCreditDollarAmt());
				entryHash = Long.parseLong(batchControl.getEntryHash());
			} catch (Exception ex) {
				retvalue.add("Batch number " + batchHeader.getBatchNumber()
						+ " Non-numeric totals -- cannot validate");
				return retvalue;
			}

			EntryAddendaTotals totals = getEntryTotals();

			if (totals.entryAddendaCount != entryAddendaCount) {
				retvalue.add("Batch number " + batchHeader.getBatchNumber()
						+ " count is out of balance -- batch: "
						+ entryAddendaCount + "  Calced: "
						+ totals.entryAddendaCount);
				;
			}
			if (entryDebitDollarAmount != totals.debitDollarAmount) {
				retvalue.add("Batch " + batchHeader.getBatchNumber()
						+ " debits are out of balance -- batch: "
						+ entryDebitDollarAmount + "  Calced: "
						+ totals.debitDollarAmount);
				;
			}
			if (entryCreditDollarAmount != totals.creditDollarAmount) {
				retvalue.add("Batch " + batchHeader.getBatchNumber()
						+ " credits are out of balance -- batch: "
						+ entryCreditDollarAmount + "  Calced: "
						+ totals.creditDollarAmount);
				;
			}
			long truncatedHash = Long.parseLong(ACHRecord.formatACHDecimal(
					String.valueOf(totals.entryHash), "0000000000"));
			if (entryHash != truncatedHash) {
				retvalue.add("Batch " + batchHeader.getBatchNumber()
						+ " hash is out of balance -- batch: " + entryHash
						+ "  Calced: " + truncatedHash);
				;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			retvalue.add("Error processing batch "
					+ batchHeader.getBatchNumber() + " : " + ex.getMessage());
		}
		return retvalue;
	}

	/**
	 * @return the batchHeader
	 */
	public synchronized ACHRecordBatchHeader getBatchHeader() {
		return batchHeader;
	}

	/**
	 * @param batchHeader
	 *            the batchHeader to set
	 */
	public synchronized void setBatchHeader(ACHRecordBatchHeader batchHeader) {
		this.batchHeader = batchHeader;
	}

	/**
	 * @return the entryRecs
	 */
	public synchronized Vector<ACHEntry> getEntryRecs() {
		return entryRecs;
	}

	/**
	 * @param entryRecs
	 *            the entryRecs to set
	 */
	public synchronized void setEntryRecs(Vector<ACHEntry> entryRecs) {
		this.entryRecs = entryRecs;
	}

	/**
	 * @param entryRecs
	 *            the entryRec to add
	 */
	public synchronized void addEntryRecs(ACHEntry entryRec) {
		this.entryRecs.add(entryRec);
	}
}
