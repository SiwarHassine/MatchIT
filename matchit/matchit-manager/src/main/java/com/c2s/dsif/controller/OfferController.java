package com.c2s.dsif.controller;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.c2s.dsif.Dto.APIResponse;
import com.c2s.dsif.entities.Offer;
import com.c2s.dsif.services.OfferService;

import ch.qos.logback.classic.Logger;

@RestController
//@RequestMapping("/offer")
public class OfferController {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(OfferController.class);
	@Autowired
	private OfferService service;
	
	   @PostMapping("/saveOffer")
	    public Offer saveOffer(@RequestBody Offer offer){
	       return service.saveOffer(offer);
	    }
	   
	   @GetMapping("/offerList")
	    public List<Offer> getAllOfferDetails(){
	        List<Offer> list = service.getAllOffer();
	        return list;
	    }
	   
	   @GetMapping("/getOfferById/{id}")
	    public Offer getOfferById( @PathVariable("id")  Long id){
		   Offer ofr = service.getOfferById(id);
	        return ofr;
	    }
	   
	//   @GetMapping("/getOfferNotEpired")
	  //  public List<Offer> getOfferByStatus(){
		//   List<Offer> ofr = service.getOfferByStatus();
	      //  return ofr;
	    //}
	   
	   @PutMapping("/updateOffer/{id}")
	    public Offer updateOffer( @PathVariable("id") Long id,  @RequestBody Offer offer){
	        Offer ofr = service.getOfferById(id);
	        ofr.setTitle(offer.getTitle());
	        ofr.setDescription(offer.getDescription());
	        ofr.setStatus(offer.getStatus());
	        ofr.setDateExpiration(offer.getDateExpiration());
	        Offer offerUpdated = service.saveOffer(ofr);
	        return offerUpdated;
	    }
	   
	   @DeleteMapping("deleteOffer/{id}")
	    public void deleteOffer(@PathVariable("id") Long id){
	        service.deleteOffer(id);
	      
	    }

	   @GetMapping("/{field}")
	   public APIResponse<List<Offer>> getProductsWithSort(@PathVariable String field) {
	       List<Offer> allOffers = service.findProductsWithSorting(field);
	       return new APIResponse<>(allOffers.size(), allOffers);
	   }

	   @GetMapping("/pagination/{offset}/{pageSize}")
	   
	   public APIResponse<Page<Offer>> getProductsWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
	       Page<Offer> productsWithPagination = service.findProductsWithPagination(offset, pageSize);
	       return new APIResponse<>(productsWithPagination.getSize(), productsWithPagination);
	   }
	   
	   @GetMapping("/getOfferNotEpired")
	   public List<Offer> getOffersNotEx() {
	       return service.getOfferByStatus();
	   }
	   
   @GetMapping("/getOfferNotEpiredPagination/{offset}/{pageSize}")
	   public APIResponse<Page<Offer>> getOffersNotExWithPagination(@PathVariable int offset, @PathVariable int pageSize, Boolean status) {
	       Page<Offer> productsWithPagination = service.findOfferStatusWithPagination(offset, pageSize, false);
	       return new APIResponse<>(productsWithPagination.getSize(), productsWithPagination);
	   }

	 
	   @GetMapping("/paginationAndSort/{offset}/{pageSize}/{field}")
	   public APIResponse<Page<Offer>> getProductsWithPaginationAndSort(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field) {
		   Page<Offer> productsWithPagination = Page.empty();
	       if (field.equals("true")) {
	    	   productsWithPagination = service.findOfferStatusWithPagination(offset, pageSize, true);
	       } else if (field.equals("false")) {
	    	   productsWithPagination = service.findOfferStatusWithPagination(offset, pageSize, false);
	       } else {
	           // handle invalid field value
	       }
	       return new APIResponse<>(productsWithPagination.getSize(), productsWithPagination);
	   }
		
	   
	   

}
