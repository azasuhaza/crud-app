package com.aza.crud.app.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import com.aza.crud.app.custom.exception.BusinessException;
import com.aza.crud.app.custom.exception.DBException;
import com.aza.crud.app.custom.exception.EmptyInputException;
import com.aza.crud.app.custom.exception.NullRecordException;
import com.aza.crud.app.model.CloudVendor;
import com.aza.crud.app.repository.CloudVendorRepositoryInterface;

@Service
public class CloudVendorService implements CloudVendorServiceInterface {

	@Autowired
	private CloudVendorRepositoryInterface repo;

	@Override
	public void deleteVendor(long vendorId) {
		
		if(vendorId <=0) {
			throw new EmptyInputException("601", "vendorId is not valid :deleteVendor() ");
		}
		
		try {
			repo.deleteById(vendorId);
		} catch (IllegalArgumentException e) {
			throw new NullRecordException("602", "vendor id is null :deleteVendor() " +e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("603", "error in service layer " + e.getMessage());
		}	
	}

	/** 
	 * java.util.NoSuchElementException: No value present
	 * */
	@Override
	public CloudVendor updateVendor(long vendorId, CloudVendor vendor) {
		
		CloudVendor updatedVendor = null;
		
		if(vendorId <=0 || vendor == null) {
			throw new EmptyInputException("604", "vendorId/vendor is null :updateVendor() " );
		}
		
		try {
			updatedVendor = repo.findById(vendorId).get();
			updatedVendor.setVendorAddress(vendor.getVendorAddress());
			updatedVendor.setVendorName(vendor.getVendorName());
			updatedVendor.setVendorPhoneNumber(vendor.getVendorPhoneNumber());			
		} catch(IllegalArgumentException e) {
			throw new NullRecordException("605", "updatedVendor is null :updateVendor() " + e.getMessage());
		} catch(Exception e) {
			throw new BusinessException("606", "error in service layer. " + e.getMessage());
		}

		
		try {
			repo.save(updatedVendor);
			return updatedVendor;			
		} catch(IllegalArgumentException e) {
			throw new NullRecordException("607", "not able to save, updatedVendor is null :updateVendor() " + e.getMessage());
		} catch(OptimisticLockingFailureException  e) {
			throw new DBException("608", "optimistic locking :updateVendor() " + e.getMessage());
		} catch(Exception e) {
			throw new BusinessException("609", "error in service layer "+ e.getMessage());
		}

	}

	@Override
	public CloudVendor getVendor(long vendorId) {
		
		try {
			return repo.findById(vendorId).get();
			
		} catch(NoSuchElementException e) {
			throw new NullRecordException("610","vendor ID not exists in DB, pick another one. " + e.getMessage());
		} catch(Exception e) {
			throw new BusinessException("611","error in service layer "+ e.getMessage());
		}
	}

	@Override
	public List<CloudVendor> getAllVendor() {
		
		try {
			List<CloudVendor> vendorList = repo.findAll();
			
			if(vendorList.isEmpty()) {
				throw new EmptyInputException("612", "no records exist");
			}
			return vendorList;
		} catch(Exception e) {
			throw new BusinessException("612", "error in service layer "+ e.getMessage());
		}
		
	}

	@Override
	public CloudVendor createVendor(CloudVendor vendor) {
		
		if(vendor.getVendorName().isEmpty() || vendor.getVendorName().length()==0 ) {
			throw new EmptyInputException("613", "vendor name is not provided");
		}
		
		try {
			CloudVendor savedVendor = repo.save(vendor);
			return savedVendor;
		} catch(IllegalArgumentException e) {
			throw new NullRecordException("614", "vendor is null " + e.getMessage());
		} catch (OptimisticLockingFailureException e) {
			throw new DBException("615", "optimistic locking violation " + e.getMessage());
		}
		catch(Exception e) {
			throw new BusinessException("616", "something is wrong in service layer " + e.getMessage());
		}
		
	}

}
