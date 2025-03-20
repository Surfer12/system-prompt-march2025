   @Data
   public class JavaEntityConfig {
       private String host;
       private int port;
       private Map<String, Object> additionalProperties;
       
       // Validation method
       public void validate() throws InteropException {
           if (StringUtils.isBlank(host)) {
               throw new InteropException("Host cannot be blank");
           }
           if (port <= 0) {
               throw new InteropException("Invalid port number");
           }
       }
   }