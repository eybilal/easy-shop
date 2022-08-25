package io.coodle.easyshop.orderservice.statemachine.action;

import io.coodle.easyshop.orderservice.statemachine.OrderEvent;
import io.coodle.easyshop.orderservice.statemachine.OrderState;
import io.coodle.easyshop.orderservice.statemachine.OrderStateMachineSagaOrchestratorImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeallocateOrderAction implements Action<OrderState, OrderEvent> {
    @Override
    public void execute(StateContext<OrderState, OrderEvent> stateContext) {
        Long orderId = (Long) stateContext.getMessage().getHeaders().get(OrderStateMachineSagaOrchestratorImpl.ORDER_ID_HEADER);

        log.error("Compensating Transaction for order with id {} ", orderId);
        // Notify inventory service to deallocate order
    }
}
