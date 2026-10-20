package org.susa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RagService {

  private final ChatClient client;
  private final VectorStore vecStore;

  public RagService(ChatClient.Builder chatClientBuilder, VectorStore vecStore) {
    this.client = chatClientBuilder.build();
    this.vecStore = vecStore;
    log.info("============ RagService construct  ====");
  }

  public String ask(String question) {
    log.info("Question received : {}", question);
    String promptText = """
        Answer the question clearly and concisely.
        Use bullet points and Java examples where applicable.

        Context (may be empty):
        %s

        Question:
        %s
        """ + question;
    log.info("🧾 Prompt : {}", promptText);
    log.info("🧾 Prompt length: {}", promptText.length());
    return client.prompt(promptText).call().content();
  }
}
