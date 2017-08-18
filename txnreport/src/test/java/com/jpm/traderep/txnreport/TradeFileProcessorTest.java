package com.jpm.traderep.txnreport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jpm.traderep.txnreport.entity.Position;
import com.jpm.traderep.txnreport.file.input.TradeFileProcessor;
import com.jpm.traderep.txnreport.file.validation.Validator;

public class TradeFileProcessorTest {

	private TradeFileProcessor tfp;
	private Validator validator;
	private Map<Integer, Map<String, Position>> rptDataMap = new TreeMap<>();

	@Before
	public void setUp() throws Exception {

		tfp = new TradeFileProcessor();
		validator = new Validator();

		try {
			rptDataMap = tfp.processTxnFile("test.csv", ",");
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Integer d : rptDataMap.keySet()) {
			System.out.println("xx" + rptDataMap.get(d));
		}
	}

	@Test
	public void populateMapTest20160104() {

		String entity = "bar";
		Integer settleDate = 20160104;

		assertEquals(3, tfp.getTxnDataMap().size()); // Should be an aggregate of 3 rows

		Integer actualSettlementDate = tfp.getTxnDataMap().get(settleDate).get(entity).getActualSettlementDate();
		BigDecimal usdSettledAmountIncoming = tfp.getTxnDataMap().get(settleDate).get(entity)
				.getUsdSettledAmountIncoming();
		BigDecimal usdSettledAmountOutgoing = tfp.getTxnDataMap().get(settleDate).get(entity)
				.getUsdSettledAmountOutgoing();
		assertEquals(actualSettlementDate, new Integer(settleDate));
		assertEquals(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP),
				usdSettledAmountIncoming.setScale(2, BigDecimal.ROUND_HALF_UP));
		assertEquals(new BigDecimal(2155.00).setScale(2, BigDecimal.ROUND_HALF_UP),
				usdSettledAmountOutgoing.setScale(2, BigDecimal.ROUND_HALF_UP));


	}

	@Test
	public void populateMapTest20160104Foo() {

		String entity = "foo";
		Integer settleDate = 20160104;

		assertEquals(tfp.getTxnDataMap().size(), 3); // Should be an aggregate of 3 rows

		Integer actualSettlementDate = tfp.getTxnDataMap().get(settleDate).get(entity).getActualSettlementDate();
		BigDecimal usdSettledAmountIncoming = tfp.getTxnDataMap().get(settleDate).get(entity)
				.getUsdSettledAmountIncoming();
		BigDecimal usdSettledAmountOutgoing = tfp.getTxnDataMap().get(settleDate).get(entity)
				.getUsdSettledAmountOutgoing();
		assertEquals(new Integer(settleDate),actualSettlementDate);
		assertEquals(new BigDecimal(3622.50).setScale(2, BigDecimal.ROUND_HALF_UP),
				usdSettledAmountIncoming.setScale(2, BigDecimal.ROUND_HALF_UP));
		assertEquals(
				new BigDecimal(10025.00).setScale(2, BigDecimal.ROUND_HALF_UP),usdSettledAmountOutgoing.setScale(2, BigDecimal.ROUND_HALF_UP));
	}

	@Test
	public void populateMapTest20160107() {

		String entity = "bar";
		Integer settleDate = 20160107;

		assertEquals(tfp.getTxnDataMap().size(), 3); // Should be an aggregate of 3 rows

		Integer actualSettlementDate = tfp.getTxnDataMap().get(settleDate).get(entity).getActualSettlementDate();
		BigDecimal usdSettledAmountIncoming = tfp.getTxnDataMap().get(settleDate).get(entity)
				.getUsdSettledAmountIncoming();
		BigDecimal usdSettledAmountOutgoing = tfp.getTxnDataMap().get(settleDate).get(entity)
				.getUsdSettledAmountOutgoing();
		assertEquals(new Integer(settleDate),actualSettlementDate);
		assertEquals(
				new BigDecimal(14899.50).setScale(2, BigDecimal.ROUND_HALF_UP),usdSettledAmountIncoming.setScale(2, BigDecimal.ROUND_HALF_UP));
		assertEquals(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP),usdSettledAmountOutgoing.setScale(2, BigDecimal.ROUND_HALF_UP));

	}

	@Test
	public void populateMapTest20160110() {

		String entity = "bar";
		Integer settleDate = 20160110;

		assertEquals(tfp.getTxnDataMap().size(), 3); // Should be an aggregate of 3 rows

		Integer actualSettlementDate = tfp.getTxnDataMap().get(settleDate).get(entity).getActualSettlementDate();
		BigDecimal usdSettledAmountIncoming = tfp.getTxnDataMap().get(settleDate).get(entity)
				.getUsdSettledAmountIncoming();
		BigDecimal usdSettledAmountOutgoing = tfp.getTxnDataMap().get(settleDate).get(entity)
				.getUsdSettledAmountOutgoing();
		
		assertEquals(new Integer(settleDate),actualSettlementDate);
		assertEquals( BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_UP),usdSettledAmountOutgoing.setScale(2, BigDecimal.ROUND_HALF_UP));
		assertEquals(new BigDecimal(14237.30).setScale(2, BigDecimal.ROUND_HALF_UP), usdSettledAmountIncoming.setScale(2, BigDecimal.ROUND_HALF_UP));

	}

	@Test(expected = IOException.class)
	public void handleExceptionForNonExistentFile() throws IOException {

		tfp.processTxnFile("XXX", ",");

	}

	@Test
	public void noExceptionForDefaultFile() throws IOException {

		tfp.processTxnFile("dailytransaction.csv", ",");

	}

	// @Test
	public void exceptionForUnsupportedDelimiter() throws IOException {

		tfp.processTxnFile("dailytransaction.csv", "|");

	}

	@Test
	public void testValidBuySell() {
		assertTrue("Valid buySell test fail", validator.validBuySell("B"));
	}

	@Test
	public void testInvalidBuySell() {
		assertFalse("Invalid buySell test fail", validator.validBuySell("X"));
	}

	@Test
	public void testValidCurrency() {
		assertTrue("Invalid currency", validator.validCurrency("USD"));
	}

	@Test
	public void testNullCurrency() {
		assertFalse("Invalid null currency", validator.validCurrency(null));
	}

	@Test
	public void testValidEntity() {
		assertTrue("Invalid Entity", validator.validCurrency("foo"));
	}

	@Test
	public void testInvalidEntity() {
		assertFalse("Invalid Enity", validator.validCurrency(null));
	}

	@Test
	public void testValidFx() {
		assertTrue("Valid Fx", validator.validFx(BigDecimal.ONE));
	}

	@Test
	public void testInValidFx() {
		assertFalse("Invalid Fx", validator.validFx(null));
	}

	@Test
	public void testValidPricePerUnit() {
		assertTrue("Valid Price per unit", validator.validPricePerUnit("1"));
	}

	@Test
	public void testInvalidPricePerUnit() {
		assertFalse("Invalid Price per unit", validator.validPricePerUnit(null));
	}

	@Test
	public void testValidUnits() {
		assertTrue("Valid Units", validator.validUnits(2));
	}

	@Test
	public void testInvalidUnits() {
		assertFalse("Valid Units", validator.validUnits(-1));
	}

	@After
	public void tearDown() throws Exception {
	}

}
