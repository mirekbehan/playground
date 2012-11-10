package cz.uhk.hidoor;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Door {
	
	
	/*privateKey 
	 * MIIEowIBAAKCAQEAxcu/EOmBWHMvnkORx68awtlTpANrs5p+gRooHR4xqS+4i8KW
cIKX2/y12wsBO9QuroXPQQ879nC4fcFS0eo0F/L/DTIbS0gIQ+zM9bwgL/w7kjj7
DNEpZwIIUlZVsbAATjaH8ZULhTw9xESbcGHw2+5gApZq6vGASGg+af8XVd0EZx1I
h+jMfXQB8AwgPemfT5nzHX46G7W+Shc4S4eJifzon2YE+fgCisAXJ8HZ6gIeB4lm
qB9yo8QbcRXdHWUxVrwrkwCvNAkocYeupGd+f3OCxftnY8yAdG02O90LQM8DsATn
/7/F7G0MsmrGaYNLaUb2lfR2Bbd4HENcCoQ1fwIDAQABAoIBAAOgswZqYfbvVeY/
MwNIkOMV/VS6w1Tt9ngDtMyj6yFBQM8/aGbz3jkor2UvFHy2bYzB44U79EuW0ats
ykoTU5VE3ayuSJXe4lfsle3D34tBww3xgZlAlNPQ07o+3x99LORCV8f0hduRuUhK
QdeT1EkUXCpv+9IHT+UEFHD5PsJSXSIEADAetzdj83Lp8vt34qQOfQi6Rdz0McPn
+zq9MnjIgEhcCqd75vtEv9AiMDaGVB3p2a7yKhY2yu4rU3VhzmuziuchOvOwIFU3
c58DWXOq14AkphDGYHJd/uI+36wqHgta0i+UkI5dtmaPgglVK1Qwx16dJIECVAjG
21zCAXECgYEA9v878J+XusVDPgLU2qQhjyrBO7ao2RzUsmGzEhDdx2h0OEeLnJsJ
L/i/QOCVw7Ba2Z45tZKSweNe2nRozYDzaB24HLTIUBnbTdUxjJX+fQQFI1QZCQDs
/Pv1NHf1YKtgR775QEtCcpyML2ZbCYyyaE00JxY4cSFdzIsF5TIfHNsCgYEAzQFo
wRfU1Mj66oEmroKYVSlKo/mCnhJ1+mwQ2I01NtjqAYSCcdfJPTxxfDaPQsx+PR5Z
kxj9WVmNedw/q1xIFZLmidOK/VzTHudZycjFw+2GZ1QJFhiplTPLp2k6UcN7Ctsj
LjjNYFGGZfiX6q/VboirZZCp5A+e4QCmpVtMWS0CgYBRD0qYmYeuH/cFbuB0MNFi
ruFju3qGZlPXhwDBuQ9P1hPuNGy5JJhLShNaM1pzMnleWfMOh9/qCbijcPjEGMHJ
NZeqtBnZT+MNFB6CyOcQGDWndn4Q2mDsVbQPWB8LbrLWWPJsC0bvXjkThT/zTl1u
pYesHDrBxfWGn0l8Aor7aQKBgCD/ihISuPJrS2A127kZUlXa6KXOQsUY6Z6eiHOi
/x7N+ZS29JxIhJ7zvLymt5aj/enumbKAwj8AmDI2TpVRrFS0qaEjyOqRZ3ZA/QXq
6FJqSEkXkMLaw2k9xhkzaezBT2vm7BnDkONhHAYL5sAi/3LkUtptV7ta/FLxXOGe
+YIdAoGBAL8tE8/x5VcE9Ih1MC+mZ1GLkE6sz+USJ/C220UVl0Ep7lWNjClD9RG6
2D10Ud/eOszHmVkdm/CpJwgUDvQS6JXMFEcwjiEojvjx9I7s7dM7bIb9cTRgP1oB
K2WGVwDwilnvSgqEuWmxgDf01qHc96mszpFIn5AvSZXs8/TQUelo
	 * 
	 * 
	 * */

	/**
	 * @param args
	 */
	private String id="Luzany217_01";
	private String name="Vstupni Dvere";
	private int securityLevel=0;
	private String pswd = "44266e20a0235eaff5cd2fc0864cbd44508214ab6206e20ae4233553034953503bae1d29a1fc413485c4f7e6af8dd02baa0299973069521c1c7ed7cd6433ba20";
	private String publicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxcu/EOmBWHMvnkORx68awtlTpANrs5p+gRooHR4xqS+4i8KWcIKX2/y12wsBO9QuroXPQQ879nC4fcFS0eo0F/L/DTIbS0gIQ+zM9bwgL/w7kjj7DNEpZwIIUlZVsbAATjaH8ZULhTw9xESbcGHw2+5gApZq6vGASGg+af8XVd0EZx1Ih+jMfXQB8AwgPemfT5nzHX46G7W+Shc4S4eJifzon2YE+fgCisAXJ8HZ6gIeB4lmqB9yo8QbcRXdHWUxVrwrkwCvNAkocYeupGd+f3OCxftnY8yAdG02O90LQM8DsATn/7/F7G0MsmrGaYNLaUb2lfR2Bbd4HENcCoQ1fwIDAQAB";
	private static final String salt = "4/Dasd@A/dsjk69!"; 
	
	public Door() {
		
	}
	
	public Door(String id, String name, int securityLevel, String pswd, String publicKey) {
		this.id = id;
		this.name = name;
		this.securityLevel = securityLevel;
		this.pswd = pswd;
		this.publicKey = publicKey;
	}
	
	/*public boolean openDoor(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (verification(password)) {
			return true;
		} else {
			//findDoor(password);
			return false;}
	}
	
	private boolean verification(String pass) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		if (this.pswd.equals(getHash(pass))) {
			return true;
		} else {return false;}
	}*/
	
	public String getHash(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		   MessageDigest md = MessageDigest.getInstance("SHA-512");
		   md.update(password.getBytes());
		   byte byteData[] = md.digest();
		   StringBuffer hexString = new StringBuffer();
	    	for (int i=0;i<byteData.length;i++) {
	    		String hex=Integer.toHexString(0xff & byteData[i]);
	   	     	if(hex.length()==1) hexString.append('0');
	   	     	hexString.append(hex);
	    	}
	        return hexString.toString();
	   }
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(Byte securityLevel) {
		this.securityLevel = securityLevel;
	}

	public String getId() {
		return id;
	}

	public String getPswd() {
		return pswd;
	}

	public String getPublicKey() {
		return publicKey;
	}
		
}
