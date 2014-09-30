package com.ach.achViewer.ach;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.Vector;


public class ACHFile {

	private ACHRecordFileHeader fileHeader = null;

	private ACHRecordFileControl fileControl = null;

	// Designates whether the file is flat or contains carriage returns
	private boolean isFedFile = false;

	private Vector<ACHBatch> batches = new Vector<ACHBatch>(10, 10);

	private Vector<String> errorMessages = new Vector<String>(10,10);

	private class BatchTotals {

		public long recordCount = 0;

		public long batchCount = 0;

		public long entryAddendaCount = 0;

		public long debitDollarAmount = 0;

		public long creditDollarAmount = 0;

		public long entryHash = 0;
		
	}

	public ACHFile() {
		setFileHeader(new ACHRecordFileHeader());
		setFileControl(new ACHRecordFileControl());
		batches = new Vector<ACHBatch>(10, 10);
	}
	
	public void parseString(String contents) throws Exception  {
		
//		System.gc();
		
		BufferedReader achReader = null;
		achReader = new BufferedReader(new StringReader(contents));
		
		parse(achReader);
	}
	
	
	public void parseFile(String filename) throws Exception {
//		System.gc();
		File achFile = new File(filename);
		if (!achFile.exists()) {
			throw new Exception("File " + achFile.getPath() + " does not exist");
		}
		if (!achFile.isFile()) {
			throw new Exception("File " + achFile.getPath() + " is not a file");
		}
		if (!achFile.canRead()) {
			throw new Exception("File " + achFile.getPath() + " cannot be read");
		}
		
		BufferedReader achReader = null;
		try {
			achReader = new BufferedReader(new FileReader(achFile.getPath()));
		} catch (FileNotFoundException ex) {
			throw new Exception("File " + achFile.getPath()
					+ " could not be opened. Reason " + ex.getMessage());
		}
		parse(achReader);
	}
	 
	private void parse(BufferedReader achReader) throws Exception {
		boolean foundFileControl = false;
		int rowCount = 0;
		try {
			String record = achReader.readLine();
			if (!record.substring(0, 1).equals("1")) {
				throw new Exception(
						"Content is not an ACH format.  First character must be a \"1\"");
			}
			ACHBatch achBatch = null;
			ACHEntry achEntry = null;
			int recLength = 94;
			while (record != null) {
				rowCount++;
				for (int recStart = 0; recStart < record.length(); recStart += recLength) {
					int endRec = recStart + recLength;
					if (endRec > record.length()) {
						endRec = record.length();
					}
					ACHRecord achRecord = ACHRecord.parseACHRecord(record
							.substring(recStart, endRec));
					if (achRecord.isFileHeaderType()) {
						setFileHeader((ACHRecordFileHeader) achRecord);
						recLength = Integer.parseInt(getFileHeader()
								.getRecordSize());
						achEntry = null;
						achBatch = null;
					} else if (achRecord.isFileControlType()) {
						if (achEntry != null) {
							achBatch.addEntryRecs(achEntry);
							achEntry = null;
						}
						if (achBatch != null) {
							addBatch(achBatch);
							achBatch = null;
						}
						if (!foundFileControl) {
							setFileControl((ACHRecordFileControl) achRecord);
							foundFileControl = true;
						}
					} else if (achRecord.isBatchHeaderType()) {
						if (achEntry != null) {
							achBatch.addEntryRecs(achEntry);
							achEntry = null;
						}
						if (achBatch != null) {
							addBatch(achBatch);
							achBatch = null;
						}

						achBatch = new ACHBatch();
						achBatch
								.setBatchHeader((ACHRecordBatchHeader) achRecord);
					} else if (achRecord.isBatchControlType()) {
						if (achEntry != null) {
							achBatch.addEntryRecs(achEntry);
							achEntry = null;
						}
						if (achBatch == null) {
							achBatch = new ACHBatch();
						}
						achBatch
								.setBatchControl((ACHRecordBatchControl) achRecord);
					} else if (achRecord.isEntryDetailType()) {
						if (achEntry != null) {
							achBatch.addEntryRecs(achEntry);
							achEntry = null;
						}
						if (achBatch == null) {
							achBatch = new ACHBatch();
						}
						achEntry = new ACHEntry();
						achEntry
								.setEntryDetail((ACHRecordEntryDetail) achRecord);
					} else if (achRecord.isAddendaType()) {
						if (achEntry == null) {
							achEntry = new ACHEntry();
						}
						achEntry.addAddendaRecs((ACHRecordAddenda) achRecord);
					} else {
						errorMessages.add("Invalid record at row " + rowCount);
					}
				}
				record = achReader.readLine();
			}
		} catch (IOException ex) {
			errorMessages.add("Content could not be processed. Reason " + ex.getMessage());
		}
		try {
			achReader.close();
		} catch (Exception ex) {
		}
		if (rowCount == 1 && errorMessages.size() == 0) {
			setFedFile(true);
		}
	}

	public BatchTotals getBatchTotals() throws Exception {
		BatchTotals retValue = new BatchTotals();
		try {
			retValue.batchCount = getBatches().size();
			for (int i = 0; i < getBatches().size(); i++) {
				// Add the number of entry/addenda counts plus two for batch
				// header and control for the nbr of records. Needed to calc
				// block count
				retValue.recordCount += Long.parseLong(getBatches().get(i)
						.getBatchControl().getEntryAddendaCount()) + 2;

				retValue.entryAddendaCount += Long.parseLong(getBatches()
						.get(i).getBatchControl().getEntryAddendaCount());
				retValue.entryHash += Long.parseLong(getBatches().get(i)
						.getBatchControl().getEntryHash());
				retValue.creditDollarAmount += Long.parseLong(getBatches().get(
						i).getBatchControl().getTotCreditDollarAmt());
				retValue.debitDollarAmount += Long.parseLong(getBatches()
						.get(i).getBatchControl().getTotDebitDollarAmt());
			}
			// Restrict hash to the first 10 characters

			retValue.entryHash = Long.parseLong(ACHRecord.formatACHDecimal(
					String.valueOf(retValue.entryHash), "0000000000"));
		} catch (Exception ex) {
			throw ex;
		}
		// Add two more for file header and control
		retValue.recordCount += 2;
		return retValue;
	}

	public boolean recalculate() {

		boolean retvalue = true;
		try {

			// Recalc all batches
			for (int i = 0; i < getBatches().size() || !retvalue; i++) {
				retvalue = getBatches().get(i).recalc();
			}

			if (!retvalue) {
				// unable to calculcate batches for some reason
				// should run validate to get a list of errors
				return false;
			}

			BatchTotals totals = getBatchTotals();
			long recsPerBlock = Long.parseLong(fileHeader.getBlockingFactor());
			long calcBlockCount = totals.recordCount / recsPerBlock;
			if ((totals.recordCount % recsPerBlock) > 0) {
				calcBlockCount++;
			}

			fileControl.setEntryAddendaCount(String
					.valueOf(totals.entryAddendaCount));
			fileControl.setTotDebitDollarAmt(String
					.valueOf(totals.debitDollarAmount));
			fileControl.setTotCreditDollarAmt(String
					.valueOf(totals.creditDollarAmount));
			fileControl.setEntryHash(String.valueOf(totals.entryHash));
			fileControl.setBatchCount(String.valueOf(totals.batchCount));
			fileControl.setBlockCount(String.valueOf(calcBlockCount));

		} catch (Exception ex) {
			ex.printStackTrace();
			retvalue = false;
		}
		return retvalue;
	}

	public boolean reverse() {

		// Reverse all batches
		for (int i = 0; i < getBatches().size(); i++) {
			getBatches().get(i).reverse();
		}

		return recalculate();
	}

	public Vector<String> validate() {

		Vector<String> retvalue = new Vector<String>(10, 10);
		try {

			// Get all batch errors
			for (int i = 0; i < getBatches().size(); i++) {
				Vector<String> batchMessages = getBatches().get(i).validate();
				retvalue.addAll(batchMessages);
			}

			long entryAddendaCount = Long.parseLong(fileControl
					.getEntryAddendaCount());
			long entryDebitDollarAmount = Long.parseLong(fileControl
					.getTotDebitDollarAmt());
			long entryCreditDollarAmount = Long.parseLong(fileControl
					.getTotCreditDollarAmt());
			long entryHash = Long.parseLong(fileControl.getEntryHash());
			long batchCount = Long.parseLong(fileControl.getBatchCount());
			long blockCount = Long.parseLong(fileControl.getBlockCount());

			BatchTotals totals = new BatchTotals();
			try {
				totals = getBatchTotals();
			} catch (Exception ex) {
				retvalue.add("Error calculating batch totals");
			}
			long recsPerBlock = Long.parseLong(fileHeader.getBlockingFactor());
			long calcBlockCount = totals.recordCount / recsPerBlock;
			if ((totals.recordCount % recsPerBlock) > 0) {
				calcBlockCount++;
			}

			if (totals.entryAddendaCount != entryAddendaCount) {
				retvalue.add("File count is out of balance -- file: "
						+ entryAddendaCount + "  Calced: "
						+ totals.entryAddendaCount);
				;
			}
			if (entryDebitDollarAmount != totals.debitDollarAmount) {
				retvalue.add("File debits are out of balance -- file: "
						+ entryDebitDollarAmount + "  Calced: "
						+ totals.debitDollarAmount);
				;
			}
			if (entryCreditDollarAmount != totals.creditDollarAmount) {
				retvalue.add("File credits are out of balance -- file: "
						+ entryCreditDollarAmount + "  Calced: "
						+ totals.creditDollarAmount);
				;
			}
			if (entryHash != totals.entryHash) {
				retvalue.add("File hash is out of balance -- file: "
						+ entryHash + "  Calced: " + totals.entryHash);
				;
			}
			if (batchCount != totals.batchCount) {
				retvalue.add("File batch count is out of balance -- file: "
						+ batchCount + "  Calced: " + totals.batchCount);
				;
			}

			if (blockCount != calcBlockCount) {
				retvalue.add("File block count is out of balance -- file: "
						+ blockCount + "  Calced: " + calcBlockCount);
				;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			retvalue.add("Error processing batch : " + ex.getMessage());
		}
		return retvalue;
	}

	public boolean save(String filename) throws Exception {

		// Create file for writing
		FileWriter fileWriter = new FileWriter(new File(filename));
		String record = "";
		int rowCount = 0;

		String recDelimiter = System.getProperty("line.separator", "\r\n");
		if (isFedFile()) {
			recDelimiter = "";
		}

		// Write ACH File Header
		record = getFileHeader().toString();
		fileWriter.write(record + recDelimiter);
		rowCount++;

		// Output each ACH Batch
		for (int i = 0; i < batches.size(); i++) {
			// Write ACH Batch Header
			record = batches.get(i).getBatchHeader().toString();
			fileWriter.write(record + recDelimiter);
			rowCount++;

			// Output each entry
			Vector<ACHEntry> achEntries = batches.get(i).getEntryRecs();
			for (int j = 0; j < achEntries.size(); j++) {
				// Output entry detail
				record = achEntries.get(j).getEntryDetail().toString();
				fileWriter.write(record + recDelimiter);
				rowCount++;
				// Output each addenda
				Vector<ACHRecordAddenda> achAddendas = achEntries.get(j)
						.getAddendaRecs();
				for (int k = 0; k < achAddendas.size(); k++) {
					// output addenda
					record = achAddendas.get(k).toString();
					fileWriter.write(record + recDelimiter);
					rowCount++;
				}
			}
			// Output bach control
			record = batches.get(i).getBatchControl().toString();
			fileWriter.write(record + recDelimiter);
			rowCount++;
		}
		// output file control
		record = getFileControl().toString();
		fileWriter.write(record + recDelimiter);
		rowCount++;

		try {
			int blockSize = Integer.parseInt(getFileHeader()
					.getBlockingFactor());
			int neededRows = blockSize - (rowCount % blockSize);
			if (neededRows > 0) {
				int recordSize = Integer.parseInt(getFileHeader()
						.getRecordSize());
				StringBuffer outputRecord = new StringBuffer("");
				for (int i = 0; i < recordSize; i++) {
					outputRecord.append("9");
				}
				for (int i = 0; i < neededRows; i++) {
					fileWriter.write(outputRecord.toString() + recDelimiter);
				}
			}
		} catch (Exception ex) {
			System.err.println("Unable to output trailing control records -- "
					+ ex.getMessage());
			ex.printStackTrace();
		}

		fileWriter.flush();
		fileWriter.close();

		return true;
	}

	/**
	 * @return the batches
	 */
	public synchronized Vector<ACHBatch> getBatches() {
		return batches;
	}

	/**
	 * @param batches
	 *            the batches to set
	 */
	public synchronized void setBatches(Vector<ACHBatch> batches) {
		this.batches = batches;
	}

	/**
	 * @param achBatch
	 *            the batch to add
	 */
	public synchronized void addBatch(ACHBatch achBatch) {
		this.batches.add(achBatch);
	}

	/**
	 * @return the fileControl
	 */
	public synchronized ACHRecordFileControl getFileControl() {
		return fileControl;
	}

	/**
	 * @param fileControl
	 *            the fileControl to set
	 */
	public synchronized void setFileControl(ACHRecordFileControl fileControl) {
		this.fileControl = fileControl;
	}

	/**
	 * @return the fileHeader
	 */
	public synchronized ACHRecordFileHeader getFileHeader() {
		return fileHeader;
	}

	/**
	 * @param fileHeader
	 *            the fileHeader to set
	 */
	public synchronized void setFileHeader(ACHRecordFileHeader fileHeader) {
		this.fileHeader = fileHeader;
	}

	/**
	 * @param isFedFile
	 *            the isFedFile to set
	 */
	public void setFedFile(boolean isFedFile) {
		this.isFedFile = isFedFile;
	}

	/**
	 * @return the isFedFile
	 */
	public boolean isFedFile() {
		return isFedFile;
	}

	/**
	 * @return Returns the errorMessages.
	 */
	public Vector<String> getErrorMessages() {
		return errorMessages;
	}

}
