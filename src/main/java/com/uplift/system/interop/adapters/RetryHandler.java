   public class RetryHandler {
       public static <T> T executeWithRetry(
           Supplier<T> operation, 
           int maxRetries, 
           long delayMillis
       ) throws InteropException {
           for (int attempt = 1; attempt <= maxRetries; attempt++) {
               try {
                   return operation.get();
               } catch (Exception e) {
                   if (attempt == maxRetries) {
                       throw new InteropException("Operation failed after " + maxRetries + " attempts", e);
                   }
                   try {
                       Thread.sleep(delayMillis);
                   } catch (InterruptedException ie) {
                       Thread.currentThread().interrupt();
                       throw new InteropException("Retry interrupted", ie);
                   }
               }
           }
           throw new InteropException("Unexpected retry failure");
       }
   }