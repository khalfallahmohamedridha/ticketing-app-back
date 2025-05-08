package com.dksoft.tn.service;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
public class ImageService {

    private static final String IMAGE_DIRECTORY = "uploads/images";
    private static final int IMAGE_WIDTH = 800;
    private static final int IMAGE_HEIGHT = 600;

    public String saveResizedImage(MultipartFile imageFile, Long eventId) throws IOException {
        // 📂 Vérifier que le dossier existe, sinon le créer
        Path directoryPath = Path.of(IMAGE_DIRECTORY);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
            log.info("📁 Dossier créé : {}", directoryPath.toAbsolutePath());
        }

        // 📛 Nom sécurisé pour l’image
        String originalFileName = imageFile.getOriginalFilename();
        String extension = (originalFileName != null && originalFileName.contains(".")) ?
                originalFileName.substring(originalFileName.lastIndexOf('.') + 1) : "jpg";

        // 🔐 Forcer à jpg si non valide (sécurité simple)
        if (!extension.matches("(?i)jpg|jpeg|png")) {
            extension = "jpg";
        }

        String fileName = String.format("event_%s_%d.%s", eventId, System.currentTimeMillis(), extension);
        Path outputPath = directoryPath.resolve(fileName);

        // 🖼️ Redimensionner et enregistrer
        Thumbnails.of(imageFile.getInputStream())
                .size(IMAGE_WIDTH, IMAGE_HEIGHT)
                .outputFormat("jpg") // format final standardisé
                .toFile(outputPath.toFile());

        log.info("✅ Image enregistrée : {}", outputPath.toAbsolutePath());

        // 🔁 Retour du chemin relatif
        return IMAGE_DIRECTORY + "/" + fileName;
    }
}
