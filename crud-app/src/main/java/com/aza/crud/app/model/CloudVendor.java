package com.aza.crud.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Data
@AllArgsConstructor
@Table(name="VENDOR", schema="SUHAZA")
public class CloudVendor {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="VENDOR_NAME")
	private String vendorName;
	
	@Column(name="VENDOR_ADDRESS")
	private String vendorAddress;
	
	@Column(name="VENDOR_PHONE")
	private String vendorPhoneNumber;
	
	public CloudVendor() {
	}
	
}
