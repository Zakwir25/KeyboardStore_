package main;

public class product {
	private String path;
	private Integer keyboardPrice;
	private Integer keyboardStocks;
	private String keyboardDescription;
	private String keyboardName;
	
	public product(String path, String keyboardName, Integer keyboardPrice, Integer keyboardStocks, String keyboardDescription) {
		super();
		this.path = path;
		this.keyboardStocks = keyboardStocks;
		this.keyboardDescription = keyboardDescription;
		this.keyboardName = keyboardName;
		this.keyboardPrice = keyboardPrice;
		}
	
	public String getkeyboardName() {
		return keyboardName;
		}

	public void setkeyboardName(String keyboardName) {
		this.keyboardName = keyboardName;
		}

	public Integer getkeyboardPrice() {
		return keyboardPrice;
		}
	
	public void setpath(String path) {
		this.path = path;
		}

	public void setkeyboardPrice(Integer keyboardPrice) {
		this.keyboardPrice = keyboardPrice;
		}

	public Integer getkeyboardStocks() {
		return keyboardStocks;
		}

	public String getkeyboardDescription() {
		return keyboardDescription;
		}
	
	public void setkeyboardStocks(Integer keyboardStocks) {
		this.keyboardStocks = keyboardStocks;
		}
	
	public void setkeyboardDescription(String keyboardDescription) {
		this.keyboardDescription = keyboardDescription;
		}
	
	public String getpath() {
		return path;
		}
}
