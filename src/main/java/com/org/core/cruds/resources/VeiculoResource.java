package com.org.core.cruds.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.org.core.cruds.model.Veiculo;
import com.org.core.cruds.repository.VeiculoRepository;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/veiculo")
public class VeiculoResource {

@Autowired
private VeiculoRepository veiculoRepository;

@GetMapping
public List<Veiculo> listVeiculo(){
 return veiculoRepository.findAll();
}
	
@PostMapping
public ResponseEntity<Veiculo> create(@RequestBody Veiculo veiculo,HttpServletResponse response){
  Veiculo veiculoSaved = veiculoRepository.save(veiculo);
  URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(veiculoSaved.getId()).toUri();
  response.setHeader("Location", uri.toASCIIString());
  return ResponseEntity.created(uri).body(veiculoSaved);
}
 @GetMapping("/{id}")
 public java.util.Optional<Veiculo> findById(@PathVariable Long id){
	 return  veiculoRepository.findById(id);
	 
 }
  

	@DeleteMapping("/{id}")
	public ResponseEntity<Optional<Veiculo>> delete(@PathVariable Long id) {
		Optional<Veiculo> veiculoToDelete = veiculoRepository.findById(id);
		if(veiculoToDelete.isPresent()) {
			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(veiculoToDelete.get().getId()).toUri();
			veiculoRepository.delete(veiculoToDelete.get());
			return  ResponseEntity.created(uri).body(veiculoToDelete);
		}
		return  ResponseEntity.badRequest().body(Optional.of(new Veiculo()));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Optional<Veiculo>> Update(@PathVariable Long id, @RequestBody Veiculo veiculoAtualizado,HttpServletResponse response){
		Optional<Veiculo> veiculoExistente = veiculoRepository.findById(id);
		if(veiculoExistente.isPresent()) {			
			 BeanUtils.copyProperties(veiculoAtualizado, veiculoExistente.get(), "id");
			 veiculoRepository.save(veiculoAtualizado);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(veiculoExistente.get().getId()).toUri();
			return  ResponseEntity.created(uri).body(veiculoExistente);
		}
		return  ResponseEntity.badRequest().body(Optional.of(new Veiculo()));
	}
}

