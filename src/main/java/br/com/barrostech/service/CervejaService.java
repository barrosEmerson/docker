package br.com.barrostech.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.barrostech.model.Cervejas;
import br.com.barrostech.repository.CervejaRepository;
import br.com.barrostech.service.exception.CervejaAlreadyExistException;

@Service
public class CervejaService {

	private CervejaRepository repository;

	public CervejaService(@Autowired CervejaRepository repository) {
		this.repository = repository;
	}

	public Cervejas save(Cervejas cerveja) {
		Optional<Cervejas> cervejaExist = repository.findByNomeAndType(cerveja.getNome(), cerveja.getType());

		if (cervejaExist.isPresent()) {
			throw new CervejaAlreadyExistException();
		}
		return repository.save(cerveja);
	}

	public boolean isUpdatingToADifferentBeer(Cervejas beer, Optional<Cervejas> beerByNameAndType) {
		return beer.alreadyExist() && !beerByNameAndType.get().equals(beer);

	}
	
	public void deleteCerveja(Long id) {
		Optional<Cervejas> cerv = repository.findById(id);
		
		if(!cerv.isPresent()) {
			throw new EntityNotFoundException();
		}
		repository.delete(cerv.get());
	}
	
	

}
