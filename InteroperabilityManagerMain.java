// Setup
InteroperabilityManager interop = new InteroperabilityManager();
interop.registerAdapter("java", new JavaEntityAdapter());
interop.registerAdapter("python", new PythonEntityAdapter());
// Register other adapters...

// Example usage
JavaEntity javaEntity = new JavaEntity();
javaEntity.setName("TestEntity");
javaEntity.setData(Map.of("key", "value"));

// Convert Java entity to Python
PythonEntity pythonEntity = interop.convertEntity(javaEntity, "java", "python");

// Create entity directly in target format
PythonEntity newPythonEntity = interop.createEntity(javaEntity, "java", "python");

// Get system metrics to monitor performance
Map<String, Object> metrics = interop.getSystemMetrics();
System.out.println("Error rate: " + metrics.get("errorRate"));