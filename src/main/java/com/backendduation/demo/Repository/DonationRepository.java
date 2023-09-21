package com.backendduation.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendduation.demo.Entity.Donation;

public interface DonationRepository extends JpaRepository<Donation,Long > {

}
