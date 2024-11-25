package ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import models.Customer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RestaurantFactory implements EntityFactory {
    @Spawns("table")
    public Entity createTable(double x, double y) {
        return FXGL.entityBuilder()
                .at(x, y)
                .type(RestaurantType.TABLE)
                .viewWithBBox(FXGL.texture("table.png", 50, 50)) // Ajusta las dimensiones según tu imagen
                .with("occupied", false)
                .buildAndAttach();
    }


    @Spawns("customer")
    public static Entity createCustomerEntity(Customer customer) {
        return FXGL.entityBuilder()
                .at(50, 50) // Posición inicial en el área de espera
                .type(RestaurantType.CUSTOMER)
                .viewWithBBox(FXGL.texture("customer.png", 50, 50)) // Ajusta las dimensiones según tu imagen
                .with("customer", customer)
                .buildAndAttach();
    }

    @Spawns("waiter")
    public Entity createWaiter(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .type(RestaurantType.WAITER)
                .viewWithBBox(FXGL.texture("waiter.png", 50, 50))
                .buildAndAttach();
    }

    @Spawns("chef")
    public Entity createChef(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .type(RestaurantType.CHEF)
                .viewWithBBox(FXGL.texture("chef.png", 50, 50)) // Ajusta las dimensiones según tu imagen
                .buildAndAttach();
    }
}