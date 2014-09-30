/*
 * Created on Apr 10, 2007
 *
 */
package com.ach.achViewer.ach;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

public class ACHRecord {

	char recordTypeCode = ' ';

	public final static char FILE_HEADER_TYPE = '1';

	public final static char BATCH_HEADER_TYPE = '5';

	public final static char ENTRY_DETAIL_TYPE = '6';

	public final static char ADDENDA_TYPE = '7';

	public final static char BATCH_CONTROL_TYPE = '8';

	public final static char FILE_CONTROL_TYPE = '9';

	public static ACHRecord parseACHRecord(String record) {
		ACHRecord achRecord = new ACHRecord(record);
		if (achRecord.isFileHeaderType()) {
			return new ACHRecordFileHeader(record);
		} else if (achRecord.isBatchHeaderType()) {
			return new ACHRecordBatchHeader(record);
		} else if (achRecord.isEntryDetailType()) {
			return new ACHRecordEntryDetail(record);
		} else if (achRecord.isAddendaType()) {
			return new ACHRecordAddenda(record);
		} else if (achRecord.isBatchControlType()) {
			return new ACHRecordBatchControl(record);
		} else if (achRecord.isFileControlType()) {
			return new ACHRecordFileControl(record);
		}
		return achRecord;
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Format: ACHRecord <fileName>");
			System.exit(-1);
		}
		File achFile = new File(args[0]);
		if (!achFile.exists()) {
			System.err.println("File " + achFile.getPath() + " does not exist");
			System.exit(-1);
		}
		if (!achFile.isFile()) {
			System.err.println("File " + achFile.getPath() + " is not a file");
			System.exit(-1);
		}
		if (!achFile.canRead()) {
			System.err.println("File " + achFile.getPath() + " cannot be read");
			System.exit(-1);
		}

		BufferedReader achReader = null;
		try {
			achReader = new BufferedReader(new FileReader(achFile.getPath()));
		} catch (FileNotFoundException ex) {
			System.err.println("File " + achFile.getPath()
					+ " could not be opened");
			System.exit(-1);
		}

		int rowCount = 0;
		try {
			String record = achReader.readLine();
			while (record != null && rowCount < 1000) {
				rowCount++;
				ACHRecord achRecord = ACHRecord.parseACHRecord(record);
				String newRecord = achRecord.toString();
				System.out.println(newRecord);
				record = achReader.readLine();
			}
		} catch (IOException ex) {
		}

	}

	public ACHRecord(char recordTypeCode) {
		super();
		setRecordTypeCode(recordTypeCode);
	}

	public ACHRecord(String record) {
		super();
		if (record == null || record.length() == 0) {
			return;
		}
		setRecordTypeCode(record.charAt(0));
	}

	public boolean isValidType() {
		return isFileHeaderType() || isBatchHeaderType() || isEntryDetailType()
				|| isAddendaType() || isBatchControlType()
				|| isFileControlType();
	}

	public boolean isFileHeaderType() {
		if (recordTypeCode == FILE_HEADER_TYPE)
			return true;
		else
			return false;
	}

	public boolean isBatchHeaderType() {
		if (recordTypeCode == BATCH_HEADER_TYPE)
			return true;
		else
			return false;
	}

	public boolean isEntryDetailType() {
		if (recordTypeCode == ENTRY_DETAIL_TYPE)
			return true;
		else
			return false;
	}

	public boolean isAddendaType() {
		if (recordTypeCode == ADDENDA_TYPE)
			return true;
		else
			return false;
	}

	public boolean isBatchControlType() {
		if (recordTypeCode == BATCH_CONTROL_TYPE)
			return true;
		else
			return false;
	}

	public boolean isFileControlType() {
		if (recordTypeCode == FILE_CONTROL_TYPE)
			return true;
		else
			return false;
	}

	protected String spaceFill(int nbrSpaces) {
		StringBuffer retValue = new StringBuffer();
		for (int i = 0; i < nbrSpaces; i++) {
			retValue.append(" ");
		}
		return retValue.toString();
	}

	protected String formatString(String inputString, int size) {
		return ((inputString == null ? "" : inputString) + spaceFill(size))
				.substring(0, size);

	}

	public static String formatACHDecimal(String inputString, String formatString) {
		return formatACHDecimal(inputString, formatString,0);
	}
	
	public static String formatACHDecimal(String inputString, String formatString,
			long defaultValue) {

		String returnValue = "";
		DecimalFormat format = new DecimalFormat(formatString);
		int length = formatString.length();
		try {
			returnValue = format.format(Long.parseLong(inputString));
		} catch (Exception ex) {
			returnValue = format.format(defaultValue);
		}
		// Truncate -- if larger than format, only return the last length digits
		if (returnValue.length() > length) {
			returnValue = returnValue.substring(returnValue.length() - length);
		}
		return returnValue;
	}

	/**
	 * @return Returns the recordTypeCode.
	 */
	public char getRecordTypeCode() {
		return recordTypeCode;
	}

	/**
	 * @param recordTypeCode
	 *            The recordTypeCode to set.
	 */
	public void setRecordTypeCode(char recordTypeCode) {
		this.recordTypeCode = recordTypeCode;
	}

	public String toString() {

		return Character.toString(recordTypeCode);
	}
}
