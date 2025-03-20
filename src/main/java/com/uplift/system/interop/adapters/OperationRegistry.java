   public class OperationRegistry {
       private static final Map<String, BiFunction<JavaEntity, Object[], Object>> operations = new HashMap<>();
       
       static {
           operations.put("getData", JavaEntity::retrieveData);
           operations.put("processData", JavaEntity::processData);
       }
       
       public static Object executeOperation(JavaEntity entity, String operation, Object... params) 
           throws InteropException {
           BiFunction<JavaEntity, Object[], Object> operationHandler = operations.get(operation);
           if (operationHandler == null) {
               throw new InteropException("Unsupported operation: " + operation);
           }
           return operationHandler.apply(entity, params);
       }
   }