package com.example.application.views.list;

public class Client {

    private int id;
    private String img;
    private String client;
    private int total;
    private String status;
    private String date;
    private Object death;
    private int recover;
    private int vaccine;
    private int newcases;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

  

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

	public int getDeath() {
		return Integer.parseInt(String.valueOf(death));
	}

	public void setDeath(Object object) {
		this.death = object;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRecover() {
		return recover;
	}

	public void setRecover(int recover) {
		this.recover = recover;
	}

	public int getVaccine() {
		return vaccine;
	}

	public void setVaccine(int vaccine) {
		this.vaccine = vaccine;
	}

	public int getNewcases() {
		return newcases;
	}

	public void setNewcases(int newcases) {
		this.newcases = newcases;
	}

}
