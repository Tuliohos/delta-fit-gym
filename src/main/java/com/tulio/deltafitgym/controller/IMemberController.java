package com.tulio.deltafitgym.controller;

import java.util.List;
import java.util.Optional;

import com.tulio.deltafitgym.model.Member;

public interface IMemberController {

	public Member save(Member member);

	public Member update(Member member);

	public void delete(Long memberCod);

	public List<Member> loadList(Member member);

	public Optional<Member> findByCod(Long cod);

}
