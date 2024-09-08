package com.aza.crud.app.repository;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.aza.crud.app.model.CloudVendor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@DataJpaTest
public class CloudVendorRepositoryInterfaceTest {

	@Autowired
	private CloudVendorRepositoryInterface cloudRepo;
	CloudVendor cloudVendor;
	
	@BeforeEach
	void setUp() {
		cloudVendor = new CloudVendor((long) 1, "MYDIN", "Jln Masjid India", "03-989002");
		cloudRepo.save(cloudVendor);
	}
	
	@AfterEach
	void tearDown(){
		cloudVendor =null;
		cloudRepo.deleteAll();
	}
	
	//success scenario
	@Test
	void testFindByVendorName_Found() {
		List<CloudVendor> vendorList = cloudRepo.findByVendorName("MYDIN");
		
		System.out.println("vendorList.get(0): " + vendorList.get(0));
		System.out.println("cloudVendor.getId(): " + cloudVendor.getId());
		
		assertNotNull(vendorList);
		assertEquals(vendorList.get(0).getId(), cloudVendor.getId());
	}
	
	//failed scenario
}
