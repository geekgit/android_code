protected String getSSID()
{
	String ssid="N/A";
	try
	{
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		ssid=(wifiManager.getConnectionInfo().getSupplicantState()==SupplicantState.COMPLETED)?wifiManager.getConnectionInfo().getSSID():"Supplicant state not completed";
	}
	catch (Exception E)
	{
	
	}
	return ssid;
}