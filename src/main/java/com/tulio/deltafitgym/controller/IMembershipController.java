package com.tulio.deltafitgym.controller;

import java.util.List;
import java.util.Optional;

import com.tulio.deltafitgym.model.Membership;

public interface IMembershipController {

	public Membership save(Membership member);

	public Membership update(Membership member);

	public void delete(Long memberCod);

	public List<Membership> loadList(Membership member);

	public Optional<Membership> findByCod(Long cod);
}
