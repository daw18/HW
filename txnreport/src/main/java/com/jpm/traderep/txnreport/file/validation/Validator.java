package com.jpm.traderep.txnreport.file.validation;

import java.math.BigDecimal;
import java.util.Date;

public class Validator {

	public boolean validPricePerUnit(String pricePerUnitStr) {
		if (pricePerUnitStr != null) {
			return true;
		} else {
			logError("Invalid price per unit");
			return false;
		}

	}

	public boolean validUnits(long units) {
		if (units > 0) {
			return true;
		} else {
			logError("Invalid units");
			return false;
		}
	}

	public boolean validSettlementDate(Date parseDate) {
		if (parseDate != null) {
			return true;
		} else {
			logError("Invalid settlement date");
			return false;
		}
	}

	public boolean validInstructionDate(Date parseDate) {
		if (parseDate != null) {
			return true;
		} else {
			logError("Invalid instruction date");
			return false;
		}
	}

	public boolean validCurrency(String currencyCode) {
	
		if (currencyCode != null) {
			return true;
		} else {
			logError("Invalid currency code");
			return false;
		}
	}

	public boolean validFx(BigDecimal agreedFxRate) {
		if (null != agreedFxRate && agreedFxRate.compareTo(BigDecimal.ZERO) > 0) {
			return true;
		} else {
			logError("Invalid FX Rate");
			return false;
		}
	}

	public boolean validEntity(String entity) {
		if (entity != null && entity.length() > 0) {
			return true;
		} else {
			logError("Invalid entity");
			return false;
		}
	}

	public boolean validBuySell(String buySellIndicator) {

		if (buySellIndicator.equals("B") || buySellIndicator.equals("S")) {
			return true;
		} else {
			logError("Invalid buy/sell indicator");
			return false;
		}
	}

	private void logError(String errorMessage) {

		System.out.println("ERROR - " + errorMessage);

	}

}
