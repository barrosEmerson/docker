package br.com.barrostech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.barrostech.model.CervejaType;
import br.com.barrostech.model.Cervejas;

public interface CervejaRepository extends JpaRepository<Cervejas, Long> {
	
	Optional<Cervejas> findByNomeAndType(String nome, CervejaType type);

}
