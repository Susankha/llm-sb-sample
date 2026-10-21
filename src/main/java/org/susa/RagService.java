package org.susa;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RagService {

  private final ChatClient client;

  public RagService(ChatClient.Builder chatClientBuilder, VectorStore vecStore) {
    String customTemplate = """
        You are a helpful assistant. Use the following context to answer the question.
        If the answer is not in the context, say "I do not know".
        CONTEXT:
        {question_answer_context}
        QUESTION:
        {query}
        """;
    log.info("============ RagService customTemplate " + customTemplate);
    this.client = chatClientBuilder.defaultSystem(
            "You are a expert Java developer. Answer the question clearly"
                + " and concisely,professional tone")
        .defaultAdvisors(QuestionAnswerAdvisor.builder(vecStore).promptTemplate(
            PromptTemplate.builder().template(customTemplate).build()).build())
        .build();
    log.info("============ RagService construct  ====");
  }

  public String ask(String question) {
    log.info("Question received : {}", question);
    String promptText = "The question is : {question}.Provide a structured answer with"
        + " bullet points and Java examples with that where applicable";
    PromptTemplate promptTemplate = new PromptTemplate(promptText);
    Prompt prompt = promptTemplate.create(Map.of("question", question));
    log.info("🧾 Prompt : {}", prompt.getContents());
    return client.prompt(prompt).call().content();
  }
}
