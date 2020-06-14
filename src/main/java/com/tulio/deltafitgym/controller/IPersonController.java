package com.tulio.deltafitgym.controller;

import com.tulio.deltafitgym.model.Person;

public interface IPersonController {

	public Boolean existsByCpf(String cpf);

	public void validate(Person person);
}
