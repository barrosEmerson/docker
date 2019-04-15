package br.com.barrostech.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.barrostech.model.Cervejas;
import br.com.barrostech.repository.CervejaRepository;
import br.com.barrostech.service.CervejaService;

@RestController
@RequestMapping("/cervejas")
public class CervejasController {
	
	@Autowired
	private CervejaRepository repository;
	
	@Autowired
	private CervejaService service;
	
	@GetMapping
	public List<Cervejas> all(){
		return repository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cervejas create(@Valid @RequestBody Cervejas cervejas) {
		return service.save(cervejas);
	}
	
	@PutMapping("/{id}")
	public Cervejas update(@PathVariable Long id, @Valid @RequestBody Cervejas cerveja){
		cerveja.setId(id);
		return service.save(cerveja);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Cervejas>delete(@PathVariable Long id){
		service.deleteCerveja(id);
		return ResponseEntity.noContent().build();
	}
}
