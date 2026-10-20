package org.susa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RagController {

  private static final Logger log = LoggerFactory.getLogger(RagController.class);
  private final RagService ragService;

  public RagController(RagService ragService) {
    this.ragService = ragService;
  }

  @GetMapping("/ask")
  public String ask(@RequestParam String question) {
    log.info("===== RagController ask ====== ");
    return ragService.ask(question);
  }

}
