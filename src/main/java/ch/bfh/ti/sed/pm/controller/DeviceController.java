package ch.bfh.ti.sed.pm.controller;

import ch.bfh.ti.sed.pm.model.Device;
import ch.bfh.ti.sed.pm.persistence.EntityManageable;

import java.util.ArrayList;
import java.util.List;

public class DeviceController {
    private EntityManageable em;

    public DeviceController(EntityManageable em) {
        this.em = em;
    }

    public void addDevice(String storageRoom) {
        em.getTransaction().begin();
        em.persist(new Device(storageRoom));
        em.getTransaction().commit();
    }

    public List<String> getAllFreeDevices(){
        List<Device> deviceList = em.createQuery("from Device d", Device.class).getResultList();
        List<String> deviceStringList = new ArrayList<String>(){
        };
        for (Device device: deviceList){
            if(!device.isBusy()){
                deviceStringList.add(String.valueOf(device.getDeviceId()));
            }
        }
        return deviceStringList;
    }
}