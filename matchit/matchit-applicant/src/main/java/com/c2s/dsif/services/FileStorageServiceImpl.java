package com.c2s.dsif.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.c2s.dsif.Dto.fileDto;
import com.c2s.dsif.Dto.userDto;
import com.c2s.dsif.entities.FileDB;
import com.c2s.dsif.entities.Mapper;
import com.c2s.dsif.repositories.FileDBRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Feign;
import feign.gson.GsonDecoder;

import java.util.regex.Matcher;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.util.StringUtils;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;

@Service
public class FileStorageServiceImpl implements FileStorageService{
	
	@Autowired
	private FileDBRepository repository;
	@Autowired
	private UserClient userClient;
	
	@Autowired
	private Mapper mapper;

	public Long store(MultipartFile file, Long userId) throws IOException {
	    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	    byte[] bytes = file.getBytes();
	    String contentType = file.getContentType();
	    FileDB fileDB = new FileDB(null, fileName, contentType, bytes, null, null);

	    userDto user = userClient.getbyId(userId);
	    fileDto filedto = mapper.toDto(fileDB);
	    filedto.setUser(user);
	    fileDB = mapper.toEntity(filedto);

	    // Enregistrer le fichier dans la base de données
	    if (file.getContentType().equalsIgnoreCase("application/pdf")) {
	        String content = extractTextFromPdf(file.getBytes());
	        fileDB.setContent(content);
	    }
	    // Enregistrer le fichier dans la base de données
	    FileDB savedFile = repository.save(fileDB);
	    return savedFile.getId();
	}


	  public userDto getUserByFileId(Long fileId) throws FileNotFoundException {
	        FileDB fileDB = repository.findById(fileId)
	                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
	        Long userId = fileDB.getUserId();
	        return userClient.getbyId(userId);
	    }
		  
    public String getContentFile(Long fileId) throws IOException {
        FileDB fileDB = repository.findById(fileId)
                .orElseThrow(() -> new NotFoundException("Not found file with id = " + fileId));
        if (!fileDB.getType().equalsIgnoreCase("application/pdf")) {
            throw new BadRequestException("The file is not a PDF");
        }
        return fileDB.getContent();
    }
    
	public void setContentFile(Long fileId, Map<String, String> content) throws JsonProcessingException {
	    ObjectMapper mapper = new ObjectMapper();
	    String jsonContent = mapper.writeValueAsString(content);

	    FileDB fileDB = repository.findById(fileId)
	            .orElseThrow(() -> new NotFoundException("Not found file with id = " + fileId));

	    fileDB.setContent(jsonContent);
	    repository.save(fileDB);
	}
	
	public void updateFile(Long fileId, MultipartFile file) throws IOException {
		FileDB fileDB = repository.findById(fileId)
				.orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
		String newContent = extractTextFromPdf(file.getBytes());
	    fileDB.setContent(newContent);
	    
	    repository.save(fileDB);
	    
	}
        
    @Override
    public Map<String, String> getContentAsJson(Long fileId) throws IOException {
        String content = getContentFile(fileId);
        
        Map<String, String> jsonMap = new HashMap<>();

        // extraire les champs clés-valeur
        // remplacez les champs 'name', 'email', etc. par les champs pertinents de votre CV
        jsonMap.put("id", fileId.toString());
        jsonMap.put("iduser", getUserByFileId(fileId).getId().toString());
        
        jsonMap.put("name", extractName(content));
        jsonMap.put("email", extractEmail(content));
        jsonMap.put("phone", extractPhone(content));
        jsonMap.put("adresse", extractAddress(content));
        jsonMap.put("GitHub", extractGitHub(content));
        jsonMap.put("linkedIn", extractLinkedIn(content));
     
        jsonMap.put("contents", extractCompetences(content));
        jsonMap.put("experience", extractExperience(content));
 
        
        return jsonMap;
    }
       
  //  @Override
    //public List<Map<String, String>> getAllFilesAsJson() throws IOException {
      //  List<FileDB> files = repository.findAll();
        //List<Map<String, String>> jsonList = new ArrayList<>();
       // for (FileDB file : files) {
         //   if (file.getType().equalsIgnoreCase("application/pdf")) {
           //     Map<String, String> jsonMap = getContentAsJson(file.getId());
             //   jsonList.add(jsonMap);
           // }
       // }
       // return jsonList;
   // }
    
    @Override
    public List<Map<String, String>> getAllContentAsJson() throws IOException {
        List<FileDB> files = repository.findAll();
        List<Map<String, String>> jsonList = new ArrayList<>();
        for (FileDB file : files) {
            if (file.getType().equalsIgnoreCase("application/pdf")) {
                String content = getContentFile(file.getId());
                Map<String, String> jsonMap = new HashMap<>();
                
                if (content != null) {
                	jsonMap.put("id", file.getId().toString());
                    jsonMap.put("name", extractName(content));
                    jsonMap.put("email", extractEmail(content));
                    jsonMap.put("phone", extractPhone(content));
                    jsonMap.put("adresse", extractAddress(content));
                    jsonMap.put("GitHub", extractGitHub(content));
                    jsonMap.put("linkedIn", extractLinkedIn(content));
                    jsonMap.put("contents", extractCompetences(content));
                    //jsonMap.put("experience", extractExperience(content));
                }
                
                jsonList.add(jsonMap);
            }
        }
        return jsonList;
    }

    @Override
    public Map<String, String> updateContentWithSkillsAndExperience(Long fileId, String contents, String experience) throws IOException {
        
    	Map<String, String> fileContent = getContentAsJson(fileId);
        String currentContents = (String)fileContent.get("contents");
        String currentExperience = (String)fileContent.get("experience");
        
        if (currentContents != null) {
        	fileContent.put("contents", currentContents +" \n "+ contents);
        } else {
        	fileContent.put("contents", contents);
        }

        if (currentExperience != null) {
        	fileContent.put("experience", currentExperience +" \n "+ experience);
        } else {
        	fileContent.put("experience", experience);
        }

        System.out.println("teeeeeeeeeeeeeeeeeeeeeeeeeeeeeeest" + fileContent);

        // Convertir le contenu du CV en JSON
        //String updatedContentJson = convertToJsonString(jsonMap);
        System.out.println("amaaaaaaaaaaaan"+fileContent);
        // Mettre à jour le contenu du CV dans la table FileDB
       // setContentFile(fileId, updatedContentJson);

        // Enregistrer les modifications dans la table FileDB
        //FileDB fileDB = repository.findById(fileId)
          //      .orElseThrow(() -> new NotFoundException("Not found file with id = " + fileId));
        //repository.save(fileDB);
        return fileContent;
    }
    
    @Override
	    public Long getFileIdByUserId(Long userId) throws FileNotFoundException {
	        FileDB file = repository.findByUserId(userId);
	        if (file != null) {
	            return file.getId();
	        } else {
	            throw new FileNotFoundException("File not found for the specified user ID");
	        }
	    }
    
	    public String extractTextFromPdf(byte[] pdfBytes) throws IOException {
	        try (PDDocument document = PDDocument.load(pdfBytes)) {
	            if (!document.isEncrypted()) {
	                PDFTextStripper stripper = new PDFTextStripper();
	                return stripper.getText(document);
	            } else {
	                throw new IOException("The document is encrypted and cannot be extracted.");
	            }
	        }
	    }	
	    
	    public String extractName(String content) {
	        // recherche du nom dans le contenu en utilisant des expressions régulières
	    	Pattern pattern = Pattern.compile("[A-Z]+\\s[A-Z]+");
	    	Matcher matcher = pattern.matcher(content);
	    	if (matcher.find()) {
	    	    return matcher.group().trim();
	    	}
	        
	        Pattern pattern1 = Pattern.compile("(?i)(nom|name)\\s*:?\\s*(\\p{Lu}[\\p{Ll}\\s-]*)");
	        Matcher matcher1 = pattern1.matcher(content);
	        if (matcher1.find()) {
	            return matcher1.group(2).trim();
	        }

	        Pattern pattern2 = Pattern.compile("(?i)(prenom|firstname)\\s*:?\\s*(\\p{Lu}[\\p{Ll}\\s-]*)");
	        Matcher matcher2 = pattern2.matcher(content);
	        if (matcher2.find()) {
	            return matcher2.group(2).trim();
	        }
	        // si aucune correspondance n'est trouvée, retourner null
	        return null;
	    }


	    public String extractEmail(String content) {
	        // recherche de l'email dans le contenu en utilisant une expression régulière
	        Pattern pattern = Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b");
	        Matcher matcher = pattern.matcher(content);
	        if (matcher.find()) {
	            return matcher.group();
	        } else {
	            return null;
	        }
	    }

	    public String extractPhone(String content) {
	        // recherche du numéro de téléphone dans le contenu en utilisant une expression régulière
	        Pattern pattern = Pattern.compile("(?i)(Téléphone|Phone)\\s*:\\s*(\\(\\+216\\)|\\+216|\\(00216\\)|00216)?\\s*(\\d{2}(\\s*\\d{3}){2})");
	        Matcher matcher = pattern.matcher(content);
	        if (matcher.find()) {
	            String phoneNumber = matcher.group(3);
	            phoneNumber = phoneNumber.replaceAll("\\s", "");
	            return "(+216) " + phoneNumber.substring(0, 2) + " " + phoneNumber.substring(2, 5) + " " + phoneNumber.substring(5, 7) + " " + phoneNumber.substring(7);
	        } else {
	            return null;
	        }
	    }


	    public String extractAddress(String content) {
	        // recherche de l'adresse dans le contenu en utilisant une expression régulière
	        Pattern pattern = Pattern.compile("(?i)(Adresse|Address)\\s*:\\s*(.*)");
	        Matcher matcher = pattern.matcher(content);
	        if (matcher.find()) {
	            return matcher.group(2).trim();
	        } else {
	            return null;
	        }
	    }
	    public String extractLinkedIn(String content) {
	        // recherche de l'adresse LinkedIn dans le contenu en utilisant une expression régulière
	        Pattern pattern = Pattern.compile("(?i)linkedin.com/in/([a-zA-Z0-9\\-]+)");
	        Matcher matcher = pattern.matcher(content);
	        if (matcher.find()) {
	            return matcher.group(0);
	        } else {
	            return null;
	        }
	    }

	    public String extractGitHub(String content) {
	        // recherche de l'adresse GitHub dans le contenu en utilisant une expression régulière
	        Pattern pattern = Pattern.compile("(?i)github.com/([a-zA-Z0-9\\-]+)");
	        Matcher matcher = pattern.matcher(content);
	        if (matcher.find()) {
	            return matcher.group(0);
	        } else {
	            return null;
	        }
	    }

	    public String extractExperience(String content) {
	    	List<String> ignoreWords = Arrays.asList(" ", "|", "_", "_,", "–,", "\t", "\n", "\r", "-", "*", "●", "(", ")", ":", ":,", ",", "/", "(", ")", "_", ":", "\r", "\t", "C", "O", "N", "T", "A", "P", "R", "F", "I", "L", ".", ";", "/");
	        Pattern pattern = Pattern.compile("(?i)(EXPÉRIENCES PROFESSIONNELLES|PARCOURS PROFESSIONNEL|EXPÉRIENCE|EXPERIENCE|STAGE)[^\\n]*\\n([\\w\\W]+?)(?=(Compétences|Skills|Certifications|centre d'interet|$))");
	        Matcher matcher = pattern.matcher(content);
	        if (matcher.find()) {
	            String experienceText = matcher.group(2).trim();
	            List<String> words = Arrays.asList(experienceText.split("\\s+"));
	            List<String> filteredWords = removeEmptyWords(words, ignoreWords);
	            return String.join(" ", filteredWords);
	        } else {
	            return null;
	        }
	    }

	    public String extractCompetences(String content) {
	        List<String> ignoreWords = Arrays.asList(" ", "|", "_", "_,", "–,", "\t", "\n", "\r", "-", "*", "●", "(", ")", ":", ":,", ",", "/", "(", ")", "_", ":", "\r", "\t", "C", "O", "N", "T", "A", "P", "R", "F", "I", "L", ".", ";", "/");
	        Pattern pattern = Pattern.compile("(?i)(Compétences|COMPÉTENCES|Skills|Système d’exploitation)[^\\n]*\\n([\\w\\W]+?)(?=(EXPÉRIENCES PROFESSIONNELLES|PARCOURS PROFESSIONNEL|FORMATION|F O R M A T I O N|EXPÉRIENCE|Certifications|centre d'interet|$))");
	        Matcher matcher = pattern.matcher(content);
	        if (matcher.find()) {
	            String competenceText = matcher.group(2).trim();
	            List<String> words = Arrays.asList(competenceText.split("\\s+"));
	            List<String> filteredWords = removeEmptyWords(words, ignoreWords);
	            return String.join(" ", filteredWords);
	        }else {
	            return null;
	        }
	    }

	    public List<String> removeEmptyWords(List<String> words, List<String> emptyWords) {
	        List<String> result = new ArrayList<>();
	        for (String word : words) {
	            word = word.trim();
	            if (!word.isEmpty() && !emptyWords.contains(word)) {
	                result.add(word);
	            }
	        }
	        return result;
	    }


	    
	   
		@Override
		public FileDB getFile(Long id) {
			return repository.findById(id).get();
		}
	
		@Override
		public Stream<FileDB> getAllFiles() {
			return repository.findAll().stream();
		}
	
		
		@Override
		public String extractTextFromFile(FileDB fileDB) throws IOException {
	        InputStream inputStream = new ByteArrayInputStream(fileDB.getData());
	        PDDocument document = PDDocument.load(inputStream);
	        PDFTextStripper stripper = new PDFTextStripper();
	        String text = stripper.getText(document);
	        document.close();
	        return text;
	    }
		
		 @Override
		 public List<byte[]> extractImagesFromPDF(FileDB fileDB) throws IOException {
		        InputStream inputStream = new ByteArrayInputStream(fileDB.getData());
		        PDDocument document = PDDocument.load(inputStream);
		        List<byte[]> images = new ArrayList<>();
		        PDPageTree pages = document.getPages();
		        for (PDPage page : pages) {
		            PDResources resources = page.getResources();
		            for (COSName xObjectName : resources.getXObjectNames()) {
		                PDXObject xObject = resources.getXObject(xObjectName);
		                if (xObject instanceof PDImageXObject) {
		                    PDImageXObject image = (PDImageXObject) xObject;
		                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		                    ImageIO.write(image.getImage(), image.getSuffix(), outputStream);
		                    images.add(outputStream.toByteArray());
		                }
		            }
		        }
		        document.close();
		        return images;
		    }
		
		

}
