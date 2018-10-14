package cz.muni.fi.pa165.currency;

import javax.inject.Inject;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("cz.muni.fi.pa165.currency")
@EnableAspectJAutoProxy
public class JavaConfig {
	@Inject
	private ExchangeRateTable table;

    @Bean
    public CurrencyConvertor currencyConvertorImpl() {
    	System.out.println("Creating convertor");
        return new CurrencyConvertorImpl(table);
    }
}
