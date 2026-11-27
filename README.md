# LLM-SpringBoot-Rag
LLM with spring boot and RAG

1) Running the project using docker compose
    ```
    docker compose up -d --build
    ```
2) Running the project Manually

    Starting LLM
    ```
    ollama serve
    ```

    Starting DB
    ```
    docker run -d --name pgvector -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=pdf_vector_db -p 5432:5432 -v pgdata:/var/lib/postgresql/data pgvector/pgvector:pg17
    ```

    Running the jar
    ```
    java -jar target/LLM_sample-1.0-SNAPSHOT.jar
    ```

3) Sending a request

    ```
    curl -iv http://localhost:11434/api/generate -d '{"model": "gemma3:1b","prompt":"What is the capital of srilanka?","stream": false}'
    
   * Connected to localhost (127.0.0.1) port 11434
    > POST /api/generate HTTP/1.1
    > Host: localhost:11434
    > User-Agent: curl/8.5.0
    > Accept: */*
    > Content-Length: 82
    > Content-Type: application/x-www-form-urlencoded
    >


    < HTTP/1.1 200 OK
    HTTP/1.1 200 OK
    < Content-Type: application/json; charset=utf-8
    Content-Type: application/json; charset=utf-8
    < Date: Fri, 27 Nov 2026 09:45:58 GMT
    Date: Fri, 27 Nov 2026 09:45:58 GMT
    < Content-Length: 1119
    Content-Length: 1119

    <
    {"model":"gemma3:1b","created_at":"2026-11-27T09:45:58.165814833Z","response":"The capital of Sri Lanka is **Sri Jayawardenepura Kotte**.\n\nHowever, Sri Lanka is a Buddhist nation, and the country’s political capital is **Sri Jayawardenepura Kotte**, which serves as the seat of the President and the judicial branch.\n\nSo, while technically the capital is a specific location, the *official* capital is the seat of government.","done":true,"done_reason":"stop","context":[105,2364,107,3689,563,506,5279,529,503,16663,18966,236881,106,107,105,4368,107,818,5279,529,21315,30144,563,5213,67973,21493,1348,5623,838,2710,751,19156,84750,108,9675,236764,21315,30144,563,496,48180,7097,236764,532,506,2891,236858,236751,5255,5279,563,5213,67973,21493,1348,5623,838,2710,751,19156,125546,837,14736,618,506,11171,529,506,5376,532,506,24722,9911,236761,108,4324,236764,1651,35886,506,5279,563,496,3530,4563,236764,506,808,56488,236829,5279,563,506,11171,529,3251,236761],"total_duration":2921235334,"load_duration":293228615,"pro* Connection #0 to host localhost left intact
    mpt_eval_count":18,"prompt_eval_duration":159241588,"eval_count":81,"eval_duration":2404242608}
    ```