package soft.secure_disk.storageService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import soft.secure_disk.storageService.dto.FileRequest;
import soft.secure_disk.storageService.model.AppFile;
import soft.secure_disk.storageService.model.StorageFolder;
import soft.secure_disk.storageService.repository.AppFileRepository;
import soft.secure_disk.storageService.service.FileService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

@Controller
@RequiredArgsConstructor
@RequestMapping("/disk")
public class StorageController {

    private final FileService fileService;
    private final StorageFolder storageFolder;
    private final AppFileRepository appFileRepository;

    @GetMapping
    public String findAll(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("files", fileService.findAllByUser_Email(userDetails.getUsername()));
        return "/storage/disk";
    }

    @GetMapping("/upload")
    public String saveFileGet() {
        return "/storage/upload";
    }

    @PostMapping("/upload")
    public String saveFilePost(@RequestParam("multipart") MultipartFile file,
                               FileRequest fileRequest,
                               @AuthenticationPrincipal UserDetails userDetails) throws FileNotFoundException {
        fileRequest.setFile(file);
        fileService.save(fileRequest, userDetails.getUsername());
        return "redirect:/disk";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable Integer id) throws FileNotFoundException {
        AppFile appFile = appFileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("File Not Found"));

        String filePathFull = storageFolder.getPath()
                .concat(File.separatorChar + appFile.getUser().getUsername() + File.separatorChar + appFile.getFileName());
        Path filePath = Path.of(filePathFull);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(filePathFull));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filePath.getFileName() + "\"")
                .body(resource);
    }

    @GetMapping("/delete/{id}")
    public String deleteFile(@PathVariable Integer id) throws IOException {
        fileService.delete(id);
        return "redirect:/disk";
    }
}
