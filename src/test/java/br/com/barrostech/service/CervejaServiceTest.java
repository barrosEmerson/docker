package br.com.barrostech.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.barrostech.model.CervejaType;
import br.com.barrostech.model.Cervejas;
import br.com.barrostech.repository.CervejaRepository;
import br.com.barrostech.service.exception.CervejaAlreadyExistException;

public class CervejaServiceTest {
	
	private CervejaService service;
	
	@Mock
	private CervejaRepository repositoryMocked;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service  = new CervejaService(repositoryMocked);
	}
	
	@Test(expected = CervejaAlreadyExistException.class)
	public void should_deny_creation_of_beer_that_exist() {
		Cervejas cervejaInDB = new Cervejas();
		cervejaInDB.setId(10L);
		cervejaInDB.setNome("Heineeken");
		cervejaInDB.setType(CervejaType.PILSEN);
		cervejaInDB.setVolume(new BigDecimal("500"));
		when(repositoryMocked.findByNomeAndType("Heineeken", CervejaType.PILSEN)).thenReturn(Optional.of(cervejaInDB));
		Cervejas cerveja = new Cervejas();
		cerveja.setNome("Heinneken");
		cerveja.setType(CervejaType.PILSEN);
		cerveja.setVolume(new BigDecimal("500"));
		
		service.save(cerveja);
	}

	@Test
	public void should_create_new_cerveja() {
		Cervejas cerveja = new Cervejas();
		cerveja.setNome("Heinneken");
		cerveja.setType(CervejaType.PILSEN);
		cerveja.setVolume(new BigDecimal("500"));
		
		Cervejas newCervejaDB = new Cervejas();
		newCervejaDB.setId(10L);
		newCervejaDB.setNome("Heineeken");
		newCervejaDB.setType(CervejaType.PILSEN);
		when(repositoryMocked.save(cerveja)).thenReturn(newCervejaDB);
		Cervejas cevSave = service.save(cerveja);
		assertThat(cevSave.getId(), equalTo(10L));
		assertThat(cevSave.getNome(),equalTo("Heineeken"));
		assertThat(cevSave.getType(), equalTo(CervejaType.PILSEN));
		
	}
}
