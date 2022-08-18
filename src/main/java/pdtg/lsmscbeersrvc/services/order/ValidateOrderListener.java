package pdtg.lsmscbeersrvc.services.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import pdtg.ls.brewery.model.events.ValidateBeerOrderRequest;
import pdtg.ls.brewery.model.events.ValidateOrderResult;
import pdtg.lsmscbeersrvc.config.JmsConfig;

/**
 * Created by Diego T. 17-08-2022
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ValidateOrderListener {

    private final BeerOrderValidator validator;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listen(ValidateBeerOrderRequest validateBeerOrderRequest){
        boolean isValid = validator.validateOrder(validateBeerOrderRequest.getBeerOrderDto());

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESULT_QUEUE, ValidateOrderResult.builder()
                .isValid(isValid)
                .orderId(validateBeerOrderRequest.getBeerOrderDto().getId())
                .build());
    }

}
