package vo;

public class City {
//select provinceName as 省份,cityName as 城市 from t_city,t_province where cityCode in (select cityCode from t_p_c where provinceCode in (select provinceCode from t_province where provinceName like '湖北'))
	private String cityCode;
	private String cityName;
	
	public City() {
		super();
		// TODO Auto-generated constructor stub
	}
	public City(String cityCode, String cityName) {
		super();
		this.cityCode = cityCode;
		this.cityName = cityName;
	}
	
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	@Override
	public String toString() {
		return "City [cityCode=" + cityCode + ", cityName=" + cityName + "]";
	}

	
}
