   public class JavaEntityMetrics {
       private int totalConnections = 0;
       private int successfulOperations = 0;
       private int failedOperations = 0;
       
       public void recordConnection() { totalConnections++; }
       public void recordSuccessfulOperation() { successfulOperations++; }
       public void recordFailedOperation() { failedOperations++; }
       
       public Map<String, Integer> getMetrics() {
           return Map.of(
               "totalConnections", totalConnections,
               "successfulOperations", successfulOperations,
               "failedOperations", failedOperations
           );
       }
   }