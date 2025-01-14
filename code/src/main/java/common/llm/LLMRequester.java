package common.llm;


import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class LLMRequester {

    private static final String apiKey = "xxxx";
    private static final String endpoint = "xxxxx";

    public LLMRequester() {
    }

    public static class LLMRequest {
        private String type = "0";
        private String msg;
        private String sessionId;
        private String modelType;

        public LLMRequest() {
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public String getModelType() {
            return modelType;
        }

        public void setModelType(String modelType) {
            this.modelType = modelType;
        }
    }


    public static class LLMResponse {
        private boolean success;
        private String code;
        private String msg;
        private Data data;


        public static class Data {
            private String id;
            private String modelName;
            private String sessionId;
            private String question;
            private String answer;
            private double time;

            public Data() {
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getModelName() {
                return modelName;
            }

            public void setModelName(String modelName) {
                this.modelName = modelName;
            }

            public String getSessionId() {
                return sessionId;
            }

            public void setSessionId(String sessionId) {
                this.sessionId = sessionId;
            }

            public String getQuestion() {
                return question;
            }

            public void setQuestion(String question) {
                this.question = question;
            }

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            public double getTime() {
                return time;
            }

            public void setTime(double time) {
                this.time = time;
            }
        }

        public LLMResponse() {
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }

    public enum ModelEnum {
        GPT_3_5_TURBO(0, "gpt-3.5-turbo"),
        GPT_4(2, "gpt-4"),
        BAI_CHUAN_2(6, "Baichuan2"),
        SPARK_V3(7, "Spark_v3"),
        GLM_4(9, "glm-4"),
        GLM_4V(10, "glm-4v"),
        GLM_3_TURBO(11, "glm-3-turbo"),
        LLAMA_2_7B_CHAT(16, "llama-2-7b-chat"),
        LLAMA_2_13B_CHAT(17, "llama-2-13b-chat"),
        LLAMA_2_70B_CHAT(18, "llama-2-70b-chat"),
        ;
        private Integer modelType;
        private String model;

        ModelEnum(Integer modelType, String model) {
            this.modelType = modelType;
            this.model = model;
        }

        public static ModelEnum getModelEnumByModel(String model) {
            if (StringUtils.isBlank(model)) {
                return ModelEnum.GPT_3_5_TURBO;
            }
            for (ModelEnum modelEnum : ModelEnum.values()) {
                if (modelEnum.getModel().equals(model)) {
                    return modelEnum;
                }
            }
            return ModelEnum.GPT_3_5_TURBO;
        }

        public static ModelEnum getModelEnumByModelType(Integer modelType) {
            if (modelType == null) {
                return ModelEnum.GPT_3_5_TURBO;
            }
            for (ModelEnum modelEnum : ModelEnum.values()) {
                if (modelEnum.getModelType().intValue() == modelType) {
                    return modelEnum;
                }
            }
            return ModelEnum.GPT_3_5_TURBO;
        }

        public Integer getModelType() {
            return modelType;
        }

        public void setModelType(Integer modelType) {
            this.modelType = modelType;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }
    }

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .retryOnConnectionFailure(false)
            .connectionPool(new ConnectionPool(20, 2000 * 1000, TimeUnit.SECONDS))
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(200, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .hostnameVerifier((hostname, session) -> true)
            .build();


    public static LLMResponse dealGPT(LLMRequest LLMRequest) {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(LLMRequest));
        Request request = new Request.Builder()
                .url(endpoint + "/api/chatGPT/chat")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String rsp = response.body().string();
                return JSON.parseObject(rsp, LLMResponse.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LLMResponse getChat(String id) {
        MediaType mediaType = MediaType.parse("application/json");
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(params));
        Request request = new Request.Builder()
                .url(endpoint + "/api/chatGPT/getChat")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String rsp = response.body().string();
                return JSON.parseObject(rsp, LLMResponse.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String[] chat(String sessionId, String msg, String modelType) {
        LLMRequest LLMRequest = new LLMRequest();
        LLMRequest.setMsg(msg);
        LLMRequest.setModelType(modelType);
        LLMRequest.setSessionId(sessionId);
        LLMResponse LLMResponse = LLMRequester.dealGPT(LLMRequest);
        return new String[]{LLMResponse == null ? "" : LLMResponse.getData().getSessionId(), LLMResponse.getData().getAnswer()};
    }
}
