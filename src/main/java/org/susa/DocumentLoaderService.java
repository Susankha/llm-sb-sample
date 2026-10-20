package org.susa;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DocumentLoaderService {

  private final PgVectorStore vStore;

  public DocumentLoaderService(PgVectorStore vStore) {
    log.info(" ===== DocumentLoaderService con =====");
    this.vStore = vStore;
  }

  @PostConstruct
  public void ingest() {
    log.info("============ DocumentLoaderService ingest ====");
    try {
      PagePdfDocumentReader reader =
          new PagePdfDocumentReader(new ClassPathResource("docs/notes.pdf"));

//      List<Document> reader = List.of(
//          new Document(
//              "Susa rocks!! Susa rocks rocks!! Susa rocks rocks!! Spring AI rocks!! Spring AI rocks!!",
//              Map.of("meta1", "meta1")),
//          new Document("The Susa rocks Big and Salvation Lurks Around the Corner"),
//          new Document(
//              "Susa rocks walk forward facing the past and you turn back toward the future.",
//              Map.of("meta2", "meta2")));

      List<Document> documents = reader.get();
      log.info("===== before vector store === "+vStore.getName());
      vStore.add(documents);
      log.info("===== Successfully added to vector store ===");

      log.info("📄 Ingested {} PDF pages into vector store", documents.size());
      log.info("===== Successfully loaded documents into vector store ===");
    } catch (Exception e) {
      log.warn("⚠️ No PDF ingested: {}", e.getMessage());
    }
  }

}
