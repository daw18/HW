/**
 * 
 */
package com.jpm.traderep.txnreport;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.jpm.traderep.txnreport.entity.Position;
import com.jpm.traderep.txnreport.file.input.TradeFileProcessor;

/**
 * @author daw
 *
 * Main class for processing and generating the transaction report.
 * 
 */
public class DailyTradeFileProcessor {

	/**
	 * @param args
	 * 
	 *            args[0] - Name of file containing the trades
	 *            args[1] - Optional delimiter
	 */

	public static void main(String[] args) {

		final String TXN_FILE_NAME = "dailytransaction.csv";
		String fileName;
		String delimiter;

		Map<Integer, Map<String, Position>> rptDataMap = new TreeMap<>();

		if (args.length == 1) {
			fileName = args[0];
		} else {
			fileName = TXN_FILE_NAME;
		}

		if (args.length == 2) {
			fileName = args[0];
			delimiter = args[1];
		} else {
			fileName = TXN_FILE_NAME;
			delimiter = ",";
		}

		TradeFileProcessor tfp = new TradeFileProcessor();
		try {
			rptDataMap = tfp.processTxnFile(fileName, delimiter);
			
		} catch (IOException e) {
			System.out.println("ERROR - Unable to process file: " + fileName);
			e.printStackTrace();
		}

		createReport(rptDataMap); 

	}

	private static void createReport(Map<Integer, Map<String, Position>> txnDataMap) {
		System.out.format("%-6s %8s %s %s", "Date  ", "Entity", " USD Settled Amount - Outgoing", "USD Settled Amount - Incoming\n");
		System.out.format("%-6s %6s %s %s", "========", "=======", "=============================", "================================\n");
		for (Integer date : txnDataMap.keySet()) {
			Map<String, Position> positions = txnDataMap.get(date);

			Map<String, Position> sortedPositions = positions.entrySet().stream()
					.sorted((p2, p1) -> p2.getValue().getUsdSettledAmountOutgoing().compareTo(p1.getValue().getUsdSettledAmountOutgoing()))
 					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
 							LinkedHashMap::new));

			for (String entity : sortedPositions.keySet()) {
				System.out.format("%6d %s %33.2f %32.2f \n", date, entity,positions.get(entity).getUsdSettledAmountIncoming(),positions.get(entity).getUsdSettledAmountOutgoing());
			}
		}
	
		System.out.format("%s","\n END OF REPORT ");
		

	}
}