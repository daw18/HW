# HW



Tech Test

=========



Built using Maven.



Can be run from command line: 



HW\txnreport>java -cp target/txnreport-0.0.1-SNAPSHOT.jar com.jpm.traderep.txnreport.DailyTradeFileProcessor test.csv



Components

==========



com.jpm.traderep.txnreport

  DailyTradeFileProcessor.java - Main class

  

com.jpm.traderep.txnreport.entity

  Position.java

  Transaction.java



com.jpm.traderep.txnreport.file.input

  TradeFileProcessor.java

  

 com.jpm.traderep.txnreport.file.validation

    Validator.java

    

 JUnit Tests

 ===========

 

 com.jpm.traderep.txnreport

    TradeFileProcessorTest.java





Sample Files

=============



Sample output report in: /txnreport/Sample Txn Report.txt



Sample data file for JUnit test: /txnreport/test.csv



Sample test data file: /txnreport/dailytransaction.csv
