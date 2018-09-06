package com.cwt.bpg.cbt.exchange.order.model.india;

import java.io.Serializable;

import javax.validation.Valid;

public class Request implements Serializable {

	private static final long serialVersionUID = 3951172881169529734L;

	@Valid
	private Air air;

	private Forex forex;

	@Valid
	private Hotel hotel;

    @Valid
	private Car car;

    private Train train;

    private IndiaInsurance insurance;

	private Tour tour;

	private Meeting meeting;

    private Visa visa;

	private String internalRemarks;

    public Air getAir()
    {
        return air;
    }

    public void setAir(Air air)
    {
        this.air = air;
    }

    public Forex getForex()
    {
        return forex;
    }

    public void setForex(Forex forex)
    {
        this.forex = forex;
    }

    public IndiaInsurance getInsurance()
    {
        return insurance;
    }

    public void setInsurance(IndiaInsurance insurance)
    {
        this.insurance = insurance;
    }

    public Tour getTour()
    {
        return tour;
    }

    public void setTour(Tour tour)
    {
        this.tour = tour;
    }

    public Meeting getMeeting()
    {
        return meeting;
    }

    public void setMeeting(Meeting meeting)
    {
        this.meeting = meeting;
    }

    public Visa getVisa()
    {
        return visa;
    }

    public void setVisa(Visa visa)
    {
        this.visa = visa;
    }

    public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

    public String getInternalRemarks()
    {
        return internalRemarks;
    }

    public void setInternalRemarks(String internalRemarks)
    {
        this.internalRemarks = internalRemarks;
    }
}
