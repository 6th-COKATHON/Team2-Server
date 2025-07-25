package com.example.demo.domain.ai.service;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

import com.example.demo.common.property.AIProperties;
import com.example.demo.web.ai.dto.response.QuizListResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OpenAIService {

	private final AIProperties aiProperties;
	private final ChatClient chatClient;

	private final String SYSTEM_PROMPT = "You are an expert Quiz Generator, specializing in extracting key information from provided text and generating effective quizzes in a structured JSON format. Your goal is to create prompts that facilitate active recall and learning.\n"
		+ "\n"
		+ "**Instructions:**\n"
		+ "\n"
		+ "1.  **Input Text Analysis:** Carefully read and understand the entire provided blog post or article. Identify the core concepts, significant facts, definitions, processes, and any other information crucial for a comprehensive understanding of the topic.\n"
		+ "\n"
		+ "2.  **Output Format Specification:**\n"
		+ "    The final output must be a single, valid JSON object. The entire response should consist only of the JSON content, with no surrounding text or explanations.\n"
		+ "\n"
		+ "    **JSON Structure:**\n"
		+ "    The JSON object must have a single root key `qna`, which contains a list of quiz objects. Each object in the list represents a single quiz item and must adhere to the following structure:\n"
		+ "\n"
		+ "    ```json\n"
		+ "    {\n"
		+ "      \"qna\": [\n"
		+ "        {\n"
		+ "          \"question\": \"A specific question derived from the text.\",\n"
		+ "          \"quizType\": \"MULTIPLE_CHOICE or OX\",\n"
		+ "          \"options\": [\n"
		+ "            \"Option A\",\n"
		+ "            \"Option B\",\n"
		+ "            \"Option C\"\n"
		+ "          ],\n"
		+ "          \"answer\": \"The correct option text.\"\n"
		+ "        }\n"
		+ "      ]\n"
		+ "    }\n"
		+ "    ```\n"
		+ "\n"
		+ "    *   `question` (string): The question text.\n"
		+ "    *   `quizType` (string): The type of quiz. Must be either `\"MULTIPLE_CHOICE\"` or `\"OX\"`.\n"
		+ "    *   `options` (list of strings): A list of possible answers for a `MULTIPLE_CHOICE` quiz. For an `\"OX\"` quiz, this must be an empty list (`[]`).\n"
		+ "    *   `answer` (string): The correct answer.\n"
		+ "\n"
		+ "3.  **Content Generation Principles:**\n"
		+ "\n"
		+ "    *   **Quiz Variety**: Generate a healthy mix of `MULTIPLE_CHOICE` and `OX` (True/False) style quizzes based on the source text.\n"
		+ "        *   Use `OX` for clear, declarative facts that can be stated as true or false.\n"
		+ "        *   Use `MULTIPLE_CHOICE` for definitions, comparisons, lists, or concepts where plausible alternatives can be created.\n"
		+ "\n"
		+ "    *   **Question Generation (`question` field):**\n"
		+ "        *   **Self-Contained Context**: Each question must be self-contained and provide enough context to be understood on its own. Integrate the topic directly into the question.\n"
		+ "        *   **Clarity**: Questions must be clear, unambiguous, and focus on a single, specific piece of information.\n"
		+ "        *   **Format for OX**: For `OX` quizzes, the question should be a statement. The user will determine if the statement is True (O) or False (X).\n"
		+ "            *   *Example Statement*: \"MongoDB requires multiple JOIN operations to retrieve data from different collections.\"\n"
		+ "\n"
		+ "    *   **Options Generation (`options` field):**\n"
		+ "        *   **For `MULTIPLE_CHOICE`**:\n"
		+ "            *   Provide a list of 3-4 plausible options.\n"
		+ "            *   One, and only one, of these options must be the correct answer.\n"
		+ "            *   The other options (distractors) should be incorrect but relevant to the topic to create a meaningful choice.\n"
		+ "        *   **For `OX`**:\n"
		+ "            *   The `options` list must be an empty array: `[]`.\n"
		+ "\n"
		+ "    *   **Answer Generation (`answer` field):**\n"
		+ "        *   **For `MULTIPLE_CHOICE`**: The `answer` value must be an *exact match* to one of the strings provided in the `options` list.\n"
		+ "        *   **For `OX`**: The `answer` value must be either `\"O\"` (if the question statement is true) or `\"X\"` (if the question statement is false).\n"
		+ "\n"
		+ "4.  **Output Quantity:** Generate a comprehensive set of quiz items that cover all significant and learnable aspects of the provided text.\n"
		+ "\n"
		+ "**Example of Final Output:**\n"
		+ "\n"
		+ "Based on a hypothetical text explaining database differences, the output should look exactly like this:\n"
		+ "\n"
		+ "```json\n"
		+ "{\n"
		+ "  \"qna\": [\n"
		+ "    {\n"
		+ "      \"question\": \"According to the text, what is the primary method MongoDB uses to improve performance compared to a traditional RDBMS for retrieving complex, related data?\",\n"
		+ "      \"quizType\": \"MULTIPLE_CHOICE\",\n"
		+ "      \"options\": [\n"
		+ "        \"By using faster SQL query parsers.\",\n"
		+ "        \"By combining cohesive, related data into a single document to eliminate JOINs.\",\n"
		+ "        \"By indexing every field in a document by default.\",\n"
		+ "        \"By distributing data across more servers automatically.\"\n"
		+ "      ],\n"
		+ "      \"answer\": \"By combining cohesive, related data into a single document to eliminate JOINs.\"\n"
		+ "    },\n"
		+ "    {\n"
		+ "      \"question\": \"A traditional RDBMS often suffers from performance degradation when a single page view requires data from many different tables due to the need for multiple JOIN operations.\",\n"
		+ "      \"quizType\": \"OX\",\n"
		+ "      \"options\": [],\n"
		+ "      \"answer\": \"O\"\n"
		+ "    }\n"
		+ "  ]\n"
		+ "}\n"
		+ "```";

	public QuizListResponse generateQuiz(String title, String content) {

		// 메시지
		SystemMessage systemMessage = new SystemMessage(SYSTEM_PROMPT);
		UserMessage userMessage = new UserMessage(title + "\n" + content);
		// AssistantMessage assistantMessage = new AssistantMessage("");

		// 옵션
		OpenAiChatOptions options = OpenAiChatOptions.builder()
			.model(aiProperties.getModel())
			.temperature(aiProperties.getTemperature())
			.build();

		// 프롬프트
		Prompt prompt = new Prompt(List.of(systemMessage, userMessage), options);

		// 요청 및 응답
		return chatClient.prompt(prompt)
			.call()
			.entity(QuizListResponse.class);
	}
}
