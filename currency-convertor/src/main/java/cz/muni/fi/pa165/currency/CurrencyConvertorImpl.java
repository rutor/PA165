package cz.muni.fi.pa165.currency;

import java.math.*;
import java.util.Currency;
import org.slf4j.*;


/**
 * This is base implementation of {@link CurrencyConvertor}.
 *
 * @author petr.adamek@embedit.cz
 */
public class CurrencyConvertorImpl implements CurrencyConvertor {

    private final ExchangeRateTable exchangeRateTable;
    private final Logger logger = LoggerFactory.getLogger(CurrencyConvertorImpl.class);

    public CurrencyConvertorImpl(ExchangeRateTable exchangeRateTable) {
        this.exchangeRateTable = exchangeRateTable;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) 
    	throws UnknownExchangeRateException, IllegalArgumentException {
    		logger.trace("Running conversion");
        if (sourceCurrency == null) {
        	throw new IllegalArgumentException("Null in source currency");
        }
        if (targetCurrency == null) {
        	throw new IllegalArgumentException("Null in target currency");
        }
        if (sourceAmount == null) {
        	throw new IllegalArgumentException("Null in source amount");
        }
        try {
	        BigDecimal ratio = exchangeRateTable.getExchangeRate(sourceCurrency, targetCurrency);
	        if (ratio == null) {
	        	logger.warn("Missing exchange rate for currencies: " + sourceCurrency.toString() + " and " + targetCurrency.toString());
	        	throw new UnknownExchangeRateException("Unknown exchange rate");
	        }
	        return sourceAmount.multiply(ratio).setScale(2, RoundingMode.HALF_EVEN);
	    } catch (ExternalServiceFailureException ex) {
	    	logger.error(ex.getMessage());
	    	throw new UnknownExchangeRateException(ex.getMessage());
	    }
    }

}
