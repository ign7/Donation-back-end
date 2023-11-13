package com.backendduation.demo.Controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backendduation.demo.Entity.Donation;
//import com.backendduation.demo.Entity.Imagem;
import com.backendduation.demo.Entity.Material;
import com.backendduation.demo.Repository.DonationRepository;
//import com.backendduation.demo.Repository.ImagemRepository;
import com.backendduation.demo.Repository.MaterialRepository;
import com.backendduation.demo.Service.MaterialService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/materiais")
public class MaterialController {

	@Autowired
	MaterialService service;
	
	@Autowired
	MaterialRepository repository;
	
//	@Autowired
//    private ImagemRepository imagemRepository;
	
	@Autowired
	DonationRepository donationRepository;
	
	
	@GetMapping("/allmateriais")
	public ResponseEntity<List<Material>>findAll(){
		List<Material> list=service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	
	@GetMapping("/pesquisarMaterial/nomematerial={nome}")
	public ResponseEntity findByNome(@PathVariable String nome){
		Material material=repository.findBynome(nome);
		return ResponseEntity.ok().body(material);
	}
	
	@PostMapping( value="/cadastrarmaterial/donationid={iddonation}", consumes = "multipart/form-data")	
		public ResponseEntity<Material> insert(@PathVariable Long iddonation,@RequestParam("material") String materialJSON, @RequestParam("imagem") MultipartFile imagem ) 
		throws JsonMappingException, JsonProcessingException {
		    Material material = new ObjectMapper().readValue(materialJSON, Material.class);
          Donation doation=donationRepository.findById(iddonation).orElseThrow(()->new IllegalArgumentException("Id nao encontrado"));
		    try {
		    	if (!imagem.isEmpty()) {
			        String nomeArquivo = imagem.getOriginalFilename();
			        String diretorio = "C:\\imagensMaterial"; 	
			        imagem.transferTo(new File(diretorio, nomeArquivo));
		            System.out.println("nome arquivo"+nomeArquivo+" diretorio :"+diretorio);
		            material.setImagem(nomeArquivo);	
		            doation.getMateriais().add(material);
		            material.setMaterial(doation);
		            material=service.insert(material);
		            return  ResponseEntity.ok().body(material);
			    }
		    	return ResponseEntity.badRequest().build();	            
	        } catch (IOException e) {
	            e.printStackTrace();
	            return ResponseEntity.badRequest().build();
	        }   
		 	
	}
	
//	@PostMapping(value = "/cadastrarmaterial/donationid={iddonation}", consumes = "multipart/form-data")
//    public ResponseEntity<Material> insert(@PathVariable Long iddonation,
//                                          @RequestParam("material") String materialJSON,
//                                          @RequestParam("imagem") MultipartFile imagemArquivo) {
//        try {            
//        	Imagem imagem = new Imagem();
//            imagem.setDadosImagem(imagemArquivo.getBytes());
//            imagemRepository.save(imagem);
//            
//            Material material = new ObjectMapper().readValue(materialJSON, Material.class);
//            material.setImagem(imagem);
//
//            Donation donation = donationRepository.findById(iddonation)
//                    .orElseThrow(() -> new IllegalArgumentException("Id não encontrado"));
//            donation.getMateriais().add(material);
//            material.setMaterial(donation);
//
//            // Salvar a imagem e o material no banco de dados
//            Material materialSalvo = repository.save(material);
//
//            return ResponseEntity.ok().body(materialSalvo);
//        } catch (Exception e) {
//            // Log de erro
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//        
//	}
	
	
	
}
        
        
