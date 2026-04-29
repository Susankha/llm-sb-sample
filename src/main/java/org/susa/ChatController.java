package org.susa;

import dev.langchain4j.model.ollama.OllamaChatModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

  private static final Logger log = LoggerFactory.getLogger(ChatController.class);
  private final AiAssistant aiAssistant;

  public ChatController(AiAssistant assistant) {
    this.aiAssistant = assistant;
  }

  @GetMapping("/chatbot")
  public String chat(@RequestParam String message) {
    log.info("===== ChatController ====== ");
    return aiAssistant.chat(message);
  }
}
