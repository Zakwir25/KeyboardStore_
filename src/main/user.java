package main;

public class user {
	private String email,password;
	
	private Integer data1, data2, data3, data4;
	
	public user(String email, String password, Integer data1, Integer data2, Integer data3, Integer data4) {
		super();
		this.email = email;
		this.password = password;
		this.data1 = data1;
		this.data2 = data2;
		this.data3 = data3;
		this.data4 = data4;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getdata1() {
		return data1;
	}

	public void setdata1(Integer data1) {
		this.data1 = data1;
	}

	public Integer getdata2() {
		return data2;
	}

	public void setdata2(Integer data2) {
		this.data2 = data2;
	}

	public Integer getdata3() {
		return data3;
	}

	public void setdata3(Integer data3) {
		this.data3 = data3;
	}

	public Integer getdata4() {
		return data4;
	}

	public void setdata4(Integer data4) {
		this.data4 = data4;
	}

}
