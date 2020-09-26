package sx3Configuration.ui;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.usb.UsbDevice;
import javax.usb.UsbDeviceDescriptor;
import javax.usb.UsbDisconnectedException;
import javax.usb.UsbException;
import javax.usb.UsbHostManager;
import javax.usb.UsbHub;
import javax.usb.UsbPort;
import javax.usb.UsbServices;
import javax.usb.event.UsbServicesEvent;
import javax.usb.event.UsbServicesListener;





public class USBManager {

	private static USBManager instance;
	private UsbServices services;
	
	USBManager() throws SecurityException, UsbException {
		services = UsbHostManager.getUsbServices();
		services.addUsbServicesListener(new UsbServicesListener() {
			@Override
			public void usbDeviceAttached(UsbServicesEvent use) {

			 use.getUsbDevice();
			 
			}

			@Override
			public void usbDeviceDetached(UsbServicesEvent use) {
				use.getUsbDevice();
			}
		});
	}

//	public static USBManager getInstance() throws SecurityException, UsbException {
//		if (instance == null) {
//			instance = new USBManager();
//		}
//		return instance;
//	}

	private ArrayList<UsbDevice> tryScan(short vid, short pid) {
		ArrayList<UsbDevice> devList = new ArrayList<UsbDevice>();
		try {
			scanDevices(devList, services.getRootUsbHub(), vid, pid);
		} catch (SecurityException | UsbException e) {
		}
		return devList;
	}

	private void scanDevices(ArrayList<UsbDevice> devList, UsbHub hub, short vid, short pid) {
		for (UsbDevice device : (List<UsbDevice>) hub.getAttachedUsbDevices()) {
			try {
				if (device.isUsbHub()) {
					scanDevices(devList, (UsbHub) device, vid, pid);
				} else {
					UsbDeviceDescriptor desc = device.getUsbDeviceDescriptor();
					if ((vid == 0) && (pid == 0)) {
						devList.add(device);
					} else {
						if ((desc.idVendor() == vid) && (desc.idProduct() == pid)) {
							devList.add(device);
						}
					}
				}
			} catch (NullPointerException e) {
			}
		}
	}

	/* List all the usb devices */
	public ArrayList<UsbDevice> getDeviceList() {
		return getDeviceList((short) 0, (short) 0);
	}

	/*
	 * List only specific VID PID devices Passing vid = 0 and pid = 0 will
	 * retrive all devices connected
	 */
	public ArrayList<UsbDevice> getDeviceList(short vid, short pid) {
		return tryScan(vid, pid);
	}

	

	static public ArrayList<UsbDevice> filterDeviceList(ArrayList<UsbDevice> inputList, short vid, short pid) {
		ArrayList<UsbDevice> newList = new ArrayList<UsbDevice>();
		for (UsbDevice dev : inputList) {
			try {
				UsbDeviceDescriptor desc = dev.getUsbDeviceDescriptor();
				if ((desc.idVendor() == vid) && (desc.idProduct() == pid)) {
					newList.add(dev);
				}
			} catch (NullPointerException e) {
			}
		}
		return newList;
	}

	static public boolean isDevicePresent(ArrayList<UsbDevice> inputList, UsbDevice dev) {
		for (UsbDevice device : inputList) {
			try {
				if (dev == device) {
					return true;
				}
			} catch (NullPointerException e) {
				if (dev == null) {
					return false;
				}
			}
		}
		return false;
	}



	public UsbServices getServices() {
		return services;
	}

//	public static void main(String[] args) {
//		ArrayList<UsbDevice> deviceList;
//		try {
//			deviceList = getInstance().getDeviceList();
//			
//			for (UsbDevice usbDevice : deviceList) {
//				UsbDeviceDescriptor usbDeviceDescriptor = usbDevice.getUsbDeviceDescriptor();
//				UsbPort productString = usbDevice.getParentUsbPort();
//				String vendorId = String.format("%04X", usbDeviceDescriptor.idVendor());
//				String productId = String.format("%04x%n", usbDeviceDescriptor.idProduct());
//				
//				System.out.println(vendorId);
//				System.out.println(productId);
//			}
//			
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UsbException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  catch (UsbDisconnectedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//			
//	}
}

