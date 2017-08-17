/**
 * 
 */
package com.jpm.traderep.txnreport.entity;

import java.math.BigDecimal;

/**
 * @author daw
 * 
 *         Position entity - an aggregation of trades settling on a specific
 *         date, grouped by entity
 *
 */
public class Position {

	private Integer actualSettlementDate;
	private String entity;
	private BigDecimal usdSettledAmountOutgoing;
	private BigDecimal usdSettledAmountIncoming;

	public Position(Integer actualSettlementDate, String entity, BigDecimal usdSettledAmountIncoming, BigDecimal usdSettledAmountOutgoing) {
		this.actualSettlementDate = actualSettlementDate;
		this.entity = entity;
		this.usdSettledAmountIncoming = usdSettledAmountIncoming;
		this.usdSettledAmountOutgoing = usdSettledAmountOutgoing;
	}

	public Integer getActualSettlementDate() {
		return actualSettlementDate;
	}

	public void setActualSettlementDate(Integer actualSettlementDate) {
		this.actualSettlementDate = actualSettlementDate;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public BigDecimal getUsdSettledAmountOutgoing() {
		return usdSettledAmountOutgoing;
	}

	public void setUsdSettledAmountOutgoing(BigDecimal usdSettledAmountOutgoing) {
		this.usdSettledAmountOutgoing = usdSettledAmountOutgoing;
	}

	public BigDecimal getUsdSettledAmountIncoming() {
		return usdSettledAmountIncoming;
	}

	public void setUsdSettledAmountIncoming(BigDecimal usdSettledAmountIncoming) {
		this.usdSettledAmountIncoming = usdSettledAmountIncoming;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actualSettlementDate == null) ? 0 : actualSettlementDate.hashCode());
		result = prime * result + ((entity == null) ? 0 : entity.hashCode());
		result = prime * result + ((usdSettledAmountIncoming == null) ? 0 : usdSettledAmountIncoming.hashCode());
		result = prime * result + ((usdSettledAmountOutgoing == null) ? 0 : usdSettledAmountOutgoing.hashCode());
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
		Position other = (Position) obj;
		if (actualSettlementDate == null) {
			if (other.actualSettlementDate != null)
				return false;
		} else if (!actualSettlementDate.equals(other.actualSettlementDate))
			return false;
		if (entity == null) {
			if (other.entity != null)
				return false;
		} else if (!entity.equals(other.entity))
			return false;
		if (usdSettledAmountIncoming == null) {
			if (other.usdSettledAmountIncoming != null)
				return false;
		} else if (!usdSettledAmountIncoming.equals(other.usdSettledAmountIncoming))
			return false;
		if (usdSettledAmountOutgoing == null) {
			if (other.usdSettledAmountOutgoing != null)
				return false;
		} else if (!usdSettledAmountOutgoing.equals(other.usdSettledAmountOutgoing))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Position [actualSettlementDate=" + actualSettlementDate + ", entity=" + entity
				+ ", usdSettledAmountOutgoing=" + usdSettledAmountOutgoing + ", usdSettledAmountIncoming="
				+ usdSettledAmountIncoming + "]";
	}

	
}
