package com.tulio.deltafitgym.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tulio.deltafitgym.model.Member;
import com.tulio.deltafitgym.model.dto.MembersGenderChartDTO;

public interface IMemberRepository extends JpaRepository<Member, Long>{
	
	public Optional<Member> findByPersonCpf(String cpf);
	
	@Query(" SELECT new com.tulio.deltafitgym.model.dto.MembersGenderChartDTO( person.gender, COUNT(person.gender) )"
			+ " FROM Member"
			+ " GROUP BY person.gender")
	public List<MembersGenderChartDTO> getMembersGenderChartData();
	
}
