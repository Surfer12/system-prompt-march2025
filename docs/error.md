<-------------> 0% EXECUTING [99ms]
> :compileJava
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/PostgresEntityAdapter.java:14: error: cannot find symbol
    private final ObjectMapper objectMapper = new ObjectMapper();
                  ^
  symbol:   class ObjectMapper
  location: class PostgresEntityAdapter
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/EnhancedInteroperabilityManager.java:21: error: EventBus has private access in InteroperabilityManager
    private final EventBus eventBus = new EventBus();
                  ^
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/EnhancedInteroperabilityManager.java:192: error: EventBus has private access in InteroperabilityManager
    public static class EnhancedEventBus extends EventBus {
                                                 ^
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/JavaEntityAdapter.java:7: error: cannot find symbol
public class JavaEntityAdapter implements LanguageAdapter<JavaEntity> {
                                                          ^
  symbol: class JavaEntity
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/JavaEntityAdapter.java:9: error: cannot find symbol
    public Map<String, Object> toNormalizedForm(JavaEntity entity) {
                                                ^
  symbol:   class JavaEntity
  location: class JavaEntityAdapter
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/JavaEntityAdapter.java:28: error: cannot find symbol
    public boolean validateEntity(JavaEntity entity) {
                                  ^
  symbol:   class JavaEntity
  location: class JavaEntityAdapter
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/InteroperabilityManagerMain.java:6: error: cannot find symbol
import com.uplift.system.interop.JavaEntity;
                                ^
  symbol:   class JavaEntity
  location: package com.uplift.system.interop
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/InteroperabilityManagerMain.java:7: error: cannot find symbol
import com.uplift.system.interop.PythonEntity;
                                ^
  symbol:   class PythonEntity
  location: package com.uplift.system.interop
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/PythonEntityAdapter.java:8: error: cannot find symbol
public class PythonEntityAdapter implements LanguageAdapter<PythonEntity> {
                                                            ^
  symbol: class PythonEntity
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/PythonEntityAdapter.java:10: error: cannot find symbol
    public Map<String, Object> toNormalizedForm(PythonEntity entity) {
                                                ^
  symbol:   class PythonEntity
  location: class PythonEntityAdapter
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/PythonEntityAdapter.java:29: error: cannot find symbol
    public boolean validateEntity(PythonEntity entity) {
                                  ^
  symbol:   class PythonEntity
  location: class PythonEntityAdapter
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/InteroperabilityManager.java:52: error: incompatible types: Object cannot be converted to T
        return targetAdapter.fromNormalizedForm(normalized);
                                               ^
  where T is a type-variable:
    T extends Object declared in method <T>convertEntity(Object,String,String)
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/PostgresEntityAdapter.java:14: error: cannot find symbol
    private final ObjectMapper objectMapper = new ObjectMapper();
                                                  ^
  symbol:   class ObjectMapper
  location: class PostgresEntityAdapter
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/EnhancedInteroperabilityManager.java:21: error: EventBus has private access in InteroperabilityManager
    private final EventBus eventBus = new EventBus();
                                          ^
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/EnhancedInteroperabilityManager.java:199: error: method does not override or implement a method from a supertype
        @Override
        ^
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/EnhancedInteroperabilityManager.java:201: error: non-static variable super cannot be referenced from a static context
            super.publish(eventType, eventData);
            ^
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/EnhancedInteroperabilityManager.java:201: error: cannot find symbol
            super.publish(eventType, eventData);
                 ^
  symbol: method publish(String,Map<String,Object>)
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/JavaEntityAdapter.java:20: error: cannot find symbol
        JavaEntity entity = new JavaEntity();
        ^
  symbol:   class JavaEntity
  location: class JavaEntityAdapter
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/JavaEntityAdapter.java:20: error: cannot find symbol
        JavaEntity entity = new JavaEntity();
                                ^
  symbol:   class JavaEntity
  location: class JavaEntityAdapter
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/InteroperabilityService.java:28: error: cannot find symbol
        interop.registerAdapter("go", new GoEntityAdapter());
                                          ^
  symbol:   class GoEntityAdapter
  location: class InteroperabilityService
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/InteroperabilityService.java:29: error: cannot find symbol
        interop.registerAdapter("swift", new SwiftEntityAdapter());
                                             ^
  symbol:   class SwiftEntityAdapter
  location: class InteroperabilityService
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/InteroperabilityService.java:30: error: cannot find symbol
        interop.registerAdapter("mojo", new MojoEntityAdapter());
                                            ^
  symbol:   class MojoEntityAdapter
  location: class InteroperabilityService
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/InteroperabilityService.java:76: error: cannot find symbol
        return interop.getAdapter(language).fromNormalizedForm(normalized);
                      ^
  symbol:   method getAdapter(String)
  location: variable interop of type EnhancedInteroperabilityManager
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/InteroperabilityService.java:87: error: cannot find symbol
        interop.getEventBus().subscribe("entity.conversion.error", event -> {
               ^
  symbol:   method getEventBus()
  location: variable interop of type EnhancedInteroperabilityManager
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/InteroperabilityService.java:93: error: cannot find symbol
        interop.getEventBus().subscribe("transaction.success", event -> {
               ^
  symbol:   method getEventBus()
  location: variable interop of type EnhancedInteroperabilityManager
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/InteroperabilityManagerMain.java:19: error: cannot find symbol
        JavaEntity javaEntity = new JavaEntity();
        ^
  symbol:   class JavaEntity
  location: class InteroperabilityManagerMain
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/InteroperabilityManagerMain.java:19: error: cannot find symbol
        JavaEntity javaEntity = new JavaEntity();
                                    ^
  symbol:   class JavaEntity
  location: class InteroperabilityManagerMain
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/InteroperabilityManagerMain.java:24: error: cannot find symbol
        PythonEntity pythonEntity = interop.convertEntity(javaEntity, "java", "python");
        ^
  symbol:   class PythonEntity
  location: class InteroperabilityManagerMain
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/InteroperabilityManagerMain.java:27: error: cannot find symbol
        PythonEntity newPythonEntity = interop.createEntity(javaEntity, "java", "python");
        ^
  symbol:   class PythonEntity
  location: class InteroperabilityManagerMain
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/PythonEntityAdapter.java:21: error: cannot find symbol
        PythonEntity entity = new PythonEntity();
        ^
  symbol:   class PythonEntity
  location: class PythonEntityAdapter
/Users/ryanoatespro/system-prompt-march2025/src/main/java/com/uplift/system/core/PythonEntityAdapter.java:21: error: cannot find symbol
        PythonEntity entity = new PythonEntity();
                                  ^
  symbol:   class PythonEntity
  location: class PythonEntityAdapter
Note: Some input files use unchecked or unsafe operations.
Note: Recompile with -Xlint:unchecked for details.
31 errors

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':compileJava'.
> Compilation failed; see the compiler error output for details.

* Try:
> Run with --info option to get more log output.
> Run with --scan to get full insights.

> Task :compileJava FAILED
1 actionable task: 1 executed
Could not execute build using connection to Gradle distribution 'https://services.gradle.org/distributions/gradle-8.8-bin.zip'.
 *  The terminal process failed to launch (exit code: 1). 
