package com.jpm.traderep.txnreport.file.input;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import com.jpm.traderep.txnreport.entity.Position;
import com.jpm.traderep.txnreport.entity.Transaction;
import com.jpm.traderep.txnreport.file.validation.Validator;

public class TradeFileProcessor {

	private Map<Integer, Map<String, Position>> txnDataMap = new TreeMap<>();

	public Map<Integer, Map<String, Position>> processTxnFile(String fileName, String delimiter) throws IOException {

		// Check for file existence

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			stream.filter(line -> !line.startsWith("#")).forEach(line -> {
				try {
					Transaction txn = parseData(delimiter, line);

					if (txn != null) {
						// System.out.println(txn);
						addToTxnMap(txn);
					}
				} catch (ParseException e) {
					System.out.println("ERROR - Unable to parse line: " + line);
					e.printStackTrace();
				}
			});

		}

		return getTxnDataMap();

	}

	private void addToTxnMap(Transaction txn) {

		// SettleDate, Entity, Position
		Map<String, Position> pos = new HashMap<>();

		if (getTxnDataMap().containsKey(txn.getActualSettlementDate())) {

			pos = getTxnDataMap().get(txn.getActualSettlementDate());
			if (pos.containsKey(txn.getEntity())) {
				Position position = pos.get(txn.getEntity());
				if (txn.getBuySell().equals("B")) {
					position.setUsdSettledAmountOutgoing(
							position.getUsdSettledAmountOutgoing().add(txn.getUsdSettledAmount()));
				} else {
					position.setUsdSettledAmountIncoming(
							position.getUsdSettledAmountIncoming().add(txn.getUsdSettledAmount()));
				}

				pos.put(txn.getEntity(), position);

			} else {
				if (txn.getBuySell().equals("B")) {
					pos.put(txn.getEntity(), new Position(txn.getActualSettlementDate(), txn.getEntity(), BigDecimal.ZERO, txn.getUsdSettledAmount()));
				} else {
					pos.put(txn.getEntity(), new Position(txn.getActualSettlementDate(), txn.getEntity(), txn.getUsdSettledAmount(), BigDecimal.ZERO));
				}

			}
		} else {
			if (txn.getBuySell().equals("B")) {
				pos.put(txn.getEntity(), new Position(txn.getActualSettlementDate(), txn.getEntity(), BigDecimal.ZERO, txn.getUsdSettledAmount()));
			} else {
				pos.put(txn.getEntity(), new Position(txn.getActualSettlementDate(), txn.getEntity(), txn.getUsdSettledAmount(), BigDecimal.ZERO));
			}

		}

		getTxnDataMap().put(txn.getActualSettlementDate(), pos);
	}

	private Transaction parseData(String delimiter, String data) throws ParseException {

		Validator validator = new Validator();
		boolean txnValid = true;
		Transaction txn = new Transaction();

		String[] dataRow = data.split(delimiter);
		String entity = dataRow[0];
		String buySell = dataRow[1];
		BigDecimal agreedFxRate = new BigDecimal(dataRow[2]);
		String currencyCode = dataRow[3];
		String instDateStr = dataRow[4];
		String settleDateStr = dataRow[5];
		long units = Long.parseLong(dataRow[6]);
		String pricePerUnitStr = dataRow[7];

		if (validator.validEntity(entity)) {
			txn.setEntity(entity);
		} else {
			txnValid = false;
		}

		if (validator.validBuySell(buySell)) {
			txn.setBuySell(buySell);
		} else {
			txnValid = false;
		}

		if (validator.validFx(agreedFxRate)) {
			txn.setAgreedFx(agreedFxRate);
		} else {
			txnValid = false;
		}

		if (validator.validCurrency(currencyCode)) {
			txn.setCurrencyCode(currencyCode);
		} else {
			txnValid = false;
		}

		if (validator.validInstructionDate(parseDate(instDateStr))) {
			txn.setInstructionDate(parseDate(instDateStr));
		} else {
			txnValid = false;
		}

		if (validator.validSettlementDate(parseDate(settleDateStr))) {
			txn.setSettlementDate(parseDate(settleDateStr));
		} else {
			txnValid = false;
		}

		if (validator.validUnits(units)) {
			txn.setUnits(units);
		} else {
			txnValid = false;
		}

		if (validator.validPricePerUnit(pricePerUnitStr)) {
			txn.setPricePerUnit(new BigDecimal(pricePerUnitStr));
		} else {

			txnValid = false;
		}

		txn.setActualSettlementDate(calculateActualSettlementDate(txn.getCurrencyCode(), txn.getSettlementDate()));

		BigDecimal usdSettlementAmount = txn.getPricePerUnit()
				.multiply(txn.getAgreedFx().multiply(new BigDecimal(txn.getUnits())));

		txn.setUsdSettledAmount(usdSettlementAmount);

		if (txnValid) {
			return txn;
		} else {
			return null;
		}
	}

	private Integer calculateActualSettlementDate(String currencyCode, Date settlementDate) {

		LocalDate ld = settlementDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate actualSettlementDate = ld;

		List<String> nonStdSettleCcyList = new ArrayList<>(Arrays.asList("AED", "SAR"));

		if (nonStdSettleCcyList.contains(currencyCode)) {

			if (ld.getDayOfWeek() == DayOfWeek.FRIDAY) { // Friday roll forward
															// 2 days to Sunday
				actualSettlementDate = ld.plusDays(2);
			}

			if (ld.getDayOfWeek() == DayOfWeek.SATURDAY) { // Saturday roll
															// forward 1 day to
															// Sunday
				actualSettlementDate = ld.plusDays(1);
			}
		} else {
			if (ld.getDayOfWeek() == DayOfWeek.SATURDAY) { // Saturday roll
															// forward 2 days to
															// Monday
				actualSettlementDate = ld.plusDays(2);
			}
			if (ld.getDayOfWeek() == DayOfWeek.SUNDAY) { // Saturday roll
															// forward 1 days to
															// Monday
				actualSettlementDate = ld.plusDays(1);
			}
		}

		DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;

		return Integer.parseInt(actualSettlementDate.format(formatter));
	}

	private Date parseDate(String date) throws ParseException {

		DateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
		Date txnDate = formatter.parse(date);

		return txnDate;
	}

	public Map<Integer, Map<String, Position>> getTxnDataMap() {
		return txnDataMap;
	}

	public void setTxnDataMap(Map<Integer, Map<String, Position>> txnDataMap) {
		this.txnDataMap = txnDataMap;
	}

}
