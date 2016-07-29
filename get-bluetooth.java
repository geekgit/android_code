protected String getBluetooth()
{
	String bluetooth="N/A";
	try
	{
		bluetooth=BluetoothAdapter.getDefaultAdapter().getAddress();
	}
	catch (Exception E)
	{
	
	}
	return bluetooth;
}