package vo;

public class User {
	private String userName;
	private String password;
	private String chrName;
	private String mail;
	private String provinceCode;
	private String cityCode;
	private String provinceName;
	private String cityName;

	
	public User() {
		super();
	}
	
	public User(String userName, String password, String chrName) {
		super();
		this.userName = userName;
		this.password = password;
		this.chrName = chrName;
	}
	
	
	

	public User(String userName, String password, String chrName, String mail,
			String provinceCode, String cityCode) {
		super();
		this.userName = userName;
		this.password = password;
		this.chrName = chrName;
		this.mail = mail;
		this.provinceCode = provinceCode;
		this.cityCode = cityCode;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getChrName() {
		return chrName;
	}
	public void setChrName(String chrName) {
		if(chrName ==null)
			chrName="";
		this.chrName = chrName;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		if(mail == null)
			mail ="";
		this.mail = mail;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		if(provinceName == null){
			provinceName = "";
		}
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		if(cityName == null){
			cityName = "";
		}
		this.cityName = cityName;
	}
	
	
	
	
	

}
