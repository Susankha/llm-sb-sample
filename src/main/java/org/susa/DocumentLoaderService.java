package org.susa;

import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DocumentLoaderService {

  private final PgVectorStore vStore;

  @Value("${app.ingest.enabled:false}")
  private boolean ingestEnabled;

  public DocumentLoaderService(PgVectorStore vStore) {
    log.info(" ===== DocumentLoaderService con =====");
    this.vStore = vStore;
  }

  @PostConstruct
  public void ingest() {
    if (!ingestEnabled) {
      return;
    }
    log.info("============ DocumentLoaderService ingest ====");
    try {
      PagePdfDocumentReader reader =
          new PagePdfDocumentReader(new ClassPathResource("docs/notes.pdf"));
      List<Document> documents = reader.get();
      log.info("===== before vector store === {}", vStore.getName());
      vStore.add(documents);
      log.info("===== Successfully added to vector store ===");

      log.info(" Ingested {} PDF pages into vector store", documents.size());
      log.info("===== Successfully loaded documents into vector store ===");
    } catch (Exception e) {
      log.warn(" No PDF ingested: {}", e.getMessage());
    }
  }

}
