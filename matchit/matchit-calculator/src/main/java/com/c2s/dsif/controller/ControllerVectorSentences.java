package com.c2s.dsif.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.c2s.dsif.beans.FileBean;
import com.c2s.dsif.beans.OfferBean;
import com.c2s.dsif.entities.Application;
import com.c2s.dsif.entities.EtatApplication;
import com.c2s.dsif.entities.APIResponse;
import com.c2s.dsif.entities.TypeApplication;
import com.c2s.dsif.repository.applicationRepository;
import com.c2s.dsif.service.FileServiceClient;
import com.c2s.dsif.service.OfferServiceClient;

import org.springframework.http.MediaType;


@RestController
public class ControllerVectorSentences {
	
	@Autowired
    private OfferServiceClient offerServiceClient;
	@Autowired
	private FileServiceClient fileServiceClient;
	
	@Autowired
	private applicationRepository repository;

	private BufferedReader loadFile(String fileName) throws IOException {
	    Resource resource = new ClassPathResource(fileName);
	    InputStream inputStream = resource.getInputStream();
	    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
	    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	    return bufferedReader;
	}
	
	@GetMapping("/list")
	public List<OfferBean> findAllOffer() {
        List<OfferBean> offerList= offerServiceClient.getOffers();
       return offerList;
    }
	 
	@GetMapping("/offer/{id}")
	public String[] getOffer(@PathVariable("id") Long id) {
	    OfferBean offer = offerServiceClient.getOfferById(id);
	    String title = offer.getTitle();
	    String description = offer.getDescription();
	    String[] titleWords = title.split("\\s+");
	    String[] descWords = description.split("\\s+");
	    String[] offerWords = new String[titleWords.length + descWords.length];
	    System.arraycopy(titleWords, 0, offerWords, 0, titleWords.length);
	    System.arraycopy(descWords, 0, offerWords, titleWords.length, descWords.length);
	    return offerWords;
	}
	 
	@GetMapping("/CV/{id}")
	public Map<String, List<String>> getFileAsWords(@PathVariable Long id) {
	    ResponseEntity<Map<String, String>> contentMap = fileServiceClient.getFileAsJson(id);
	    return extractWordsFromContents(contentMap);
	}

	@GetMapping("/CvUpdated/{fileId}")
	public Map<String, List<String>> getFileUpdatedAsWords(@PathVariable Long fileId,
	                                                      @RequestBody Map<String, String> requestBody) throws IOException {
	    ResponseEntity<Map<String, String>> contentMap = fileServiceClient.updateContentWithSkillsAndExperience(fileId, requestBody);
	    return extractWordsFromContents(contentMap);
	}

	private Map<String, List<String>> extractWordsFromContents(ResponseEntity<Map<String, String>> contentMap) {
	    String contents = contentMap.getBody().get("contents");
	    String experience = contentMap.getBody().get("experience");
	    String[] contentsArray = contents.split("\r\n");
	    String[] experienceArray = experience.split("\r\n");
	    List<String> contentsList = new ArrayList<>();
	    List<String> experienceList = new ArrayList<>();

	    for (String line : contentsArray) {
	        String[] words = line.split("\\s+");
	        contentsList.addAll(Arrays.asList(words));
	    }

	    for (String line : experienceArray) {
	        String[] words = line.split("\\s+");
	        experienceList.addAll(Arrays.asList(words));
	    }

	    Map<String, List<String>> resultMap = new HashMap<>();
	    resultMap.put("skillsArray", contentsList);
	    resultMap.put("experienceArray", experienceList);
	    return resultMap;
	}


	
	@GetMapping("/userId/{id}")
	public Long getUserIdFile(@PathVariable Long id) throws FileNotFoundException {
	    ResponseEntity<Map<String, String>> contentMap = fileServiceClient.getFileAsJson(id);
	    if (contentMap != null && contentMap.getBody() != null) {
	        String userId = contentMap.getBody().get("iduser");
	        if (userId != null) {
	            return Long.parseLong(userId);
	        }
	    }
	    // handle case where "userId" key was not found or is null
		return id;
	}
	
	@GetMapping("/score/{idFile}")
	public String similarityBetweenAllOfferAndFile(@PathVariable Long idFile) {
	    List<OfferBean> offerList = getOfferByStatus();
	    
	    if (!offerList.isEmpty()) {
	        for (OfferBean offer : offerList) {
	            try {
	                Double percentage = calculateAndSaveSimilarity(idFile, offer.getId(), null);
	                System.out.println("Similarity with Offer " + offer.getId() + ": " + percentage);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return "Success";
	}
	

	@GetMapping("/scoreWithAllFiles/{idOffer}")
	public String similarityBetweenOfferAndAllFile(@PathVariable Long idOffer) throws IOException {
	    ResponseEntity<List<Map<String, String>>> contentMap = fileServiceClient.getAllContentAsJson();
	    List<Map<String, String>> contentList = contentMap.getBody();
	    System.out.println("Content list size: " + contentList.size());
	    if (contentList != null && !contentList.isEmpty()) {
	        for (Map<String, String> fileMap : contentList) {
	            String id = fileMap.get("id");
	            Double percentage = calculateAndSaveSimilarity(Long.parseLong(id), idOffer, null);
	            System.out.println("Similarity with Offer " + Long.parseLong(id) + ": " + percentage);
	        }
	    } else {
	        System.out.println("Content list is empty or null");
	    } 
	    return "Success";
	}




	

	@GetMapping("/reCalcul/{idFile}/{idOffer}")
	public Double similarityBetweenOfferAndFile(@PathVariable Long idFile, @PathVariable Long idOffer,
			@RequestParam Map<String, String> requestParams) throws IOException {
	    System.out.println("requessssssst " + requestParams );
		Double score = calculateAndSaveSimilarity(idFile, idOffer, requestParams);
	    return score;
	}

	public Double calculateAndSaveSimilarity(Long idFile, Long idOffer, Map<String, String> requestBody) throws IOException {
	    Map<String, List<String>> cvWordsMap = getFileAsWords(idFile);
	    List<String> cvContentsWords;
	    List<String> cvExperienceWords;

	    if (requestBody != null) {
	        Map<String, List<String>> updatedCvWordsMap = getFileUpdatedAsWords(idFile, requestBody);
	        cvContentsWords = updatedCvWordsMap.get("skillsArray");
	        cvExperienceWords = updatedCvWordsMap.get("experienceArray");
	    } else {
	        cvContentsWords = cvWordsMap.get("skillsArray");
	        cvExperienceWords = cvWordsMap.get("experienceArray");
	    }

	    String[] offerWords = getOffer(idOffer);
	    Double[] cvContentsVector = calculateSentenceVector(cvContentsWords != null ? cvContentsWords.toArray(new String[0]) : new String[0]);
	    Double[] cvExperienceVector = calculateSentenceVector(cvExperienceWords != null ? cvExperienceWords.toArray(new String[0]) : new String[0]);
	    Double[] offerVector = calculateSentenceVector(offerWords);

	    Double similarityContents = calculateCosineSimilarity(cvContentsVector, offerVector);
	    Double similarityExperience = calculateCosineSimilarity(cvExperienceVector, offerVector);

	    Long userId = getUserIdFile(idFile);
	    Double percentageContents = similarityContents * 100;
	    Double percentageExperience = similarityExperience * 100;

	    System.out.println("Similarity Contents: " + percentageContents);
	    System.out.println("Similarity Experience: " + percentageExperience);

	    Application existingApplication = repository.findByUserIdAndIdOffer(userId, idOffer);

	    if (existingApplication != null) {
	        // Si plusieurs enregistrements sont renvoyés, vous pouvez choisir le premier enregistrement
	        if (existingApplication instanceof List) {
	            List<Application> applicationList = (List<Application>) existingApplication;
	            existingApplication = applicationList.get(0);
	        }
	        existingApplication.setScore(percentageContents);
	        existingApplication.setScoreExp(percentageExperience);
	        existingApplication.setType(TypeApplication.POSTULER);
	        existingApplication.setEtat(EtatApplication.ENCOURS);
	        existingApplication.setDateCreation(new Date());
	        repository.save(existingApplication);
	    } else {
	        Application newApplication = new Application(null, TypeApplication.AUTO, EtatApplication.NULL, percentageContents, percentageExperience, userId, idOffer, null);
	        repository.save(newApplication);
	    }

	    // Retourne les deux pourcentages de similarité
	    return percentageContents;
	}
	
	@GetMapping("/files")
	public List<String[]> getAllFilesAsWords() throws IOException {
	    ResponseEntity<List<Map<String, String>>> contentMap = fileServiceClient.getAllContentAsJson();
	    List<String[]> contentsList = new ArrayList<>();

	    for (Map<String, String> fileMap : contentMap.getBody()) {
	        String name = fileMap.get("name");
	        String contents = fileMap.get("contents");
	        String id = fileMap.get("id");
	        if (contents != null) {
	            String[] contentsArray = contents.split("\\s+");
	            String[] fileContents = new String[contentsArray.length + 2];
	            fileContents[0] = id;
	            fileContents[1] = name;
	            for (int i = 0; i < contentsArray.length; i++) {
	                fileContents[i + 2] = contentsArray[i];
	            }
	            contentsList.add(fileContents);
	        }
	    }
	    return contentsList;
	}

	public Double[] calculateSentenceVector(String[] words) throws IOException {
	    BufferedReader file = loadFile("salma-vectors-francais.txt");

	    Double[][] vectors = file.lines()
	            .map(line -> line.split(" ", 2))
	            .filter(parts -> parts.length > 0)
	            .filter(parts -> Arrays.asList(words).contains(parts[0]))
	            .map(parts -> parts[1].split(" "))
	            .map(arr -> Arrays.stream(arr)
	                    .mapToDouble(Double::parseDouble)
	                    .boxed().toArray(Double[]::new))
	            .toArray(Double[][]::new);

	    if (vectors.length == 0) {
	        return new Double[0];
	    }

	    Double[] sum = Arrays.stream(vectors)
	            .reduce((acc, vector) -> {
	                for (int i = 0; i < vector.length; i++) {
	                    acc[i] += vector[i];
	                }
	                return acc;
	            })
	            .orElse(new Double[0]);

	    int size = vectors[0].length;
	    Double[] average = Arrays.stream(sum)
	            .map(value -> value / size)
	            .toArray(Double[]::new);

	    return average;
	}

	private double calculateCosineSimilarity(Double[] offer1Vector, Double[] sentence1Vector) {
	    double dotProduct = IntStream.range(0, offer1Vector.length)
	            .mapToDouble(i -> offer1Vector[i] * sentence1Vector[i])
	            .sum();

	    double magnitude1 = Math.sqrt(Arrays.stream(offer1Vector)
	            .map(d -> d * d)
	            .reduce(0.0, Double::sum));

	    double magnitude2 = Math.sqrt(Arrays.stream(sentence1Vector)
	            .map(d -> d * d)
	            .reduce(0.0, Double::sum));

	    if (magnitude1 == 0 || magnitude2 == 0) {
	        return 0.0;
	    } else {
	        return dotProduct / (magnitude1 * magnitude2);
	    }
	}

               /************************************************************/	
	
	@GetMapping("/allApplication")
	public List<Application> getAllApplication(){
		return repository.findAll();
	}
	
	@GetMapping("/applicationByUserId/{userId}")
	public List<Application> getApplicationsByUser(@PathVariable Long userId){
		return repository.findByuserIdAndType(userId, TypeApplication.POSTULER);
	}
	
	@GetMapping("/applicationByUserIdPaginated/{userId}/{offset}/{pageSize}")
	public List<Application> getApplicationsByUserPagination(@PathVariable Long userId,
	        @PathVariable int offset, @PathVariable int pageSize) {
	    Pageable pageable = PageRequest.of(offset, pageSize);
	    Page<Application> applicationsPage = repository.findByUserIdAndType(userId, TypeApplication.POSTULER, pageable);
	    return applicationsPage.getContent();
	}
	
	@GetMapping("/applicationByOfferid/{Idoffer}")
	public List<Application> getApplicationsByOfferid(@PathVariable Long Idoffer){
		return repository.findByidOfferAndType(Idoffer, TypeApplication.POSTULER);
	}
	
	@GetMapping("/applicationByOfferidAndUserId/{Idoffer}/{userId}")
	public Application getApplicationsByOfferidAnduserId(@PathVariable Long Idoffer, @PathVariable Long userId){
		return repository.findByidOfferAndUserId(Idoffer, userId);
	}
	
	@GetMapping("/applicationByMonth")
	public List<Application> getApplicationsSortedByMonth() {
	    return repository.findAllApplicationByDateCreation();
	 }
	
	@DeleteMapping("/deleteApplication/{id}")
		public void deleteApplication (@PathVariable Long id) {
			repository.deleteById(id);
		}
	
	 @GetMapping("/getOfferNotEpired")
	    public List<OfferBean> getOfferByStatus(){
		 return offerServiceClient.getOffersNotEx();
	 }
	    
	 //Postuler à une offre
	 @PutMapping(value = "/updateApplication/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
			 produces = MediaType.APPLICATION_JSON_VALUE)
	public Application updateApplication( @PathVariable("id") Long id,  @RequestBody Application app){
	   Application a = repository.findById(id).get();
	   if(app.getType()!=null){
		   a.setType(app.getType());
	   }
	   a.setEtat(app.getEtat());
	   Application appUpdated = repository.save(a);
	   return appUpdated;
	    }
	 
	public Page<Application> findApplicationWithPagination(int offset,int pageSize){
	    Page<Application> applications = repository.findAll(PageRequest.of(offset, pageSize));
	   return  applications;
	    }
	
	   @GetMapping("/paginationApplication/{offset}/{pageSize}")
	   public APIResponse<Page<Application>> getProductsWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
	       Page<Application> productsWithPagination = findApplicationWithPagination(offset, pageSize);
	       return new APIResponse<>(productsWithPagination.getSize(), productsWithPagination);
	   }
	
	
}








