package com.jpm.traderep.txnreport.entity;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @author daw
 * 
 *         Trade entity - Incoming trade execution instruction
 *
 */

public class Transaction {
	
	private String entity;
	private String buySell;
	private BigDecimal agreedFx;
	private String currencyCode;
	private Date instructionDate;
	private Date settlementDate;
	private long units;
	private BigDecimal pricePerUnit;
	private Integer actualSettlementDate;
	private BigDecimal usdSettledAmount;

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getBuySell() {
		return buySell;
	}

	public void setBuySell(String buySell) {
		this.buySell = buySell;
	}

	public BigDecimal getAgreedFx() {
		return agreedFx;
	}

	public void setAgreedFx(BigDecimal agreedFx) {
		this.agreedFx = agreedFx;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Date getInstructionDate() {
		return instructionDate;
	}

	public void setInstructionDate(Date instructionDate) {
		this.instructionDate = instructionDate;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public long getUnits() {
		return units;
	}

	public void setUnits(long units) {
		this.units = units;
	}

	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(BigDecimal pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public Integer getActualSettlementDate() {
		return actualSettlementDate;
	}

	public void setActualSettlementDate(Integer actualSettlementDate) {
		this.actualSettlementDate = actualSettlementDate;
	}

	public BigDecimal getUsdSettledAmount() {
		return usdSettledAmount;
	}

	public void setUsdSettledAmount(BigDecimal usdSettledAmount) {
		this.usdSettledAmount = usdSettledAmount;
	}

	@Override
	public String toString() {
		return "Transaction [entity=" + entity + ", buySell=" + buySell + ", agreedFx=" + agreedFx + ", currencyCode="
				+ currencyCode + ", instructionDate=" + instructionDate + ", settlementDate=" + settlementDate
				+ ", units=" + units + ", pricePerUnit=" + pricePerUnit + ", actualSettlementDate="
				+ actualSettlementDate + ", usdSettledAmount=" + usdSettledAmount + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actualSettlementDate == null) ? 0 : actualSettlementDate.hashCode());
		result = prime * result + ((agreedFx == null) ? 0 : agreedFx.hashCode());
		result = prime * result + ((buySell == null) ? 0 : buySell.hashCode());
		result = prime * result + ((currencyCode == null) ? 0 : currencyCode.hashCode());
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		result = prime * result + ((instructionDate == null) ? 0 : instructionDate.hashCode());
		result = prime * result + ((pricePerUnit == null) ? 0 : pricePerUnit.hashCode());
		result = prime * result + ((settlementDate == null) ? 0 : settlementDate.hashCode());
		result = prime * result + (int) (units ^ (units >>> 32));
		result = prime * result + ((usdSettledAmount == null) ? 0 : usdSettledAmount.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (actualSettlementDate == null) {
			if (other.actualSettlementDate != null)
				return false;
		} else if (!actualSettlementDate.equals(other.actualSettlementDate))
			return false;
		if (agreedFx == null) {
			if (other.agreedFx != null)
				return false;
		} else if (!agreedFx.equals(other.agreedFx))
			return false;
		if (buySell == null) {
			if (other.buySell != null)
				return false;
		} else if (!buySell.equals(other.buySell))
			return false;
		if (currencyCode == null) {
			if (other.currencyCode != null)
				return false;
		} else if (!currencyCode.equals(other.currencyCode))
			return false;
		if (entity == null) {
			if (other.entity != null)
				return false;
		} else if (!entity.equals(other.entity))
			return false;
		if (instructionDate == null) {
			if (other.instructionDate != null)
				return false;
		} else if (!instructionDate.equals(other.instructionDate))
			return false;
		if (pricePerUnit == null) {
			if (other.pricePerUnit != null)
				return false;
		} else if (!pricePerUnit.equals(other.pricePerUnit))
			return false;
		if (settlementDate == null) {
			if (other.settlementDate != null)
				return false;
		} else if (!settlementDate.equals(other.settlementDate))
			return false;
		if (units != other.units)
			return false;
		if (usdSettledAmount == null) {
			if (other.usdSettledAmount != null)
				return false;
		} else if (!usdSettledAmount.equals(other.usdSettledAmount))
			return false;
		return true;
	}

}
