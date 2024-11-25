package ui;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import models.Customer;
import models.Waiter;
import models.Chef;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class RestaurantApp extends GameApplication {
    private static final int RESTAURANT_CAPACITY = 20;
    private static final int NUM_WAITERS = 2;
    private static final int NUM_CHEFS = 3;
    private static final int WINDOW_WIDTH = 1024;
    private static final int WINDOW_HEIGHT = 768;

    private List<Entity> tables;
    private List<Waiter> waiters;
    private List<Chef> chefs;
    private List<Customer> waitingCustomers;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(WINDOW_WIDTH);
        settings.setHeight(WINDOW_HEIGHT);
        settings.setTitle("Simulador de Restaurante");
        settings.setVersion("1.0");
        settings.setManualResizeEnabled(true);
        settings.setScaleAffectedOnResize(true);
        settings.setPreserveResizeRatio(true);
    }

    @Override
    protected void initGame() {
        initializeGameComponents();
        createBackground();
        createKitchenAndWaitingArea();
        createTables();
        createStaff();
        startCustomerGenerator();
    }

    private void initializeGameComponents() {
        tables = new ArrayList<>();
        waiters = new ArrayList<>();
        chefs = new ArrayList<>();
        waitingCustomers = new ArrayList<>();
        FXGL.getGameWorld().addEntityFactory(new RestaurantFactory());
    }

    private void createBackground() {
        FXGL.entityBuilder()
                .at(0, 0)
                .view(FXGL.texture("floor.jpeg", WINDOW_WIDTH, WINDOW_HEIGHT))
                .buildAndAttach();
    }


    private void createKitchenAndWaitingArea() {
        // Área de cocina
        FXGL.entityBuilder()
                .at(WINDOW_WIDTH - 200, 50)
                .view(new Rectangle(150, 200, Color.LIGHTYELLOW))
                .buildAndAttach();

        // Área de espera
        FXGL.entityBuilder()
                .at(50, 50)
                .view(new Rectangle(150, 200, Color.LIGHTBLUE))
                .buildAndAttach();
    }

    private void createTables() {
        RestaurantFactory restaurantFactory = new RestaurantFactory();
        int startX = 250;
        int startY = 150;
        int spacing = 120;

        for (int i = 0; i < RESTAURANT_CAPACITY; i++) {
            int row = i / 5;
            int col = i % 5;
            Entity table = restaurantFactory.createTable(
                    startX + col * spacing,
                    startY + row * spacing);
            tables.add(table);
        }
    }

    private void createStaff() {
        // Crear meseros
        for (int i = 0; i < NUM_WAITERS; i++) {
            waiters.add(new Waiter(i));
            FXGL.spawn("waiter", 100, 300 + i * 50);
        }

        // Crear cocineros
        for (int i = 0; i < NUM_CHEFS; i++) {
            chefs.add(new Chef(i));
            FXGL.spawn("chef", WINDOW_WIDTH - 150, 100 + i * 50);
        }
    }

    private void startCustomerGenerator() {
        FXGL.run(() -> {
            if (waitingCustomers.size() < RESTAURANT_CAPACITY * 2) {
                Customer newCustomer = new Customer(waitingCustomers.size());
                waitingCustomers.add(newCustomer);
                RestaurantFactory.createCustomerEntity(newCustomer);
            }
        }, Duration.seconds(getPoissonRandomTime()));
    }

    private double getPoissonRandomTime() {
        double lambda = 2.0;
        return -Math.log(1.0 - Math.random()) / lambda;
    }

    public static void main(String[] args) {
        launch(args);
    }
}