package models;

/**
 * Clase que representa a un cliente en el restaurante
 * Cada cliente tiene un ID único y diferentes estados posibles
 */
public class Customer {
    // Enumeración para los posibles estados de un cliente
    public enum CustomerState {
        WAITING_FOR_TABLE, // Esperando mesa
        WAITING_FOR_WAITER, // Esperando ser atendido
        ORDERING, // Ordenando comida
        WAITING_FOR_FOOD, // Esperando la comida
        EATING, // Comiendo
        LEAVING // Saliendo del restaurante
    }

    private final int id; // Identificador único del cliente
    private CustomerState state; // Estado actual del cliente
    private long arrivalTime; // Tiempo de llegada al restaurante
    private int tableNumber; // Número de mesa asignada
    private Order currentOrder; // Orden actual del cliente

    // Constructor
    public Customer(int id) {
        this.id = id;
        this.state = CustomerState.WAITING_FOR_TABLE;
        this.arrivalTime = System.currentTimeMillis();
    }

    // Métodos para obtener y modificar el estado del cliente
    public CustomerState getState() {
        return state;
    }

    public void setState(CustomerState state) {
        this.state = state;
    }

    // Otros métodos getter y setter
    public int getId() {
        return id;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }
}