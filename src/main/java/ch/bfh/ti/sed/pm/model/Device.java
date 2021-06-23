package ch.bfh.ti.sed.pm.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * a Class that represents a device 
 */
@Entity
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    /**
     * a device have a ID for tracking 
     */
    private long deviceId;

    private String storageRoom;

    /**
     * a busy Boolean flag to keep track of busy/free Devices
     */
    private boolean busy = false;

    /**
     * No-args Constructor
     */
    public Device() {

    }

    public Device(String storageRoom) {
        this.storageRoom = storageRoom;
    }

    /**
     *
     * @return busy a boolean flag for device state busy/free
     */
    public boolean isBusy() {
        return busy;
    }

    /**
     * Getter for device
     * @return DeviceId
     */
    public long getDeviceId() {
        return deviceId;
    }

    /**
     * Setter for deviceId
     * @param deviceId device identificator to be set
     */
    public void setDeviceId(long deviceId) {
        this.deviceId = deviceId;
    }

    public String getStorageRoom() {
        return storageRoom;
    }

    public void setStorageRoom(String storageRoom) {
        this.storageRoom = storageRoom;
    }

    /**
     * Changes Device State from free to busy or vis versa 
     */
    public void changeDeviceState() {
        this.busy= !this.busy;
    }

    @Override
    public String toString() {
    	return "Device n:"+this.deviceId;
    }
}