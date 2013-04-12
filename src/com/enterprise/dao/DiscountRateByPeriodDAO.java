package com.enterprise.dao;

import java.util.List;

import com.enterprise.beans.DiscountRateByPeriodBean;

public interface DiscountRateByPeriodDAO {
	void insert(DiscountRateByPeriodBean drpb) throws DataAccessException;
	void delete(int id);
	DiscountRateByPeriodBean findDiscountRatesById(int id) throws DataAccessException;
	List<DiscountRateByPeriodBean> findAllDiscountRatesByHotel(int id) throws DataAccessException;
	List<DiscountRateByPeriodBean> findAllDiscountRates() throws DataAccessException;
}
