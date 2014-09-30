/*
 * Created on Apr 10, 2007
 *
 */
package com.ach.achViewer.ach;

public class ACHRecordBatchHeader extends ACHRecord {

	String serviceClassCode = spaceFill(3);

	String companyName = spaceFill(16);

	String companyDiscretionaryData = spaceFill(20);

	String companyId = spaceFill(10);

	String standardEntryClassCode = spaceFill(3);

	String companyEntryDescription = spaceFill(10);

	String companyDescriptiveDate = spaceFill(6);

	String effectiveEntryDate = spaceFill(6);

	String settlementDate = spaceFill(3);

	String originatorStatusCode = spaceFill(1);

	String originationDfiId = spaceFill(8);

	String batchNumber = spaceFill(7);

	public ACHRecordBatchHeader() {
		super(ACHRecord.BATCH_HEADER_TYPE);
		setServiceClassCode("220");
		setBatchNumber("1");
	}

	public ACHRecordBatchHeader(String record) {
		super(record);
		if (isBatchHeaderType()) {
			parseRecord(record);
		}
	}

	private void parseRecord(String record) {

		if (record.length() >= 4) {
			setServiceClassCode(record.substring(1, 4));
		}

		if (record.length() >= 20) {
			setCompanyName(record.substring(4, 20));
		}

		if (record.length() >= 40) {
			setCompanyDiscretionaryData(record.substring(20, 40));
		}

		if (record.length() >= 50) {
			setCompanyId(record.substring(40, 50));
		}

		if (record.length() >= 53) {
			setStandardEntryClassCode(record.substring(50, 53));
		}

		if (record.length() >= 63) {
			setCompanyEntryDescription(record.substring(53, 63));
		}

		if (record.length() >= 69) {
			setCompanyDescriptiveDate(record.substring(63, 69));
		}

		if (record.length() >= 75) {
			setEffectiveEntryDate(record.substring(69, 75));
		}

		if (record.length() >= 78) {
			setSettlementDate(record.substring(75, 78));
		}

		if (record.length() >= 79) {
			setOriginatorStatusCode(record.substring(78, 79));
		}

		if (record.length() >= 87) {
			setOriginationDfiId(record.substring(79, 87));
		}

		if (record.length() >= 94) {
			setBatchNumber(record.substring(87, 94));
		}
	}

	public String toString() {

		return super.toString() + serviceClassCode + companyName
				+ companyDiscretionaryData + companyId + standardEntryClassCode
				+ companyEntryDescription + companyDescriptiveDate
				+ effectiveEntryDate + settlementDate + originatorStatusCode
				+ originationDfiId + batchNumber;
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
		this.batchNumber = formatACHDecimal(batchNumber,"0000000");
	}

	/**
	 * @return Returns the companyDescriptiveDate.
	 */
	public String getCompanyDescriptiveDate() {
		return companyDescriptiveDate;
	}

	/**
	 * @param companyDescriptiveDate
	 *            The companyDescriptiveDate to set.
	 */
	public void setCompanyDescriptiveDate(String companyDescriptiveDate) {
		this.companyDescriptiveDate = formatString(companyDescriptiveDate, 6);
	}

	/**
	 * @return Returns the companyDiscretinaryData.
	 */
	public String getCompanyDiscretionaryData() {
		return companyDiscretionaryData;
	}

	/**
	 * @param companyDiscretionaryData
	 *            The companyDiscretinaryData to set.
	 */
	public void setCompanyDiscretionaryData(String companyDiscretionaryData) {
		this.companyDiscretionaryData = formatString(companyDiscretionaryData, 20);
	}

	/**
	 * @return Returns the companyEffectiveEntryDate.
	 */
	public String getEffectiveEntryDate() {
		return effectiveEntryDate;
	}

	/**
	 * @param effectiveEntryDate
	 *            The effectiveEntryDate to set.
	 */
	public void setEffectiveEntryDate(String effectiveEntryDate) {
		this.effectiveEntryDate = formatString(effectiveEntryDate, 6);
	}

	/**
	 * @return Returns the companyEntryDescription.
	 */
	public String getCompanyEntryDescription() {
		return companyEntryDescription;
	}

	/**
	 * @param companyEntryDescription
	 *            The companyEntryDescription to set.
	 */
	public void setCompanyEntryDescription(String companyEntryDescription) {
		this.companyEntryDescription = formatString(companyEntryDescription, 10);
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
	 * @return Returns the companyName.
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName
	 *            The companyName to set.
	 */
	public void setCompanyName(String companyName) {
		this.companyName = formatString(companyName, 16);
	}

	/**
	 * @return Returns the originationDfiId.
	 */
	public String getOriginationDfiId() {
		return originationDfiId;
	}

	/**
	 * @param originationDfiId
	 *            The originationDfiId to set.
	 */
	public void setOriginationDfiId(String originationDfiId) {
		this.originationDfiId = formatString(originationDfiId, 8);
	}

	/**
	 * @return Returns the originatorStatusCode.
	 */
	public String getOriginatorStatusCode() {
		return originatorStatusCode;
	}

	/**
	 * @param originatorStatusCode
	 *            The originatorStatusCode to set.
	 */
	public void setOriginatorStatusCode(String originatorStatusCode) {
		this.originatorStatusCode = formatString(originatorStatusCode, 1);
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
		this.serviceClassCode = formatACHDecimal(serviceClassCode,"000");
	}

	/**
	 * @return Returns the settlementDate.
	 */
	public String getSettlementDate() {
		return settlementDate;
	}

	/**
	 * @param settlementDate
	 *            The settlementDate to set.
	 */
	public void setSettlementDate(String settlementDate) {
		this.settlementDate = formatACHDecimal(settlementDate,"000");
	}

	/**
	 * @return Returns the standardEntryClassCode.
	 */
	public String getStandardEntryClassCode() {
		return standardEntryClassCode;
	}

	/**
	 * @param standardEntryClassCode
	 *            The standardEntryClassCode to set.
	 */
	public void setStandardEntryClassCode(String standardEntryClassCode) {
		this.standardEntryClassCode = formatString(standardEntryClassCode, 3);
	}

}
