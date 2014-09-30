/*
 * Created on Apr 10, 2007
 *
 */
package com.ach.achViewer.ach;

public class ACHRecordFileHeader extends ACHRecord {

	String priorityCode = spaceFill(2);

	String immediateDestination = spaceFill(10);

	String immediateOrigin = spaceFill(10);

	String fileCreationDate = spaceFill(6);

	String fileCreationTime = spaceFill(4);

	String fileIdModifier = spaceFill(1);

	String recordSize = spaceFill(3);

	String blockingFactor = spaceFill(2);

	String formatCode = spaceFill(1);

	String immediateDestinationName = spaceFill(23);

	String immediateOriginName = spaceFill(23);

	String referenceCode = spaceFill(8);

	public ACHRecordFileHeader() {
		super(ACHRecord.FILE_HEADER_TYPE);
		setPriorityCode("01");
		setRecordSize("94");
		setBlockingFactor("1");
	}

	public ACHRecordFileHeader(String record) {
		super(record);
		if (isFileHeaderType()) {
			parseRecord(record);
		}
	}

	private void parseRecord(String record) {

		if (record.length() >= 3) {
			setPriorityCode(record.substring(1, 3));
		}
		if (record.length() >= 13) {
			setImmediateDestination(record.substring(3, 13));
		}

		if (record.length() >= 23) {
			setImmediateOrigin(record.substring(13, 23));
		}

		if (record.length() >= 29) {
			setFileCreationDate(record.substring(23, 29));
		}

		if (record.length() >= 33) {
			setFileCreationTime(record.substring(29, 33));
		}

		if (record.length() >= 34) {
			setFileIdModifier(record.substring(33, 34));
		}

		if (record.length() >= 37) {
			setRecordSize(record.substring(34, 37));
		}

		if (record.length() >= 39) {
			setBlockingFactor(record.substring(37, 39));
		}

		if (record.length() >= 40) {
			setFormatCode(record.substring(39, 40));
		}

		if (record.length() >= 63) {
			setImmediateDestinationName(record.substring(40, 63));
		}

		if (record.length() >= 86) {
			setImmediateOriginName(record.substring(63, 86));
		}

		if (record.length() >= 94) {
			setReferenceCode(record.substring(86, 94));
		}
	}

	public String toString() {

		return super.toString() + priorityCode + immediateDestination
				+ immediateOrigin + fileCreationDate + fileCreationTime
				+ fileIdModifier + recordSize + blockingFactor + formatCode
				+ immediateDestinationName + immediateOriginName
				+ referenceCode;
	}

	/**
	 * @return Returns the blockingFactor.
	 */
	public String getBlockingFactor() {
		return blockingFactor;
	}

	/**
	 * @param blockingFactor
	 *            The blockingFactor to set.
	 */
	public void setBlockingFactor(String blockingFactor) {
		this.blockingFactor = formatACHDecimal(blockingFactor, "00", 10);
	}

	/**
	 * @return Returns the fileCreationDate.
	 */
	public String getFileCreationDate() {
		return fileCreationDate;
	}

	/**
	 * @param fileCreationDate
	 *            The fileCreationDate to set.
	 */
	public void setFileCreationDate(String fileCreationDate) {
		this.fileCreationDate = formatString(fileCreationDate, 6);
	}

	/**
	 * @return Returns the fileCreationTime.
	 */
	public String getFileCreationTime() {
		return fileCreationTime;
	}

	/**
	 * @param fileCreationTime
	 *            The fileCreationTime to set.
	 */
	public void setFileCreationTime(String fileCreationTime) {
		this.fileCreationTime = formatString(fileCreationTime, 4);
	}

	/**
	 * @return Returns the fileIdModifier.
	 */
	public String getFileIdModifier() {
		return fileIdModifier;
	}

	/**
	 * @param fileIdModifier
	 *            The fileIdModifier to set.
	 */
	public void setFileIdModifier(String fileIdModifier) {
		this.fileIdModifier = formatString(fileIdModifier, 1);
	}

	/**
	 * @return Returns the formatCode.
	 */
	public String getFormatCode() {
		return formatCode;
	}

	/**
	 * @param formatCode
	 *            The formatCode to set.
	 */
	public void setFormatCode(String formatCode) {
		this.formatCode = formatString(formatCode, 1);
	}

	/**
	 * @return Returns the immediateDestination.
	 */
	public String getImmediateDestination() {
		return immediateDestination;
	}

	/**
	 * @param immediateDestination
	 *            The immediateDestination to set.
	 */
	public void setImmediateDestination(String immediateDestination) {
		this.immediateDestination = formatString(immediateDestination, 10);
	}

	/**
	 * @return Returns the immediateDestinationName.
	 */
	public String getImmediateDestinationName() {
		return immediateDestinationName;
	}

	/**
	 * @param immediateDestinationName
	 *            The immediateDestinationName to set.
	 */
	public void setImmediateDestinationName(String immediateDestinationName) {
		this.immediateDestinationName = formatString(immediateDestinationName,
				23);
	}

	/**
	 * @return Returns the immediateOrigin.
	 */
	public String getImmediateOrigin() {
		return immediateOrigin;
	}

	/**
	 * @param immediateOrigin
	 *            The immediateOrigin to set.
	 */
	public void setImmediateOrigin(String immediateOrigin) {
		this.immediateOrigin = formatString(immediateOrigin, 10);
	}

	/**
	 * @return Returns the immediateOriginName.
	 */
	public String getImmediateOriginName() {
		return immediateOriginName;
	}

	/**
	 * @param immediateOriginName
	 *            The immediateOriginName to set.
	 */
	public void setImmediateOriginName(String immediateOriginName) {
		this.immediateOriginName = formatString(immediateOriginName, 23);
	}

	/**
	 * @return Returns the priorityCode.
	 */
	public String getPriorityCode() {
		return priorityCode;
	}

	/**
	 * @param priorityCode
	 *            The priorityCode to set.
	 */
	public void setPriorityCode(String priorityCode) {
		this.priorityCode = formatString(priorityCode, 2);
	}

	/**
	 * @return Returns the recordSize.
	 */
	public String getRecordSize() {
		return recordSize;
	}

	/**
	 * @param recordSize
	 *            The recordSize to set.
	 */
	public void setRecordSize(String recordSize) {
		this.recordSize = formatACHDecimal(recordSize, "000", 94);
	}

	/**
	 * @return Returns the referenceCode.
	 */
	public String getReferenceCode() {
		return referenceCode;
	}

	/**
	 * @param referenceCode
	 *            The referenceCode to set.
	 */
	public void setReferenceCode(String referenceCode) {
		this.referenceCode = formatString(referenceCode, 8);
	}

}
