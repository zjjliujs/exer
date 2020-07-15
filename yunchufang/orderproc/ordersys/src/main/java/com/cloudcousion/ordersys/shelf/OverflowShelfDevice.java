package com.cloudcousion.ordersys.shelf;

import com.cloudcousion.orderserver.model.OrderTemperature;
import com.cloudcousion.ordersys.kitchen.CookedOrder;
import com.cloudcousion.ordersys.utils.OrderValueCalculatorI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.UUID;

public class OverflowShelfDevice extends ShelfDevice {

    private Queue<CookedOrder> hotOrders;
    private Queue<CookedOrder> coldOrders;
    private Queue<CookedOrder> frozenOrders;
    private Queue<CookedOrder> allOrders;

    public OverflowShelfDevice(int capacity) {
        super(capacity);
        hotOrders = new PriorityQueue<>();
        coldOrders = new PriorityQueue<>();
        frozenOrders = new PriorityQueue<>();
        allOrders = new PriorityQueue<>();
    }

    @Override
    public boolean putOrder(CookedOrder order) {
        if (getFree() == 0) {
            return false;
        }
        order.setShelfType(ShelfType.OVERFLOW);
        switch (order.getTemp()) {
            case Hot:
                hotOrders.offer(order);
                break;
            case Cold:
                coldOrders.offer(order);
                break;
            case Frozen:
                frozenOrders.offer(order);
                break;
            default:
                //Impossible!
                throw new RuntimeException("OverflowShelfDevice, put orders, error temp:" + order.getTemp());
        }
        allOrders.offer(order);
        return true;
    }

    /**
     * Take one order to put to temp device
     *
     * @return
     */
    public CookedOrder takeOrder(OrderTemperature temperature) {
        CookedOrder order;
        switch (temperature) {
            case Hot:
                order = hotOrders.poll();
                break;
            case Cold:
                order = coldOrders.poll();
                break;
            case Frozen:
                order = frozenOrders.poll();
                break;
            default:
                //Impossible!
                throw new RuntimeException("Error temperature:" + temperature);
        }
        if (order != null) {
            allOrders.remove(order);
        }
        return order;
    }

    @Override
    public int getFree() {
        return capacity - orderSize();
    }

    @Override
    public int orderSize() {
        return hotOrders.size() + coldOrders.size() + frozenOrders.size();
    }

    @Override
    public List<CookedOrder> allOrders() {
        return new ArrayList<>(allOrders);
    }

    @Override
    public CookedOrder takeOrder(UUID id, OrderTemperature temperature) {
        CookedOrder order;
        switch (temperature) {
            case Hot:
                order = takeOrder(hotOrders, id);
                break;
            case Cold:
                order = takeOrder(coldOrders, id);
                break;
            case Frozen:
                order = takeOrder(frozenOrders, id);
                break;
            default:
                //Impossible
                throw new RuntimeException("error temperature:" + temperature);
        }
        if (order != null) {
            allOrders.remove(order);
        }
        return order;
    }

    private CookedOrder takeOrder(Queue<CookedOrder> orders, UUID id) {
        Iterator<CookedOrder> it = orders.iterator();
        while (it.hasNext()) {
            CookedOrder order = it.next();
            if (order.getId().equals(id)) {
                it.remove();
                return order;
            }
        }
        return null;
    }

    @Override
    public CookedOrder peekOrder(UUID id, OrderTemperature temperature) {
        switch (temperature) {
            case Hot:
                return peekOrder(hotOrders, id);
            case Cold:
                return peekOrder(coldOrders, id);
            case Frozen:
                return peekOrder(frozenOrders, id);
            default:
                //Impossible
                throw new RuntimeException("error temperature:" + temperature);
        }
    }

    private CookedOrder peekOrder(Queue<CookedOrder> orders, UUID id) {
        Iterator<CookedOrder> it = orders.iterator();
        while (it.hasNext()) {
            CookedOrder order = it.next();
            if (order.getId().equals(id)) {
                return order;
            }
        }
        return null;
    }

    /**
     * If this shelf have order with the temperature
     *
     * @param temperature
     * @return
     */
    public boolean haveOrder(OrderTemperature temperature) {
        switch (temperature) {
            case Hot:
                return hotOrders.size() > 0;
            case Cold:
                return coldOrders.size() > 0;
            case Frozen:
                return frozenOrders.size() > 0;
            default:
                //Impossible!
                throw new RuntimeException("error temperature:" + temperature);
        }
    }

    public CookedOrder pollOrder(OrderTemperature temperature) {
        CookedOrder order;
        switch (temperature) {
            case Hot:
                order = hotOrders.poll();
                break;
            case Cold:
                order = coldOrders.poll();
                break;
            case Frozen:
                order = frozenOrders.poll();
                break;
            default:
                //Impossible
                throw new RuntimeException("error temperature:" + temperature);
        }
        if (order != null) {
            allOrders.remove(order);
        }
        return order;
    }

    public CookedOrder pollOrder() {
        CookedOrder order = allOrders.poll();
        if (order != null) {
            switch (order.getTemp()) {
                case Hot:
                    hotOrders.remove(order);
                    break;
                case Cold:
                    coldOrders.remove(order);
                    break;
                case Frozen:
                    frozenOrders.remove(order);
                    break;
                default:
                    //Impossible
                    throw new RuntimeException("error temperature:" + order.getTemp());
            }
        }
        return order;
    }

    @Override
    public List<CookedOrder> evaluate(OrderValueCalculatorI evaluator) {
        List<CookedOrder> wasted = new ArrayList<>();
        Iterator<CookedOrder> it = allOrders.iterator();
        while (it.hasNext()) {
            CookedOrder order = it.next();
            float v = evaluator.calculateOrderValue(order);
            order.setValue(v);
            if (v <= 0) {
                wasted.add(order);
                switch (order.getTemp()) {
                    case Hot:
                        hotOrders.remove(order);
                        break;
                    case Cold:
                        coldOrders.remove(order);
                        break;
                    case Frozen:
                        frozenOrders.remove(order);
                        break;
                    default:
                        //Impossible
                        throw new RuntimeException("Error temp:" + order.getTemp());
                }
                it.remove();
            }
            logger.logDebug("EvaluateOrders value:" + v + ", order id:" + order.getId());
        }
        return wasted;
    }
}
