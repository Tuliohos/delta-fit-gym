package com.tulio.deltafitgym.controller;

import java.util.List;
import java.util.Optional;

import com.tulio.deltafitgym.model.Member;
import com.tulio.deltafitgym.model.dto.MemberDTO;
import com.tulio.deltafitgym.model.dto.MembersGenderChartDTO;

public interface IMemberController {

	public Member save(Member member);

	public Member update(Member member);

	public void delete(Long memberCod);

	public List<MemberDTO> loadList(Member member);

	public Optional<Member> findByCod(Long cod);

	public List<MembersGenderChartDTO> getMembersGenderChartData();
}
