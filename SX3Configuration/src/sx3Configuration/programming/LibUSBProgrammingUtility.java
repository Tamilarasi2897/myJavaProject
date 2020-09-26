package sx3Configuration.programming;

import java.util.ArrayList;

import org.usb4java.Device;
import org.usb4java.DeviceDescriptor;
import org.usb4java.DeviceList;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;

public class LibUSBProgrammingUtility {
	
	public static final int cypressVendorId = 1204;
	public static final int cypressProductIdFx3 = 243;
	
	

	public static void main(String[] args) {
		// Read the USB device list
		LibUSBProgrammingUtility libUSBProgrammingUtility = new LibUSBProgrammingUtility();
		libUSBProgrammingUtility.getDeviceList();

	

	}

	public ArrayList<String> getDeviceList() {
		int result = LibUsb.init(null);
		ArrayList<String> devices = new ArrayList<>();
	    DeviceList list = new DeviceList();
	    result = LibUsb.getDeviceList(null, list);
	    if (result < 0) {
	    	devices.add("Error :  Unable to read Device Descriptor.");
	    	return devices;
	    };

	    try
	    {
	        // Iterate over all devices and scan for the right one
	        for (Device device: list)
	        {
	            DeviceDescriptor descriptor = new DeviceDescriptor();
	            result = LibUsb.getDeviceDescriptor(device, descriptor);
	            
	            if (result != LibUsb.SUCCESS) {
	            	devices.add("Error : Unable to read Device Descriptor.");
	            }
	            else {
	            	System.out.println(descriptor.idVendor() + ">>" + descriptor.idProduct());
	            if (descriptor.idVendor() == cypressVendorId && descriptor.idProduct() == cypressProductIdFx3) {
	            	
	            	devices.add(Short.toString(descriptor.idProduct()));
	            }
	            }
	        }
	    }
	    finally
	    {
	        // Ensure the allocated device list is freed
	        LibUsb.freeDeviceList(list, true);
	    }
	    
	    return devices;
	}
	
	public ArrayList<Device> getDevices() {
		int result = LibUsb.init(null);
		ArrayList<Device> devices = new ArrayList<>();
	    DeviceList list = new DeviceList();
	    result = LibUsb.getDeviceList(null, list);
	    if (result < 0) throw new LibUsbException("Unable to get device list", result);

		try {
			// Iterate over all devices and scan for the right one
			for (Device device : list) {
				DeviceDescriptor descriptor = new DeviceDescriptor();
				result = LibUsb.getDeviceDescriptor(device, descriptor);
				if (result != LibUsb.SUCCESS)
					throw new LibUsbException("Unable to read device descriptor", result);
				else if (descriptor.idVendor() == cypressVendorId && descriptor.idProduct() == cypressProductIdFx3) {
					System.out.println(descriptor.idVendor() + ">>" + descriptor.idProduct());
					devices.add(device);
				}
			}
		}
	    finally
	    {
	        // Ensure the allocated device list is freed
	        LibUsb.freeDeviceList(list, true);
	    }
	    
	    return devices;
	}

}
