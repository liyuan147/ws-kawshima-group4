package kata.ex01.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author kawasima
 */
public class HighwayDrive implements Serializable {
    private LocalDateTime enteredAt; // 入った時間
    private LocalDateTime exitedAt; // 出た時間
    private VehicleFamily vehicleFamily; // 車種
    private RouteType routeType; // 都市/地方

    private Driver driver;

    public HighwayDrive() {
    }

    public LocalDateTime getEnteredAt() {
        return this.enteredAt;
    }

    public LocalDateTime getExitedAt() {
        return this.exitedAt;
    }

    public VehicleFamily getVehicleFamily() {
        return this.vehicleFamily;
    }

    public RouteType getRouteType() {
        return this.routeType;
    }

    public Driver getDriver() {
        return this.driver;
    }

    public void setEnteredAt(LocalDateTime enteredAt) {
        this.enteredAt = enteredAt;
    }

    public void setExitedAt(LocalDateTime exitedAt) {
        this.exitedAt = exitedAt;
    }

    public void setVehicleFamily(VehicleFamily vehicleFamily) {
        this.vehicleFamily = vehicleFamily;
    }

    public void setRouteType(RouteType routeType) {
        this.routeType = routeType;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String toString() {
        return "HighwayDrive(enteredAt=" + this.getEnteredAt() + ", exitedAt=" + this.getExitedAt() + ", vehicleFamily=" + this.getVehicleFamily() + ", routeType=" + this.getRouteType() + ", driver=" + this.getDriver() + ")";
    }
}
