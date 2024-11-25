package utils;

import models.Customer;
import models.Order;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Monitor principal que maneja la sincronización del restaurante
 * Controla el acceso a las mesas y la cola de espera
 */
public class RestaurantMonitor {
    private final int capacity; // Capacidad total del restaurante
    private final boolean[] tables; // Estado de las mesas (true = ocupada)
    private final Queue<Customer> waitingQueue; // Cola de espera de clientes
    private final utils.OrderBuffer orderBuffer; // Buffer de órdenes

    public RestaurantMonitor(int capacity) {
        this.capacity = capacity;
        this.tables = new boolean[capacity];
        this.waitingQueue = new LinkedList<>();
        this.orderBuffer = new utils.OrderBuffer();
    }

    /**
     * Cliente intentando entrar al restaurante
     *
     * @param customer Cliente que quiere entrar
     * @return número de mesa asignada o -1 si debe esperar
     */
    public synchronized int enterRestaurant(Customer customer) {
        // Buscar mesa libre
        for (int i = 0; i < tables.length; i++) {
            if (!tables[i]) {
                tables[i] = true; // Ocupar mesa
                return i;
            }
        }
        // Si no hay mesas libres, añadir a la cola de espera
        waitingQueue.add(customer);
        return -1;
    }

    /**
     * Cliente saliendo del restaurante
     *
     * @param tableNumber Número de mesa que se desocupa
     */
    public synchronized void leaveRestaurant(int tableNumber) {
        tables[tableNumber] = false; // Liberar mesa

        // Si hay clientes esperando, asignar la mesa al siguiente
        if (!waitingQueue.isEmpty()) {
            Customer nextCustomer = waitingQueue.poll();
            tables[tableNumber] = true;
            notifyCustomer(nextCustomer, tableNumber);
        }
    }

    /**
     * Notificar a un cliente que ya tiene mesa
     *
     * @param customer    Cliente a notificar
     * @param tableNumber Número de mesa asignada
     */
    private void notifyCustomer(Customer customer, int tableNumber) {
        customer.setTableNumber(tableNumber);
        customer.setState(Customer.CustomerState.WAITING_FOR_WAITER);
    }

    /**
     * Obtener el buffer de órdenes
     */
    public utils.OrderBuffer getOrderBuffer() {
        return orderBuffer;
    }
}